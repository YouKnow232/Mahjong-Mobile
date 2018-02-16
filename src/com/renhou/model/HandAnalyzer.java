package com.renhou.model;

import java.util.ArrayList;

public class HandAnalyzer {
    
    /**
     * Compiles a list of all possible melds that can be made from the given list of tiles.
     * The melds list is given as a triplet of indices from the give list of tiles.
     * 
     * @param hand A sorted array of tiles
     * @return A list of melds each represented by a list of 3 indices 
     */
    
    // NOTE: Optimize duplicate pruning
    private static int[][] listMelds(Tile[] hand) { 
        ArrayList<int[]> meldList = new ArrayList<int[]>();
        
        for (int i = 0; i < hand.length-2; i++) {
            for (int j = i+1; j < hand.length-1; j++) {
                if (hand[i].getType() == hand[j].getType()) {   // TileType check, find new base tile if false
                    if (hand[i].getRank() == hand[j].getRank()) {   // Triplet detection - second tile
                        
                        for (int k = j+1; k < hand.length; k++) {
                            
                            if (hand[j].getType() == hand[k].getType() &&   // Triplet detection - third tile
                                hand[j].getRank() == hand[k].getRank()) {
                                
                                meldList.add(new int[] {i,j,k});
                            }
                        }
                        
                    } else if (hand[i].getType() != TileType.WIND &&    // Sequence detection - second tile
                               hand[i].getType() != TileType.DRAGON &&
                               hand[i].getRank() == hand[j].getRank() - 1){
                        
                        for (int k = j+1; k < hand.length; k++) {
                            
                            if (hand[j].getType() == hand[k].getType() &&   // Sequence detection - third tile
                                hand[j].getRank() == hand[k].getRank() - 1) {
                                
                                meldList.add(new int[] {i,j,k});
                            }
                        }
                    }
                } else {    // TileType check, stop search for second meld tile and start on new base tile
                    break;
                }
            }
        }
        
        // post-detection pruning
        ArrayList<Tile[]> meldListTiles = new ArrayList<Tile[]>();
        
        for (int[] meld : meldList) {   // Build tile representation of meldList
            meldListTiles.add(new Tile[] {hand[meld[0]], hand[meld[1]], hand[meld[2]]});
        }
        
        ArrayList<Integer> toRemove = new ArrayList<Integer>(); // Finding redundancies
        for (int i = 0; i < meldListTiles.size() - 1; i++) {
            if (!toRemove.contains(i)) {
                for (int j = i+1; j < meldListTiles.size(); j++) {
                    if (meldListTiles.get(i)[0].getType() == meldListTiles.get(j)[0].getType() &&
                        meldListTiles.get(i)[0].getRank() == meldListTiles.get(j)[0].getRank() &&
                        meldListTiles.get(i)[1].getType() == meldListTiles.get(j)[1].getType() &&
                        meldListTiles.get(i)[1].getRank() == meldListTiles.get(j)[1].getRank() &&
                        meldListTiles.get(i)[2].getType() == meldListTiles.get(j)[2].getType() &&
                        meldListTiles.get(i)[2].getRank() == meldListTiles.get(j)[2].getRank()) {
                        
                        toRemove.add(j);
                    }
                }
            }
        }
        
        for (int i = toRemove.size() - 1; i >= 0; i--) {    // Pruning redundancies
            meldList.remove(toRemove.get(i).intValue());
        }
        
        
        return meldList.toArray(new int[meldList.size()][]);
    }
    
    /**
     * Outputs a list of valid groupings of melds such that for each grouping no tile is
     * in two melds at once and the remaining two tiles form a pair.
     * 
     * Output will be converted to a 3D list of tiles from a 3D list of ints.
     * The first dimension chooses between different groupings,
     * the second dimension chooses between melds/the pair in the grouping,
     * the third dimension chooses between each tile of the meld in the grouping.
     * 
     * @param hand The reference used to calculate melds
     * @param melds A list of melds
     * @return Meld groupings, each grouping ends with the indices of the pair.
     */
    private static Tile[][][] groupMelds(Tile[] hand, int[][] melds) {
        // If no valid grouping exists make checks for kokushi and toitoi
        
        return null; // TODO: placeholder
    }
    
