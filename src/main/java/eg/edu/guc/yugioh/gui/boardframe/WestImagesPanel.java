package eg.edu.guc.yugioh.gui.boardframe;

import java.awt.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WestImagesPanel extends JPanel {

	private final CountPhase CountPhase;

	public WestImagesPanel() {
		setLayout(new BorderLayout());
		setOpaque(false);

		CountPhase = new CountPhase(1);

		add(CountPhase, BorderLayout.CENTER);

		setPreferredSize(CountPhase.getPreferredSize());
		validate();
	}

	public CountPhase getCurrentCountPhase() {
		return CountPhase;
	}
}
