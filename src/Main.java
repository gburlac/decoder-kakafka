import java.util.Map;

public class Main {
    public static void main(String[] args) {
        byte[] message = new byte[] {

            8, 1, 18, 21, 100, 117, 109, 109, 121, 95, 109, 97, 114, 107, 101, 116, 95, 98, 114, 105, 101, 102, 95, 105, 100, 24, -43, -18, 4

        };

        Map<Integer, Object> decoded = ProtoDecoder.decodeMessage(message);
        System.out.println("Decode byte: " + decoded);
    }
}
