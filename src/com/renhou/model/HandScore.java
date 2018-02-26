package com.renhou.model;

import java.util.ArrayList;

/**
 * HandScore is an object used to group various data gathered after analyzing a complete hand.
 * Han count and basic points are calculated and stored after initialization.
 * 
 * @author Chase
 *
 */
public class HandScore implements Comparable<HandScore>{
    private final Yaku[] yakuList;
    private final int dora;
    private final int uraDora;
    private final int redDora;
    private final int fu;
    private final int han;
    private final int basicPoints;
    
    HandScore(Yaku[] yakuList, int dora, int uraDora, int redDora, int fu, boolean isOpen) {
        ArrayList<Yaku> yakumanCheck = new ArrayList<Yaku>();
        for (Yaku y : yakuList) {
            if (y.isYakuman()) {
                yakumanCheck.add(y);
            }
        }
        
        boolean yakuman;
        if (yakumanCheck.size() > 0) {
            yakuman = true;
            this.yakuList = yakumanCheck.toArray(new Yaku[yakumanCheck.size()]);
        } else {
            yakuman = false;
            this.yakuList = yakuList;
        }
        
        this.dora = dora;
        this.uraDora = uraDora;
        this.redDora = redDora;
        this.fu = fu;
        
        int hanTally = 0;
        if (isOpen)
            for (Yaku y : this.yakuList)
                hanTally += y.getOpenHanValue();
        else
            for (Yaku y : this.yakuList)
                hanTally += y.getClosedHanValue();
        
        if (yakuman) {
            han = hanTally;
        } else {
            han = hanTally + dora + uraDora + redDora;
        }
        
        
        if (han < 5) {
            
            int rawBasicPoints = (int)(fu * Math.pow(2, han+2));
            
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
            if (yakuman)
                this.basicPoints = 8000 * yakuList.length;
            else
                this.basicPoints = 8000;
        }
    }
    
    /**
     * Calculates payment from basicPoints for a ron.
     * 
     * @param isDealer - If the winner of the hand was the dealer.
     * @return The final payment due to the winning player.
     */
    public int getRonPayment(boolean isDealer) {
        int output;
        if (isDealer) {
            output = basicPoints * 6;
        } else {
            output = basicPoints * 4;
        }
        
        return (int)(100*Math.ceil(output/100.0));
    }
    
    /**
     * Calculates payment from basicPoints for a tsumo.  Returns an int list of size 2.
     * First item is non-dealer payment, second is dealer's payment.
     * If caluclating a dealer's tsumo, dealer's payment will be 0.
     * 
     * @param isDealer - if the winner of the hand was the dealer
     * @return int[2] [non-dealer payment, dealer payment]
     */
    public int[] getTsumoPayment(boolean isDealer) {
        int[] output = new int[2];
        if (isDealer) {
            output[0] = basicPoints * 2;
            output[1] = 0;
        } else {
            output[0] = basicPoints * 1;
            output[1] = basicPoints * 2;
        }
        output[0] = (int)(100*Math.ceil(output[0]/100.0));
        output[1] = (int)(100*Math.ceil(output[1]/100.0));
        
        return output;
    }
    
    // Getters
    public Yaku[] getYakuList() {return yakuList;}
    public int getDora() {return dora;};
    public int getUraDora() {return uraDora;}
    public int getRedDora() {return redDora;}
    public int getFu() {return fu;}
    public int getHan() {return han;}
    public int getBasicPoints() {return basicPoints;}
    
    @Override
    public int compareTo(HandScore hs) {
        return new Integer(basicPoints).compareTo(new Integer(hs.getBasicPoints()));
    }
}