package zadanie1;

public class CRC {
    private String divisor;
    protected CRC(String divisor) {
        this.divisor = divisor;
    }
    public String encode(String data) {
        StringBuilder frame = new StringBuilder();
        frame.append(data);
        int crcSize = divisor.length() - 1;
        for (int i = 0; i < crcSize; i++) {
            frame.append("0");
        }
        char[] ramka = frame.toString().toCharArray();
        for (int i = 0; i < data.length(); i++) {
            // jeœli kawa³ek zaczyna siê od 1
            if (ramka[i] == '1') {
                for (int j = 0; j < divisor.length(); j++) {
                    //ksoruj po wszystkich bitach
                    ramka[i + j] = XOR(ramka[i + j], divisor.charAt(j));
                }
            }
        }
        // zwracamy ostatnie X znaków (czyli sam kod CRC)
        return new String(ramka).substring(frame.length() - crcSize);
    }
    public String decode(String data) {
        return data.substring(0, data.length() - (divisor.length() - 1));
    }
    public boolean check(String data) {
        String decoded = decode(data);
        return data.equals(decoded + encode(decoded));
    }
    protected char XOR(char a, char b) {
        return a == b ? '0' : '1';
    }
}