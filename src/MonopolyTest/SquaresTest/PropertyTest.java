package MonopolyTest.SquaresTest;

import static org.junit.Assert.*;

import Monopoly.ColourGroups;
import Monopoly.Squares.Property;
import org.junit.Test;
import org.junit.Before;

public class PropertyTest {

    private Property property;

    /**
     * Set up a test property
     *
     * @author Thanuja
     */
    @Before
    public void setUp() {
        property = new Property("Test Monopoly.Squares.Property", 200, ColourGroups.GREEN);
    }

    /**
     * Test that initial owner is null
     *
     * @author Thanuja
     */
    @Test
    public void testGetInitialOwner() {
        assertNull(property.getOwner());
    }

    /**
     * Test that rent is 10% of price
     *
     * @author Thanuja
     */
    @Test
    public void testGetRent() {
        assertEquals(20, property.getRent());
    }

    /**
     * Test that rent with colour set is 20% of price
     *
     * @author Thanuja
     */
    @Test
    public void testGetRentWithColourSet() {
        assertEquals(40, property.getRent(0));
    }

    /**
     * Test that house price is according to colour group
     *
     * @author Shrimei
     */
    @Test
    public void testGetHousePrice() {
        assertEquals(200, property.getHousePrice());
    }

    /**
     * Test that rent changes according to number of houses on property
     *
     * @author Shrimei
     */
    @Test
    public void testGetRentHouses() {
        assertEquals(100, property.getRent(1));
        assertEquals(300, property.getRent(2));
        assertEquals(600, property.getRent(3));
        assertEquals(900, property.getRent(4));
    }

    /**
     * Test that rent is increased for hotel
     *
     * @author Shrimei
     */
    @Test
    public void testGetRentHotel() {
        assertEquals(1170, property.getRentHotel());
    }

    /**
     * Test whether player can buy house depending on how many houses/hotels are already owned
     * (assuming colour set is already owned)
     * @author Shrimei
     */
    @Test
    public void testCanBuyHouse() {
        assertTrue(property.canBuyHouseOnProperty(0, 0));
        assertTrue(property.canBuyHouseOnProperty(1, 0));
        assertFalse(property.canBuyHouseOnProperty(4, 0));
    }

    /**
     * Test whether player can buy hotel
     * @author Shrimei
     */
    @Test
    public void testCanBuyHotel(){
        assertTrue(property.canBuyHotelOnProperty(0));
        assertFalse(property.canBuyHotelOnProperty(1));
    }
}