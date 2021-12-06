package Monopoly.Squares;

public class BlankSquare implements Square {

    private String name;

    /**
     * Create a new blank square with a name
     * @author Thanuja
     * @param name the name of the box
     */
    public BlankSquare(String name){
        this.name = name;
    }

    /**
     * Get name of square
     * @author Thanuja
     * @return the name of the square
     */
    @Override
    public String getName() {
        return name;
    }

}
