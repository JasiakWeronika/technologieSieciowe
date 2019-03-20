package zadanie1;

import java.io.File;
import java.util.Scanner;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        CRC crc = new CRC("1011");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz rodzaj operacji:\n 1 - kodowanie,\n 2 - dekodowanie,\n 3 - sprawdzanie poprawno�ci.");
        int n = scanner.nextInt();
        System.out.println("Podaj nazw� pliku, kt�rego dane maj� zosta� zmodyfikowane.");
        String fileName = scanner.next();
        String data = Files.toString(new File("src/testy/" + fileName + ".txt"), Charsets.UTF_8);
        switch (n) {
            case 1:
            	System.out.println("Podaj nazw� pliku, w kt�rym ma zosta� zapisany wynik.");
                String file1 = scanner.next();
                scanner.close();
                String encode = Encode.encodeData(data, 4, crc);
                Files.write(encode, new File("src/testy/" + file1 + ".txt"), Charsets.UTF_8);
                break;
            case 2:
            	System.out.println("Podaj nazw� pliku, w kt�rym ma zosta� zapisany wynik.");
                String file2 = scanner.next();
                scanner.close();
                String decode = Decode.decodeData(data, crc);
                Files.write(decode, new File("src/testy/" + file2 + ".txt"), Charsets.UTF_8);
                break;
            case 3:
                String frame = Check.checkData(data, crc);
                if (frame != null) {
                    System.out.println("Ramka " + frame + " jest nieprawid�owa.");
                } else {
                    System.out.println("Ramka jest prawid�owa.");
                }
                break;
        }
    }
}