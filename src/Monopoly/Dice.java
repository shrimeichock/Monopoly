package Monopoly;

import java.util.Random;

public class Dice {

    private int diceNumber;

    /**
     * Dice Constructor
     * @author Sabah
     */
    public Dice (){
        this.diceNumber=0;
    }

    /**
     * Outputs an integer between 1 and 6
     * @author Maisha
     * @author Sabah
     * @return dice number that was rolled, int
     */
    public int rollDice(){
        Random rn = new Random();
        this.diceNumber = rn.nextInt(5)+1;
        return this.diceNumber;
    }

    /**
     * @author Sabah
     * @return diceNumber in integer form
     */
    public int getDiceNumber() {
        return diceNumber;
    }
}
