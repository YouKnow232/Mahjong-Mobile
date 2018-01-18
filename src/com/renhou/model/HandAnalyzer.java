package com.renhou.model;

public class HandAnalyzer { //Probably just put this in the Game class or even make it its own package in the future
    
    /**
     * Groups a hand into possible meld configurations.  Should return null when no valid meld configuration.
     *  7 pairs, and a special case for Kokushi Musou should be handled.
     * 
     * @param h A Hand object
     * @return 3D Tile array:
     *             first dimension: meld arrangements
     *             second dimension: melds/pair
     *             third dimension: individual tiles in a meld/pair
     */
    private static Tile[][][] meldConfig(Hand h) {
        
        //TODO: Break up a hand into complete melds
        
        return new Tile[0][0][0]; //TODO: Place holder
    }
    
    /**
     * Tags each meld and groups them in the same order as their input, then adds a group of Tags the hand as a whole.
     * 
     * @param h
     * @return
     */
    private static YakuTags[][] tagMelds(Tile[][] melds) {
        YakuTags[][] tags = new YakuTags[melds.length+1][];    //TODO: I don't even know if this partial space allocation works.
        
        //TODO: Tag melds/hand to make yaku recognition easier. (i.e. sequence of chi, triple dragons)
        
        return tags;
    }
    
    /**
     * Outputs a list of yaku that are compliant with the given list of yaku tags.
     * //Consider storing the yaku logic as a table of boolean logic statements using the yaku tags.
     * 
     * @param tags YakuTag list from tagMelds
     * @return String[] a string array of yaku names. //perhaps change this to Yaku enum with imbeded han values and yaku override information (e.g. ryanpeikou overriding chitoittsu).
     */
    private static String[] listYaku(YakuTags[][] tags) {
        
        //TODO: Output applicable yaku based on tags.
        
        return new String[0]; //TODO: Place Holder
    }
    
    /**
     * ### This setup doesn't account for situational timing yaku such as houtei or ippatsu! ###
     * ### These may be accounted for in the game logic before making the HandAnalyzer call. ###
     * 
     * @param h The Hand to be analyzed
     * @param d A tile object, the most recently drawn/called tile
     * @return A string list of yaku
     */
    public static String[] yakuAnal(Hand h, Tile d) {
        Tile[][][] meldConfigs = meldConfig(h);
        YakuTags[][][] tagSets = new YakuTags[meldConfigs.length][][];
        String[][] yakuSets = new String[meldConfigs.length][];
        
        int i = 0;
        for (Tile[][] meldConfig : meldConfigs) {
            tagSets[i] = tagMelds(meldConfig);
            yakuSets[i] = listYaku(tagSets[i]);
            i++;
        }
        
        //TODO: pick the most valuable yaku set
        
        return yakuSets[0]; //TODO: Place holder
    }
    
    //Main method for testing
    public static void main(String[] args) {
        Tile[] initTiles = {new Tile(TileType.MAN, 1), new Tile(TileType.MAN, 2), new Tile(TileType.MAN, 3),
                            new Tile(TileType.SOU, 2), new Tile(TileType.SOU, 4), new Tile(TileType.SOU, 6), 
                            new Tile(TileType.PIN, 9), new Tile(TileType.PIN, 9), new Tile(TileType.PIN, 9), 
                            new Tile(TileType.WIND, 2), new Tile(TileType.WIND, 2), new Tile(TileType.WIND, 2), new Tile(TileType.DRAGON, 2)};
        
        Hand hand = new Hand(initTiles);
        Tile[][][] tags = meldConfig(hand);
        
        // Print block for debugging.
        for (Tile[][] meldConfig : tags) {
            for (Tile[] meld : meldConfig) {
                for (Tile tile : meld) {
                    System.out.print(tile);
                }
                System.out.println();
            }
            System.out.println();
        }
        
        int[] iarr = new int[] {1,2,3};
        System.out.println(iarr);;
    }
    
    private enum YakuTags { //For use in the tagging process of tagHand() and listYaku()
        //Meld specific tags (need to reference a meld)
        MELDMAN, MELDPIN, MELDSOU, MELDSIMPLES, MELDTERMINALS, MELDHONORS, MELD1TERMINAL, MELDCHII, MELDPON, MELDKAN, MELDOPEN, MELDCLOSED, MELDYAKUHAI,
        //Meld relationship tags (need to reference two or more melds;
        MELDSAMERANK, MELDSAMESUIT, MELDSTRAIGHT,
        //Pair specific tags
        PAIRSIMPLES, PAIRTERMINALS, PAIRHONORS, PAIRYAKUHAI,
        //General Hand Tags (meta tags derived from meld/pair tags)
        HANDMAN, HANDPIN, HANDSOU, HANDSIMPLES, HANDTERMINALS, HANDHONORS, HANDCHII, HANDPON, HANDKAN, HANDOPEN, HANDCLOSED,
        HANDNOMAN, HANDNOPIN, HANDNOSOU,
        //Special exceptions
        ALLPAIRS, KOKUSHI
    }
}
