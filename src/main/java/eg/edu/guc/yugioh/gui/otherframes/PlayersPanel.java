package eg.edu.guc.yugioh.gui.otherframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel {
	private JLabel player;
	private JTextField nameField;

	public PlayersPanel(String s) {
		super(new BorderLayout());
		setSize(270, 20);
		setBackground(Color.BLACK);
		player = new JLabel(" " + s + " ");
		player.setFont(new Font("Calibri", Font.ITALIC, 18));
		player.setForeground(Color.WHITE);
		player.setMinimumSize(new Dimension());
		nameField = new JTextField(s);
		((AbstractDocument) nameField.getDocument()).setDocumentFilter(new DocumentFilter() {
			private static final int LIMIT = 14;

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if ((fb.getDocument().getLength() + string.length()) <= LIMIT) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if ((fb.getDocument().getLength() - length + text.length()) <= LIMIT) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});
		nameField.setPreferredSize(new Dimension(210, 25));
		this.add(player, BorderLayout.WEST);
		this.add(nameField, BorderLayout.CENTER);
	}

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}
}
