package Monopoly;

import Monopoly.Squares.Property;
import Monopoly.Squares.Railroad;
import Monopoly.Squares.Square;
import Monopoly.Squares.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BoardPanel {
    private JPanel mainPanel;
    private LinkedHashMap<JPanel, Square> allSquares;

    /**
     * Create a new monopoly board with a roll button in the center to display the dice
     * @author Maisha
     * @author Thanuja
     * @author Shrimei
     * @param board     the board of the BoardPanel
     */
    public BoardPanel(Board board){

        final int boardWidth = 600;
        final int boardHeight = 600;
        final int boxHeight = 50;
        final int boxWidth = 50;

        allSquares = new LinkedHashMap<>();

        mainPanel = new JPanel();

        mainPanel.setSize(new Dimension(boardWidth, boardHeight));
        Color panelColour = BoardColours.BOARD.getColour();

        JPanel topBorder = new JPanel();
        topBorder.setBackground(panelColour);
        JPanel bottomBorder = new JPanel();
        bottomBorder.setBackground(panelColour);

        Dimension topBottom = new Dimension(boardWidth, boxHeight);
        topBorder.setPreferredSize(topBottom);
        bottomBorder.setPreferredSize(topBottom);

        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(panelColour);
        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(panelColour);

        Dimension leftRight = new Dimension(boxHeight, boardHeight * boxWidth);
        rightBorder.setPreferredSize(leftRight);
        leftBorder.setPreferredSize(leftRight);

        HashMap<Integer, Square> squares = board.getSquares();

        // create new panel for each square and add to one HashMap
        for (int i=0; i<squares.size(); i++){
            JPanel box = new JPanel(new BorderLayout());
            box.setPreferredSize(new Dimension(boxWidth, boxHeight));

            Square square = squares.get(i);

            //label square with the property name
            JTextArea label = new JTextArea(boxWidth, boxHeight);
            label.setText(square.getName());
            label.setLineWrap(true);
            label.setWrapStyleWord(true);
            label.setEditable(false);
            box.add(label, BorderLayout.CENTER);

            //if square is a property, show colour group
            if (square instanceof Property) {
                label.setBackground(((Property) square).getColourGroup().getColour());
            }else if (square instanceof Railroad){
                label.setForeground(BoardColours.LIGHTTEXT.getColour());
                label.setBackground(BoardColours.RAILROAD.getColour());
            }else if (square instanceof Utility){
                label.setForeground(BoardColours.LIGHTTEXT.getColour());
                label.setBackground(BoardColours.UTILITY.getColour());
            } else{
                label.setBackground(BoardColours.BLANKBOX.getColour());
            }

            allSquares.put(box, square);
        }

        // add box panels to the corresponding border panel
        //bottom boxes
        for (int i = 0; i < 9; i++){
            JPanel box =  (JPanel) allSquares.keySet().toArray()[8-i];
            bottomBorder.add(box);
        }

        //left boxes
        for (int i = 0; i < 8; i++){
            JPanel box =  (JPanel) allSquares.keySet().toArray()[16-i];
            leftBorder.add(box);
        }

        //top boxes
        for (int i = 0; i < 9; i++){
            JPanel box =  (JPanel) allSquares.keySet().toArray()[17+i];
            topBorder.add(box);
        }
        //right boxes
        for (int i = 0; i < 8; i++){
            JPanel box =  (JPanel) allSquares.keySet().toArray()[26+i];
            rightBorder.add(box);
        }

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(topBorder, BorderLayout.NORTH);
        mainPanel.add(rightBorder, BorderLayout.EAST);
        mainPanel.add(leftBorder, BorderLayout.WEST);
        mainPanel.add(bottomBorder, BorderLayout.SOUTH);
    }

    /**
     * Get a certain panel based on the position
     * @author Shrimei
     * @param position      position to get
     * @return              corresponding JPanel
     */
    public JPanel getPanel(int position){
        return (JPanel) allSquares.keySet().toArray()[position];
    }

    /**
     * Get the main panel of BoardPanel
     * @author Maisha
     * @return      the main panel
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
