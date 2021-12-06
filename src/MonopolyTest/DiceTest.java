package MonopolyTest;

import Monopoly.Dice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

    private Dice dice;

    /**
     * @author Maisha
     */
    @Before
    public void setUp() {
        this.dice = new Dice();
    }

    /**
     * Tests that the initial dice number is 0
     * @author Maisha
     */
    @Test
    public void getDiceNumber() {
        assertEquals(0,dice.getDiceNumber()); //initial dice value
    }

    /**
     * Tests the number rolled is between 1 and 6.
     * @author Maisha
     */
    @Test
    public void rollDice() {
        int diceNum = dice.rollDice();
        int min = 1;
        int max = 6;
        assertTrue(diceNum <= max);
        assertTrue(diceNum >= min);
    }

}