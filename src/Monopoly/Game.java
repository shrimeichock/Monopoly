package Monopoly;

import Monopoly.Squares.*;
import org.xml.sax.SAXException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

public class Game {

    private Dice dice1;
    private Dice dice2;
    private ArrayList<Player> players;
    private int currentPlayerNumber;
    private int doubles;
    private Board board;

    private ArrayList<MonopolyInterfaceView> views;

    /**
     * @author Sabah
     * @author Shrimei
     * Creates a Monopoly board
     */
    public Game(){
        this.dice1= new Dice();
        this.dice2= new Dice();
        this.players = new ArrayList<>();
        this.currentPlayerNumber = 0;
        this.doubles = 0;
        this.views = new ArrayList<>();
    }

    /**
     * Set board version
     * @author Shrimei
     * @param version                       name of file
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException                  IOException
     * @throws SAXException                 SAXException
     */
    public void setBoardVersion(String version) throws ParserConfigurationException, IOException, SAXException {
        this.board = new Board(version);
    }

    /**
     * Return the board
     * @author Maisha
     * @return Monopoly.Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @author Thanuja
     * @param view to add
     */
    public void addView(MonopolyInterfaceView view){
        views.add(view);
    }

    /**
     * @author Thanuja
     * @param view to remove
     */
    public void removeView(MonopolyInterfaceView view){
        views.remove(view);
    }


    /**
     * @author Sabah
     * @author Shrimei
     * @param newPlayer to be added
     * Adds a player and gives them a unique ID
     */
    public void addPlayer(Player newPlayer) {
        int length = players.size();
        newPlayer.setId(length);
        players.add(newPlayer);
    }

    /**
     * @author Sabah
     * @param player to be removed
     * Removes the player and their ownership of their properties so that
     * it is available for other players to buy
     */
    private void removePlayer(Player player){
        players.remove(player);
        for (OwnableSquare ownableSquare: player.getOwnableSquares()){
            ownableSquare.setOwner(null);
        }
        doubles = -1; // in case the player removed just rolled doubles
    }

    /**
     * @author Shrimei
     * Loops through players in the game
     */
    private void switchTurn(){
        if(currentPlayerNumber+1 == players.size()){
            this.currentPlayerNumber = 0;
        } else {
            this.currentPlayerNumber += 1;
        }
    }

    /**
     * @author Thanuja
     * @return dice 1
     */
    public Dice getDice1() {
        return dice1;
    }

    /**
     * @author Thanuja
     * @return dice 2
     */
    public Dice getDice2() {
        return dice2;
    }

    /**
     * Get the current player
     * @author Shrimei
     * @return current player
     */
    public Player getCurrentPlayer(){
        return players.get(currentPlayerNumber);
    }

    /**
     * Handle purchase transaction
     * @author Maisha
     * @author Shrimei
     * @author Thanuja
     * @return true if current player is able to purchase current property, else false.
     */
    public boolean purchaseTransaction(){
        boolean canPurchase = false;
        Player currentPlayer = getCurrentPlayer();
        Square currentSquare =  getCurrentSquare();

        if (currentSquare instanceof OwnableSquare){
            OwnableSquare ownableSquare = (OwnableSquare) currentSquare;
            canPurchase = currentPlayer.purchaseSquare(ownableSquare);
            if (canPurchase){
                ownableSquare.setOwner(currentPlayer);
            }
        }
        return canPurchase;
    }


    /**
     * Get current square's rent amount
     * @author Thanuja
     * @return int, rent amount, 0 if no rent amount for the current square
     */
    public int getCurrentRentAmount(){
        int rent = 0;
        Square currentSquare =  getCurrentSquare();
        if ((currentSquare instanceof OwnableSquare) && ((OwnableSquare)currentSquare).getOwner() != null) {
            OwnableSquare currentOwnableSquare = (OwnableSquare) currentSquare;
            rent = currentOwnableSquare.getOwner().getRentAmount(currentOwnableSquare, dice1.getDiceNumber() + dice2.getDiceNumber());
        }
        return rent;
    }

    /**
     * Handle rent transaction
     * Remove player if they become bankrupt.
     * Current property must have an owner
     * @author Maisha
     * @author Shrimei
     * @author Sabah
     * @author Thanuja
     * @return true if current player is able to pay rent on current property, else false.
     */
    public boolean rentTransaction(){
        boolean canPayRent = false;
        Player currentPlayer = getCurrentPlayer();

        int rentAmount = getCurrentRentAmount();
        if (rentAmount > 0) { // if there is a rent amount on the current square
            OwnableSquare currentOwnableSquare = (OwnableSquare) getCurrentSquare();

            canPayRent = currentPlayer.payRent(rentAmount);
            if (canPayRent) { //pay rent if enough money
                currentOwnableSquare.getOwner().collectRent(rentAmount);
            } else { //player ran out of money, they are bankrupt
                currentPlayerBankrupt();
            }
        }
        return canPayRent;
    }

