package com.renhou.model;

/**
 * A list of yaku.  Enum parameters in order are han vlaue when closed, han value when open,
 * then a list of yaku that this yaku overrides.
 * 
 * If only one han value is given, it is the closed hand value, and the open hand value is assumed zero (i.e. not applicable to open hands).
 * 
 * Yakuman implicitly override all nonyakuman yaku.  Use the static lists and the isYakuman() method to avoid double counting.
 * 
 * @author Chase
 *
 */

public enum Yaku {
    
    // Special
    RIICHI (1),                 // Ready hand
    CHIITOITSU (2),             // 7 pairs
    NAGASHIMANGAN(5),           // Draw game, No discarded simples, not involved in any calls
    
    // Based on luck
    TSUMO (1),                  // Self-draw
    IPPATSU (1),                // One-shot after riichi
    HAITEI (1, 1),              // Tsumo on last tile
    HOUTEI (1, 1),              // Ron on last tile
    RINSHAN (1, 1),             // Dead wall draw
    CHANKAN (1, 1),             // Robbing a kan
    DOUBLERIICHI (2,            // First turn riichi
            new Yaku[] {RIICHI}),
    
    // Based on sequences
    PINFU (1, 1),               // no fu
    IIPEIKOU (2),               // Double sequence 
    SANSHOKUDOUJUN (2, 1),      // Three color straight
    ITTSUU (2, 1),              // Straight 1 to 9
    RYANPEIKOU (3,              // Pair of double sequence
            new Yaku[] {IIPEIKOU} ),
    
    // Based on triplets/quads
    TOITOI (2, 2),              // All triplets 
    SANANKOU (2, 2),            // Three closed triplets
    SANSHOKUDOUKOU (2, 2),      // Three color triplets
    SANKANTSU (2, 2),           // Three kans
    
    // Based on terminals/honors
    TANYAO (1, 1),              // All simples
    YAKUHAIDRAGON (1, 1),       // Triplet of dragons
    YAKUHAISEAT (1, 1),         // Triplet of seat wind
    YAKUHAIROUND (1, 1),        // Triplet of round wind
    CHANTA (2, 1),              // Terminal or honor in each meld and pair
    JUNCHAN (3, 2,              // Terminal in each meld and pair
            new Yaku[] {CHANTA}),
    HONROU (2, 2,               // All terminals and honors
            new Yaku[] {CHANTA}),
    SHOUSANGEN (2, 2),          // Little three dragons
    HONITTSU (3, 2),            // Half-flush
    CHINITTSU (6, 5,            // Full flush
            new Yaku[] {HONITTSU}),
    
    // Yakuman
    KOKUSHI (13),               // Thirteen orphans
    KOKUSHI13SIDED (26,         // Thirteen orphans (thirteen-sided wait)
            new Yaku[] {KOKUSHI}),
    SUUANKOU (13),              // Four concealed triplets (tsumo only)
    SUUANKOUTANKI (26,          // Four concealed triplets (single wait on pair)
            new Yaku[] {SUUANKOU}),
    DAISANGEN (13, 13),         // Big three dragons
    SHOUSUUSHI (13, 13),        // Little four winds
    DAISUUSHI (26, 26),         // Big four winds
    TSUUIISOU (13, 13),         // All honors
    CHIROUTOU (13, 13),         // All terminals
    RYUUIISOU (13, 13),         // All green
    CHUURENPOUTOU (13),         // Nine gates
    JUNSEICHUURENPOUTOU(26,     // Nine gates (nine-sided)
            new Yaku[] {CHUURENPOUTOU}),
    SUUKANTSU (13, 13),         // 4 Kans
    TENHOU (13),                // Complete hand on dealer draw
    CHIIHOU (13),               // Complete hand on non-dealer draw (no calls beforehand)
    RENHOU (13);                // Complete hand on a discard within the first go-around (no calls beforehand)

    
    public final static Yaku[] nonYakuman = new Yaku[] {RIICHI, CHIITOITSU, NAGASHIMANGAN, TSUMO, IPPATSU, HAITEI, HOUTEI, RINSHAN,
            CHANKAN, DOUBLERIICHI, PINFU, IIPEIKOU, SANSHOKUDOUJUN, ITTSUU, RYANPEIKOU, TOITOI, SANANKOU, SANSHOKUDOUKOU, SANKANTSU,
            TANYAO, YAKUHAIDRAGON, YAKUHAISEAT, YAKUHAIROUND, CHANTA, JUNCHAN, HONROU, SHOUSANGEN, HONITTSU, CHINITTSU};
    
    public final static Yaku[] yakuman = new Yaku[] {KOKUSHI, KOKUSHI13SIDED, SUUANKOU, SUUANKOUTANKI, DAISANGEN, SHOUSUUSHI,
            DAISUUSHI, TSUUIISOU, CHIROUTOU, RYUUIISOU, CHUURENPOUTOU, JUNSEICHUURENPOUTOU, SUUKANTSU, TENHOU, CHIIHOU, RENHOU};

    
    private final int hanClosed;
    private final int hanOpen;
    private final Yaku[] yakuOverrides;
    
    private Yaku(int hanClosed, int hanOpen, Yaku[] yakuOverrides) {
        this.hanClosed = hanClosed;
        this.hanOpen = hanOpen;
        this.yakuOverrides = yakuOverrides;
    }
    
    private Yaku(int hanClosed, int hanOpen) {
        this(hanClosed, hanOpen, new Yaku[] {});
    }
    
    private Yaku(int hanClosed, Yaku[] yakuOverrides) {
        this(hanClosed, 0, yakuOverrides);
    }
    
    private Yaku(int hanClosed) {
        this(hanClosed, 0, new Yaku[] {});
    }
    
    // Getters
    public int getClosedHanValue() {return hanClosed;}
    public int getOpenHanValue() {return hanOpen;}
    public Yaku[] getOverrideList() {return yakuOverrides;}
    
    public boolean isYakuman() {
        if (hanClosed > 12)
            return true;
        else
            return false;
    }
}