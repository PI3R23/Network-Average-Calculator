import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;

public class Master {
    private final int port;
    ArrayList<Integer> inputs;
    byte[] buf;
    DatagramPacket datagramPacket;

    public Master(int port, int number) throws IOException {
        this.port = port;
        inputs = new ArrayList<>();

        inputs.add(number);

        DatagramSocket socket = new DatagramSocket(port);

        new Thread(() -> {
            try {
                while (true) {
                    String s = getData(socket);

                    if (s.equals("-1")) {
                        System.out.println("Received -1, shutting down...");
                        sendBroadcastData(socket,"-1");
                        socket.close();
                        break;
                    }

                    else if (s.equals("0")) {
                        System.out.println("Average: " +(int)inputs.stream().mapToDouble(x->x).filter(x -> x!=0).average().getAsDouble());
                        sendBroadcastData(socket, String.valueOf((int)inputs.stream().mapToDouble(x->x).filter(x -> x!=0).average().getAsDouble()));
                    }
                    else
                    {
                        System.out.println("Received: " + s);
                        inputs.add(Integer.valueOf(s));
                    }

                }
            } catch (IOException e) {
                System.out.println("Error: cannot recieve data");
                System.exit(1);
            }
        }).start();
    }

    public String getData(DatagramSocket socket) throws IOException {
        buf = new byte[127];
        datagramPacket = new DatagramPacket(buf, buf.length);
        socket.receive(datagramPacket);
        return new String(buf).trim();
    }

    public void sendBroadcastData(DatagramSocket socket, String somethingToSend) throws IOException {

        byte[] buf = somethingToSend.getBytes();

        InetAddress localAddress = InetAddress.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localAddress);
        short mask =  networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength();

        StringBuilder binMask= new StringBuilder();
        for (int i = 0; i < mask; i++) {
            binMask.append("1");
        }

        for (int i = 0; i < 32 - mask; i++) {
            binMask.append("0");
        }

        for (int i = 8; i < binMask.length(); i += 9) {
            binMask.insert(i, ".");
        }

        String[] splitMask = binMask.toString().split("\\.");
        String[] splitAddress = localAddress.getHostAddress().split("\\.");
        StringBuilder broadCast = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            broadCast.append(Integer.parseInt(splitAddress[i])|(~Integer.parseInt(splitMask[i],2) & 255));
            broadCast.append(".");
        }
        broadCast.deleteCharAt(broadCast.length()-1);

        socket.setBroadcast(true);
        InetAddress address = InetAddress.getByName(broadCast.toString());
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
        socket.send(datagramPacket);
        socket.setBroadcast(false);
    }
}
