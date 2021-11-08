package Monopoly;

public class CardController {

    private final Game game;

    /**
     * Create card controller
     * @author Maisha
     * @param game  the game model
     */
    public CardController(Game game){
        this.game = game;
    }

    /**
     * @author Maisha
     * @return  game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Control purchase card
     * @author Thanuja
     * @return true if player is able to purchase property, else false.
     */
    public boolean purchaseCard(){
        return game.purchaseTransaction();
    }

    /**
     * Control rent payment
     * @author Thanuja
     * @return true if player is able to pay rent, else false.
     */
    public boolean payCardRent(){
        return game.rentTransaction();
    }

    /**
     * Control switch turn
     * @author Thanuja
     */
    public void handleSwitchTurn(){
        game.handleSwitchTurn();
    }

    /**
     * @author Thanuja
     * @return true if player is winner, else false
     */
    public boolean handlePotentialWinner(){
        return game.isWinner();
    }
}
