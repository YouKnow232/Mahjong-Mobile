package com.renhou.model;

/**
 * For use with round and seat wind logic.  Not to be confused with the wind tiles.
 * @author Chase
 *
 */
public enum Wind {
    EAST(1), SOUTH(2), WEST(3), NORTH(4);
    
    private int rank;
    
    private Wind(int rank) {
        this.rank = rank;
    }
    
    public int getRank() {return rank;}
}
