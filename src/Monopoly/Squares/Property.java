package Monopoly.Squares;

import Monopoly.ColourGroups;

public class Property extends OwnableSquare {

    private final int price;
    private final String name;
    private final ColourGroups colourGroup;
    private final int rent;
    private final int rentWithColourSet;
    private final int rentOneHouse;
    private final int rentTwoHouses;
    private final int rentThreeHouses;
    private final int rentFourHouses;
    private final int rentHotel;
    private int housePrice;

    /**
     * @author Thanuja, Maisha
     * @param name          name of property
     * @param price         price of property
     * @param colourGroup   colour group of property
     * Sets the property's information. Standard rent is 10% of the property
     * price and the rent with colour set is 20%
     */
    public Property(String name, int price, ColourGroups colourGroup){
        super();
        this.name = name;
        this.price = price;
        this.rent = (int) ( price * 0.1); // 10% of property price
        this.rentWithColourSet = this.rent * 2; //20% of property price
        this.rentOneHouse = rent * 5;
        this.rentTwoHouses = rentOneHouse * 3;
        this.rentThreeHouses = rentTwoHouses * 2;
        this.rentFourHouses = (int)(rentThreeHouses * 1.5);
        this.rentHotel = (int)(rentFourHouses * 1.3);
        this.colourGroup = colourGroup;
        this.housePrice = colourGroup.getHousePrice();
    }


    /**
     * Get the price to buy a house
     * @return house price
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * Returns true if there are less than 4 houses on the property and no hotels
     * @author Maisha
     * @param housesOwned number of houses on property
     * @param hotelOwned number of hotels on propery
     * @return whether house can be bought
     */
    public boolean canBuyHouseOnProperty(int housesOwned, int hotelOwned){
        return (housesOwned != 4 && hotelOwned == 0);
    }

    /**
     * Returns true if the property doesn't already have a hotel
     * @author Maisha
     * @param hotelOwned number of hotels on property
     * @return whether hotel can be bought
     */
    public boolean canBuyHotelOnProperty(int hotelOwned){
        return hotelOwned != 1;
    }

    /**
     * Get property name
     * @author Thanuja
     * @return name of property
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get price to buy property
     * @author Thanuja
     * @return price of property
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Get Colour group that property belongs to
     * @author Thanuja
     * @return colour group
     */
    public ColourGroups getColourGroup() {
        return colourGroup;
    }

    /**
     * Get basic rent for property (no colour set or houses)
     * @author Shrimei
     * @return standard rent
     */
    public int getRent() {
        return rent;
    }

    /**
     * Get rent for a property depending on how many houses are on it
     * @author Thanuja
     * @return rent for the number of houses owned
     */
    @Override
    public int getRent(int numberOfHouses) {
        if (numberOfHouses == 1){
            return rentOneHouse;
        } else if(numberOfHouses == 2){
            return rentTwoHouses;
        } else if (numberOfHouses == 3){
            return rentThreeHouses;
        } else if (numberOfHouses == 4) {
            return rentFourHouses;
        } else {
            return rentWithColourSet;
        }
    }

    /**
     * Get the rent for property with a hotel
     * @author Shrimei
     * @return rent with 1 hotel
     */
    public int getRentHotel() {
        return rentHotel;
    }
}
