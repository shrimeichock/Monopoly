package Monopoly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveGameController implements ActionListener {

    private Game game;

    /**
     * Create new save game controller
     * @author Thanuja
     * @param game      the game model
     */
    public SaveGameController(Game game) {
        this.game = game;
    }

    /**
     * Handle when save is pressed
     * @author Thanuja
     * @param actionEvent   the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        game.saveGame();
    }
}
