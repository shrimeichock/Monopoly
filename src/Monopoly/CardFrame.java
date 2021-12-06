package Monopoly;

import Monopoly.Squares.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Color;

/**
 * CardView Class
 * Creates frame to display an ownable card
 * @author Shrimei
 */
public class CardFrame extends JOptionPane {

    private OwnableSquare square;
    private CardController cardController;
    private JPanel mainPanel;
    private Player player;
    private String currency;

    /**
     * Called when player lands on ownable square, show buy/rent options
     * @author Shrimei
     * @author Thanuja
     * @param square  that is being displayed
     * @param game      the game model
     */
    public CardFrame(OwnableSquare square, Game game){
        super(square.getName());
        this.setLayout(new BorderLayout());
        this.setSize(200, 250);

        this.square = square;
        this.player = game.getCurrentPlayer();
        this.currency = game.getBoard().getCurrency();

        mainPanel = new JPanel(new BorderLayout());

        displayOwnableSquareInfo();

        this.cardController = new CardController(game);

        if(square.getOwner() == null) { //no owner, can buy
            handleBuyOption();
        }else if(square.getOwner().equals(game.getCurrentPlayer())){ //current player is owner
            handleIsOwner();
        }else{
            handlePayRent();
        }
    }

    /**
     * Called from the CardFrame constructor
     * Display for the corresponding OwnableSquare type
     * @author Shrimei
     * @author Thanuja
     */
    private void displayOwnableSquareInfo(){
        JPanel fieldPanel = new JPanel();
        JPanel valuePanel = new JPanel();

        fieldPanel.setLayout(new BoxLayout(fieldPanel,BoxLayout.Y_AXIS));
        valuePanel.setLayout(new BoxLayout(valuePanel,BoxLayout.Y_AXIS));

        Border fieldBorder = new EmptyBorder(6,3,3,3);
        Border valueBorder = new EmptyBorder(6,3,3,3);

        fieldPanel.setBorder(fieldBorder);
        valuePanel.setBorder(valueBorder);

        JLabel name = new JLabel(square.getName(), SwingConstants.CENTER);
        name.setBorder(valueBorder);
        name.setOpaque(true);

        // tell the corresponding OwnableSquare method to: fill the field and value panels, use the borders, and format the name label
        if(square instanceof Property){
            displayPropertyInfo(fieldPanel, valuePanel, fieldBorder, valueBorder, name);
        } else if(square instanceof Railroad){
            displayRailroadInfo(fieldPanel, valuePanel, fieldBorder, valueBorder, name);
        } else if(square instanceof Utility){
            displayUtilityInfo(fieldPanel, valuePanel, fieldBorder, valueBorder, name);
        }

        mainPanel.add(name, BorderLayout.NORTH);
        mainPanel.add(fieldPanel, BorderLayout.WEST);
        mainPanel.add(valuePanel, BorderLayout.EAST);
    }

    /**
     * Display Utility info (name, price, rent) on card
     * @author Shrimei
     */
    private void displayUtilityInfo(JPanel fieldPanel, JPanel valuePanel, Border fieldBorder, Border valueBorder, JLabel name) {
        Utility utility = (Utility) square;

        name.setForeground(BoardColours.LIGHTTEXT.getColour());
        name.setBackground(BoardColours.UTILITY.getColour());

        JLabel price = new JLabel("Price: ");
        JLabel rentOne = new JLabel("1 Utility Owned");
        JLabel rentTwo = new JLabel("2 Utilities Owned");

        JLabel[] labels = {price, rentOne, rentTwo};

        for (JLabel label : labels){
            label.setBorder(fieldBorder);
            fieldPanel.add(label);
        }

        JLabel priceVal = new JLabel(currency + utility.getPrice());
        JLabel rentOneVal = new JLabel("4 X roll");
        JLabel rentTwoVal = new JLabel("10 X roll");

        JLabel[] values = {priceVal, rentOneVal, rentTwoVal};

        for (JLabel value : values){
            value.setBorder(valueBorder);
            valuePanel.add(value);
        }

    }

    /**
     * Display railroad info (name, price, rent) on card
     * @author Shrimei
     */
    private void displayRailroadInfo(JPanel fieldPanel, JPanel valuePanel, Border fieldBorder, Border valueBorder, JLabel name) {
        Railroad railroad = (Railroad) square;

        name.setForeground(BoardColours.LIGHTTEXT.getColour());
        name.setBackground(BoardColours.RAILROAD.getColour());

        JLabel price = new JLabel("Price: ");
        JLabel rentOne = new JLabel("RENT: ");
        JLabel rentTwo = new JLabel("2 Railroads Owned: ");
        JLabel rentThree = new JLabel("3 Railroads Owned: ");
        JLabel rentFour = new JLabel("4 Railroads Owned: ");

        JLabel[] labels = {price, rentOne, rentTwo, rentThree, rentFour};

        for (JLabel label : labels){
            label.setBorder(fieldBorder);
            fieldPanel.add(label);
        }

        JLabel priceVal = new JLabel(currency + railroad.getPrice());
        JLabel rentOneVal = new JLabel(currency + railroad.getRent(1));
        JLabel rentTwoVal = new JLabel(currency + railroad.getRent(2));
        JLabel rentThreeVal = new JLabel(currency + railroad.getRent(3));
        JLabel rentFourVal = new JLabel(currency + railroad.getRent(4));

        JLabel[] values = {priceVal, rentOneVal, rentTwoVal, rentThreeVal, rentFourVal};

        for (JLabel value : values){
            value.setBorder(valueBorder);
            valuePanel.add(value);
        }

    }

