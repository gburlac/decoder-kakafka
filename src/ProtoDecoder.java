import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProtoDecoder {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ProtoDecoder <path-to-binary-file>");
            return;
        }

        try {
            byte[] message = Files.readAllBytes(Path.of(args[0]));
            Map<Integer, Object> decoded = decodeMessage(message);
            System.out.println(decoded);
        } catch (IOException ex) {
            System.err.println("Failed to read file: " + ex.getMessage());
        }
    }

    public static Map<Integer, Object> decodeMessage(byte[] data) {
        Map<Integer, Object> result = new HashMap<>();
        int index = 0;

        while (index < data.length) {

            Varint keyVarint = decodeVarint(data, index);
            int key = (int) keyVarint.value;
            index = keyVarint.nextIndex;

            int fieldNumber = key >> 3;
            int wireType = key & 0x07;

            switch (wireType) {

                case 0: // varint
                    Varint valueVarint = decodeVarint(data, index);
                    result.put(fieldNumber, valueVarint.value);
                    index = valueVarint.nextIndex;
                    break;

                case 2: // length-delimited
                    Varint lengthVarint = decodeVarint(data, index);
                    int length = (int) lengthVarint.value;
                    index = lengthVarint.nextIndex;

                    byte[] strBytes = new byte[length];
                    System.arraycopy(data, index, strBytes, 0, length);

                    String value = new String(strBytes, StandardCharsets.UTF_8);
                    result.put(fieldNumber, value);

                    index += length;
                    break;

                default:
                    throw new RuntimeException("Unsupported wire type: " + wireType);
            }
        }

        return result;
    }

    private static Varint decodeVarint(byte[] data, int startIndex) {
        long result = 0;
        int shift = 0;
        int index = startIndex;

        while (true) {
            int b = data[index] & 0xFF; // convert signed byte to unsigned
            index++;

            result |= (long) (b & 0x7F) << shift;

            if ((b & 0x80) == 0) {
                break;
            }

            shift += 7;
        }

        return new Varint(result, index);
    }

    private static class Varint {
        long value;
        int nextIndex;

        Varint(long value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }
}
