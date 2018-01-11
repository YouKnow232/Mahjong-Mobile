package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Wall {
    private ArrayList<Tile> livewall;
    private ArrayList<Tile> deadwall;
    
    public Wall(Tile[] tileset) {
        ArrayList<Tile> wall =  new ArrayList<Tile>(Arrays.asList(tileset));
        deadwall = (ArrayList<Tile>) wall.subList(0, 14);
        livewall = (ArrayList<Tile>) wall.subList(14, wall.size());
    }
    
    public Tile[] getLiveWallArr() {
        return (Tile[]) livewall.toArray();
    }
    
    public Tile[] getDeadWallArr() {
        return (Tile[]) deadwall.toArray();
    }
    
    /**
     * Static method for initializing a tileset suitable for riichi mahjong.
     * */
    public static Tile[] riichiTileSet() {
        Tile[] output = new Tile[136];
        //for (int i = 0; i < ) {
        	//TODO: init standard riichi mahjong tiles
        //}
        return output;
    }
}
