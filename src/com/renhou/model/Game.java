package com.renhou.model;

public class Game {
    private int riichiPot;
    private int honbaCounter;
    private Wall wall;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player[] players;
    
    private int goAround;
    private Player activePlayer;
    
    public Game(String p1Name, String p2Name, String p3Name, String p4Name) {
        riichiPot = 0;
        honbaCounter = 0;
        wall = new Wall(Wall.riichiTileSet());
        this.p1 = new Player(p1Name, 25000);
        this.p2 = new Player(p2Name, 25000);
        this.p3 = new Player(p3Name, 25000);
        this.p4 = new Player(p4Name, 25000);
        this.players = new Player[] {p1, p2, p3, p4};
        int goAround = 1;
        Player activePlayer = p1;
        
        // Draw hands
        for (int i = 0; i < 3; i++) {
            for (Player p : players) {
                for (int j = 0; j < 4; j++) {
                    p.getHand().draw(wall.draw());
                }
            }
        }
        for (Player p : players) {
            p.getHand().draw(wall.draw());
        }
        
    }
    
    //TODO: put some methods here
}
