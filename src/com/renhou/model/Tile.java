package com.renhou.model;

public abstract class Tile implements Comparable<Tile> {
    protected final Suit suit;
    
    public Tile(Suit suit) {
        this.suit = suit;
    }
    
    public Suit getSuit() {
        return suit;
    }
}
