package Monopoly;

import Monopoly.Squares.Jail;
import Monopoly.Squares.Square;

import javax.swing.*;

public class PlayersPanel {

    private Game game;
    private final JPanel playersPanel;
    private DefaultListModel<String> playersListModel;

    /**
     * @author Sabah
     * @param game
     * Shows the list of players on the top label
     * and the current player on the bottom label
     * Is initially empty
     */
    public PlayersPanel(Game game) {
        this.game = game;
        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));

        JLabel playerNamesLabel = new JLabel("List of Players: ");
        playersPanel.add(playerNamesLabel);

        // create empty list model and add to PlayerStatePanel
        this.playersListModel  = new DefaultListModel<>();
        JList <String> playerList = new JList<>(playersListModel);
        playerList.setFixedCellWidth(300);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playersPanel.add(new JScrollPane(playerList));
    }

    /**
     * @author Maisha
     * Update the player panel with the new player positions
     */
    public void updatePlayers(){
        this.playersListModel.clear();

        // (re)create list with the players in the game
        for (Player player : game.getPlayers()){
            Square currentSquare = game.getBoard().getSquares().get(player.getPosition() % game.getBoard().getSquares().size());
            String positionName = currentSquare.getName();
            if (player.isJailTurn() && (currentSquare instanceof Jail)){
                positionName = ((Jail)currentSquare).getOtherName();
            }
            if(player instanceof PlayerAI){
                this.playersListModel.addElement("AI Player: "+ player.getId() + "      Position: " + positionName);
            } else {
                this.playersListModel.addElement("     Player: "+ player.getId() + "      Position: " + positionName);
            }
        }
    }

    /**
     * @author Sabah
     * @return  playersPanel
     * getter for playersPanel
     */
    public JPanel getPlayersPanel() {return playersPanel;}

}
