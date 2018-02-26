package com.renhou.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import static com.renhou.model.MeldTag.*;

public class HandAnalyzer {
    private static Tile[] kokushiTileArr = 
            new Tile[] {new Tile("1m"), new Tile("9m"), new Tile("1p"), new Tile("9p"),new Tile("1s"),
                    new Tile("9s"), new Tile("w1"), new Tile("w2"),new Tile("w3"), new Tile("w4"),
                    new Tile("d1"), new Tile("d2"),new Tile("d3")};
    
    private static int[] nineGatesRankArr =
            new int[] {1,1,1,2,3,4,5,6,7,8,9,9,9};
    
    
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
                            if (hand[j].getType() == hand[k].getType()) {   // Triplet detection - third tile
                                if (hand[j].getRank() == hand[k].getRank()) {
                                    meldList.add(new int[] {i,j,k});
                                } else if (hand[j].getRank() < hand[k].getRank()) {
                                    break;  // Stop searching after third tile overshoots rank
                                }
                            } else {
                                break;  // Stop searching after third tile overshoots type
                            }
                        }
                        
                    } else if (hand[i].getType() != TileType.WIND &&    // Sequence detection - second tile
                               hand[i].getType() != TileType.DRAGON &&
                               hand[i].getRank() == hand[j].getRank() - 1){
                        
                        for (int k = j+1; k < hand.length; k++) {
                            if (hand[j].getType() == hand[k].getType()) {
                                if (hand[j].getRank() == hand[k].getRank() - 1) {   // Sequence detection - third tile
                                    meldList.add(new int[] {i,j,k});
                                } else if (hand[j].getRank() < hand[k].getRank()) {
                                    break;  // Stop searching after third tile overshoots rank
                                }
                            } else {
                                break;  // Stop searching after third tile overshoots type
                            }
                        }
                    }
                } else {
                    break;  // TileType check, stop search for second meld tile and start on new base tile
                }
            }
        }
        
        
        
        return meldList.toArray(new int[meldList.size()][]);
    }
    
    /**
     * Helper method for groupMelds.  Finds a pair in the hand Tile array from the left over
     * indices given in the melds int array.  If no pair exists, returns null.
     * 
     * @param hand A Tile list representing the tiles in the hand or a section of the hand
     * @param melds A meld list given as indices of the hand array
     * @return A Tile[] array containing the two pair tiles, null if no such pair exists
     */
    private static Tile[] findPair(Tile[] hand, int[][] melds) {
        // Build index set for pair remainder finding
        HashSet<Integer> set = new HashSet<Integer>();
        for (int[] meld : melds)
            for (int i : meld) set.add(i);
        
        int pair1 = -1;
        for (int i = 0; i < hand.length; i++) {
            
            if (!set.contains(i)) {
                
                if (pair1 < 0) {
                    pair1 = i;
                    
                } else if (hand[pair1].getType() == hand[i].getType() &&
                           hand[pair1].getRank() == hand[i].getRank()) {
                    
                    return new Tile[] {hand[pair1], hand[i]};
                }
            }
        }
        
        return null;
    }
    
    /**
     * Helper method for groupMelds. Returns true there is a recurring integer in the 2d int list.
     * 
     * @param testSet 2d int list
     * @return true if recurring int in testSet
     */
    private static boolean isOverlap(int[][] testSet) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int[] intList : testSet) {
            for (int i : intList) {
                if (set.contains(i))
                    return true;
                else
                    set.add(i);
            }
        }
        return false;
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
        
        ArrayList<Tile[][]> groupings = new ArrayList<Tile[][]>();
        
        int maxMelds = (hand.length - 2) / 3;
        
        // TODO: Make this not disgusting
        switch (maxMelds) {
            case 0:
                groupings.add(new Tile[][] {{hand[0], hand[1]}});
                break;
                
            case 1:
                for (int i = 0; i < melds.length; i++) {
                    Tile[] pair = findPair(hand, new int[][] {melds[i]});
                    if (pair != null) {
                        Tile[] meld = new Tile[] {hand[melds[i][0]], hand[melds[i][1]], hand[melds[i][2]]};
                        groupings.add(new Tile[][] {meld, pair});
                    }
                }
                break;
            case 2:
                for (int i = 0; i < melds.length - 1; i++) {
                    for (int j = i+1; j < melds.length; j++) {
                        if (!isOverlap(new int[][] {melds[i], melds[j]})) {
                            Tile[] pair = findPair(hand, new int[][] {melds[i], melds[j]});
                            if (pair != null) {
                                Tile[] meld1 = new Tile[] {hand[melds[i][0]], hand[melds[i][1]], hand[melds[i][2]]};
                                Tile[] meld2 = new Tile[] {hand[melds[j][0]], hand[melds[j][1]], hand[melds[j][2]]};
                                groupings.add(new Tile[][] {meld1, meld2, pair});
                            }
                        }
                    }
                }
                break;
                
            case 3:
                for (int i = 0; i < melds.length - 2; i++) {
                    for (int j = i+1; j < melds.length - 1; j++) {
                        if (!isOverlap(new int[][] {melds[i], melds[j]})) {
                            for (int k = j+1; k < melds.length; k++) {
                                if (!isOverlap (new int[][] {melds[i], melds[j], melds[k]})) {
                                    Tile[] pair = findPair(hand, new int[][] {melds[i], melds[j], melds[k]});
                                    if (pair != null) {
                                        Tile[] meld1 = new Tile[] {hand[melds[i][0]], hand[melds[i][1]], hand[melds[i][2]]};
                                        Tile[] meld2 = new Tile[] {hand[melds[j][0]], hand[melds[j][1]], hand[melds[j][2]]};
                                        Tile[] meld3 = new Tile[] {hand[melds[k][0]], hand[melds[k][1]], hand[melds[k][2]]};
                                        groupings.add(new Tile[][] {meld1, meld2, meld3, pair});
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                
            case 4:
                for (int i = 0; i < melds.length - 3; i++) {
                    for (int j = i+1; j < melds.length - 2; j++) {
                        if (!isOverlap(new int[][] {melds[i], melds[j]})) {
                            for (int k = j+1; k < melds.length - 1; k++) {
                                if (!isOverlap (new int[][] {melds[i], melds[j], melds[k]})) {
                                    for (int l = k+1; l < melds.length; l++) {
                                        if (!isOverlap (new int[][] {melds[i], melds[j], melds[k], melds[l]})) {
                                            Tile[] pair = findPair(hand, new int[][] {melds[i], melds[j], melds[k], melds[l]});
                                            if (pair != null) {
                                                Tile[] meld1 = new Tile[] {hand[melds[i][0]], hand[melds[i][1]], hand[melds[i][2]]};
                                                Tile[] meld2 = new Tile[] {hand[melds[j][0]], hand[melds[j][1]], hand[melds[j][2]]};
                                                Tile[] meld3 = new Tile[] {hand[melds[k][0]], hand[melds[k][1]], hand[melds[k][2]]};
                                                Tile[] meld4 = new Tile[] {hand[melds[l][0]], hand[melds[l][1]], hand[melds[l][2]]};
                                                groupings.add(new Tile[][] {meld1, meld2, meld3, meld4, pair});
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                
            default:
                throw new IllegalArgumentException("Hand too large");
        }
        
        // Post-detection pruning
        boolean removeThis = false;
        
        ArrayList<Integer> toRemove = new ArrayList<Integer>(); // Finding redundancies
        for (int i = 0; i < groupings.size() - 1; i++) {
            if (!toRemove.contains(i)) {
                for (int j = i+1; j < groupings.size(); j++) {
                    removeThis = false;
                    for (int k = 0; k < groupings.get(i).length; k++) {
                        if (groupings.get(i)[k][0].isCongruent(groupings.get(j)[k][0]) &&
                            groupings.get(i)[k][1].isCongruent(groupings.get(j)[k][1]) &&
                            groupings.get(i)[k].length > 2 &&
                            groupings.get(i)[k][2].isCongruent(groupings.get(j)[k][2])) {
                            
                            removeThis = true;;
                        } else {
                            break;
                        }
                    }
                    if (removeThis) {
                        toRemove.add(j);
                    }
                }
            }
        }
  
        for (int i = toRemove.size() - 1; i >= 0; i--) {    // Pruning redundancies
            groupings.remove(toRemove.get(i).intValue());
        }
        
        return groupings.toArray(new Tile[groupings.size()][][]);
    }
    
    /**
     * Helper method for tagMeldGroup.  Tags a single meld.
     * 
     * @return A list of tags describing the given meld
     */
    private static MeldTag[] tagMeld(Tile[] meld, Tile draw, Wind seat, Wind round, boolean isOpen) {
        ArrayList<MeldTag> output = new ArrayList<MeldTag>();
        
        TileType t = meld[0].getType();
        int r = meld[0].getRank();
        
        if (meld.length == 3 || meld.length == 4) {
            if (isOpen) {
                output.add(MELDOPEN);
            } else {
                output.add(MELDCLOSED);
            }
            
            if (!t.isHonor()) {
                if (t == TileType.MAN) {
                    output.add(MELDMAN);
                } else if (t == TileType.PIN) {
                    output.add(MELDPIN);
                } else {
                    output.add(MELDSOU);
                }
            } else {
                output.add(MELDHONORS);
                
                if (t == TileType.WIND) {
                    if (r == seat.getRank()) {
                        output.add(MELDSEATWIND);
                    } else if (r == round.getRank()) {
                        output.add(MELDROUNDWIND);
                    }
                } else {
                    output.add(MELDDRAGON);
                    if (r == 1) {
                        output.add(MELDGREEN);
                    }
                }
            }
            
            if (r == meld[1].getRank()) {   // Pon or Kan
                if (meld.length == 3) {
                    output.add(MELDPON);
                } else {
                    output.add(MELDKAN);
                }
                
                if (draw.isCongruent(meld[0])) {
                    output.add(WAITSHANPON);
                }
                
                if (!t.isHonor()) {
                    if (r == 1 || r == 9) {
                        output.add(MELDTERMINAL);
                    } else {
                        output.add(MELDSIMPLES);
                    }
                    
                    if (t == TileType.SOU && (r == 2 || r == 3 ||
                            r==4 || r == 6 || r == 8)) {
                        output.add(MELDGREEN);
                    }
                }
                
            } else {                        // Chi
                output.add(MELDCHII);
                
                if (draw.isCongruent(meld[0])) {
                    if (r == 7) {
                        output.add(WAITPENCHAN);
                    } else {
                        output.add(WAITRYANMEN);
                    }
                } else if (draw.isCongruent(meld[1])) {
                    output.add(WAITKANCHAN);
                } else if (draw.isCongruent(meld[2])) {
                    if (r == 3) {
                        output.add(WAITPENCHAN);
                    } else {
                        output.add(WAITRYANMEN);
                    }
                }
                
                if (r == 1 || r == 7) {
                    output.add(MELDTERMINAL);
                } else {
                    output.add(MELDSIMPLES);
                }
                
                if (t == TileType.SOU && r == 2) {
                    output.add(MELDGREEN);
                }
            }
            
            
        } else if (meld.length == 2) {  // Pair
            if (draw.isCongruent(meld[0])) {
                output.add(WAITTANKI);
            }
            
            if (!t.isHonor()) {
                if (t == TileType.MAN) {
                    output.add(PAIRMAN);
                } else if (t == TileType.PIN) {
                    output.add(PAIRPIN);
                } else {
                    output.add(PAIRSOU);
                    if (r == 2 || r == 3 || r == 4 || r == 6 || r == 8) {
                        output.add(PAIRGREEN);
                    }
                }
                if (r == 1 || r == 9) {
                    output.add(PAIRTERMINALS);
                } else {
                    output.add(PAIRSIMPLES);
                }
                
            } else {
                output.add(PAIRHONORS);
                
                if (t == TileType.WIND) {
                    if (r == seat.getRank()) {
                        output.add(PAIRSEATWIND);
                    } else if (r == round.getRank()) {
                        output.add(PAIRROUDNWIND);
                    }
                } else {
                    output.add(PAIRDRAGON);
                    if (r == 1) {
                        output.add(PAIRGREEN);
                    }
                }
            }
            
        } else {
            throw new IllegalArgumentException("Meld length '" + meld.length + "' not handled.");
        }
        
        return output.toArray(new MeldTag[output.size()]);
    }
    
    /**
     * Attributes tags to the hand and the hands component melds/pair.
     * 
     * output is a 2D list of MeldTag enums.
     * the first dimension chooses which aspect of the hand the tags are referencing:
     *      [meld1],[meld2],[meld3],[meld4],[pair],[whole hand]
     * the second dimension chooses between each individual tag.
     * 
     * @param meldGroup Contains a valid combination of melds from the closed hand ending with a pair
     * @param openMelds The fixed melds from the original hand
     * @param seat The seat wind of the player
     * @param round The round wind
     * @return A set of tags grouped by category
     */
    private static MeldTag[][] tagMeldGroup(Tile[][] meldGroup, Tile[][] openMelds, Tile draw, Wind seat, Wind round) {
        MeldTag[][] output = new MeldTag[6][];
        for (int i = 0; i < openMelds.length; i++) {
            output[i] = tagMeld(openMelds[i], draw, seat, round, true);
            i++;
        }
        for (int i = 0; i < meldGroup.length; i++) {
            output[i+openMelds.length] = tagMeld(meldGroup[i], draw, seat, round, false);
        }
        
        // Convert to List<MeldTag> objects for access to contains()
        ArrayList<MeldTag> handTags = new ArrayList<MeldTag>();
        ArrayList<MeldTag> meld1Tags = new ArrayList<MeldTag>(Arrays.asList(output[0]));
        ArrayList<MeldTag> meld2Tags = new ArrayList<MeldTag>(Arrays.asList(output[1]));
        ArrayList<MeldTag> meld3Tags = new ArrayList<MeldTag>(Arrays.asList(output[2]));
        ArrayList<MeldTag> meld4Tags = new ArrayList<MeldTag>(Arrays.asList(output[3]));
        ArrayList<MeldTag> pairTags = new ArrayList<MeldTag>(Arrays.asList(output[4]));
        
        // All tiles tags
        MeldTag[] meldt1 = {MELDMAN, MELDPIN, MELDSOU, MELDSIMPLES, MELDTERMINAL, MELDHONORS, MELDGREEN};
        MeldTag[] pairt1 = {PAIRMAN, PAIRPIN, PAIRSOU, PAIRSIMPLES, PAIRTERMINALS, PAIRHONORS, PAIRGREEN};
        MeldTag[] handt1 = {HANDMAN, HANDPIN, HANDSOU, HANDSIMPLES, HANDTERMINALS, HANDHONORS, HANDGREEN};
        for (int i = 0; i < handt1.length; i++) {
            if (meld1Tags.contains(meldt1[i]) && meld2Tags.contains(meldt1[i]) &&
                    meld3Tags.contains(meldt1[i]) && meld4Tags.contains(meldt1[i]) && pairTags.contains(pairt1[i])) {
                handTags.add(handt1[i]);
            }
        }
        // All melds tags
        MeldTag[] meldt2 = {MELDCHII, MELDKAN, MELDCLOSED};
        MeldTag[] handt2 = {HANDCHII, HANDKAN, HANDCLOSED};
        for (int i = 0; i < handt2.length; i++) {
            if (meld1Tags.contains(meldt2[i]) && meld2Tags.contains(meldt2[i]) &&
                    meld3Tags.contains(meldt2[i]) && meld4Tags.contains(meldt2[i])) {
                handTags.add(handt2[i]);
            }
        }
        if (!handTags.contains(HANDCLOSED)) {
            handTags.add(HANDOPEN);
        }
        if (!meld1Tags.contains(MELDCHII) && !meld2Tags.contains(MELDCHII) && 
                !meld3Tags.contains(MELDCHII) && !meld4Tags.contains(MELDCHII)) {
            handTags.add(HANDNOCHII);
        }
        
        // No tiles tags
        MeldTag[] meldt3 = {MELDMAN, MELDPIN, MELDSOU};
        MeldTag[] pairt3 = {PAIRMAN, PAIRPIN, PAIRSOU};
        MeldTag[] handt3 = {HANDNOMAN, HANDNOPIN, HANDNOSOU};
        for (int i = 0; i < handt3.length; i++) {
            if (!meld1Tags.contains(meldt3[i]) && !meld2Tags.contains(meldt3[i]) &&
                    !meld3Tags.contains(meldt3[i]) && !meld4Tags.contains(meldt3[i]) && !pairTags.contains(pairt3[i])) {
                handTags.add(handt3[i]);
            }
        }
        
        ArrayList<ArrayList<MeldTag>> componentTags = new ArrayList<ArrayList<MeldTag>>();
        componentTags.add(meld1Tags); componentTags.add(meld2Tags);
        componentTags.add(meld3Tags); componentTags.add(meld4Tags);
        componentTags.add(pairTags);
        base: for (MeldTag t : new MeldTag[] {WAITRYANMEN, WAITSHANPON, WAITTANKI, WAITPENCHAN, WAITKANCHAN}) {
            for (ArrayList<MeldTag> tags : componentTags) {
                if (tags.contains(t) && tags.contains(MELDCLOSED)) {
                    tags.remove(MELDCLOSED);
                    tags.add(MELDOPEN);
                    break base;
                }
            }
        }
        
        // IIPEIKOU, RYANPEIKOU, CHUURENPOUTOU, JUNSEICHUURENPOUTOU detection
        if (openMelds.length < 1) {
            int peikouCounter = 0;
            for (int i = 0; i < meldGroup.length - 1; i++) {
                for (int j = i+1; j < meldGroup.length; j++) {
                    if (meldGroup[i][0].isCongruent(meldGroup[j][0]) &&
                            meldGroup[i][1].isCongruent(meldGroup[j][1])) {
                        peikouCounter++;
                    }
                }
            }
            if (peikouCounter == 2) {
                handTags.add(RYANPEIKOU);
            } else if (peikouCounter == 1 || peikouCounter == 3 || peikouCounter == 6) {
                // Checks for 2, 3, or 4 of the same meld
                handTags.add(IIPEIKOU);
            }
            
            ArrayList<Tile> handArr = new ArrayList<Tile>();
            for (Tile[] meld : meldGroup) handArr.addAll(Arrays.asList(meld));
            handArr.sort(null);
            
            boolean nineGates = true;
            boolean nineSided = false;
            for (int i = 0, j = 0; i < nineGatesRankArr.length; i++, j++) {
                if (nineGatesRankArr[i] == handArr.get(j).getRank()) {
                    continue;
                } else if (j != 0 && handArr.get(j).isCongruent(handArr.get(j-1)) &&
                        j < handArr.size()-1 && nineGatesRankArr[i] == handArr.get(j+1).getRank()) {
                    if (handArr.get(j).isCongruent(draw))
                        nineSided = true;
                    j++;
                } else {
                    nineGates = false;
                    break;
                }
            }
            if (nineGates) {
                if(nineSided)
                    handTags.add(JUNSEICHUURENPOUTOU);
                else
                    handTags.add(CHUURENPOUTOU);
            }
             
        }
        // ITTSUU detection
        ArrayList<Tile[]> meldArr = new ArrayList<Tile[]>();
        for (Tile[] meld : meldGroup)
            meldArr.add(meld);
        for (Tile[] meld : openMelds)
            meldArr.add(meld);
        
        boolean a,b,c;
        for (TileType type : new TileType[] {TileType.MAN, TileType.PIN, TileType.SOU}) {
            a = false; b = false; c = false;
            for (Tile[] meld : meldArr) {
                if (meld[0].getType() == type && meld[0].getRank() == 1 && meld[1].getRank() == 2)
                    a = true;
                if (meld[0].getType() == type && meld[0].getRank() == 4 && meld[1].getRank() == 5) 
                    b = true;
                if (meld[0].getType() == type && meld[0].getRank() == 7 && meld[1].getRank() == 8)
                    c = true;
            }
            if (a && b && c) {
                handTags.add(ITTSUU);
                break;
            }
        }
        // SANSHOKUDOUJUN, SANSHOKUDOUKOU detection
        base: for (int i = 0; i < meldArr.size() - 2; i++) {
            for (int j = i+1; j < meldArr.size() - 1; j++) {
                for (int k = j+1; k < meldArr.size(); k++) {
                    if (meldArr.get(i)[0].getRank() == meldArr.get(j)[0].getRank() &&
                            meldArr.get(i)[0].getRank() == meldArr.get(k)[0].getRank() &&
                            meldArr.get(i)[0].getType() != meldArr.get(j)[0].getType() &&
                            meldArr.get(j)[0].getType() != meldArr.get(k)[0].getType() &&
                            meldArr.get(i)[0].getType() != meldArr.get(k)[0].getType()) {
                        
                        if (meldArr.get(i)[0].isCongruent(meldArr.get(i)[1]) &&
                                meldArr.get(j)[0].isCongruent(meldArr.get(j)[1]) &&
                                meldArr.get(k)[0].isCongruent(meldArr.get(k)[1])) {
                            handTags.add(SANSHOKUDOUKOU);
                            break base;
                            
                        } else if (!meldArr.get(i)[0].isCongruent(meldArr.get(i)[1]) &&
                                !meldArr.get(j)[0].isCongruent(meldArr.get(j)[1]) &&
                                !meldArr.get(k)[0].isCongruent(meldArr.get(k)[1])) {
                            handTags.add(SANSHOKUDOUJUN);
                            break base;
                        }
                    }
                }
            }
        }
        
        output[5] = handTags.toArray(new MeldTag[handTags.size()]);
        return output;

    }
    
    /**
     * Checks for and tags special exception hands chiitoitsu and kokushi.
     * Returns null if neither are applicable.
     * 
     * @return A list of tags, meld lists will be empty
     */
    public static MeldTag[][] tagSpecial(Tile[] hand, Tile draw, Wind seat, Wind round) {
        boolean kokushi = true;
        int kokushiPairI = -1;
        for (int i = 0, j = 0; i < kokushiTileArr.length; i++, j++) {
            if (kokushiTileArr[i].isCongruent(hand[j])) {
                continue;
            } else if (j != 0 && hand[j].isCongruent(hand[j-1]) &&
                    j < hand.length-1 && kokushiTileArr[i].isCongruent(hand[j+1])) {
                kokushiPairI = j;
                j++;
            } else {
                kokushi = false;
                break;
            }
        }
        if (kokushi) {
            MeldTag[][] kokushiOutput = new MeldTag[6][];
            kokushiOutput[5] = new MeldTag[] {KOKUSHI, HANDCLOSED};
            if (hand[kokushiPairI].isCongruent(draw))
                kokushiOutput[4] = new MeldTag[] {WAITTANKI};
            
            return kokushiOutput;
        }
        
        boolean chiitoi = true;
        for (int i = 0; i < hand.length - 1; i+=2) {
            if (hand[i].isCongruent(hand[i+1])) {
                chiitoi = false;
                break;
            }
        }
        if (chiitoi) {
            ArrayList<MeldTag> chiitoiHandTags = new ArrayList<MeldTag>(Arrays.asList(new MeldTag[] {HANDCLOSED}));
            
            boolean man = false;
            boolean pin = false;
            boolean sou = false;
            boolean term = false;
            boolean honor = false;
            for (Tile t : hand) {
                if (!man && t.getType() == TileType.MAN) {
                    man = true;
                    if (!term && (t.getRank() == 1 || t.getRank() == 9)) {
                        term = true;
                    }
                } else if (!pin && t.getType() == TileType.PIN) {
                    pin = true;
                    if (!term && (t.getRank() == 1 || t.getRank() == 9)) {
                        term = true;
                    }
                } else if (!sou && t.getType() == TileType.SOU) {
                    sou = true;
                    if (!term && (t.getRank() == 1 || t.getRank() == 9)) {
                        term = true;
                    }
                } else if (!honor && t.getType() == TileType.WIND ||
                        t.getType() == TileType.DRAGON) {
                    honor = true;
                } else if (man && pin && sou && term && honor) {
                    break;
                }
            }
            
            if (man && !pin && !sou && !honor) {
                chiitoiHandTags.add(HANDMAN);
            } else if (!man && pin && !sou && !honor) {
                chiitoiHandTags.add(HANDPIN);
            } else if (!man && !pin && sou && !honor) {
                chiitoiHandTags.add(HANDSOU);
            } else if (!man && !pin && !sou && honor) {
                chiitoiHandTags.add(HANDHONORS);
            }
            
            if(!term && !honor)
                chiitoiHandTags.add(HANDSIMPLES);
            if (!man)
                chiitoiHandTags.add(HANDNOMAN);
            if (!pin)
                chiitoiHandTags.add(HANDNOPIN);
            if (!sou)
                chiitoiHandTags.add(HANDNOSOU);
            
            MeldTag[][] chiitoiOutput = new MeldTag[6][];
            chiitoiOutput[5] = chiitoiHandTags.toArray(new MeldTag[chiitoiHandTags.size()]);
            return chiitoiOutput;
        }
        
        return null;
    }
    
    /**
     * Compiles a list of yaku based on the given meld tags.
     * 
     * @param tags 6 lists of meld tags describing a complete hand
     * @return a list of Yaku
     */
    private static Yaku[] detectYaku(MeldTag[][] tags) {
        ArrayList<Yaku> output = new ArrayList<Yaku>();
        ArrayList<ArrayList<MeldTag>> meldTags = new ArrayList<ArrayList<MeldTag>>();
        ArrayList<MeldTag> pairTags = new ArrayList<MeldTag>();
        ArrayList<MeldTag> handTags = new ArrayList<MeldTag>();
        ArrayList<MeldTag> allTags = new ArrayList<MeldTag>();
        int i = 0;
        for (MeldTag[] tagGroup : tags) {
            allTags.addAll(Arrays.asList(tagGroup));
            if (i < 4)
                meldTags.add(new ArrayList<MeldTag>(Arrays.asList(tagGroup)));
            if (i == 4)
                pairTags.addAll(Arrays.asList(tagGroup));
            if (i == 5)
                handTags.addAll(Arrays.asList(tagGroup));
                
            i++;
        }
        
        // Yakuman
        if (handTags.contains(KOKUSHI)) {
            if (allTags.contains(WAITTANKI)) {
                output.add(Yaku.KOKUSHI13SIDED);
            } else {
                output.add(Yaku.KOKUSHI);
            }
        }
        if (handTags.contains(HANDNOCHII) && handTags.contains(HANDCLOSED)) {
            if (allTags.contains(WAITTANKI)) {
                output.add(Yaku.SUUANKOUTANKI);
            } else {
                output.add(Yaku.SUUANKOU);
            }
        }
        int numDragonMelds = 0;
        for (ArrayList<MeldTag> meld : meldTags) {
            if (meld.contains(MELDDRAGON))
                numDragonMelds++;
        }
        if (numDragonMelds == 3) {
            output.add(Yaku.DAISANGEN);
        }
        int numWindMelds = 0;
        for (ArrayList<MeldTag> meld : meldTags) {
            if (meld.contains(MELDHONORS) && !meld.contains(MELDDRAGON))
                numWindMelds++;
        }
        if (numWindMelds == 4) {
            output.add(Yaku.DAISUUSHI);
        }
        if (numWindMelds == 3 && pairTags.contains(PAIRHONORS) && !pairTags.contains(PAIRDRAGON)) {
            output.add(Yaku.TSUUIISOU);
        }
        if (handTags.contains(HANDTERMINALS)) {
            output.add(Yaku.CHIROUTOU);
        }
        if (handTags.contains(HANDGREEN)) {
            output.add(Yaku.RYUUIISOU);
        }
        if (handTags.contains(CHUURENPOUTOU)) {
            output.add(Yaku.CHUURENPOUTOU);
        }
        if (handTags.contains(JUNSEICHUURENPOUTOU)) {
            output.add(Yaku.JUNSEICHUURENPOUTOU);
        }
        if (handTags.contains(HANDKAN) ) {
            output.add(Yaku.SUUKANTSU);
        }
        
        // Non-yakuman
        if (output.size() < 1) {
            if (handTags.contains(HANDCHII) && allTags.contains(WAITRYANMEN) && !pairTags.contains(PAIRSEATWIND) &&
                    !pairTags.contains(PAIRROUDNWIND) && !pairTags.contains(PAIRDRAGON)) {
                output.add(Yaku.PINFU);
            }
            if (handTags.contains(RYANPEIKOU)) {
                output.add(Yaku.RYANPEIKOU);
            } else if(handTags.contains(IIPEIKOU)) {
                output.add(Yaku.IIPEIKOU);
            }
            if (handTags.contains(SANSHOKUDOUJUN)) {
                output.add(Yaku.SANSHOKUDOUJUN);
            }
            if (handTags.contains(ITTSUU)) {
                output.add(Yaku.ITTSUU);
            }
            if (handTags.contains(HANDNOCHII)) {
                output.add(Yaku.TOITOI);
            }
            int numConcTrip = 0;
            for (ArrayList<MeldTag> meld : meldTags) {
                if (!meld.contains(MELDCHII) && meld.contains(MELDCLOSED))
                    numConcTrip++;
            }
            if (numConcTrip == 3) {
                output.add(Yaku.SANANKOU);
            }
            if (handTags.contains(SANSHOKUDOUKOU)) {
                output.add(Yaku.SANSHOKUDOUKOU);
            }
            int numKans = 0;
            for (ArrayList<MeldTag> meld : meldTags) {
                if (meld.contains(MELDKAN))
                    numKans++;
            }
            if (numKans == 3) {
                output.add(Yaku.SANKANTSU);
            }
            if (handTags.contains(HANDSIMPLES)) {
                output.add(Yaku.TANYAO);
            }
            for (ArrayList<MeldTag> meld : meldTags) {
                if (meld.contains(MELDDRAGON)) {
                    output.add(Yaku.YAKUHAIDRAGON);
                }
            }
            if (meldTags.get(0).contains(MELDSEATWIND) || meldTags.get(1).contains(MELDSEATWIND) ||
                    meldTags.get(2).contains(MELDSEATWIND) || meldTags.get(3).contains(MELDSEATWIND)) {
                output.add(Yaku.YAKUHAISEAT);
            }
            if (meldTags.get(0).contains(MELDROUNDWIND) || meldTags.get(1).contains(MELDROUNDWIND) ||
                    meldTags.get(2).contains(MELDROUNDWIND) || meldTags.get(3).contains(MELDROUNDWIND)) {
                output.add(Yaku.YAKUHAIROUND);
            }
            if (handTags.contains(HANDTERMINALS)) {
                output.add(Yaku.JUNCHAN);
            } else if (handTags.contains(HANDNOCHII) &&
                    (meldTags.get(0).contains(MELDTERMINAL) || meldTags.get(0).contains(MELDHONORS)) &&
                    (meldTags.get(1).contains(MELDTERMINAL) || meldTags.get(1).contains(MELDHONORS)) &&
                    (meldTags.get(2).contains(MELDTERMINAL) || meldTags.get(2).contains(MELDHONORS)) &&
                    (meldTags.get(3).contains(MELDTERMINAL) || meldTags.get(3).contains(MELDHONORS))) {
                output.add(Yaku.HONROU);
            } else if (!meldTags.get(0).contains(MELDSIMPLES) && !meldTags.get(1).contains(MELDSIMPLES) && 
                    !meldTags.get(2).contains(MELDSIMPLES) && !meldTags.get(3).contains(MELDSIMPLES) && 
                    !pairTags.contains(PAIRSIMPLES)) {
                output.add(Yaku.CHANTA);
            }
            if (numDragonMelds == 2 && pairTags.contains(PAIRDRAGON)) {
                output.add(Yaku.SHOUSANGEN);
            }
            int numNoSuit = 0;
            for (MeldTag tag : new MeldTag[] {HANDNOMAN, HANDNOPIN, HANDNOSOU}) {
                if (handTags.contains(tag))
                    numNoSuit++;
            }
            if (handTags.contains(HANDMAN) || handTags.contains(HANDPIN) || handTags.contains(HANDSOU)) {
                output.add(Yaku.CHINITTSU);
            } else if (numNoSuit == 2) {
                output.add(Yaku.HONITTSU);
            }
        }
        
        return output.toArray(new Yaku[output.size()]);
    }
    
    /**
     * Counts fu from meld tags and isTsumo boolean.
     * 
     * @param tags MeldTag lists describing a hand
     * @param isTsumo Whether the hand was won by tsumo or ron 
     * @return
     */
    private static int countFu(MeldTag[][] tags, boolean isTsumo) {
        ArrayList<ArrayList<MeldTag>> meldTags = new ArrayList<ArrayList<MeldTag>>();
        ArrayList<MeldTag> pairTags = new ArrayList<MeldTag>();
        ArrayList<MeldTag> handTags = new ArrayList<MeldTag>();
        ArrayList<MeldTag> allTags = new ArrayList<MeldTag>();
        int i = 0;
        for (MeldTag[] tagGroup : tags) {
            allTags.addAll(Arrays.asList(tagGroup));
            if (i < 4)
                meldTags.add(new ArrayList<MeldTag>(Arrays.asList(tagGroup)));
            if (i == 4)
                pairTags.addAll(Arrays.asList(tagGroup));
            if (i == 5)
                handTags.addAll(Arrays.asList(tagGroup));
                
            i++;
        }
        
        if (handTags.contains(ALLPAIRS)) {
            return 25;
        } else if (handTags.contains(KOKUSHI)) {
            return 30;
        }
        
        int output = 20;
        // pon/kan block
        int kanMulti, openMulti, simpleMulti;
        for (ArrayList<MeldTag> meldt : meldTags) {
            if (!meldt.contains(MELDCHII)) {
                if (meldt.contains(MELDPON))
                    kanMulti = 1; 
                else
                    kanMulti = 4;
                if (meldt.contains(MELDOPEN))
                    openMulti = 1;
                else
                    openMulti = 2;
                if (meldt.contains(MELDSIMPLES))
                    simpleMulti = 1;
                else
                    simpleMulti = 2;
                
                output += 2 * kanMulti * openMulti * simpleMulti;
            }
        }
        
        if (!allTags.contains(WAITRYANMEN) && !allTags.contains(WAITSHANPON)) {
            output += 2;
        }
        
        if (pairTags.contains(PAIRDRAGON) || pairTags.contains(PAIRSEATWIND) || pairTags.contains(PAIRROUDNWIND)) {
            output += 2;
        }
        
        if (handTags.contains(HANDCLOSED) && !isTsumo) {
            output += 10;
        }
        
        boolean pinfu = false;
        if (output == 20)
            pinfu = true;
        
        if (isTsumo && !pinfu) {
            output += 2;
        }
        
        if (handTags.contains(HANDOPEN) && pinfu) {
            output += 2;
        }
        
        return (int)(10*Math.ceil(output/10.0));
    }
    
    /**
     * Analyzes a complete hand's contents, giving a list of yaku and counting fun and han
     * 
     * @param h The Hand to be analyzed
     * @param draw A tile object, the most recently drawn/called tile
     * @param dora A list of dora tiles
     * @param uradora A list uradora tiles
     * @param seat The seat wind of the player whose hand is being scored
     * @param round The round wind
     * @return A descriptive HandScore object
     */
    public static HandScore analyze(Hand h, Tile draw, boolean isTsumo, Tile[] dora, Tile[] uradora,
            Wind seat, Wind round, Yaku[] situationalYaku) {
        
        boolean isOpen = (h.getMelds().length > 0);
        Tile[] handArr = h.getHandArr();
        Arrays.sort(handArr);
        Tile[][] fixedMelds = h.getMelds();
        
        int doraCount = 0;
        int uraDoraCount = 0;
        int redDoraCount = 0;
        for (Tile th : handArr) {
            for (Tile td : dora) {
                if (th.isCongruent(td)) {
                    doraCount++;
                }
            }
            for (Tile tud : uradora) {
                if (th.isCongruent(tud)) {
                    uraDoraCount++;
                }
            }
            if (th.isRed()) {
                redDoraCount++;
            }
        }
        for (Tile[] mh : fixedMelds) {
            for (Tile tmh : mh) {
                for (Tile td : dora) {
                    if (tmh.isCongruent(td)) {
                        doraCount++;
                    }
                }
                for (Tile tud : uradora) {
                    if (tmh.isCongruent(tud)) {
                        uraDoraCount++;
                    }
                }
                if (tmh.isRed()) {
                    redDoraCount++;
                }
            }
        }
        
        int[][] closedMelds = listMelds(handArr);
        Tile[][][] closedMeldGroups = groupMelds(handArr, closedMelds);
        MeldTag[][][] tags;
        if (closedMeldGroups.length < 1) {
            tags = new MeldTag[1][][];
            tags[0] = tagSpecial(handArr, draw, seat, round);
        } else {
            tags = new MeldTag[closedMeldGroups.length][][];
            for (int i = 0; i < closedMeldGroups.length; i++) {
                tags[i] = tagMeldGroup(closedMeldGroups[i], fixedMelds, draw, seat, round);
            }
        }
        
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
        int fu = countFu(tags[bestIndex], isTsumo);
        
        // Compile yaku
        Yaku[] finalYakuList = new Yaku[yakuLists[bestIndex].length + situationalYaku.length];
        int i = 0;
        for (Yaku y : yakuLists[bestIndex])
            finalYakuList[i++] = y;
        for (Yaku y : situationalYaku)
            finalYakuList[i++] = y;
        
        return new HandScore(finalYakuList, doraCount, uraDoraCount, redDoraCount, fu, isOpen);
    }
    
    // Main method for testing
    public static void main(String[] args) {
//        Tile[] initTiles = {new Tile("1p"), new Tile("2p"), new Tile("3p"),     // split quad 
//                            new Tile("4p"), new Tile("5p"), new Tile("6p"), 
//                            new Tile("6p"), new Tile("6p"), new Tile("6p"), 
//                            new Tile("2d"), new Tile("2d"), new Tile("2d"), 
//                            new Tile("1m"), new Tile("1m")};
//        Tile draw = new Tile("2d");
        
        Tile[] initTiles = {new Tile("1m"), new Tile("1m"), new Tile("1m"),     // NineGates
                            new Tile("2m"), new Tile("3m"), new Tile("4m"),
                            new Tile("5m"), new Tile("6m"), new Tile("7m"),
                            new Tile("8m"), new Tile("8m"), new Tile("9m"),
                            new Tile("9m"), new Tile("9m")};
        Tile draw = new Tile("7m");
        
//        Tile[] initTiles = {new Tile(""), new Tile(""), new Tile(""),
//                            new Tile(""), new Tile(""), new Tile(""),
//                            new Tile(""), new Tile(""), new Tile(""),
//                            new Tile(""), new Tile(""), new Tile(""),
//                            new Tile(""), new Tile("")};
        
        Hand hand = new Hand(initTiles);
        boolean isTsumo = true;
        Tile[] dora = new Tile[] {new Tile("1p").doraTile()};
        Tile[] uraDora = new Tile[] {};
        Wind seat = Wind.EAST;
        Wind round = Wind.EAST;
        Yaku[] situationalYaku = new Yaku[] {Yaku.RIICHI}; 
        
        long startTime = System.currentTimeMillis();
        HandScore hs = analyze(hand, draw, isTsumo, dora, uraDora, seat, round, situationalYaku);
        //int[][] melds = listMelds(initTiles);
        //Tile[][][] meldGroups = groupMelds(initTiles, melds);
        long endTime = System.currentTimeMillis();
        
//        // Print block for debugging.
//        System.out.println("Individual melds: [tiles] [indices]");
//        int x = 0;
//        for (int[] meld : melds) {
//            System.out.print(x+": [");
//            for (int i : meld) {
//                System.out.print(initTiles[i] + ",");
//            }
//            System.out.print("] [");
//            
//            for (int i : meld) {
//                System.out.print(i+",");
//            }
//            System.out.println("]");
//            x++;
//        }
//        System.out.println("\nMeld Groupings");
//        
//        for (Tile[][] meldGroup: meldGroups) {
//            for (Tile[] meld : meldGroup) {
//                System.out.print("[");
//                for (Tile t : meld) {
//                    System.out.print(t+",");
//                }
//                System.out.print("]");
//            }
//            System.out.println();
//        }

        // Analyze print block
        System.out.print("Yaku List: \n[");
        for (Yaku y : hs.getYakuList()) {
            System.out.print(y + ",");
        }
        System.out.println("]");
        if (hs.getDora() > 0)
            System.out.println("Dora: " + hs.getDora());
        if (hs.getUraDora() > 0)
            System.out.println("Uradora: " + hs.getUraDora());
        if (hs.getRedDora() > 0)
            System.out.println("Red Dora: " + hs.getRedDora());
        System.out.println("Han: " + hs.getHan());
        System.out.println("Fu: " + hs.getFu());
        System.out.println("BasicPoints: " + hs.getBasicPoints());
        System.out.println("Total Worth nondealer: " + hs.getBasicPoints()*4);
        
        
        System.out.println("\nTime taken in milliseconds: " + (endTime-startTime));
    }
}
