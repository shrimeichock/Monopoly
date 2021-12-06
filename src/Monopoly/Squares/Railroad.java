package Monopoly.Squares;

public class Railroad extends OwnableSquare {

    private String name;
    private int price;
    private int rentOne;
    private int rentTwo;
    private int rentThree;
    private int rentFour;

    /**
     * Constructor for railroad
     * @author Thanuja
     * @param name  name of railroad
     */
    public Railroad(String name){
        super();
        this.name = name;
        this.price = 200;
        this.rentOne = 25;
        this.rentTwo = 50;
        this.rentThree = 100;
        this.rentFour = 200;
    }

    /**
     * Get name of railroad
     * @author Shrimei
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get price of railroad
     * @author Shrimei
     * @return price to buy railroad
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Get rent according to numbe rof railroads owned by the player
     * @author Maisha
     * @param numberOfRailroads owned
     * @return rent
     */
    public int getRent(int numberOfRailroads){
        if (numberOfRailroads == 1){
            return rentOne;
        } else if (numberOfRailroads == 2){
            return rentTwo;
        } else if (numberOfRailroads == 3){
            return rentThree;
        } else if (numberOfRailroads == 4) {
            return rentFour;
        }else {
            return 0;
        }
    }

}
