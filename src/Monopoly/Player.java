package Monopoly;

import Monopoly.Squares.OwnableSquare;
import Monopoly.Squares.Property;
import Monopoly.Squares.Railroad;
import Monopoly.Squares.Utility;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Serializable {

    private int money;
    private int railroadNumber;
    private int utilityNumber;

    private ArrayList<OwnableSquare> ownableSquares;

    private HashMap<ColourGroups, Integer> colourGroupMatch;
    private int id;
    private int position;
    private boolean jailTurn;

    private HashMap<Property, Integer> numberOfHouses;
    private HashMap<Property, Integer> numberOfHotel;

    /**
     * @author Sabah
     * @author Shrimei
     * @author Maisha
     * Each player begins with 1500 dollars and starts on position 0
     */
    public Player() {
        this.money = 1500;
        this.position = 0;
        this.railroadNumber = 0;
        this.utilityNumber = 0;
        this.ownableSquares = new ArrayList<>();
        this.colourGroupMatch = new HashMap<>();
        this.jailTurn = false;
        this.numberOfHouses = new HashMap<>();
        this.numberOfHotel = new HashMap<>();
    }

    /**
     * Buy a house on the property if player has enough money
     * @author Maisha
     * @author Shrimei
     * @param property      the property the house is being bought on
     * @return              returns if house can be bought on property
     */
    public boolean buyHouseOnProperty(Property property){
        if (this.money >= property.getHousePrice()) {
            numberOfHouses.merge(property, 1, Integer::sum);
            this.money -= property.getHousePrice();
            return true;
        }
        return false;
    }

    /**
     * Buy hotel on property if player has enough money. Clear out number of houses.
     * @author Maisha
     * @param property  the property being checked
     * @return          returns if hotel can be bought on property
     */
    public boolean buyHotelOnProperty(Property property){
        if (this.money >= property.getHousePrice()) {
            //numberOfHouses.put(property, 0); //empty out number of houses on property
            //update has hotel
            numberOfHotel.put(property, 1);
            //if property.hasHouse then no more buying hotels on property
            this.money -= property.getHousePrice();
            return true;
        }
        return false;
    }

    /**
     * Get number of houses on property
     * @author Shrimei
     * @param property      the property being checked
     * @return              the number of properties existing on property
     */
    public int getNumberOfHouses(Property property) {
        return numberOfHouses.getOrDefault(property, 0);
    }

    /**
     * Get number of hotels on property
     * @author Maisha
     * @param property      the property being checked
     * @return              the number of hotels bought on the property
     */
    public int getNumberOfHotel(Property property) {
        return numberOfHotel.getOrDefault(property, 0);
    }

    /**
     * Get list of squares owned by player
     * @author Maisha
     * @return          returns all the squares owned
     */
    public ArrayList<OwnableSquare> getOwnableSquares() {
        return ownableSquares;
    }

    /**
     * True if turn should be skipped
     * @author Sabah
     * @return              returns if turn should be skipped true or false
     */
    public boolean isJailTurn() {
        return jailTurn;
    }

    /**
     * Set whether turn should be skipped
     * @author Sabah
     * @param jailTurn          the turn being checked
     */
    public void setJailTurn(boolean jailTurn) {
        this.jailTurn = jailTurn;
    }

    /**
     * An ID is given to each new player when they join the game
     * @author Sabah
     * @param id the player's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get player ID
     * @author Shrimei
     * @return the player's id
     */
    public int getId() {
        return id;
    }

    /**
     * Get amount of money that player has
     * @author Thanuja
     * @return amount of money player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Moves the player a certain number of boxes based on the roll
     * @author Shrimei
     * @param roll the dice roll number
     * @return the new position of the player
     */
    public int changePosition(int roll) {
        this.position += roll;
        return position;
    }

    /**
     * Set a players position
     * @author Thanuja
     * @param position position number to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get current position of player
     * @author Shrimei
     * @return the current position of the player
     */
    public int getPosition() {
        return position;
    }

    /**
     * Return true if square was purchased successfully (enough money)
     * @author Maisha
     * @author Shrimei
     * @param ownableSquare     the ownableSquare being purchased
     * @return                  returns true if player is able to purchase, else false.
     */
    public boolean purchaseSquare(OwnableSquare ownableSquare) {
        int cost = ownableSquare.getPrice();
        if (this.money >= cost) {
            this.money = this.money - cost;
            ownableSquares.add(ownableSquare);
            if (ownableSquare instanceof Property) {
                colourGroupMatch.merge(((Property) ownableSquare).getColourGroup(), 1, Integer::sum);
            } else if (ownableSquare instanceof Railroad) {
                railroadNumber += 1;
            } else if (ownableSquare instanceof Utility) {
                utilityNumber += 1;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * If the user has enough money to pay rent, it is deducted from their account.
     * @author Maisha
     * @author Shrimei
     * @author Thanuja
     * @param rent          the rent amount to pay
     * @return true if player is able to pay rent, else false.
     */
    public boolean payRent(int rent) {
        if (this.money >= rent) {
            this.money -= rent;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds collected rent to the owner's account
     * @author Maisha
     * @param rent      the property collecting rent
     */
    public void collectRent(int rent) {
        this.money += rent;
    }

    /**
     * Decides the appropriate rent amount to pay
     * @author Shrimei
     * @author Thanuja
     * @author Maisha
     * @param ownableSquare     the square receiving rent
     * @return the rent amount
     */
    public int getRentAmount(OwnableSquare ownableSquare, int roll) {
        if (ownableSquare instanceof Railroad) {
            return ownableSquare.getRent(railroadNumber);
        } else if (ownableSquare instanceof Property) {
            Property property = (Property) ownableSquare;
            if (hasAllColours(property)) {
                if (getNumberOfHotel(property)==1){
                    return property.getRentHotel();
                } else {
                    int numHouses = numberOfHouses.getOrDefault(property, 0);
                    return property.getRent(numHouses);
                }
            } else {
                return property.getRent(); //no colour set
            }
        } else if (ownableSquare instanceof Utility) {
            Utility utility = (Utility) ownableSquare;
            return utility.getRent(roll, utilityNumber);
        }
        return 0;
    }

    /**
     * @author Sabah
     * Add 200 dollars to the current player, when passing or landing on go.
     */
    public void collect200() {
        this.money += 200;
    }

    /**
     * Check if player owns all properties in colour group
     * @author Maisha
     * @author Shrimei
     * @param property      property to check
     * @return              true if colour set is owned, false otherwise
     */
    public boolean hasAllColours(Property property){
        return colourGroupMatch.getOrDefault(property.getColourGroup(), 0) == property.getColourGroup().getMax();
    }

    /**
     * @author Maisha
     * @param property      the property being checked
     * @return              true if every property has equal amount of houses on it
     */
    public boolean hasEqualHouseOnEveryProperty(Property property){
        int count = 0;
        Color propertyColour = property.getColourGroup().getColour();
        if (hasAllColours(property)){
            for (OwnableSquare os : this.ownableSquares){
                if (os instanceof Property){
                    Property p = (Property)os;
                    if (p.getColourGroup().getColour().equals(propertyColour) && getNumberOfHouses(p)>=getNumberOfHouses(property)){
                        count += 1;
                    }
                }
            }
        }
        return count == property.getColourGroup().getMax();
    }

}
