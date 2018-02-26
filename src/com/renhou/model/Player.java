package com.renhou.model;

class Player {
    private final String name;
    private int score;
    private Hand hand;
    
    Player(String name, int score) {
        this.name = name;
        this.score = score;
        hand = new Hand();
    }
    
    int scoreAdjust(int adjustment) {
        score += adjustment;
        return score;
    }
    
    public String getName() { return name; }
    public int getScore() { return score; }
    public Hand getHand() { return hand; }
}
