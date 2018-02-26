package com.renhou.model;

/**
 * Wind order is [East, South, West, North]
 * Dragon order is [Red, Green, Blue]
 * 
 * @author Chase
 *
 */
public enum TileType {
    MAN(9, false), PIN(9, false), SOU(9, false),
    WIND(4, true), DRAGON(3, true);
    
    private int rankCap;
    private boolean isHonor;
    
    private TileType(int rankCap, boolean isHonor) {
        this.rankCap = rankCap;
        this.isHonor = isHonor;
    }
    
    public int getRankCap() { return rankCap; }
    public boolean isHonor() { return isHonor; }
}
