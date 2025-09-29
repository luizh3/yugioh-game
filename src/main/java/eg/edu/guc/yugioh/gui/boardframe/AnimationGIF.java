package eg.edu.guc.yugioh.gui.boardframe;

import javax.swing.*;

public class AnimationGIF {

    private final String uri;

    public AnimationGIF(String uri) {
        this.uri = uri;
    }

    static AnimationGIF getSpellAnimation(){
        return new AnimationGIF("images/yugiAction.gif");
    }

    public ImageIcon toImageIcon() {
        return new ImageIcon(uri);
    }

    public String getUri() {
        return uri;
    }
}
