package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Wall {
    public static final int deadWallLen = 14;
    
    private ArrayList<Tile> wall;
    private int doraCount;
    
    Wall(Tile[] tileset) {
        wall = new ArrayList<Tile>(Arrays.asList(tileset));
        Collections.shuffle(wall);
        
        doraCount = 1;
    }
    
    Tile draw() {
        Tile t = wall.get(wall.size()-1);
        wall.remove(wall.size()-1);
        return t;
    }
    
    void increaseDora() {
        doraCount++;
    }
    
    public Tile[] getDora() {
        Tile[] output = new Tile[doraCount];
        for (int i = 0; i < doraCount; i++) {
            output[i] = wall.get(4 + i*2);
        }
        return output;
    }
    
    Tile[] getUradora() {
        Tile[] output = new Tile[doraCount];
        for (int i = 0; i < doraCount; i++) {
            output[i] = wall.get(5 + i*2);
        }
        return output;
        
    }
    
    Tile[] getWallArr() {
        return wall.toArray(new Tile[wall.size()]);
    }
    
    int getDoraCount() {
        return doraCount;
    }
    
    /**
     * Static method for initializing a tileset suitable for riichi mahjong.
     * 
     * Consider moving this method to a rules class or package
     * 
     * Also Consider just saving the array to disk and loading it instead of reconstructing it every time.
     * */
    public static Tile[] riichiTileSet() {
        Tile[] output = new Tile[136];
        int i = 0;
        for (TileType t : new TileType[] {TileType.MAN, TileType.PIN, TileType.SOU}) {
            for (int r = 1; r <= 9; r++) {
                for (int x = 0; x < 4; x++) {
                    if (r == 5 && x == 0)   // Pretty inefficient
                        output[i++] = new Tile(t, r, true);
                    else
                        output[i++] = new Tile(t, r);
                }
            }
        }
        for (int r = 1; r <= 4; r++)  {
            for (int x = 0; x < 4; x++)
                output[i++] = new Tile(TileType.WIND, r);
        }
        for (int r = 1; r <= 3; r++) {
            for (int x = 0; x < 4; x++)
                output[i++] = new Tile(TileType.DRAGON, r);
        }
        return output;
    }
}
