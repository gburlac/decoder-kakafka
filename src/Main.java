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
            var1 = "08 02 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 38 39 18 b1 a8 03 22 19 0a 17 47 4f 4f 47 4c 45 5f 43 41 4d 50 41 49 47 4e 5f 4d 41";
        }

        byte[] message = ProtoDecoder.parseHexString(var1);

        Map<Integer, Object> decoded = ProtoDecoder.decodeMessage(message);
        System.out.println(ProtoDecoder.formatDecodedJson(decoded));
    }
}
