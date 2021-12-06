package Monopoly;

import Monopoly.Squares.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Board implements Serializable {
    private HashMap<Integer, Square> squares;
    private String currency;
    private String version;
    private int jailPosition;
    private int goPosition;
    private int goToJailPosition;

    /**
     * Build board according to specified version
     * @author Shrimei
     * @param version of board
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException                  IOException
     * @throws SAXException                 SAXException
     */
    public Board(String version) throws ParserConfigurationException, IOException, SAXException {
        this.importFromXMLFile(version);
    }

    /**
     * Return the list of squares on the board
     * @author Thanuja
     * @return box hashmap
     */
    public HashMap<Integer, Square> getSquares() {
        return squares;
    }

    /**
     * Return go to jail position
     * @author Thanuja
     * @return  position of Go To Jail
     */
    public int getGoToJailPosition(){
        return goToJailPosition;
    }

    /**
     * Return casted version of jail square
     * @author Thanuja
     * @return Jail square
     */
    public Jail getJailSquare(){
        return (Jail) squares.get(jailPosition);
    }

    /**
     * Return jail position
     * @author Thanuja
     * @return int, position of jail
     */
    public int getJailPosition(){
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
     * Return the currency symbol
     * @author Shrimei
     * @return currency symbol
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Return version name
     * @author Shrimei
     * @return version
     */
    public String getVersion() {

        return version;
    }

    /**
     * Import the given version of monopoly from an XML file and update the board accordingly
     * @author Shrimei
     * @param filename of version to import
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException                  IOException
     * @throws SAXException                 SAXException
     */
    public void importFromXMLFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(filename);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();

        VersionHandler vh = new VersionHandler();
        s.parse(file, vh);
        this.squares = vh.updateSquares();
        this.currency = vh.getCurrency();
        this.version = vh.getVersion();
        this.jailPosition = vh.getJailPosition();
        this.goPosition = vh.getGoPosition();
        this.goToJailPosition = vh.getGoToJailPosition();
    }

}
