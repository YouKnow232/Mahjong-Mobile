package com.renhou.model;

/**
 * HandScore is an object used to group various data gathered after analyzing a complete hand.
 * Han count and basic points are calculated and stored after initialization.
 * 
 * @author Chase
 *
 */
public class HandScore implements Comparable<HandScore>{
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
        
        
        if (han < 5) {
            int rawBasicPoints = (int)(fu * Math.pow(2, hanTally+2));
            
            if (rawBasicPoints > 2000)
                this.basicPoints = 2000;
            else
                this.basicPoints = rawBasicPoints;
            
        } else if (han < 6) {
            this.basicPoints = 2000;
        } else if (han < 8) {
            this.basicPoints = 3000;
        } else if (han < 11) {
            this.basicPoints = 4000;
        } else if (han < 13) {
            this.basicPoints = 6000;
        } else {
            if (yakuList[0].isYakuman())
                this.basicPoints = 8000 * yakuList.length;
            else
                this.basicPoints = 8000;
        }
    }
    
    // Getters
    public Yaku[] getYakuList() {return yakuList;}
    public int getFu() {return fu;}
    public int getHan() {return han;}
    public int getBasicPoints() {return basicPoints;}
    
    @Override
    public int compareTo(HandScore hs) {
        return new Integer(basicPoints).compareTo(new Integer(hs.getBasicPoints()));
    }
}