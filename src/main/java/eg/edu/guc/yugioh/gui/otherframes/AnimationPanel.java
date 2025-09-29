package eg.edu.guc.yugioh.gui.otherframes;

import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.boardframe.AnimationGIF;

import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {
    boolean hasSwapped = false;

    public AnimationPanel(AnimationGIF animationGIF) {
        super();
        setSize(1367, 792);
        setVisible(false);

        JLabel animationLabel = new JLabel(animationGIF.toImageIcon());

        setLayout(new GridLayout(0, 1));
        add(animationLabel);

        validate();
    }

    public void AnimationAsk() {

        int align_x = GUI.getBoardFrame().getX();
        int align_y = GUI.getBoardFrame().getY();

        if(!hasSwapped) {
            setVisible(true);
            hasSwapped = true;
        }
        else {
            setVisible(false);
        }
    }

}