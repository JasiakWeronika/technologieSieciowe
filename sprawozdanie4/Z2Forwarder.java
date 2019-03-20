import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Z2Forwarder {
    private int destinationPort;
    private final int datagramSize = 50;
    private final int capacity = 1000;
    // PARAMETRY W MILISEKUNDACH
    private final int minDelay = 2000;
    private final int maxDelay = 10000;
    private final int sleepTime = 100;
    // NIEZAWODNOSC PRZEKAZYWANIA PAKIETU
    private final double reliability = 0.8;
    // PRAWDOPODOBIENSTWO ZDUPLIKOWANIA PAKIETU
    private final double duplicatePpb = 0.1;
    private InetAddress localHost;
    private DatagramSocket socket;
    private DatagramPacket[] buffer;
    private int[] delay;
    private Receiver receiver;
    private Sender sender;
    private Random random;

    public Z2Forwarder(int myPort, int destPort) throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        buffer = new DatagramPacket[capacity];
        delay = new int[capacity];
        random = new Random();
        receiver = new Receiver();
        sender = new Sender();
    }

    public void start() {
        sender.start();
        receiver.start();
    }

    class Receiver extends Thread {
    	
        private void addToBuffer(DatagramPacket packet) {
            if (random.nextDouble() > reliability) {
            	return; // UTRATA PAKIETU
            }
            int i;
            synchronized (buffer) {
                for (i = 0; i < capacity && buffer[i] != null; i++) ;
                if (i < capacity) {
                    delay[i] = minDelay + (int) (random.nextDouble() * (maxDelay - minDelay));
                    buffer[i] = packet;
                }
            }
        }
        
        public void run() {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[datagramSize], datagramSize);
                try {
                    socket.receive(packet);
                    addToBuffer(packet);
                    while (random.nextDouble() < duplicatePpb) {
                    	addToBuffer(packet);
                    }
                } catch (java.io.IOException e) {
                    System.out.println("Forwader.Receiver.run: " + e);
                }
            }
        }
    }

    class Sender extends Thread {
    	
        private void checkBuffer() throws java.io.IOException {
            synchronized (buffer) {
                int i;
                for (i = 0; i < capacity; i++) {
                    if (buffer[i] != null) {
                        delay[i] -= sleepTime;
                        if (delay[i] <= 0) {
                            buffer[i].setPort(destinationPort);
                            socket.send(buffer[i]);
                            buffer[i] = null;
                        }
                    }
                }
            }
        }

        public void run() {
            try {
                while (true) {
                    checkBuffer();
                    sleep(sleepTime);
                }
            } catch (Exception e) {
                System.out.println("Forwader.Sender.run: " + e);
            }
        }
    }
}