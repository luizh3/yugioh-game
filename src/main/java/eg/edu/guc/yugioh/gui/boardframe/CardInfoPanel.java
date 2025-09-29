package eg.edu.guc.yugioh.gui.boardframe;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;

@SuppressWarnings("serial")
public class CardInfoPanel extends JPanel {

    private JLabel cardImageLabel;
    private JLabel cardNameLabel;
    private JLabel cardTypeLabel;
    private JLabel attackLabel;
    private JLabel defenseLabel;
    private JLabel levelLabel;
    private JTextArea descriptionArea;
    private JScrollPane descriptionScrollPane;

    public CardInfoPanel() {
        initializeComponents();
        setupLayout();
        clearCardInfo();
    }

    private void initializeComponents() {
        // Configurar o painel principal
        setPreferredSize(new Dimension(260, 700));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Informações da Carta",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY
        ));

        // Inicializar componentes
        cardImageLabel = new JLabel();
        cardImageLabel.setPreferredSize(new Dimension(150, 220));
        cardImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardImageLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        cardImageLabel.setBackground(Color.WHITE);
        cardImageLabel.setOpaque(true);

        cardNameLabel = new JLabel("Nome da Carta");
        cardNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cardNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cardTypeLabel = new JLabel("Tipo");
        cardTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        cardTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        attackLabel = new JLabel("ATK: -");
        attackLabel.setFont(new Font("Arial", Font.BOLD, 12));
        attackLabel.setForeground(new Color(200, 0, 0));

        defenseLabel = new JLabel("DEF: -");
        defenseLabel.setFont(new Font("Arial", Font.BOLD, 12));
        defenseLabel.setForeground(new Color(0, 0, 200));

        levelLabel = new JLabel("Nível: -");
        levelLabel.setFont(new Font("Arial", Font.BOLD, 12));
        levelLabel.setForeground(new Color(255, 165, 0));

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(250, 250, 250));
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 11));

        descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setBorder(BorderFactory.createTitledBorder("Descrição"));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Painel superior com imagem e nome
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(cardImageLabel, BorderLayout.CENTER);

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);
        namePanel.add(cardNameLabel, BorderLayout.NORTH);
        namePanel.add(cardTypeLabel, BorderLayout.CENTER);
        namePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        topPanel.add(namePanel, BorderLayout.SOUTH);

        // Painel central com estatísticas
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estatísticas"));
        statsPanel.add(attackLabel);
        statsPanel.add(defenseLabel);
        statsPanel.add(levelLabel);

        // Painel principal
        add(topPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(descriptionScrollPane, BorderLayout.SOUTH);
    }

    public void displayCardInfo(Card card) {
        if (card == null) {
            clearCardInfo();
            return;
        }

        // Nome da carta
        cardNameLabel.setText(card.getName());

        // Imagem da carta
        try {
            String imagePath = "images/" + card.getName() + ".jpg";
            ImageIcon originalIcon = new ImageIcon(imagePath);
            if (originalIcon.getIconWidth() <= 0 || originalIcon.getIconHeight() <= 0) {
                imagePath = "images/" + card.getName() + ".png";
                originalIcon = new ImageIcon(imagePath);
            }
            if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                Image img = originalIcon.getImage();
                Image scaledImg = img.getScaledInstance(140, 200, Image.SCALE_SMOOTH);
                cardImageLabel.setIcon(new ImageIcon(scaledImg));
                cardImageLabel.setText("");
            } else {
                cardImageLabel.setIcon(null);
                cardImageLabel.setText("<html><center>Imagem<br>não encontrada</center></html>");
            }
        } catch (Exception e) {
            cardImageLabel.setIcon(null);
            cardImageLabel.setText("<html><center>Erro ao<br>carregar imagem</center></html>");
            System.err.println("Erro ao carregar imagem da carta: " + e.getMessage());
        }

        if (card instanceof MonsterCard) {
            MonsterCard monster = (MonsterCard) card;
            cardTypeLabel.setText("Carta Monstro");
            attackLabel.setText("ATK: " + monster.getAttackPoints());
            defenseLabel.setText("DEF: " + monster.getDefensePoints());
            levelLabel.setText("Nível: " + monster.getLevel());
            descriptionArea.setText("Carta monstro com " + monster.getAttackPoints()
                    + " pontos de ataque e " + monster.getDefensePoints()
                    + " pontos de defesa. Nível " + monster.getLevel() + ".");
        } else if (card instanceof SpellCard) {
            SpellCard spell = (SpellCard) card;
            cardTypeLabel.setText("Carta de Magia");
            attackLabel.setText("ATK: -");
            defenseLabel.setText("DEF: -");
            levelLabel.setText("Nível: -");
            descriptionArea.setText(spell.getDescription());
        } else {
            cardTypeLabel.setText("Carta Desconhecida");
            attackLabel.setText("ATK: -");
            defenseLabel.setText("DEF: -");
            levelLabel.setText("Nível: -");
            descriptionArea.setText("Informações não disponíveis.");
        }

        // Atualizar a exibição
        revalidate();
        repaint();
    }

    public void clearCardInfo() {
        try {
            cardNameLabel.setText("Selecione uma carta");
            cardTypeLabel.setText("Clique em uma carta para ver detalhes");
            cardImageLabel.setIcon(null);
            cardImageLabel.setText("<html><center>Nenhuma carta<br>selecionada</center></html>");
            attackLabel.setText("ATK: -");
            defenseLabel.setText("DEF: -");
            levelLabel.setText("Nível: -");
            descriptionArea.setText("Clique em uma carta na mão, campo ou cemitério para ver suas informações detalhadas.");

            revalidate();
            repaint();
        } catch (Exception e) {
            System.err.println("Erro ao limpar informações da carta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}