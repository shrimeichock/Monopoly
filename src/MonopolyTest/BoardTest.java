package MonopolyTest;

import Monopoly.Board;
import Monopoly.Squares.Jail;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    /**
     * @author Shrimei
     */
    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        board = new Board("Standard.xml");
    }

    /**
     * Test that the hashmap of properties is set correctly when board is initialized
     * @author Shrimei
     */
    @Test
    public void testSetProperties() {
        assertEquals(board.getSquares().get(0).getName(), "GO");
        assertEquals(board.getSquares().get(11).getName(), "States Avenue");
        assertEquals(board.getSquares().get(33).getName(), "Boardwalk");
    }

    /**
     * Test jail square.
     * @author Thanuja
     */
    @Test
    public void testGetJailSquare(){
        assertEquals("Visiting Jail", board.getJailSquare().getName());
        assertNotNull(board.getJailSquare());
    }

    /**
     * Test jail position.
     * @author Thanuja
     */
    @Test
    public void testGetJailPosition(){
        assertEquals("Visiting Jail", board.getSquares().get(board.getJailPosition()).getName());
        assertTrue(board.getSquares().get(board.getJailPosition()) instanceof Jail);
    }

    /**
     * Test go to jail position matches the name displayed.
     * @author Thanuja
     */
    @Test
    public void testGetGoToJailPosition(){
        assertEquals("Go to Jail", board.getSquares().get(board.getGoToJailPosition()).getName());
    }

    /**
     * Test go position matches the name displayed.
     * @author Thanuja
     */
    @Test
    public void testGoPosition(){
        assertEquals("GO", board.getSquares().get(board.getGoPosition()).getName());
    }

    /**
     * Tests that international version of board was imported correctly
     * @author Shrimei
     */
    @Test
    public void testImportFromXML() throws ParserConfigurationException, IOException, SAXException {
        board = new Board("International.xml");

        assertEquals("International", board.getVersion());
        assertEquals("£", board.getCurrency());
        assertEquals(8, board.getJailPosition());
        assertEquals(0, board.getGoPosition());
        assertEquals(26, board.getGoToJailPosition());
        assertEquals("Whitechapel Road", board.getSquares().get(2).getName()); //property
        assertEquals("Kingcross Station", board.getSquares().get(4).getName()); //railroad
        assertEquals("Electric Company", board.getSquares().get(10).getName()); //utility
        assertEquals("Visiting Jail", board.getSquares().get(8).getName()); //jail
    }
}
