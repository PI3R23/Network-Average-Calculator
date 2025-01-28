import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Slave {

    public Slave(int port, int number) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        byte[] buf = String.valueOf(number).getBytes();
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
        socket.send(datagramPacket);

    }
}
