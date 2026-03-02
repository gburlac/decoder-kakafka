//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] var0) {
        String var1;
        if (var0.length > 0) {
            var1 = String.join(" ", var0);
        } else {
            var1 = "08 03 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 32 38 18 b1 a8 03";
        }

        byte[] message = ProtoDecoder.parseHexString(var1);

        Map<Integer, Object> decoded = ProtoDecoder.decodeMessage(message);
        Map<Integer, Object> ordered = new TreeMap<>(decoded);
        System.out.println("Decode results: {");
        int index = 0;
        int total = ordered.size();
        for (Map.Entry<Integer, Object> entry : ordered.entrySet()) {
            String keyLabel;
            switch (entry.getKey()) {
                case 1:
                    keyLabel = "eventType";
                    break;
                case 2:
                    keyLabel = "marketBriefId";
                    break;
                case 3:
                    keyLabel = "globalClientID";
                    break;
                default:
                    keyLabel = String.valueOf(entry.getKey());
                    break;
            }
            String value = String.valueOf(entry.getValue()).replace("\\", "\\\\").replace("\"", "\\\"");
            String comma = (index < total - 1) ? "," : "";
            System.out.println("  \"" + keyLabel + "\": \"" + value + "\"" + comma);
            index++;
        }
        System.out.println("}");
    }
}
