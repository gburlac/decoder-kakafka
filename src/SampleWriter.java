import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SampleWriter {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java SampleWriter <output-binary-file>");
            return;
        }

        byte[] message = new byte[] {
            8, 1, 18, 21,
            100, 117, 109, 109, 121, 95, 109, 97,
            114, 107, 101, 116, 95, 98, 114, 105,
            101, 102, 95, 105, 100,
            24, (byte) -43, (byte) -18, 4
        };

        try {
            Files.write(Path.of(args[0]), message);
            System.out.println("Wrote " + message.length + " bytes to " + args[0]);
        } catch (IOException ex) {
            System.err.println("Failed to write file: " + ex.getMessage());
        }
    }
}

