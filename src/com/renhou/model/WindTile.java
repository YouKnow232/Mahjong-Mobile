package com.renhou.model;

public class WindTile extends Tile {
    private final Wind type;
    
    public WindTile(Wind type) {
        super(Suit.WIND);
        this.type = type;
    }
    
    public Wind getType() {
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
