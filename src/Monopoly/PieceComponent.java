package Monopoly;

import javax.swing.*;
import java.awt.*;

/**
 * Create a piece on board corresponding to a player
 * @author Shrimei
 */
class PieceComponent extends JLabel {

    private JPanel currentBox;

    /**
     * Initialize a label(piece) to represent a player. Player starts at "GO"
     * @author Shrimei
     * @param player Current player
     * @param startBox  "GO" panel
     */
    public PieceComponent(Player player, JPanel startBox){
        super("Player " + player.getId());
        this.setOpaque(true);
        if (player instanceof PlayerAI){
            this.setBackground(Color.pink);
        }else{
            this.setBackground(Color.cyan);
        }
        this.currentBox = startBox;
        startBox.add(this, BorderLayout.SOUTH);
    }

    /**
     * Move the piece to the specified box
     * @author Shrimei
     * @param newBox box that piece is being moved to
     */
    public void movePiece(JPanel newBox){
        currentBox.remove(this);
        newBox.add(this, BorderLayout.SOUTH);
        currentBox.repaint(); //repaint old box
        currentBox = newBox;
        currentBox.repaint(); //repaint new box
        //gameView.repaint();
    }

    /**
     * Remove piece when player is bankrupt
     * @author Shrimei
     */
    public void removePiece(){
        currentBox.remove(this);
        currentBox.repaint();
    }
}
