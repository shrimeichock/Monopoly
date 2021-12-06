package Monopoly;

import java.awt.*;
// enums are automatically Serializable

public enum ColourGroups{

    BROWN (new Color(153, 102, 0), 2, 50), GREY(new Color(204, 229, 255), 3, 50), PINK(new Color(247,121,247), 3, 100),
    ORANGE(new Color(255, 153, 51), 3, 100), RED(Color.RED, 3, 150), YELLOW(Color.YELLOW, 3,150),
    GREEN(new Color(0,153,76), 3, 200), BLUE(new Color(0,102,204), 2, 200);

    private final Color colour;
    private final int max;
    private int housePrice;

    /**
     * @author Shrimei
     * @param colour        the colour group
     * @param max           the max amount of the colour set
     * @param housePrice    price to buy 1 house/hotel
     */
    ColourGroups (Color colour, int max, int housePrice){
        this.colour = colour;
        this.max = max;
        this.housePrice = housePrice;
    }

    /**
     * @author Maisha
     * @return housePrice
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * @author Shrimei
     * @return the colour group that the property belongs to
     */
    public Color getColour() {
        return colour;
    }


    /**
     * @author Shrimei
     * @return  the number of properties of that colour required to get a complete colour set
     */
    public int getMax() {
        return max;
    }

    }
