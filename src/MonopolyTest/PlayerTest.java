package MonopolyTest;

import static org.junit.Assert.*;

import Monopoly.Squares.Railroad;
import Monopoly.Squares.Utility;
import org.junit.Test;
import org.junit.Before;
import Monopoly.Squares.Property;
import Monopoly.Player;
import Monopoly.ColourGroups;


public class PlayerTest {

    private Player player;
    private Property property;
    private Property propertyOfSameColour;
    private Property propertyOfSameColour2;
    private Player owner;
    private Property rentedProperty;

    /**
     * Set up a test player
     * @author Thanuja
     */
    @Before
    public void setUp(){
        player = new Player();
        owner = new Player();
        property = new Property("Vermont Avenue", 100, ColourGroups.GREY);
        rentedProperty = new Property("St. Charles Place", 140, ColourGroups.PINK);
        propertyOfSameColour = new Property("Test1", 100, ColourGroups.GREY);
        propertyOfSameColour2 = new Property("Test2", 100, ColourGroups.GREY);
    }

    /**
     * Test initial money amount
     * @author Thanuja
     */
    @Test
    public void getInitialMoney() {
        assertEquals(1500, player.getMoney());
    }

    /**
     * Test initial position
     * @author Thanuja
     */
    @Test
    public void getInitialPosition() {
        assertEquals(0,player.getPosition());
    }

    /**
     * Test initial skip turn value
     * @author Thanuja
     */
    @Test
    public void getInitialSkipTurn() {
        assertFalse(player.isJailTurn());
    }

    /**
     * Test first move
     * @author Thanuja
     */
    @Test
    public void getPositionAfterFirstMove() {
        player.changePosition(8);
        assertEquals(8,player.getPosition());
    }

    /**
     * Test multiple moves
     * @author Thanuja
     */
    @Test
    public void getPositionAfterMultipleMoves() {
        player.changePosition(3);
        player.changePosition(8);
        player.changePosition(11);
        assertEquals(22,player.getPosition());
    }

    /**
     * Test the process of purchasing property
     * @author Maisha
     */
    @Test
    public void testPurchaseSquare(){
        assertTrue(player.purchaseSquare(property));
    }

    /**
     * Test the process of purchasing property with insufficient funds
     * @author Maisha
     */
    @Test
    public void noMoneyToPurchaseProperty(){
        for (int i = 0; i < 15; i++){
            player.purchaseSquare(property);
        }
        assertFalse(player.purchaseSquare(property));
    }

    /**
     * Test the process of collecting rent
     * @author Maisha
     */
    @Test
    public void collectRent(){
        player.collectRent(10);
        assertEquals(1510, player.getMoney()); //10% of 100$ (price of property) plus players money
    }

    /**
     * @author Sabah
     * Test for Railroad Rent Amount, tests for different types of properties
     */
    @Test
    public void getRentAmountRailRoad(){
        Railroad railroad = new Railroad("Reading Railroad");
        assertEquals(0, player.getRentAmount(railroad, 0));
        player.purchaseSquare(railroad);
        assertEquals(25, player.getRentAmount(railroad, 0));

    }
    /**
     * Test the process of collecting rent with utility.
     * @author Sabah, Shrimei
     */
    @Test
    public void getRentAmountUtility(){
        Utility waterWorks= new Utility("Water Works");
        Utility electricCompany= new Utility("Electric Company");
        assertEquals(0, player.getRentAmount(waterWorks, 10));
        player.purchaseSquare(waterWorks);
        assertEquals(40, player.getRentAmount(waterWorks, 10));
        player.purchaseSquare(electricCompany);
        assertEquals(100, player.getRentAmount(electricCompany, 10));
    }

    /**
     * Test the process of collecting rent with colour set.
     * @author Thanuja
     **/
    @Test
    public void getRentColourSet(){
        // purchase GREY property 3 times to own the colour set
        player.purchaseSquare(property);
        player.purchaseSquare(property);
        player.purchaseSquare(property);

        assertEquals(20, player.getRentAmount(property, 10)); //20% of 100$ (price of property)
    }

