import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

public class DecoderUi {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DecoderUi::createAndShowUi);
    }

    private static void createAndShowUi() {
        JFrame frame = new JFrame("Proto Decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel inputLabel = new JLabel("Field 1 (hex message):");
        JTextArea inputArea = new JTextArea(4, 40);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setText("08 03 12 14 6d 61 72 6b 65 74 42 72 69 65 66 49 64 41 42 43 31 31 32 38 18 b1 a8 03");

        JLabel outputLabel = new JLabel("Field 2 (decoded message):");
        JTextArea outputArea = new JTextArea(6, 40);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);

        JScrollPane inputScroll = new JScrollPane(inputArea);
        JScrollPane outputScroll = new JScrollPane(outputArea);

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(inputLabel, gbc);

        gbc.gridy = 1;
        form.add(inputScroll, gbc);

        gbc.gridy = 2;
        form.add(outputLabel, gbc);

        gbc.gridy = 3;
        form.add(outputScroll, gbc);

        JButton decodeButton = new JButton("Decode message");
        decodeButton.addActionListener(event -> {
            String hexInput = inputArea.getText().trim();
            try {
                byte[] message = ProtoDecoder.parseHexString(hexInput);
                Map<Integer, Object> decoded = ProtoDecoder.decodeMessage(message);
                outputArea.setText(ProtoDecoder.formatDecodedJson(decoded));
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Invalid hex input: " + ex.getMessage());
            } catch (RuntimeException ex) {
                outputArea.setText("Decode failed: " + ex.getMessage());
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(decodeButton, BorderLayout.EAST);

        content.add(form, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(content);
        frame.setMinimumSize(new Dimension(600, 420));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

