package zadanie1;

public class Check {
    static public String checkData(String data, CRC crcType) {
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
                return frame;
            }
        }
        return null;
    }
}