    /**
     * Handle buy house transaction, return true if player successfully buys house
     * @author maisha
     * */
    public boolean buyHouseHotel(Property property){
        Player currentPlayer = getCurrentPlayer();

        boolean flag1, flag2, flag5;
        boolean flag3 = false;

        flag1 = currentPlayer.hasAllColours(property); //have colour set
        flag2 = property.canBuyHouseOnProperty(currentPlayer.getNumberOfHouses(property),currentPlayer.getNumberOfHotel(property)); //don't already have 4 houses
        flag5 = currentPlayer.hasEqualHouseOnEveryProperty(property);


        if (flag1 && flag2 && flag5){
            flag3 = currentPlayer.buyHouseOnProperty(property);
            for (MonopolyInterfaceView view : this.views){
                view.handlePlayerState();
            }
        } else if (flag1 && flag5){
            boolean flag4 = property.canBuyHotelOnProperty(currentPlayer.getNumberOfHotel(property)); //don't already have hotel

            if (flag4){
                flag3 = currentPlayer.buyHotelOnProperty(property);
                for (MonopolyInterfaceView view : this.views){
                    view.handlePlayerState();
                }
            }
        }
        return flag3;
    }

    /**
     * Handle when current player is bankrupt
     * @author Thanuja
     */
    public void currentPlayerBankrupt(){
        for (MonopolyInterfaceView view : this.views){
            view.handleBankruptcy(getCurrentPlayer()); // show they are bankrupt
        }
        removePlayer(getCurrentPlayer()); //remove player from game

        if (currentPlayerNumber == 0) { // if first player went bankrupt
            currentPlayerNumber = players.size() - 1; // set to last player (temporary)
        }else{
            currentPlayerNumber -= 1;
        }
        checkIfWinner(); //check if winner exists, only 1 player left
    }

    /**
     * Check if only 1 player left (winner)
     * @author Maisha
     * @return false if no winner yet, else true or exit the game
     */
    public boolean checkIfWinner(){
        //check if 1 player left
        if (players.size() == 1){
            for (MonopolyInterfaceView view : views){
                view.handleWinner();
            }
            if (views.size() > 0) { // don't exit if testing
                System.exit(0); //exit game
            }
        }
        return players.size() == 1;
    }

    /**
     * Add current player to jail
     * @author Thanuja
     * @param roll      the dice roll of the player
     */
    public void addCurrentPlayerToJail(int roll) {
        Player currentPlayer = getCurrentPlayer();
        //int roll = dice1.getDiceNumber() + dice2.getDiceNumber(); // roll parameter makes this method easier to test
        if (canGetGoAmount(roll)) {
            if (!currentPlayer.payRent(200)) { // return the GO amount
                currentPlayerBankrupt();
            }
        }
        Jail jail = board.getJailSquare();
        currentPlayer.setJailTurn(true);
        currentPlayer.setPosition(board.getJailPosition());
        jail.addToJail(currentPlayer);

        this.doubles = -1; // in case player rolled doubles when landing on go to jail
    }

    /**
     * Remove current player from jail
     * @author Thanuja
     */
    private void removeCurrentPlayerFromJail() {
        Jail jail = board.getJailSquare();
        Player currentPlayer = getCurrentPlayer();
        jail.removeFromJail(currentPlayer);
        currentPlayer.setJailTurn(false);

        this.doubles = -1; // even if player rolled doubles to exit, they cannot roll again (handled in handleSwitchTurn)
    }


    /**
     * Encapsulate the checking if a player can roll again
     * @author Thanuja
     * @return boolean      true if they can reroll, false otherwise
     */
    private boolean cannotReRoll(){
        // dice1.getDiceNumber() != dice2.getDiceNumber() - did not roll doubles
        // doubles < 0 - if player just went bankrupt or if player just entered/exited jail
        return ((dice1.getDiceNumber() != dice2.getDiceNumber()) || (doubles < 0));
    }

    /**
     * Handle switch turn
     * @author Sabah
     * @author Shrimei
     * @author Thanuja
     */
    public void handleSwitchTurn(){
        if (cannotReRoll()){
            this.switchTurn(); // if 2 or more players remaining
            doubles = 0;
        }else {
            doubles += 1;
            if (doubles >= 3) {  // when player rolls doubles more than 3 times

                this.addCurrentPlayerToJail(dice1.getDiceNumber() + dice2.getDiceNumber());
                for (MonopolyInterfaceView view : this.views){
                    view.handleJailEntered("You rolled 3 doubles! Go to Jail.");
                }

                this.switchTurn(); // switches the turn (in milestone 3 change it to go to jail)
                doubles = 0;
            }
        }

        for (MonopolyInterfaceView view : this.views){
            view.handlePlayerState();
        }

        if (getCurrentPlayer() instanceof PlayerAI){
            Timer timer = new Timer(2000, new MyTimerActionListener());
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Call handleMove on AI Player after 2 seconds of displaying their state
     * @author Shrimei
     */
    class MyTimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            handleMove();
        }
    }