    /**
     * Attributes tags to the hand and the hands component melds/pair.
     * 
     * output is a 3D list of MeldTag enums.
     * The first dimension chooses which grouping,
     * the second dimension chooses which aspect of the hand the tags are referencing:
     *      [meld1],[meld2],[meld3],[meld4],[pair],[whole hand]
     * the third dimension chooses between each individual tag.
     * 
     * @param meldGroups Contains at least one valid combination of melds from the closed hand
     * @param openMelds The fixed melds from the original hand
     * @param seat The seat wind of the player
     * @param round The round wind
     * @return A set of tags per meld grouping
     */
    private static MeldTag[][][] tagMeldGroups(Tile[][][] meldGroups, Tile[][] openMelds, Tile draw, Wind seat, Wind round) {
        
        return null; // TODO: placeholder
    }
    
    private static Yaku[] detectYaku(MeldTag[][] tags) {
        
        return null; // TODO: placeholder
    }
    
    /**
     * Counts fu from meld tags and winds. Output is given as a list of length 2.
     * First item is the actual fu score, second item is pinfu indicator given as a 0 or a 1. 
     * @param tags
     * @return
     */
    private static int[] countFu(MeldTag[][] tags) {
        
        return new int[] {-1, -1}; // TODO: placeholder
    }
    
    /**
     * Analyzes a complete hand's contents, giving a list of yaku and counting fun and han
     * 
     * @param h The Hand to be analyzed
     * @param draw A tile object, the most recently drawn/called tile
     * @param dora A list of Tile objects that are equivalent to the dora tile
     * @param seat The seat wind of the player whose hand is being scored
     * @param round The round wind
     * @return A descriptive HandScore object
     */
    public static HandScore analyze(Hand h, Tile draw, Tile[] dora, Wind seat, Wind round, Yaku[] situationalYaku) {
        boolean isOpen = (h.getMelds().length > 0);
        Tile[] handArr = h.getHandArr();
        Tile[][] fixedMelds = h.getMelds();
        int[][] closedMelds = listMelds(handArr);
        Tile[][][] closedMeldGroups = groupMelds(handArr, closedMelds);
        MeldTag[][][] tags = tagMeldGroups(closedMeldGroups, fixedMelds, draw, seat, round);
        
        
        // Detects yaku in each meld grouping and determines the best scoring grouping
        Yaku[][] yakuLists = new Yaku[tags.length][];
        int[] hanValues = new int[tags.length];
        
        for (int i = 0; i < tags.length; i++) {
            yakuLists[i] = detectYaku(tags[i]);
            int total = 0;
            for (Yaku y : yakuLists[i]) {
                if (isOpen)
                    total += y.getOpenHanValue();
                else
                    total += y.getClosedHanValue();
            }
            hanValues[i] = total;
        }
        
        int bestIndex = 0;
        int bestHanValue = 0;
        for (int i = 0; i < tags.length; i++) {
            if (hanValues[i] > bestHanValue) {
                bestIndex = i;
                bestHanValue = hanValues[i];
            }
        }
        
        // Count fu
        int[] fuOutput = countFu(tags[bestIndex]);
        int fu = fuOutput[0];
        
        // Compile yaku
        Yaku[] finalYakuList = new Yaku[yakuLists[bestIndex].length + situationalYaku.length + fuOutput[1]];
        int i = 0;
        for (Yaku y : yakuLists[bestIndex])
            finalYakuList[i++] = y;
        for (Yaku y : situationalYaku)
            finalYakuList[i++] = y;
        if (fuOutput[1] > 1)
            finalYakuList[i] = Yaku.PINFU;
        
        return new HandScore(finalYakuList, fu, isOpen);
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
        int x = 0;
        for (int[] meld : melds) {
            System.out.print(x+": [");
            for (int i : meld) {
                System.out.print(initTiles[i] + ",");
            }
            System.out.println("]");
            x++;
        }
    }
}
