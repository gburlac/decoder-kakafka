import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String hexInput;
        if (args.length > 0) {
            hexInput = String.join(" ", args);
        } else {
            hexInput = "08 03 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 32 38 18 b1 a8 03";
        }

        byte[] message = ProtoDecoder.parseHexString(hexInput);

        Map<Integer, Object> decoded = ProtoDecoder.decodeMessage(message);
        System.out.println("Decode byte: " + decoded);
    }
}