    /**
     * Handle roll based on type of box player is on
     * @author Thanuja
     */
    public void handleRoll() {
        if (getCurrentPlayer().isJailTurn() && !exitJailBeforeRoll()) {
            handleJailTurn();  // handle when a player is still in jail
        }else{
            handleMove(); // handle a normal roll
        }
    }

    /**
     * Check if current player can and wants to exit jail early
     * @author Thanuja
     * @return          true if exited jail early, false otherwise
     */
    private boolean exitJailBeforeRoll(){
        boolean exitedEarly = false;
        Player currentPlayer = getCurrentPlayer();
        Jail jail = board.getJailSquare();
        // can pay 50 dollar fine to the Bank before throwing the dice for first/second turn in Jail
        if ((jail.getJailTime(currentPlayer) <= 2) && (currentPlayer.getMoney() >= 50)){
            boolean wantToPayExitFee = false;
            for (MonopolyInterfaceView view : this.views){
                if (view.askIfJailExit()){ // only 1 view has to say yes
                    wantToPayExitFee = true;
                }
            }

            if (wantToPayExitFee){
                boolean canPayExitFee = currentPlayer.payRent(50);
                if (canPayExitFee) {
                    this.removeCurrentPlayerFromJail();
                    for (MonopolyInterfaceView view : this.views) {
                        view.handleJailExited("You paid " + board.getCurrency() + "50 fine! Exit Jail.");
                    }

                    exitedEarly = true;
                } // else, cannot pay exit fee (continue jail round)
            }
        }
        return exitedEarly;
    }

    /**
     * Handle when current player exits jail after rolling the dice
     * @author Thanuja
     * @param roll          the roll the current player rolled
     * @param message       the reason for exiting jail
     */
    private void exitJailAfterRoll(int roll, String message){
        Player currentPlayer = getCurrentPlayer();
        this.removeCurrentPlayerFromJail();
        currentPlayer.changePosition(roll);
        for (MonopolyInterfaceView view : this.views) {
            view.handleJailExited(message);
        }
        // cannot call Game.handleMove() because player already rolled the dice, so call view.handleRoll() instead
        for (MonopolyInterfaceView view : this.views){
            view.handleRoll(); // update player position, show the card they landed on, handle purchase/rent, etc
        }
    }

