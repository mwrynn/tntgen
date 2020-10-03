package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public abstract class Character {
    public int str = 0;
    public int dex = 0;
    public int con = 0;
    public int spd = 0;

    public int iq = 0;
    public int lk = 0;
    public int chr = 0;
    public int wiz = 0;

    public float strMult = 1.0f;
    public float dexMult = 1.0f;
    public float conMult = 1.0f;
    public float spdMult = 1.0f;

    public float iqMult = 1.0f;
    public float lkMult = 1.0f;
    public float chrMult = 1.0f;
    public float wizMult = 1.0f;

    public RulesSet rulesSet = RulesSet.DELUXE; //default rulesSet is DELUXE

    public int getAdds() {
        if (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL) {
            return Math.max(str, 12)-12 + Math.max(dex, 12)-12 + Math.max(lk, 12)-12 + Math.max(spd, 12)-12;
        } else { //fifth
            return ( Math.max(str, 12)-12 + Math.max(dex, 12)-12 + Math.max(lk, 12)-12 ) //positive adds
            - ( 9-Math.min(str, 9) + 9-Math.min(dex, 9) + 9-Math.min(lk, 9)); //negative adds
        }
    }

    public abstract boolean isValidInRulesSet();

    public abstract String kinName();
}
