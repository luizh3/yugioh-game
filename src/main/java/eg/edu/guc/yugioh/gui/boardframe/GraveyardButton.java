package eg.edu.guc.yugioh.gui.boardframe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.configsGlobais.Logger;

@SuppressWarnings("serial")
public class GraveyardButton extends JButton implements ActionListener {
	private static ImageIcon graveyard = new ImageIcon("images/Graveyard.png");
	private boolean active ;

	public GraveyardButton(boolean active) {
		super(graveyard);
		this.active=active;
		addActionListener(this);
		setPreferredSize(new Dimension(CardButton.getDimension('W'),150));
	}


	public void updateGraveyard(){
		ArrayList<Card> graveyardList ;

		Logger.logs().info("GraveyardButton - updateGraveyard active: " + active);

		if(active)
			graveyardList = Card.getBoard().getActivePlayer().getField().getGraveyard();
		else graveyardList = Card.getBoard().getOpponentPlayer().getField().getGraveyard();
		if(graveyardList.size()>0){
			Card current = graveyardList.get(graveyardList.size()-1);

			boolean isInstanceMonster = current instanceof MonsterCard;

			Logger.logs().info("GraveyardButton - updateGraveyard isInstanceMonster: " + isInstanceMonster);

			if(isInstanceMonster){
				setIcon(new ImageIcon("images/"+current.getName()+".jpg"));
				setToolTipText(current.getName()+"\n ATK: "+((MonsterCard)current).getAttackPoints()+"\n DEF: "+((MonsterCard)current).getDefensePoints()+"\n Level: "+((MonsterCard)current).getLevel());
			}
			else{ setIcon(new ImageIcon("images/"+current.getName()+".png"));
				setToolTipText(current.getName());
			}
		}
		else setIcon(graveyard);
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if (active) {
			Integer count = Card.getBoard().getActivePlayer().getField().getGraveyard().size();
			if (count > 0) {
				Double quantidade = (double) (count / 5);
				JFrame janela = new JFrame();
				JPanel painelPrincipal = criarPanel(quantidade);
				for (int i = 0; i < count; i++) {
					Card card = Card.getBoard().getActivePlayer().getField().getGraveyard().get(i);
					if (card instanceof MonsterCard) {
						MonsterButton cardMonster = new MonsterButton((MonsterCard) card);
						painelPrincipal.add(cardMonster.getName(), cardMonster);
					} else {
						SpellButton spellButton = new SpellButton((SpellCard) card);
						painelPrincipal.add(spellButton.getName(), spellButton);
					}

				}
				abrirPanel(painelPrincipal, janela);
			}

		} else {
			Integer count = Card.getBoard().getOpponentPlayer().getField().getGraveyard().size();
			if (count > 0) {
				Double quantidade = (double) (count / 5);
				JFrame janela = new JFrame();

				JPanel painelPrincipal = criarPanel(quantidade);
				for (int i = 0; i < count; i++) {
					Card card = Card.getBoard().getOpponentPlayer().getField().getGraveyard().get(i);
					if (card instanceof MonsterCard) {
						MonsterButton cardMonster = new MonsterButton((MonsterCard) card);
						painelPrincipal.add(cardMonster.getName(), cardMonster);
					} else {
						SpellButton spellButton = new SpellButton((SpellCard) card);
						painelPrincipal.add(spellButton.getName(), spellButton);
					}

				}

				abrirPanel(painelPrincipal, janela);
			}
		}


	}

private JPanel criarPanel(Double quantidade){
	GridLayout gridLayout = new GridLayout((int) Math.ceil(quantidade), 5);
	return new JPanel(gridLayout);
}
 private void abrirPanel(JPanel painelPrincipal,JFrame janela){

	 painelPrincipal.setPreferredSize(new Dimension(475,165));
	 janela.getContentPane().add(painelPrincipal);
	 janela.pack();
	 janela.setLocationRelativeTo(null);
	 janela.setVisible(true);
 }

}
