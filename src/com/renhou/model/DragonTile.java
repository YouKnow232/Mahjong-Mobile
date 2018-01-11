package com.renhou.model;

public class DragonTile extends Tile {
    private final Dragon type;
    
    public DragonTile(Dragon type) {
        super(Suit.DRAGON);
        this.type = type;
    }
    
    public Dragon getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return type + " " + suit;
    }
    
    public int compareTo(Tile t) {
        Integer selfSuitOrd = this.getSuit().ordinal();
        Integer tSuitOrd = t.getSuit().ordinal();
        int suitCompare = selfSuitOrd.compareTo(tSuitOrd);
        if (suitCompare == 0) {
            Integer thisOrd = this.getType().ordinal();
            Integer tOrd = ((DragonTile)t).getType().ordinal();
            return thisOrd.compareTo(tOrd);
        } else {
            return suitCompare;
        }
    }
}
