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

        byte[] var2 = ProtoDecoder.parseHexString(var1);
        Map var3 = ProtoDecoder.decodeMessage(var2);
        TreeMap var4 = new TreeMap(var3);
        System.out.println("Decode byte: {");
        int var5 = 0;
        int var6 = var4.size();

        for(Map.Entry var8 : var4.entrySet()) {
            String var9 = String.valueOf(var8.getValue()).replace("\\", "\\\\").replace("\"", "\\\"");
            String var10 = var5 < var6 - 1 ? "," : "";
            System.out.println("  \"" + String.valueOf(var8.getKey()) + "\": \"" + var9 + "\"" + var10);
            ++var5;
        }

        System.out.println("}");
    }
}
