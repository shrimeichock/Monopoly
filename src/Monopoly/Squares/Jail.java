package Monopoly.Squares;

import Monopoly.Player;

import java.util.HashMap;

public class Jail implements Square {

    private String name;
    private String otherName;
    private HashMap<Player, Integer> playersInJail;

    /**
     * Constructor for Jail square
     * @author Thanuja
     */
    public Jail(){
        this.name = "Visiting Jail";
        this.otherName = "In Jail";
        this.playersInJail = new HashMap<>();
    }

    /**
     * Get name of jail square
     * @author Thanuja
     * @return jail name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get name of visiting jail square
     * @author Thanuja
     * @return visiting jail name
     */
    public String getOtherName() {
        return otherName;
    }

    /**
     * Add player to jail list
     * @author Thanuja
     * @param player player to add
     */
    public void addToJail(Player player){
        playersInJail.put(player, 1);
    }

    /**
     * Remove player from jail list
     * @author Thanuja
     * @param player player to remove
     */
    public void removeFromJail(Player player){
        playersInJail.remove(player);
    }

    /**
     * Increase a player's time in jail if they are already in jail
     * @author Thanuja
     * @param player player to increase time for
     */
    public void incrementJailTime(Player player){
        int previousTime = playersInJail.getOrDefault(player, 0);
        playersInJail.replace(player, previousTime + 1); // replace key's entry only if currently mapped to some value
    }

    /**
     * Return how long a specific player has been in jail
     * @author Thanuja
     * @param player player to check time for
     * @return time in jail
     */
    public int getJailTime(Player player){
        return playersInJail.getOrDefault(player, 0);
    }
}
