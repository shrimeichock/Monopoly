package Monopoly;

import java.awt.*;

public enum BoardColours {

    RAILROAD (Color.BLACK), UTILITY(Color.DARK_GRAY), BOARD(new Color(100,100,100)),
    DICE(Color.LIGHT_GRAY), BLANKBOX(Color.LIGHT_GRAY), LIGHTTEXT(new Color(220,220,220)), BORDER(Color.GRAY);

    private final Color colour;

    /**
     * @author Thanuja
     * @param colour the colour value
     */
    BoardColours (Color colour){
        this.colour = colour;
    }

    /**
     * @author Thanuja
     * @return the colour of that section
     */
    public Color getColour() {
        return colour;
    }

}
