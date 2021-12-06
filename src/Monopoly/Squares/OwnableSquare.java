package Monopoly.Squares;

import Monopoly.Player;

public abstract class OwnableSquare implements Square{
    private Player owner;

    public OwnableSquare(){
        this.owner = null;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public Player getOwner(){
        return owner;
    }

    public abstract int getRent(int number);
    public abstract int getPrice();
}
