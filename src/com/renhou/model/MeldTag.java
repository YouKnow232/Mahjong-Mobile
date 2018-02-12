package com.renhou.model;

public enum MeldTag { // For use in the tagging process of tagHand() and listYaku()
    // Meld specific tags (need to reference a meld)
    MELDMAN, MELDPIN, MELDSOU,
    MELDSIMPLES, MELDTERMINALS, MELDHONORS, MELD1TERMINAL,
    MELDCHII, MELDPON, MELDKAN,
    MELDOPEN, MELDCLOSED,
    MELDYAKUHAI,
    MELDGREEN,
    // Meld relationship tags (need to reference two or more melds;
    MELDSAMERANK, MELDSAMESUIT,
    MELDSTRAIGHT,
    // Pair specific tags
    PAIRSIMPLES, PAIRTERMINALS, PAIRHONORS, PAIRYAKUHAI, PAIRGREEN,
    
    // General Hand Tags (meta tags derived from meld/pair tags)
    HANDMAN, HANDPIN, HANDSOU,
    HANDSIMPLES, HANDTERMINALS, HANDHONORS,
    HANDCHII, HANDPON, HANDKAN,
    HANDOPEN, HANDCLOSED,
    HANDNOMAN, HANDNOPIN, HANDNOSOU,
    HANDGREEN,
    // Special exceptions
    ALLPAIRS, KOKUSHI, IIPEIKOU, SANSHOKUDOUJUN, ITTSUU, RYANPEIKOU, SANSHOKUDOUKOU
}