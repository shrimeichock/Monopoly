package Monopoly;

import Monopoly.Squares.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;
import java.util.HashMap;

public class VersionHandler extends DefaultHandler {
    private Boolean isName;
    private Boolean isPrice;
    private Boolean isColour;
    private Boolean isCurrency;
    private Boolean isVersion;
    private String name;
    private int price;
    private ColourGroups colour;
    private HashMap<Integer, Square> squares;
    private int count;
    private String currency;
    private String version;
    private int jailPosition;
    private int goPosition;
    private int goToJailPosition;

    public VersionHandler(){
        squares = new HashMap<>();
        isName = false;
        isPrice = false;
        isColour = false;
        isCurrency = false;
        isVersion = false;
        count = 0;

        // default values if board does not have them
        jailPosition = 8;
        goPosition = 0;
        goToJailPosition = -1;
    }

    /**
     * Return the list of squares
     * @author Shrimei
     * @return squares hashmap
     */
    public HashMap<Integer, Square> updateSquares(){
        return squares;
    }

    /**
     * Return currency symbol
     * @author Shrimei
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Return version name
     * @author Shrimei
     * @return version name
     */
    public String getVersion() {
        return version;
    }

    /**
     * Check which attribute is being populated
     * @author Shrimei
     * @param uri namespace URI
     * @param localName local name string
     * @param qName XML 1.0 qualified name
     * @param a attributes attached to element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes a) {
        switch (qName) {
            case "name":
                isName = true;
                break;
            case "price":
                isPrice = true;
                break;
            case "ColourGroups":
                isColour = true;
                break;
            case "currency":
                isCurrency = true;
                break;
            case "versionName":
                isVersion = true;
                break;
        }
    }

    /**
     * Create the appropriate square
     * @author Shrimei
     * @param uri namespace URI
     * @param localName local name string
     * @param qName XML 1.0 qualified name
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "BlankSquare":
                BlankSquare blankSquare = new BlankSquare(name);
                this.squares.put(count, blankSquare);
                count += 1;
                break;
            case "Property":
                Property property = new Property(name, price, colour);
                this.squares.put(count, property);
                count += 1;
                break;
            case "Utility":
                Utility utility = new Utility(name);
                this.squares.put(count, utility);
                count += 1;
                break;
            case "Railroad":
                Railroad railroad = new Railroad(name);
                this.squares.put(count, railroad);
                count += 1;
                break;
            case "Jail":
                Jail jail = new Jail();
                this.squares.put(count, jail);
                this.jailPosition = count;
                count += 1;
                break;
        }
    }

    /**
     * Get attributes within each element
     * @author Shrimei
     * @author Thanuja
     * @param ch array of characters
     * @param start starting index
     * @param length size of array
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        if (isName) {
            name = new String(ch, start, length);
            if (name.equals("GO")){
                goPosition = count;
            }else if (name.equals("Go to Jail")){
                goToJailPosition = count;
            }
            isName = false;
        } else if (isPrice) {
            price = Integer.parseInt(new String(ch, start, length));
            isPrice = false;
        } else if (isColour) {
            String value = new String(ch, start, length);
            colour =  ColourGroups.valueOf(value);
            Color.getColor(new String(ch, start, length));
            isColour = false;
        } else if (isCurrency){
            currency = new String(ch, start, length);
            isCurrency = false;
        } else if(isVersion){
            version = new String(ch, start, length);
            isVersion = false;
        }
    }

    /**
     * Return jail position
     * @author Thanuja
     * @return int, position of jail
     */
    public int getJailPosition() {
        return jailPosition;
    }

    /**
     * Return go position
     * @author Sabah
     * @return int, position of go
     */
    public int getGoPosition() {
        return goPosition;
    }

    /**
     * Return go to jail position
     * @author Thanuja
     * @return int, position of go to jail
     */
    public int getGoToJailPosition() {
        return goToJailPosition;
    }
}
