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
        //TODO: remove select tiles from hiddenHand
        openMelds.add(meld);
    }
    
    void replace(Tile old, Tile replacement) {
        //TODO
    }
    
    public Tile[] getHandArr() {
        return (Tile[]) hiddenHand.toArray();
    }
    
    public Tile[][] getMelds() {
        return (Tile[][]) openMelds.toArray();
    }
    
    //TODO: Test code, pls delete later
    public static void main(String[] args) {
        int[] arr = new int[] {3,2,1,5,4,6};
        Arrays.sort(arr);
        for (int i : arr) {
            System.out.print(i);
            System.out.print(", ");
        }
    }
}
