//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Map;

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
        System.out.println(ProtoDecoder.formatDecodedJson(decoded));
    }
}
