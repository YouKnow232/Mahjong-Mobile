package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;

public class HandAnalyzer { // Probably just put this in the Game class or even make it its own package in the future
    
    /**
     * Compiles a list of all possible melds that can be made from the given list of tiles.
     * The melds list is given as a triplet of indices from the give list of tiles.
     * 
     * @param hand A sorted array of tiles
     * @return A list of melds each represented by a list of 3 indices 
     */
    
    // TODO: Optimize by adding for loop breaks
    // TODO: Eliminate functionally identical melds
    //      -prune melds afterwards
    //      -detect from a pruned hand (only prune 4th identical tile for triplets)
    public static int[][] listMelds(Tile[] hand) { 
        ArrayList<int[]> meldList = new ArrayList<int[]>();
        
        for (int i = 0; i < hand.length-2; i++) {
            for (int j = i+1; j < hand.length-1; j++) {
                
                if (hand[i].getType() == hand[j].getType() && // Triplet detection - second tile
                    hand[i].getRank() == hand[j].getRank()) {
                    
                    for (int k = j+1; k < hand.length; k++) {
                        
                        if (hand[j].getType() == hand[k].getType() &&   // Triplet detection - third tile
                            hand[j].getRank() == hand[k].getRank()) {
                            
                            meldList.add(new int[] {i,j,k});
                        }
                    }
                    
                } else if (hand[i].getType() != TileType.WIND &&    // Sequence detection - second tile
                           hand[i].getType() != TileType.DRAGON &&
                           hand[i].getType() == hand[j].getType() &&
                           hand[i].getRank() == hand[j].getRank() - 1){
                    
                    for (int k = j+1; k < hand.length; k++) {
                        
                        if (hand[j].getType() == hand[k].getType() &&   // Sequence detection - third tile
                            hand[j].getRank() == hand[k].getRank() - 1) {
                            
                            meldList.add(new int[] {i,j,k});
                        }
                    }
                }
            }
        }
        
        return meldList.toArray(new int[meldList.size()][]);
    }
    
    /**
     * Tags each meld and groups them in the same order as their input, then adds a group of Tags the hand as a whole.
     * 
     * @param h
     * @return
     */
    public static MeldTag[][] tagMelds(Tile[][] melds) {
        MeldTag[][] tags = new MeldTag[melds.length+1][];    // TODO: I don't even know if this partial space allocation works.
        
        // TODO: Tag melds/hand to make yaku recognition easier. (i.e. sequence of chi, triple dragons)
        
        return tags;
    }
    
    /**
     * Outputs a list of yaku that are compliant with the given list of yaku tags.
     * //Consider storing the yaku logic as a table of boolean logic statements using the yaku tags.
     * 
     * @param tags MeldTag list from tagMelds
     * @return String[] a string array of yaku names. //perhaps change this to Yaku enum with imbeded han values and yaku override information (e.g. ryanpeikou overriding chitoittsu).
     */
    public static String[] listYaku(MeldTag[][] tags) {
        
        // TODO: Output applicable yaku based on tags.
        
        return new String[0]; //TODO: Place Holder
    }
    
    /**
     * ### This setup doesn't account for situational timing yaku such as houtei or ippatsu! ###
     * ### These may be accounted for in the game logic before making the HandAnalyzer call. ###
     * 
     * @param h The Hand to be analyzed
     * @param d A tile object, the most recently drawn/called tile
     * @return A descriptive HandScore object
     */
    public static HandScore analyze(Hand h, Tile d) {
        Tile[][][] meldConfigs = null;
        MeldTag[][][] tagSets = new MeldTag[meldConfigs.length][][];
        String[][] yakuSets = new String[meldConfigs.length][];
        
        int i = 0;
        for (Tile[][] meldConfig : meldConfigs) {
            tagSets[i] = tagMelds(meldConfig);
            yakuSets[i] = listYaku(tagSets[i]);
            i++;
        }
        
        // TODO: pick the most valuable yaku set
        
        return null; // TODO: Place holder
    }
    
    // Main method for testing
    public static void main(String[] args) {
        /*Tile[] initTiles = {new Tile(TileType.MAN, 1), new Tile(TileType.MAN, 2), new Tile(TileType.MAN, 3),
                            new Tile(TileType.SOU, 2), new Tile(TileType.SOU, 4), new Tile(TileType.SOU, 6), 
                            new Tile(TileType.PIN, 9), new Tile(TileType.PIN, 9), new Tile(TileType.PIN, 9), 
                            new Tile(TileType.WIND, 2), new Tile(TileType.WIND, 2), new Tile(TileType.WIND, 2),
                            new Tile(TileType.DRAGON, 2), new Tile(TileType.DRAGON, 2)};*/
        
        Tile[] initTiles = {new Tile("1p"), new Tile("2p"), new Tile("3p"), 
                            new Tile("4p"), new Tile("5p"), new Tile("6p"), 
                            new Tile("6p"), new Tile("6p"), new Tile("6p"), 
                            new Tile("2d"), new Tile("3d"), new Tile("1d"), 
                            new Tile("1m"), new Tile("1s")};
        
        
        int[][] melds = listMelds(initTiles);
        
        
        // Print block for debugging.
        for (int[] meld : melds) {
            System.out.print("[");
            for (int i : meld) {
                System.out.print(initTiles[i] + ",");
            }
            System.out.println("]");
        }
    }
}
