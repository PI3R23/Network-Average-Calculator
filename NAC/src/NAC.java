import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class NAC {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage : java NAC <port> <number>");
            System.exit(1);
        }
        int port = 0;
        int number = 0;
        try{
            port = Integer.parseInt(args[0]);
            number = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e){
            System.out.println("Error: argument must be an integer");
            System.exit(1);
        }

        if (isPortAvailable(port)){
            System.out.println("Starting in master mode...");
            System.out.println("Received: " +number);
            new Master(port,number);
        }
        else{
            System.out.println("Starting in slave mode...");
            new Slave(port,number);
        }

    }

    private static boolean isPortAvailable(int port) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
            return true;
        } catch (SocketException e) {
            return false;
        }
        finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
