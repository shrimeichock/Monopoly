package MonopolyTest;

import Monopoly.ColourGroups;
import Monopoly.Game;
import Monopoly.PlayerAI;
import Monopoly.Squares.OwnableSquare;
import Monopoly.Squares.Property;
import Monopoly.Player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    /**
     * Setup the Monopoly.Game Test
     * @author Thanuja
     */
    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        this.game = new Game();
        this.game.setBoardVersion("Standard.xml");
    }

    /**
     * Test that initial number of players is 0
     * @author Thanuja
     */
    @Test
    public void getInitialNumberOfPlayers() {
        assertEquals(0, game.getPlayers().size());
    }


    /**
     * Test initial current player
     * @author Thanuja
     */
    @Test
    public void getInitialCurrentPlayer() {
        assertEquals(0, game.getCurrentPlayerNumber()); // initial current player is 0
    }

    /**
     * Test that player is added to game, with the next lowest ID
     * @author Thanuja
     */
    @Test
    public void testAddPlayer() {
        Player testPlayer = new Player();
        game.addPlayer(testPlayer);

        assertEquals(1, game.getPlayers().size()); // the size is 1 players
        assertEquals(testPlayer, game.getPlayers().get(0)); // new player added in the last position of the array
        assertEquals(0, testPlayer.getId()); // new player has ID of 0
    }

    /**
     * Test removing the first player (position 0 in the players array)
     * @author Thanuja
     */
    @Test
    public void testRemovePlayer() {
        Player testPlayer = new Player();
        game.addPlayer(testPlayer);
        game.addPlayer(new Player());
        game.currentPlayerBankrupt(); // remove first player

        assertEquals(1, game.getPlayers().size()); // the size is 1 player
        assertEquals(1, game.getPlayers().get(0).getId()); // player 1 now in position 0
    }

    /**
     * Test a successful property purchase
     * @author Thanuja
     */
    @Test
    public void testPurchaseTransactionSuccess() {
        game.addPlayer(new Player());

        Player testBuyer = game.getPlayers().get(0);

        Property testProperty = (Property) game.getBoard().getSquares().get(1);
        testBuyer.changePosition(1); // tested in MonopolyTest.PlayerTest // have player 0 land on Mediterranean (hard coded in Monopoly.Board)
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayerNumber()).getPosition()); // verify current player is now on test property (to be able to purchase it)

        boolean purchased = game.purchaseTransaction();
        assertTrue(purchased);

        Assert.assertEquals(1440, testBuyer.getMoney()); // 1500 - 60
        Assert.assertEquals(testBuyer, testProperty.getOwner()); // test that current player now owns current property
    }

    /**
     * Test a successful rent payment
     * @author Thanuja
     */
    @Test
    public void testRentTransactionSuccess() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        Property testProperty = (Property) game.getBoard().getSquares().get(1);
        Player testBuyer = game.getPlayers().get(1);
        testBuyer.purchaseSquare(testProperty); // tested in MonopolyTest.PlayerTest
        testProperty.setOwner(testBuyer);

        Player testRenter = game.getPlayers().get(0);

        testRenter.changePosition(1); // tested in MonopolyTest.PlayerTest // have player 1 land on Mediterranean (hard coded in Monopoly.Board)
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayerNumber()).getPosition()); // verify current player is now on test property (to pay rent)

        boolean canPayRent = game.rentTransaction();
        assertTrue(canPayRent);

        Assert.assertEquals(1494, testRenter.getMoney()); // 1500 - 6
        Assert.assertEquals(1446, testProperty.getOwner().getMoney()); // 1440 + 60
    }

    /**
     * Test an unsuccessful property purchase
     * @author Thanuja
     */
    @Test
    public void testPurchaseTransactionFailure(){
        game.addPlayer(new Player());

        Player testBuyer = game.getPlayers().get(0);

        // decrease testBuyer money to $50
        Property testExpensiveProperty = new Property("Test Property", 1450, ColourGroups.GREEN);
        testBuyer.purchaseSquare(testExpensiveProperty); // tested in MonopolyTest.PlayerTest

        Property testProperty = (Property) game.getBoard().getSquares().get(1);
        testBuyer.changePosition(1); // tested in MonopolyTest.PlayerTest // have player 0 land on Mediterranean (hard coded in Monopoly.Board)
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayerNumber()).getPosition()); // verify current player is now on test property (to be able to purchase it)

        boolean purchased = game.purchaseTransaction();
        assertFalse(purchased);

        Assert.assertEquals(50, testBuyer.getMoney()); // no change for buyer
        assertNull(testProperty.getOwner()); // test that property does not have owner
    }

    /**
     * Test an unsuccessful rent payment (and bankruptcy)
     * @author Thanuja
     */
    @Test
    public void testRentTransactionFailure() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        Property testProperty = (Property) game.getBoard().getSquares().get(1);
        Player testBuyer = game.getPlayers().get(1);
        testBuyer.purchaseSquare(testProperty); // tested in MonopolyTest.PlayerTest
        testProperty.setOwner(testBuyer);

        Player testRenter = game.getPlayers().get(0);

        // decrease testRenter money to $5
        Property testExpensiveProperty = new Property("Test Monopoly.Squares.Property", 1495, ColourGroups.GREEN);
        testRenter.purchaseSquare(testExpensiveProperty); // tested in MonopolyTest.PlayerTest

        testRenter.changePosition(1); // tested in MonopolyTest.PlayerTest // have player 1 land on Mediterranean (hard coded in Monopoly.Board)
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayerNumber()).getPosition()); // verify current player is now on test property (to pay rent)

        boolean canPayRent = game.rentTransaction();
        assertFalse(canPayRent);

        Assert.assertEquals(5, testRenter.getMoney()); // no change for renter
        Assert.assertEquals(1440, testProperty.getOwner().getMoney()); // no change for owner

        assertEquals(1, game.getPlayers().size()); // verify player was removed from players list
        assertEquals(1, game.getPlayers().get(0).getId()); // verify only player 1 remains
    }

    /**
     * Test that current player changes when doubles are not rolled
     * @author Thanuja
     */
    @Test
    public void testHandleSwitchTurnNormal() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        game.getDice1().rollDice(); // roll only 1 dice, to guarantee no doubles
        game.handleSwitchTurn();
        assertEquals(1, game.getCurrentPlayerNumber()); // new current player is 1
    }

    /**
     * Test that current player does not switch turn when a double is rolled
     * @author Thanuja
     */
    @Test
    public void testHandleSwitchTurnDouble() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        // do not roll dice, to mimic a double
        game.handleSwitchTurn();
        assertEquals(0, game.getCurrentPlayerNumber()); // new current player still 0 (first double)

        game.handleSwitchTurn();
        assertEquals(0, game.getCurrentPlayerNumber()); // new current player still 0 (second double)
    }

    /**
     * Test that current player is skipped after they roll 3 doubles
     * @author Thanuja
     */
    @Test
    public void testHandleSwitchTurnSkip() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        // do not roll dice, to mimic a double (both dice are initialized as 0)
        game.handleSwitchTurn();
        game.handleSwitchTurn();
        assertEquals(0, game.getCurrentPlayerNumber()); // new current player still 0 after two doubles

        game.handleSwitchTurn(); // third double
        assertEquals(1, game.getCurrentPlayerNumber()); // new current player is 1

        assertTrue(game.getPlayers().get(0).isJailTurn());

        //part of skip turn logic is handled at the beginning of handleMove - created public handleSkipTurn() to test

        game.getDice1().rollDice(); // roll only 1 dice, to guarantee no doubles
        game.handleSwitchTurn();
        //game.handleSkipTurn(); // player 0 could exit jail here
        assertEquals(0, game.getCurrentPlayerNumber()); // becomes player 0 again, who is in jail
        assertTrue(game.getCurrentPlayer().isJailTurn());

        // is different with Jail handling
        //game.handleSwitchTurn();
        //game.handleSkipTurn();
        ///assertEquals(0, game.getCurrentPlayerNumber()); // player 0 can now go again
    }

    /**
     * Test a roll dice.
     * @author Thanuja
     */
    @Test
    public void testHandleMove() {
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        Player currentPlayer = game.getPlayers().get(0);
        game.handleMove(); // will move piece between 1 and 12
        assertTrue(0 < currentPlayer.getPosition());
        assertTrue(12 >= currentPlayer.getPosition());
    }

    /**
     * Test whether player can buy houses/hotels
     * @author Shrimei
     * @author Maisha
     */
    @Test
    public void testCanBuyHouse(){
        game.addPlayer(new Player());
        game.addPlayer(new Player());

        //Grey colour group
        Property Oriental = new Property("Oriental Avenue", 100, ColourGroups.GREY);
        Property Vermont = new Property("Vermont Avenue", 100, ColourGroups.GREY);
        Property Connecticut = new Property("Connecticut Avenue", 120, ColourGroups.GREY);

        assertFalse(game.buyHouseHotel(Oriental));
        game.getCurrentPlayer().purchaseSquare(Oriental);
        assertFalse(game.buyHouseHotel(Oriental));
        game.getCurrentPlayer().purchaseSquare(Vermont);
        game.getCurrentPlayer().purchaseSquare(Connecticut); //have colour set

        //buy houses on all properties
        game.buyHouseHotel(Oriental);
        game.buyHouseHotel(Vermont);
        game.buyHouseHotel(Connecticut);

        //make sure one house exists on all properties
        assertEquals(1, game.getCurrentPlayer().getNumberOfHouses(Oriental));
        assertEquals(1, game.getCurrentPlayer().getNumberOfHouses(Vermont));
        assertEquals(1, game.getCurrentPlayer().getNumberOfHouses(Connecticut));

        //buy houses so all properties have four houses
        for(int i = 0 ; i<3; i++){
            game.buyHouseHotel(Oriental);
            game.buyHouseHotel(Vermont);
            game.buyHouseHotel(Connecticut);
        }

        //all properties should have four houses now
        assertEquals(4, game.getCurrentPlayer().getNumberOfHouses(Oriental));
        assertEquals(4, game.getCurrentPlayer().getNumberOfHouses(Vermont));
        assertEquals(4, game.getCurrentPlayer().getNumberOfHouses(Connecticut));

        game.buyHouseHotel(Oriental); //buy hotel
        assertFalse(game.buyHouseHotel(Oriental)); //already have 4 houses, 1 hotel
        //assertEquals(0, game.getCurrentPlayer().getNumberOfHouses(Oriental)); //clear houses to 0
        assertEquals(1, game.getCurrentPlayer().getNumberOfHotel(Oriental));

    }

    /**
     * Test serialization of primitive values of Player
     * @author Thanuja
     */
    @Test
    public void testSaveAndImportGamePlayersPrimitiveValues() {
        Player player1 = new Player();
        player1.payRent(500);
        player1.setPosition(15);
        this.game.addPlayer(player1);

        Player player2 = new PlayerAI();
        player2.setJailTurn(true);
        this.game.addPlayer(player2);

        this.game.saveGame("test-serialization-file");

        Game importedGame = new Game();
        importedGame.loadGame("test-serialization-file");

        // assert both games have 2 players
        assertEquals(2, this.game.getPlayers().size());
        assertEquals(2, importedGame.getPlayers().size());

        // assert some of the integer/boolean values
        assertEquals(this.game.getPlayers().get(0).getMoney(), importedGame.getPlayers().get(0).getMoney());
        assertEquals(this.game.getPlayers().get(0).getPosition(), importedGame.getPlayers().get(0).getPosition());
        assertEquals(this.game.getPlayers().get(1).isJailTurn(), importedGame.getPlayers().get(1).isJailTurn());

        // assert player 2 is AI for both games
        assertTrue(this.game.getPlayers().get(1) instanceof PlayerAI);
        assertTrue(importedGame.getPlayers().get(1) instanceof PlayerAI);
    }


    /**
     * Test serialization of a Player's purchase transaction of an OwnableSquare
     * @author Thanuja
     */
    @Test
    public void testSaveAndImportGameColourSetAndHouse() {
        Player player1 = new Player();
        this.game.addPlayer(player1);

        //colour group test
        player1.setPosition(1);
        game.purchaseTransaction();
        player1.setPosition(2);
        game.purchaseTransaction();
        game.buyHouseHotel((Property) this.game.getCurrentSquare()); // house on property 2

        this.game.saveGame("test-serialization-file");

        Game importedGame = new Game();
        importedGame.loadGame("test-serialization-file");

        // assert player still owns 2 squares
        assertEquals(2, this.game.getPlayers().get(0).getOwnableSquares().size());
        assertEquals(2, importedGame.getPlayers().get(0).getOwnableSquares().size());

        // assert rent with colour group is used for colour set
        assertEquals(12, this.game.getPlayers().get(0).getRentAmount((OwnableSquare) this.game.getBoard().getSquares().get(1), 0));
        assertEquals(12, importedGame.getPlayers().get(0).getRentAmount((OwnableSquare) importedGame.getBoard().getSquares().get(1),0));

        // assert rent with 1 house is used for colour set
        assertEquals(30, this.game.getPlayers().get(0).getRentAmount((OwnableSquare) this.game.getBoard().getSquares().get(2), 0));
        assertEquals(30, importedGame.getPlayers().get(0).getRentAmount((OwnableSquare) importedGame.getBoard().getSquares().get(2),0));
 }


    /**
     * Test serialization of Player in Jail
     * @author Thanuja
     */
    @Test
    public void testSaveAndImportGamePlayerInJail() {
        Player player1 = new Player();
        this.game.addPlayer(player1);

        this.game.addCurrentPlayerToJail(5); // arbitrary roll number

        this.game.saveGame("test-serialization-file");

        Game importedGame = new Game();
        importedGame.loadGame("test-serialization-file");

        assertEquals(1, this.game.getBoard().getJailSquare().getJailTime(this.game.getPlayers().get(0)));
        assertEquals(1, importedGame.getBoard().getJailSquare().getJailTime(importedGame.getPlayers().get(0)));

    }

    /**
     * Test serialization of current player number
     * @author Thanuja
     */
    @Test
    public void testSaveAndImportCurrentPlayerNumber(){
        Player player1 = new Player();
        Player player2 = new Player();

        this.game.addPlayer(player1);
        this.game.addPlayer(player2);

        this.game.getDice1().rollDice(); // roll only 1 dice, to guarantee no doubles
        this.game.handleSwitchTurn();

        this.game.saveGame("test-serialization-file");

        Game importedGame = new Game();
        importedGame.loadGame("test-serialization-file");

        assertEquals(1, this.game.getCurrentPlayerNumber());
        assertEquals(1, importedGame.getCurrentPlayerNumber());
    }

    /**
     * Test serialization of doubles
     * @author Thanuja
     */
    @Test
    public void testSaveAndImportDoubles(){
        Player player1 = new Player();
        Player player2 = new Player();

        this.game.addPlayer(player1);
        this.game.addPlayer(player2);

        // do not roll dice, to guarantee doubles
        this.game.handleSwitchTurn(); // will remain player 0
        this.game.handleSwitchTurn(); // will remain player 0

        this.game.saveGame("test-serialization-file");

        Game importedGame = new Game();
        importedGame.loadGame("test-serialization-file");

        importedGame.handleSwitchTurn();

        // now, imported game should have player 0 in jail
        assertTrue(importedGame.getPlayers().get(0).isJailTurn());
    }

}