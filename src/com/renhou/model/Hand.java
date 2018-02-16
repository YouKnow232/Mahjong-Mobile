package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;

class Hand {
    private ArrayList<Tile> hiddenHand;
    private ArrayList<Tile[]> openMelds;
    
    Hand(Tile[] tiles) {
        hiddenHand = new ArrayList<Tile>(Arrays.asList(tiles));
        openMelds = new ArrayList<Tile[]>();
    }
    
    void addMeld(Tile[] meld) {
        // TODO: remove select tiles from hiddenHand
        openMelds.add(meld);
    }
    
    void replace(Tile old, Tile replacement) {
        // TODO
    }
    
    public Tile[] getHandArr() {
        return (Tile[]) hiddenHand.toArray();
    }
    
    public Tile[][] getMelds() {
        return (Tile[][]) openMelds.toArray();
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
