import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

class Z2Sender {
    private final int datagramSize = 50;
    private final int sleepTime = 500;
    private final int maxPacket = 50;
    private InetAddress localHost;
    private int destinationPort;
    private DatagramSocket socket;
    private SenderThread sender;
    private ReceiverThread receiver;
    private RetransmitThread retransmit;
    final ArrayList<Z2Packet> packetsBuffer = new ArrayList<>();
    BufferedReader in = new BufferedReader(new FileReader("src/testy/plik.txt"));;

    public Z2Sender(int myPort, int destPort) throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        sender = new SenderThread();
        receiver = new ReceiverThread();
        retransmit = new RetransmitThread();
    }

    public void start() {
        receiver.start();
        retransmit.start();
        sender.start();
    }

    public void sendPacket(Z2Packet p) throws IOException {
        DatagramPacket packet = new DatagramPacket(p.data, p.data.length, localHost, destinationPort);
        socket.send(packet);
    }

    class SenderThread extends Thread {
        @Override
        public void run() {
            int i, x;
            try {
                for (i = 0; (x = in.read()) >= 0; i++) {
                    Z2Packet p = new Z2Packet(4 + 1);
                    p.setIntAt(i, 0);
                    p.data[4] = (byte) x;
                    p.received = false;
                    synchronized (packetsBuffer) {
                        packetsBuffer.add(p);
                    }
                    sendPacket(p);
                    System.out.println("Send: " + (char) p.data[4]);
                    sleep(sleepTime);
                }
            } catch (Exception e) {
                System.out.println("Z2Sender.SenderThread.run: " + e);
            }
        }
    }
    
    class ReceiverThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    byte[] data = new byte[datagramSize];
                    DatagramPacket packet = new DatagramPacket(data, datagramSize);
                    socket.receive(packet);
                    Z2Packet p = new Z2Packet(packet.getData());
                    p.received = true;
                    synchronized (packetsBuffer) {
                        packetsBuffer.set(p.getIntAt(0), p);
                    }
                }
            } catch (Exception e) {
                System.out.println("Z2Sender.ReceiverThread.run: " + e);
            }
        }
    }

    class RetransmitThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    synchronized (packetsBuffer) {
                        for (Z2Packet packet : packetsBuffer) {
                            if (!packet.received) {
                            	System.out.println("Retransmit: " + (char) packet.data[4]);
                                sendPacket(packet);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}