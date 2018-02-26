package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;

class Hand {
    private ArrayList<Tile> hiddenHand;
    private ArrayList<Tile[]> openMelds;
    private ArrayList<Tile> borrowedTiles;
    
    Hand(Tile[] tiles) {
        hiddenHand = new ArrayList<Tile>(Arrays.asList(tiles));
        openMelds = new ArrayList<Tile[]>();
        borrowedTiles = new ArrayList<Tile>();
    }
    
    Hand () {
        this(new Tile[] {});
    }
    
    void callMeld(Tile[] tiles, Tile t) {
        if (t != null) {
            Tile[] meld = new Tile[tiles.length+1];
            for (int i = 0; i < tiles.length; i++)
                meld[i] = tiles[i];
            meld[tiles.length] = t;
            borrowedTiles.add(t);
        } else {
            openMelds.add(tiles);
        }
        
        for (Tile tile : tiles) {
            hiddenHand.remove(tile);
        }
    }
    
    void callMeld(Tile[] tiles) {
        callMeld(tiles, null);
    }
    
    void discard(Tile t) {
        hiddenHand.remove(t);
    }
    
    void draw(Tile t) {
        hiddenHand.add(t);
    }
    
    public boolean isBorrowed(Tile t) {
        return borrowedTiles.contains(t);
    }
    
    public Tile[] getHandArr() {
        return hiddenHand.toArray(new Tile[hiddenHand.size()]);
    }
    
    public Tile[][] getMelds() {
        return openMelds.toArray(new Tile[openMelds.size()][]);
    }
    
    public Tile[] getBorrowedTiles() {
        return borrowedTiles.toArray(new Tile[borrowedTiles.size()]);
    }
    
    // TODO: Test code, pls delete later
    public static void main(String[] args) {
        Tile[] arr = new Tile[] {new Tile("2p"), new Tile("6p"), new Tile("9p"), new Tile("5m"), new Tile("1d"), new Tile("2w"), new Tile("5s")};
        Arrays.sort(arr);
        for (Tile i : arr) {
            System.out.print(i);
            System.out.print(", ");
        }System.out.println();
        
        Tile[] tileSet = Wall.riichiTileSet();
        for (Tile i : tileSet) {
            System.out.print(i);
            System.out.print(", ");
        }System.out.println();
        
    }
    
    @Override
    public String toString() {  // TODO: Debugging interpretation, delete later
        String output = "open melds: ";
        
        for (Tile[] meld : openMelds) {
            output.concat("[");
            for (Tile t : meld) {
                output.concat(t.toString() + ", ");
            }
            output.concat("], ");
        }
        
        output.concat("\n");
        
        for (Tile t : hiddenHand) {
            output.concat(t.toString() + ", ");
        }
        
        return output;
    }
}
