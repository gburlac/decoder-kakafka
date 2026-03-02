import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProtoDecoder {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ProtoDecoder <path-to-binary-file | hex-bytes>");
            return;
        }

        try {
            byte[] message;
            if (args.length > 1) {
                message = parseHexString(String.join(" ", args));
            } else {
                Path inputPath = Path.of(args[0]);
                if (Files.exists(inputPath) && Files.isRegularFile(inputPath)) {
                    message = Files.readAllBytes(inputPath);
                } else {
                    message = parseHexString(args[0]);
                }
            }
            Map<Integer, Object> decoded = decodeMessage(message);
            System.out.println(decoded);
        } catch (IOException ex) {
            System.err.println("Failed to read file: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid hex input: " + ex.getMessage());
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

    public static byte[] parseHexString(String hexInput) {
        String trimmed = hexInput.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Hex input is empty.");
        }

        String[] tokens;
        if (trimmed.matches(".*\\s+.*")) {
            tokens = trimmed.split("\\s+");
        } else {
            if (trimmed.length() % 2 != 0) {
                throw new IllegalArgumentException("Hex string must have an even number of characters.");
            }
            int count = trimmed.length() / 2;
            tokens = new String[count];
            for (int i = 0; i < count; i++) {
                tokens[i] = trimmed.substring(i * 2, i * 2 + 2);
            }
        }

        byte[] bytes = new byte[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.startsWith("0x") || token.startsWith("0X")) {
                token = token.substring(2);
            }
            if (token.length() == 0 || token.length() > 2) {
                throw new IllegalArgumentException("Invalid hex byte: " + tokens[i]);
            }
            int value = Integer.parseInt(token, 16);
            if (value < 0 || value > 0xFF) {
                throw new IllegalArgumentException("Hex byte out of range: " + tokens[i]);
            }
            bytes[i] = (byte) value;
        }

        return bytes;
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
