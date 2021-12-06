package MonopolyTest.SquaresTest;

import Monopoly.Game;
import Monopoly.Player;
import Monopoly.Squares.Jail;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class JailTest {

    private Jail jail;
    private Player player;
    private Player playerAlreadyInJail;
    private Game game;

    /**
     * Set up a test jail and test player in a game
     * @author Thanuja
     */
    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        // to test game jail
        this.game = new Game();
        this.game.setBoardVersion("Standard.xml");

        this.jail = game.getBoard().getJailSquare(); //this.jail = new Jail("Visiting Jail", "In Jail");
        this.player = new Player();
        this.playerAlreadyInJail = new Player();
        jail.addToJail(playerAlreadyInJail);

        // to test jail methods in Game
        game.addPlayer(player); // player 0
        //game.addPlayer(playerAlreadyInJail); // player 1
    }

    /**
     * Test jail time of player not in jail.
     * @author Thanuja
     */
    @Test
    public void testInitialJailTimeNotInJail() {
        assertEquals(0, jail.getJailTime(player));
    }

    /**
     * Add player to jail and verify jail time.
     * @author Thanuja
     */
    @Test
    public void testAddToJail() {
        this.jail.addToJail(player);
        assertEquals(1, jail.getJailTime(player));
    }

    /**
     * Test an increment to jail time for a player in jail.
     * @author Thanuja
     */
    @Test
    public void testIncrementJailTime() {
        this.jail.incrementJailTime(playerAlreadyInJail);
        assertEquals(2, jail.getJailTime(playerAlreadyInJail));
    }

    /**
     * Test an increment to jail time for a player not in jail.
     * Increment should not add non-jail players to jail.
     * @author Thanuja
     */
    @Test
    public void testIncrementJailTimePlayerNotInJail() {
        this.jail.incrementJailTime(player);
        assertEquals(0, jail.getJailTime(player));
        this.jail.incrementJailTime(player);
        assertEquals(0, jail.getJailTime(player));
    }

    /**
     * Remove player from jail and verify jail time.
     * @author Thanuja
     */
    @Test
    public void testRemoveFromJail() {
        this.jail.removeFromJail(playerAlreadyInJail);
        assertEquals(0, jail.getJailTime(playerAlreadyInJail));
    }

    /**
     * Test for landing on the "Go To Jail" Square
     * @author Thanuja
     */
    @Test
    public void testEnterJailLandingOnGoToJail(){
        player.changePosition(26);
        game.handleIfGoToJail(26);

        assertTrue(player.isJailTurn());
        assertEquals(8, player.getPosition());
        assertEquals(1, jail.getJailTime(player));
    }

    /**
     * Test for rolling 3 doubles to land in jail
     * @author Thanuja
     */
    @Test
    public void testEnterJail3Doubles(){
        // do not roll dice, to mimic doubles
        game.handleSwitchTurn();
        game.handleSwitchTurn();
        game.handleSwitchTurn();

        assertTrue(player.isJailTurn());
        assertEquals(8, player.getPosition());
        assertEquals(1, jail.getJailTime(player));
    }

    /**
     * Test for entering jail on same roll GO was received
     * @author Thanuja
     */
    @Test
    public void testEnterJailAfterPassGo(){
        // do not roll dice, to mimic doubles
        player.changePosition(20);
        player.changePosition(10);
        player.changePosition(10);

        game.handleIfGo(10);
        game.addCurrentPlayerToJail(10);

        assertEquals(1500, player.getMoney());
    }

    /**
     * Test for entering jail on same roll GO was received, and becomes bankrupt
     * @author Thanuja
     */
    @Test
    public void testEnterJailAfterPassGoBankrupt(){
        // do not roll dice, to mimic doubles
        player.changePosition(20);
        player.changePosition(10);
        player.changePosition(10);

        game.handleIfGo(10);

        player.payRent(1600); // player now has $100 (ex. buying property / paying rent on third turn after passing Go)

        game.addCurrentPlayerToJail(10); // game will try to take back $200

        assertEquals(0, game.getPlayers().size()); // player is removed from game
    }
}