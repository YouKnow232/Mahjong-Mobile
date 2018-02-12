package com.renhou.model;

/**
 * HandScore is an object used to group various data gathered after analyzing a complete hand.
 * Han count and basic points are calculated and stored after initialization.
 * 
 * @author Chase
 *
 */
public class HandScore {
    private final Yaku[] yakuList;
    private final int fu;
    private final int han;
    private final int basicPoints;
    
    HandScore(Yaku[] yakuList, int fu, boolean isOpen) {
        this.yakuList = yakuList;
        this.fu = fu;
        
        int hanTally = 0;
        if (isOpen)
            for (Yaku y : yakuList)
                hanTally += y.getOpenHanValue();
        else
            for (Yaku y : yakuList)
                hanTally += y.getClosedHanValue();
         
        this.han = hanTally;
        
        this.basicPoints = (int)(fu * Math.pow(2, hanTally+2));
    }
    
    // Getters
    public Yaku[] getYakuList() {return yakuList;}
    public int getFu() {return fu;}
    public int getHan() {return han;}
    public int getBasicPoints() {return basicPoints;}
}