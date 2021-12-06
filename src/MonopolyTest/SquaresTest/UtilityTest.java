package MonopolyTest.SquaresTest;

import Monopoly.Squares.Utility;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    private Utility utility;

    /**
     * Set up a test utility
     * @author Thanuja
     */
    @Before
    public void setUp(){
        utility = new Utility("Test Utility");
    }

    /**
     * Test that initial owner is null
     * @author Thanuja
     */
    @Test
    public void testGetInitialOwner(){
        assertNull(utility.getOwner());
    }


    /**
     * Test utility buy price
     * @author Thanuja
     */
    @Test
    public void testGetPrice(){
        assertEquals(150, utility.getPrice());
    }


    /**
     * Test rent with 1 railroad
     * @author Thanuja
     */
    @Test
    public void testGet1UtilityRent() {
        int utilityNumber = 1;
        int roll = 5;
        assertEquals(20, utility.getRent(roll, utilityNumber));
    }

    /**
     * Test rent with 2 utilities
     * @author Thanuja
     */
    @Test
    public void testGet2RailroadRent() {
        int numUtilities = 2;
        int roll = 6;
        assertEquals(60, utility.getRent(roll, numUtilities));
    }
}