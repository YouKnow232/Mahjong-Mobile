package com.renhou.model;

import java.util.ArrayList;

class Discard {
    private ArrayList<Tile> tileArr;
    private ArrayList<Tile> takenTiles;
    private Tile riichiTile;
    private boolean nextTileSideways;
    
    Discard() {
        tileArr = new ArrayList<Tile>();
        takenTiles = new ArrayList<Tile>();
        nextTileSideways = false;
    }
    
    void addTile(Tile t, boolean riichi, boolean taken) {
        if ((riichi && riichiTile == null) || nextTileSideways) {
            if (taken) {
                nextTileSideways = true;
            } else {
                riichiTile = t;
                nextTileSideways = false;
            }
        }
        
        if (taken) {
            takenTiles.add(t);
        }
        
        tileArr.add(t);
    }
    
    void addTile(Tile t) {
        addTile(t, false, false);
    }
    
    
    public Tile[] getTiles() {
        return tileArr.toArray(new Tile[tileArr.size()]);
    }
    
    public Tile[] getTakenTiles() {
        return takenTiles.toArray(new Tile[takenTiles.size()]);
    }
    
    public Tile getRiichiTile() {
        return riichiTile;
    }
}
