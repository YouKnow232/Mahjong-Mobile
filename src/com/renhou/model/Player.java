package com.renhou.model;

class Player {
    private final String name;
    private int score;
    private Hand hand;
    
    Player(String name, int score) {
        this.name = name;
        this.score = score = 25000;
        hand = null;
    }
    
    // should probably but all logic in the Game class
    void drawHand(Tile[] tiles) {
        if (hand == null) {
            hand = new Hand(tiles);
        }
    }
    
    int scoreAdjust(int adjustment) {
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
