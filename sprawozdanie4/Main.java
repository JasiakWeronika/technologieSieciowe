import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz rodzaj operacji:\n 1 - wysy³anie,\n 2 - odbieranie,\n 3 - spedycja,\n 4 - przyk³adowe u¿ycie.");
        int n = scanner.nextInt();
        System.out.println("Podaj nazwê pierwszego portu.");
        int port1 = scanner.nextInt();
        System.out.println("Podaj nazwê drugiego portu.");
        int port2 = scanner.nextInt();
        switch (n) {
            case 1:
                new Z2Sender(port1, port2).start();
                break;
            case 2:
                new Z2Receiver(port1, port2).start();
                break;
            case 3:
                new Z2Forwarder(port1, port2).start();
                break;
            case 4:
                new Z2Receiver(6002, 6003).start();
                new Z2Forwarder(6001, 6002).start();
                new Z2Forwarder(6003, 6000).start();
                new Z2Sender(6000, 6001).start();
                break;
        }
    }
}