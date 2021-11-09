package Monopoly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameView implements MonopolyInterfaceView {
    private BoardPanel boardPanel;
    private PlayersPanel playersPanel;
    private PlayerStatePanel playerStatePanel;
    private Game game;
    private GameController gameController;

    /**
     * Create an overall game view
     * @author Maisha
     */
    public GameView(){
        JFrame frame = new JFrame("Monopoly Game");
        this.game = new Game();

        this.game.addView(this);

        frame.setPreferredSize(new Dimension(950, 590));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {  //defining a class inside another class
            public void windowOpened(WindowEvent e) {
                handleBoardPlayersUpdate();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        boardPanel = new BoardPanel(game);
        JPanel boardPanel = this.boardPanel.getMainPanel();

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        playersPanel = new PlayersPanel(game);
        playerStatePanel = new PlayerStatePanel(game.getPlayers().get(game.getCurrentPlayerNumber()));

        JPanel playerPanel1 = playersPanel.getPlayersPanel();
        JPanel playerPanel2 = playerStatePanel;

        playerPanel.add(playerPanel1);
        playerPanel.add(playerPanel2);

        mainPanel.add(boardPanel, BorderLayout.WEST);
        mainPanel.add(playerPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);

        gameController = new GameController(game);
    }

    /**
     * Gets the initial number of players
     * @author Maisha
     * @return Integer  number of players
     */
    public Integer handleNumberOfPlayers(){
        Integer[] options = {2,3,4};
        Integer input = (Integer) JOptionPane.showInputDialog(null,"How many players do you wish to have?","PLAYERS",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (input == null) {
            System.exit(0);
        }
        return input;
    }

    /**
     * Called when game is started, add selected number of players and display on panel
     * @author Maisha
     * @author Thanuja
     */
    @Override
    public void handleBoardPlayersUpdate() {
        int num = this.handleNumberOfPlayers();
        //System.out.println(num);
        for (int i = 0; i < num - 2; i++) {
            this.game.addPlayer(new Player());
        }
        this.playersPanel.updatePlayers();
    }

    /**
     * Handles dice roll, updates dice view, display card of property that was landed on
     * @author Thanuja
     */
    @Override
    public void handleRoll() {
        this.boardPanel.getDicePanel().updateDiceLabel();

        // FIXME should package as an event
        Player currentPlayer = game.getCurrentPlayer();
        Square currentSquare = game.getCurrentSquare();

        if(currentSquare.getType().equals("Monopoly.Property")) {
            CardFrame card = new CardFrame((Property) currentSquare, currentPlayer, game);
            // do not switch turn until card is handled property
        }else{
            gameController.handleSwitchTurn();
        }
    }

    /**
     * Update player state (money, position, properties owned)
     * @author Thanuja
     */
    @Override
    public void handlePlayerState() {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayerNumber());
        this.playerStatePanel.updatePlayer(currentPlayer);
        this.playersPanel.updatePlayers();
    }

    /**
     * Call main method to run the game
     * @author Maisha
     * @param args      arguments
     */
    public static void main(String[] args) {
        GameView gameView = new GameView();
    }

}
