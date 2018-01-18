package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;

class Wall {
    private ArrayList<Tile> wall;
    final int deadWallLen = 14;
    
    Wall(Tile[] tileset) {
        wall =  new ArrayList<Tile>(Arrays.asList(tileset));
    }
    
    public Tile[] getWallArr() {
        return (Tile[]) wall.toArray();
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
            output[i++] = new Tile(t, r);
        }
    }
    for (int r = 1; r <= 4; r++)  {
        output[i++] = new Tile(TileType.WIND, r);
    }
    for (int r = 1; r <= 3; r++) {
        output[i++] = new Tile(TileType.DRAGON, r);
    }
        return output;
    }
}
