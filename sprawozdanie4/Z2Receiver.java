import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Z2Receiver {
    private int bufferSize = 1000;
    private final int datagramSize = 50;
    private InetAddress localHost;
    private int destinationPort;
    private DatagramSocket socket;
    private ReceiverThread receiver;
    private PrintThread printThread;
    private Z2Packet[] buffer = new Z2Packet[bufferSize];
    private int nextItem = 0;

    public Z2Receiver(int myPort, int destPort) throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        receiver = new ReceiverThread();
        printThread = new PrintThread();
    }

    public void start() {
        receiver.start();
        printThread.start();
    }

    class ReceiverThread extends Thread {
        public void run() {
            try {
                while (true) {
                    byte[] data = new byte[datagramSize];
                    DatagramPacket packet = new DatagramPacket(data, datagramSize);
                    socket.receive(packet);
                    Z2Packet p = new Z2Packet(packet.getData());
                    buffer[p.getIntAt(0)] = p;
                    // WYSLANIE POTWIERDZENIA
                    packet.setPort(destinationPort);
                    socket.send(packet);
                }
            } catch (Exception e) {
                System.out.println("Z2Receiver.ReceiverThread.run: " + e);
            }
        }
    }

    class PrintThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    if (buffer[nextItem] != null) {
                        System.out.println("Received: " + (char) buffer[nextItem].data[4]);
                        nextItem++;
                    } else {
                        Thread.sleep(50);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}