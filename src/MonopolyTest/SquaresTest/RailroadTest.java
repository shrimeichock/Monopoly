package MonopolyTest.SquaresTest;

import Monopoly.Squares.Railroad;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RailroadTest {

    private Railroad railroad;

    /**
     * Set up a test railroad
     * @author Thanuja
     */
    @Before
    public void setUp(){
        railroad = new Railroad("Test Railroad");
    }

    /**
     * Test that initial owner is null
     * @author Thanuja
     */
    @Test
    public void testGetInitialOwner(){
        assertNull(railroad.getOwner());
    }

    /**
     * Test railroad buy price
     * @author Thanuja
     */
    @Test
    public void testGetPrice(){
        assertEquals(200, railroad.getPrice());
    }

    /**
     * Test rent with 1 railroad
     * @author Thanuja
     */
    @Test
    public void testGet1RailroadRent() {
        assertEquals(25, railroad.getRent(1));
    }

    /**
     * Test rent with 2 railroads
     * @author Thanuja
     */
    @Test
    public void testGet2RailroadRent() {
        assertEquals(50, railroad.getRent(2));
    }

    /**
     * Test rent with 3 railroads
     * @author Thanuja
     */
    @Test
    public void testGet3RailroadRent() {
        assertEquals(100, railroad.getRent(3));
    }

    /**
     * Test rent with 4 railroads
     * @author Thanuja
     */
    @Test
    public void testGet4RailroadRent() {
        assertEquals(200, railroad.getRent(4));
    }
}