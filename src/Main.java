import java.util.Map;
import java.util.TreeMap;

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
        Map<Integer, Object> ordered = new TreeMap<>(decoded);

        System.out.println("Decoded results: {");
        int index = 0;
        int total = ordered.size();
        for (Map.Entry<Integer, Object> entry : ordered.entrySet()) {
            String value = String.valueOf(entry.getValue()).replace("\\", "\\\\").replace("\"", "\\\"");
            String comma = (index < total - 1) ? "," : "";
            System.out.println("  \"" + entry.getKey() + "\": \"" + value + "\"" + comma);
            index++;
        }
        System.out.println("}");
    }
}