    /**
     * Display the property info (price, name, colour, rent, houses) on a frame
     * @author Shrimei
     */
    private void displayPropertyInfo(JPanel fieldPanel, JPanel valuePanel, Border fieldBorder, Border valueBorder, JLabel name) {
        Property property = (Property) square;

        Color colour = property.getColourGroup().getColour();
        name.setBackground(colour);

        JLabel price = new JLabel("Price: ");
        JLabel rent = new JLabel("Rent: ");
        JLabel rentWithSet = new JLabel("Rent with colour set: ");
        JLabel rentOneHouse = new JLabel("Rent with 1 house: ");
        JLabel rentTwoHouses = new JLabel("Rent with 2 houses: ");
        JLabel rentThreeHouses = new JLabel("Rent with 3 houses: ");
        JLabel rentFourHouses = new JLabel("Rent with 4 houses: ");
        JLabel rentHotel = new JLabel("Rent with hotel: ");
        JLabel houseCost = new JLabel("House cost: ");
        JLabel hotelCost = new JLabel("Hotel cost: ");

        JLabel[] labels = {price, rent, rentWithSet, rentOneHouse, rentTwoHouses, rentThreeHouses, rentFourHouses, rentHotel, houseCost, hotelCost};

        for (JLabel label : labels){
            label.setBorder(fieldBorder);
            fieldPanel.add(label);
        }

        JLabel priceVal = new JLabel(currency + property.getPrice());
        JLabel rentVal = new JLabel(currency +property.getRent());
        JLabel rentWithSetVal = new JLabel(currency + property.getRent(0));
        JLabel rentOneHouseVal = new JLabel(currency + property.getRent(1));
        JLabel rentTwoHousesVal = new JLabel(currency + property.getRent(2));
        JLabel rentThreeHousesVal = new JLabel(currency + property.getRent(3));
        JLabel rentFourHousesVal = new JLabel(currency + property.getRent(4));
        JLabel rentHotelVal = new JLabel(currency + property.getRentHotel());
        JLabel houseCostVal= new JLabel(currency + property.getHousePrice());
        JLabel hotelCostVal= new JLabel(currency + property.getHousePrice());

        JLabel[] values = {priceVal, rentVal, rentWithSetVal, rentOneHouseVal, rentTwoHousesVal, rentThreeHousesVal, rentFourHousesVal, rentHotelVal, houseCostVal, hotelCostVal};

        for (JLabel value : values){
            value.setBorder(valueBorder);
            valuePanel.add(value);
        }
    }

    /**
     * When property with no owner is landed on, give player option to purchase
     * @author Shrimei
     * @author Thanuja
     */
    private void handleBuyOption(){
        if (player instanceof PlayerAI){
            if (square.getPrice() <= player.getMoney()){ // buy property if player has enough money
                cardController.purchaseCard();
            }
        }else {
            int result = JOptionPane.showConfirmDialog(null, mainPanel, "Purchase square?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                boolean canPurchase = cardController.purchaseCard();
                if (!canPurchase) {
                    JOptionPane.showMessageDialog(null, "You do not have enough money to purchase the property");
                } // else, they successfully purchased
            }
        }
    }

    /**
     * If player lands on property owned by someone else, pay rent
     * @author Thanuja
     * @author Maisha
     */
    private void handlePayRent(){
        int rent = cardController.getRentAmount();
        if(!(player instanceof PlayerAI)){
            JOptionPane.showMessageDialog(null, mainPanel , "Pay "+ currency + rent + " in rent", JOptionPane.PLAIN_MESSAGE);
        }
        cardController.payCardRent();
    }

    /**
     * If player is owner, display message saying they are the owner. If square is a property, ask if they want t buy house/hotel
     * @author Thanuja
     * @author Shrimei
     */
    private void handleIsOwner(){
        if(square instanceof Property){
            if (player instanceof PlayerAI){ // AI player only buys houses/hotel if they land on their property again
                cardController.purchaseHouse((Property)square);
            }else {
                int result = JOptionPane.showConfirmDialog(null, mainPanel, "Purchase a house/hotel?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    boolean canPurchase = cardController.purchaseHouse((Property) square);
                    if (!canPurchase) {
                        JOptionPane.showMessageDialog(null, "You are not able to purchase a house/hotel");
                    } // else, they successfully purchased
                }
            }
        } else {
            if (!(player instanceof PlayerAI)) {
                JOptionPane.showMessageDialog(null, mainPanel, "You own this property", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }

}
