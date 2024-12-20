package eg.edu.guc.yugioh.gui.boardframe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.configsGlobais.Logger;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.otherframes.AnimationFrame;

@SuppressWarnings("serial")
public class HandOptionsFrame extends JFrame implements ActionListener {

    private JButton leftButton = new JButton("Summon Monster");
    private JButton rightButton = new JButton("Set Monster");
    private JButton cancelButton = new JButton("Cancel");
    private SpellCard spell;
    private MonsterCard monster;
    private JTextArea cardName = new JTextArea();
    private JTextArea cardDescription = new JTextArea();

    public HandOptionsFrame(boolean isMonsterOptions, Card card) {
        super("Choose Action");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (!isMonsterOptions) {
            spell = (SpellCard) card;
            leftButton.setText("Activate Spell");
            rightButton.setText("Set Spell");
        } else {
            monster = (MonsterCard) card;
        }

        configureTextArea(cardName, card.getName(), true);
        configureTextArea(cardDescription, card.getDescription(), false);

        constructFrame();
    }

    private void configureTextArea(JTextArea textArea, String text, boolean isTitle) {
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(text);
        textArea.setFont(textArea.getFont().deriveFont(isTitle ? Font.BOLD : Font.PLAIN, isTitle ? 16f : 14f));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void constructFrame() {
        setLayout(new GridBagLayout());
        setSize(600, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        add(cardName, c);

        c.gridy = 1;
        add(cardDescription, c);

        c.gridwidth = 1;
        c.gridy = 2;

        c.gridx = 0;
        add(leftButton, c);

        c.gridx = 1;
        add(rightButton, c);

        c.gridx = 2;
        add(cancelButton, c);

        styleButtons();

        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setVisible(true);
    }
    


    private void styleButtons() {
        JButton[] buttons = {leftButton, rightButton, cancelButton};
        for (JButton button : buttons) {
            button.setFocusPainted(false);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        Logger.logs().info("HandOptionsFrame - actionPerformed: " + action);

        try {
            switch (action) {
                case "Cancel":
                    GUI.getBoardFrame().resetHandlers();
                    break;

                case "Activate Spell":
                    activateSpellCard();
                    break;

                case "Set Spell":
                    handleSetSpell();
                    break;

                case "Summon Monster":
                case "Set Monster":
                    handleMonsterOptions(action);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown action: " + action);
            }
        } catch (Exception ex) {
            Logger.logs().error("HandOptionsFrame - actionPerformed Exception: " + ex.getMessage());
            GUI.errorFrame(ex);
        } finally {
            GUI.getBoardFrame().updateBoardFrame();
            dispose();
        }
    }

    private void activateSpellCard() throws Exception {
        if (spell instanceof ChangeOfHeart || spell instanceof MagePower) {
            new ConfirmFrame("Please click a monster to activate on");
            GUI.getBoardFrame().setSpellToActivate(spell);
            GUI.getBoardFrame().setMonsterToSummon(null);
        } else {
            Card.getBoard().getActivePlayer().activateSpell(spell, null);
            GUI.getBoardFrame().setSpellToActivate(null);
            GUI.getBoardFrame().setMonsterToSummon(null);
        }
        new AnimationFrame().AnimationAsk();
    }

    private void handleSetSpell() throws Exception {
        Card.getBoard().getActivePlayer().setSpell(spell);
        GUI.getBoardFrame().setMonsterToSummon(null);
        GUI.getBoardFrame().setSpellToActivate(null);
    }

    private void handleMonsterOptions(String action) throws Exception {
        boolean isSummon = action.equals("Summon Monster");
        if (monster.getLevel() < 5) {
            if (isSummon) {
                Card.getBoard().getActivePlayer().summonMonster(monster);
            } else {
                Card.getBoard().getActivePlayer().setMonster(monster);
            }
            GUI.getBoardFrame().setMonsterToSummon(null);
        } else {
            ritualSummon(isSummon);
        }
        GUI.getBoardFrame().setSpellToActivate(null);
    }

    private void ritualSummon(boolean isAttackMode) throws Exception {
        int sacrificesNeeded = monster.getLevel() < 7 ? 1 : 2;
        GUI.getBoardFrame().setSacrificesCount(sacrificesNeeded);

        if (Card.getBoard().getActivePlayer().getField().getMonstersArea().size() >= sacrificesNeeded) {
            new ConfirmFrame("Please click " + sacrificesNeeded + " monster(s) to sacrifice");
            GUI.getBoardFrame().setMonsterToSummon(monster);
            GUI.getBoardFrame().setSacrificeAttack(isAttackMode);
        } else {
            throw new Exception("You don't have enough sacrifices.");
        }
    }
}
