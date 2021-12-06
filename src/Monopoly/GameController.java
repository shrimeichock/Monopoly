package Monopoly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {

    private Game game; // model

    /**
     * Create game controller
     * @author Maisha
     * @author Thanuja
     * @param game  the game model
     */
    public GameController(Game game){
        this.game = game;
    }

    /**
     * Handle roll button pressed
     * @author Maisha
     * @author Thanuja
     * @param e     action event (button pressed)
     */
    @Override
    public void actionPerformed(ActionEvent e) { // roll is pressed
        game.handleRoll();
    }

    /**
     * Control switch turn
     * @author Thanuja
     */
    public void handleSwitchTurn(){
        game.handleSwitchTurn();
    }


}
