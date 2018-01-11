package com.renhou.model;

public class SuitedTile extends Tile {
    private final int rank;
    private final boolean red;
    
    public SuitedTile(Suit suit, int rank, boolean red) {
        super(suit);
        this.rank = rank;
        this.red = red;
    }
    
    public SuitedTile(Suit suit, int rank) {
        this(suit, rank, false);
    }
    
    public int getRank() {
        return rank;
    }
    
    public boolean isRed() {
        return red;
    }
    
    @Override
    public String toString() {
        String redstr = "";
        if(red) {
            redstr = "RED "; 
        }
        return redstr + rank + " " + suit;
    }
    
    public int compareTo(Tile t) {
        Integer selfSuitOrd = this.getSuit().ordinal();
        Integer tSuitOrd = t.getSuit().ordinal();
        int suitCompare = selfSuitOrd.compareTo(tSuitOrd);
        if (suitCompare == 0) {
            Integer selfRank = this.getRank();
            Integer tRank = ((SuitedTile)t).getRank();
            return selfRank.compareTo(tRank);
        } else {
            return suitCompare;
        }
    }
}
