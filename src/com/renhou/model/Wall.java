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
     * */
    public static Tile[] riichiTileSet() {
        Tile[] output = new Tile[136];
        //for (int i = 0; i < ) {
        	//TODO: init standard riichi mahjong tiles
        //}
        return output;
    }
}
