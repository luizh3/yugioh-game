package eg.edu.guc.yugioh.gui.otherframes;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ErrorFrame extends JFrame {

    public ErrorFrame(String error) {
        super("Error");
        setSize(700, 220);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Centralizar a janela na tela
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Configurar layout principal
        getContentPane().setLayout(new BorderLayout(10, 10));
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);

        // Adicionar imagem de erro (X)
        JLabel xLabel = createImageLabel("images/Icon_Simple_Error.png");
        if (xLabel != null) {
            xLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(xLabel, BorderLayout.WEST);
        }

        // Adicionar mensagem de erro
        JLabel errorMsg = new JLabel(
                "<html><p style='text-align: center;'>" + formatErrorMessage(error) + "</p></html>");
        errorMsg.setFont(new Font("Arial", Font.BOLD, 16));
        errorMsg.setForeground(Color.RED);
        errorMsg.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(errorMsg, BorderLayout.CENTER);

        // Adicionar imagem do Kaiba
        JLabel kaibaLabel = createImageLabel("images/kaiba.png");
        if (kaibaLabel != null) {
            kaibaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(kaibaLabel, BorderLayout.EAST);
        }

        setVisible(true);
    }

    /**
     * Helper para criar JLabel com imagem carregada de arquivo.
     */
    private JLabel createImageLabel(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            return null;
        }
    }

    /**
     * Formata a mensagem de erro com quebras de linha se necessário.
     */
    private String formatErrorMessage(String message) {
        final int maxLineLength = 50;
        StringBuilder formatted = new StringBuilder();
        int lineLength = 0;

        for (String word : message.split(" ")) {
            if (lineLength + word.length() > maxLineLength) {
                formatted.append("<br>");
                lineLength = 0;
            }
            formatted.append(word).append(" ");
            lineLength += word.length() + 1;
        }

        return formatted.toString().trim();
    }
}
