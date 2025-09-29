package eg.edu.guc.yugioh.gui.boardframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.gui.GUI;

@SuppressWarnings("serial")
public class SpellButton extends CardButton implements ActionListener{	
	private SpellCard spell;

	public SpellButton() {
		super(spellIcon);
	}

	public SpellButton(SpellCard spell) {
		super(new ImageIcon("images/"+spell.getName()+".png"));
		this.spell = spell;
		String spellInfo = String.format("<html><div>%s<br>%s</div></html>", spell.getName(), spell.getDescription());
		setToolTipText(spellInfo);
		// Mantém tooltip
		// Atualiza painel lateral ao passar o mouse
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (GUI.getBoardFrame() != null && GUI.getBoardFrame().getCardInfoPanel() != null) {
					GUI.getBoardFrame().getCardInfoPanel().displayCardInfo(spell);
				}
			}
		});
		setVisible(true);
		addActionListener(this);
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Atualiza o painel lateral ao clicar
		if (GUI.getBoardFrame() != null && GUI.getBoardFrame().getCardInfoPanel() != null) {
			GUI.getBoardFrame().getCardInfoPanel().displayCardInfo(spell);
		}
		if(spell.getLocation()==Location.HAND)
			new HandOptionsFrame(false,spell);
		else 
		if(spell.getLocation()==Location.FIELD)
			spellInFieldAction();	
	}

	private void spellInFieldAction() {
		if((spell instanceof ChangeOfHeart || spell instanceof MagePower)){
			GUI.getBoardFrame().setSpellToActivate(spell);
			new ConfirmFrame("Please Choose a monster to Activate spell on");
		}else{
			try {
				Card.getBoard().getActivePlayer().activateSpell(spell, null);
			} catch (Exception e2) {
				GUI.errorFrame(e2);
			}
		}
		GUI.getBoardFrame().updateBoardFrame();
	}
}