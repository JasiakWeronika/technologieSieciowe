package zadanie1;

public class Decode {
    public static String decodeData(String data, CRC crcType) {
        StringBuilder toReturn = new StringBuilder();
        String flag = "01111110";
        String[] frames = data.split(flag);
        for (String frame: frames) {
            // pierwszy frame jest pusty
            if (frame.length() == 0) {
                continue;
            }
            // rozpychanie bitów
            frame = frame.replace("111110", "11111");
            if (!crcType.check(frame)) {
                System.out.println("Ramka " + frame + " jest nieprawid³owa");
                return "-1";
            }
            toReturn.append(crcType.decode(frame));
        }
        return toReturn.toString();
    }
}