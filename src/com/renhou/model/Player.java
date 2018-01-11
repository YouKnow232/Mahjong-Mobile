package com.renhou.model;

public class Player {
    private final String name;
    private int score;
    private Hand hand;
    
    public Player(String name, int score) {
        this.name = name;
        this.score = score = 25000;
        hand = null;
    }
    
    public void drawHand(Tile[] tiles) {
        if (hand == null) {
            hand = new Hand(tiles);
        }
    }
    
    public int scoreAdjust(int adjustment) {
        score += adjustment;
        return score;
    }
    
    public int getScore() {
        return score;
    }
    
    public String getName() {
        return name;
    }
}