    /**
     * Handle if current player's turn needs to be skipped
     * @author Sabah
     * @author Thanuja
     */
    private void handleJailTurn(){
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isJailTurn()){

            Jail jail = board.getJailSquare();

            int roll = dice1.rollDice() + dice2.rollDice();
            // only move player if they can exit jail
            for (MonopolyInterfaceView view : this.views){ // update with jail roll
                view.handleRoll();
            }

            if (dice1.getDiceNumber() == dice2.getDiceNumber()) {
                exitJailAfterRoll(roll, "You rolled doubles! Exit Jail.");
            } else {
                jail.incrementJailTime(currentPlayer);
            }

            if (jail.getJailTime(currentPlayer) > 3) { // end of third round in jail and still in jail
                boolean canPayExitFee = currentPlayer.payRent(50);
                if (canPayExitFee) {
                    exitJailAfterRoll(roll, "You paid " + board.getCurrency() + "50 fine! Exit Jail.");
                }else{
                    currentPlayerBankrupt();
                }
            }

            for (MonopolyInterfaceView view : this.views){
                view.handleEndOfTurn();
            }

            for (MonopolyInterfaceView view : this.views){ // update with player change after end of turn
                view.handlePlayerState();
            }
        }
    }

    /**
     * Rolls the dice and moves the player
     * @author Shrimei
     * @author Thanuja
     * @author Maisha
     * @author Sabah
     * @author Thanuja
     */
    public void handleMove(){

        // old docstring from play() method
        /*Allows players to play the game and gives them options to roll, quit, buy properties and pay rent.
        Ends the game if the player chooses 'quit' or there is only 1 player remaining
        returns 0 if continue, 1 to quit*/

        for (MonopolyInterfaceView view : this.views){
            view.handlePlayerState();
        }

        //handleSkipTurn();
        //don't want rest to occur if player was in jail

        Player currentPlayer = getCurrentPlayer(); // only get actual current player after skip turn was checked

        int roll = dice1.rollDice() + dice2.rollDice();
        currentPlayer.changePosition(roll); //move the player

        handleIfGo(roll);
        // show property card
        // update views based on dice roll
        // move the players
        for (MonopolyInterfaceView view : this.views){
            view.handleRoll();
        }

        for (MonopolyInterfaceView view : this.views) {
            view.handlePlayerState();
        }

        handleIfGoToJail(roll);

        for (MonopolyInterfaceView view : this.views){
            view.handleEndOfTurn();
        }
    }


    /**
     * Check if current player is eligible to receive Go amount based on roll
     * @author Thanuja
     * @param roll          the player's roll
     * @return              true if player can get go amount, false otherwise
     */
    private boolean canGetGoAmount(int roll){
        int newPosition = getCurrentPlayer().getPosition() % board.getSquares().size();
        return ((newPosition - roll) < board.getGoPosition());
    }

    /**
     * Handles GO, landing or passing position 0, player collects 200 dollars
     * @author Sabah
     * @param roll takes the dice amount
     */
    public void handleIfGo(int roll){
        Player currentPlayer = getCurrentPlayer();

        if (canGetGoAmount(roll)){
            currentPlayer.collect200();
            for (MonopolyInterfaceView view : this.views){
                view.handlePassedGo();
            }
        }
    }

    /**
     * Handles landing Go To Jail, displays message to player
     * @author Thanuja
     * @param roll takes the dice amount
     */
    public void handleIfGoToJail(int roll){
        Player currentPlayer = getCurrentPlayer();
        int newPosition = currentPlayer.getPosition() % board.getSquares().size();

        if(newPosition == board.getGoToJailPosition()){
            this.addCurrentPlayerToJail(roll);
            for (MonopolyInterfaceView view : this.views){
                view.handleJailEntered("You landed on Go to Jail!");
            }
        }
    }

    /**
     * Getter for the list of players
     * @author Sabah
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the current player number
     * @author Sabah
     * @return players
     *
     */
    public int getCurrentPlayerNumber() { // returns int, do we need to change?
        return currentPlayerNumber;
    }

    /**
     * Get position (square) of the current player
     * @author Shrimei
     * @return square of the current player
     */
    public Square getCurrentSquare() {
        return board.getSquares().get(getCurrentPlayer().getPosition() % board.getSquares().size());
    }

    /**
     * Load a previous game
     * @author Thanuja
     * @return          true if loaded successfully, false otherwise
     */
    public boolean loadGame(){
        boolean loadedSuccessfully = false;
        for (MonopolyInterfaceView view : this.views){
            if (view.askIfLoadPreviousGame()) {
                String fileName = view.getFilenameOfGame();
                if ((fileName != null) && (!fileName.equals(""))) {
                    loadedSuccessfully = loadGame(fileName);
                    if (!loadedSuccessfully){
                        view.handleGameLoadFailure( fileName + ".ser");
                    }
                }
            }
        }
        return loadedSuccessfully;
    }


    /**
     * Import the Game's players and board from a serialized file
     * @author Thanuja
     * @param fileName      filename to import from (without extension)
     * @return              true if successful, false if exception occurs
     */
    public boolean loadGame(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object object;

            object = objectInputStream.readObject();
            this.currentPlayerNumber = (int) object;

            object = objectInputStream.readObject();
            this.doubles = (int) object;

            object = objectInputStream.readObject();
            if(object instanceof Board){
                this.board = (Board) object;
            }

            while ((object = objectInputStream.readObject()) != null && (object instanceof Player)) {
                Player player = (Player) object;
                this.addPlayer(player);
            }

            fileInputStream.close();
            objectInputStream.close();

            return true;
        } catch (ClassNotFoundException | IOException e) {
            return false;
        }
    }

    /**
     * Save game for each of the views
     * @author Thanuja
     */
    public void saveGame(){
        for (MonopolyInterfaceView view : this.views){
            String fileName = view.getFilenameOfGame();
            if ((fileName != null)&& !fileName.equals("")) {
                boolean savedSuccesfully = saveGame(fileName);
                view.handleGameSaving(savedSuccesfully, fileName + ".ser");
            }
        }
    }

    /**
     * Save the Game's players and board to a serialized file
     * @author Thanuja
     * @param fileName      filename to save as (without extension)
     * @return              true if successful, false if exception occurs
     */
    public boolean saveGame(String fileName){
        try {
            // use ObjectOutputStream for object serialization
            FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // may not need to save the dice

            objectOutputStream.writeObject(currentPlayerNumber);
            objectOutputStream.flush();
            objectOutputStream.writeObject(doubles);
            objectOutputStream.flush();

            objectOutputStream.writeObject(this.board);
            objectOutputStream.flush();

            for (Object player : this.players) {
                objectOutputStream.writeObject(player);
                objectOutputStream.flush();
            }

            objectOutputStream.writeObject("end of file"); // to allow reloading without errors
            objectOutputStream.flush();

            fileOutputStream.close();
            objectOutputStream.close();

            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

}



