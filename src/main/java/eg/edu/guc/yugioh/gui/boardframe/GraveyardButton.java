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
	private JPanel painelPrincipalActive ;

	private JPanel painelPrincipalOpponent;


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
				if(painelPrincipalActive==null){
					painelPrincipalActive = criarPanel(quantidade);
				}else{
					painelPrincipalActive.removeAll();
					painelPrincipalActive.revalidate();
					painelPrincipalActive.repaint();
				}

				for (int i = 0; i < count; i++) {
					Card card = Card.getBoard().getActivePlayer().getField().getGraveyard().get(i);
					if (card instanceof MonsterCard) {
						MonsterButton cardMonster = new MonsterButton((MonsterCard) card);
						painelPrincipalActive.add(cardMonster.getName(), cardMonster);
					} else {
						SpellButton spellButton = new SpellButton((SpellCard) card);
						painelPrincipalActive.add(spellButton.getName(), spellButton);
					}

				}
				abrirPanel(painelPrincipalActive, janela);
			}

		} else {
			Integer count = Card.getBoard().getOpponentPlayer().getField().getGraveyard().size();
			if (count > 0) {
				Double quantidade = (double) (count / 5);
				JFrame janela = new JFrame();
				if(painelPrincipalOpponent==null) {
					painelPrincipalOpponent = criarPanel(quantidade);
				}else{
					painelPrincipalOpponent.removeAll();
					painelPrincipalOpponent.revalidate();
					painelPrincipalOpponent.repaint();
				}

				for (int i = 0; i < count; i++) {
					Card card = Card.getBoard().getOpponentPlayer().getField().getGraveyard().get(i);
					if (card instanceof MonsterCard) {
						MonsterButton cardMonster = new MonsterButton((MonsterCard) card);

						painelPrincipalOpponent.add(cardMonster.getName(), cardMonster);
					} else {
						SpellButton spellButton = new SpellButton((SpellCard) card);
						painelPrincipalOpponent.add(spellButton.getName(), spellButton);
					}

				}

				abrirPanel(painelPrincipalOpponent, janela);
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
