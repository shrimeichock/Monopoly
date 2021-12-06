package Monopoly;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DicePanel {

    private JButton diceButton;
    //private JPanel dicePanel;
    private JLabel diceLabel1;
    private JLabel diceLabel2;
    private Dice dice1;
    private Dice dice2;

    /**
     * Crate 2 dice views and display on a panel
     * @author Sabah
     * @author Maisha
     * @author Thanuja
     * @param dice1     first dice
     * @param dice2     second dice
     */
    public DicePanel(Dice dice1, Dice dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;

        JPanel dicePanel = new JPanel();
        dicePanel.setLayout(new BoxLayout(dicePanel,BoxLayout.X_AXIS));
        diceLabel1 = new JLabel();
        diceLabel2 = new JLabel();
        diceLabel1.setHorizontalAlignment(JLabel.CENTER);
        diceLabel2.setHorizontalAlignment(JLabel.CENTER);
        diceLabel1.setVerticalAlignment(JLabel.CENTER);
        diceLabel2.setVerticalAlignment(JLabel.CENTER);

        Border border = new EmptyBorder(50,50, 50,50);

        diceLabel1.setBorder(border);
        diceLabel2.setBorder(border);

        Font diceFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
        diceLabel1.setFont(diceFont);
        diceLabel2.setFont(diceFont);

        dicePanel.add(diceLabel1);
        dicePanel.add(diceLabel2);
        dicePanel.setBackground(BoardColours.DICE.getColour());
        diceLabel1.setText(String.valueOf(this.dice1.getDiceNumber()));
        diceLabel2.setText(String.valueOf(dice2.getDiceNumber()));

        this.diceButton = new JButton();
        //diceButton.addActionListener(gameController); //add controller in GameView

        //dicePanel displays roll value on button
        this.diceButton.add(dicePanel);
        this.diceButton.setBorderPainted(true);

        this.diceButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BoardColours.BORDER.getColour(), 2),
                BorderFactory.createLineBorder(BoardColours.BOARD.getColour(), 100)));

    }
    /**
     * @author Sabah
     * Updates the dice labels on the board according to the roll
     */
    public void updateDiceLabel(){
        diceLabel1.setText(String.valueOf(dice1.getDiceNumber()));
        diceLabel2.setText(String.valueOf(dice2.getDiceNumber()));
    }

    /**
     * Getter for diceButton
     * @author Thanuja
     * @return      JButton, the dice button
     */
    public JButton getDiceButton() {
        return diceButton;
    }

}