    /**
     * Test process of collecting basic rent (no colour set)
     * @author Shrimei
     */
    @Test
    public void getRentStandard(){
        player.purchaseSquare(property);
        assertEquals(10, player.getRentAmount(property, 10));
    }

    /**
     * Test process of collecting rent with houses and hotels
     * @author Shrimei
     */
    @Test
    public void getRentHousesHotels(){
        player.purchaseSquare(property);
        player.purchaseSquare(property);
        player.purchaseSquare(property); //get colour set

        player.buyHouseOnProperty(property); //1 house
        assertEquals(50, player.getRentAmount(property, 10));

        player.buyHouseOnProperty(property); //2 houses
        assertEquals(150, player.getRentAmount(property, 10));

        player.buyHouseOnProperty(property); //3 houses
        assertEquals(300, player.getRentAmount(property, 10));

        player.buyHouseOnProperty(property); //4 houses
        assertEquals(450, player.getRentAmount(property, 10));

        player.buyHotelOnProperty(property); //1 hotel
        assertEquals(585, player.getRentAmount(property, 10));
    }

    /**
     * Test the process of paying rent
     * @author Maisha
     */
    @Test
    public void payRent(){
        assertTrue(player.payRent(50));
    }

    /**
     * Test the process of paying rent with insufficient funds
     * @author Maisha
     */
    @Test
    public void noMoneyToPayRent(){
        for (int i = 0; i < 15; i++){
            player.purchaseSquare(property);
        }
        assertFalse(player.payRent(10));
    }

    /**
     * Test if buyHouseOnProperty can buy a house successfully
     * @author Maisha
     */
    @Test
    public void testBuyHouseOnProperty(){
        assertTrue(player.buyHouseOnProperty(property));
    }

    /**
     * Test if buyHotelOnProperty can buy a hotel successfully
     * @author Maisha
     */
    @Test
    public void testBuyHotelOnProperty(){
        assertTrue(player.buyHotelOnProperty(property));
    }

    /**
     * Test if number of houses is updated correctly
     * @author Maisha
     */
    @Test
    public void testGetNumberOfHouses(){
        assertEquals(0,player.getNumberOfHouses(property));
        player.purchaseSquare(property);
        player.buyHouseOnProperty(property);
        assertEquals(1, player.getNumberOfHouses(property));
    }

    /**
     * Test if number of hotels is updated correctly
     * @author Maisha
     */
    @Test
    public void testGetNumberOfHotel(){
        assertEquals(0,player.getNumberOfHotel(property));
        player.purchaseSquare(property);
        player.buyHotelOnProperty(property);
        assertEquals(1,player.getNumberOfHotel(property));
    }

    /**
     * Test if colour group is being updated
     * @author Maisha
     */
    @Test
    public void testHasAllColours(){
        player.purchaseSquare(property);
        assertFalse(player.hasAllColours(property));
        player.purchaseSquare(propertyOfSameColour);
        player.purchaseSquare(propertyOfSameColour2);
        assertTrue(player.hasAllColours(property));
    }

    /**
     * Test if player has equal number of houses on all properties of the same colour group
     * @author Maisha
     */
    @Test
    public void testHasEqualHouseOnEveryProperty(){
        //buy all the squares of one colour group
        player.purchaseSquare(property);
        player.purchaseSquare(propertyOfSameColour);
        player.purchaseSquare(propertyOfSameColour2);

        //buy a house on one property
        player.buyHouseOnProperty(property);
        assertFalse(player.hasEqualHouseOnEveryProperty(property));

        //buy a house on all properties
        player.buyHouseOnProperty(propertyOfSameColour);
        player.buyHouseOnProperty(propertyOfSameColour2);
        assertTrue(player.hasEqualHouseOnEveryProperty(property));
    }
}