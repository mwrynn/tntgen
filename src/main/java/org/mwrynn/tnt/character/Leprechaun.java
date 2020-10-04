package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Leprechaun extends Character {
    public Leprechaun(RulesSet rulesSet) {
        super(rulesSet);

        if ((this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL)) {
            strMult = 0.5f;
            dexMult = 1.5f;
            iqMult = 1.5f;
            lkMult = 1.5f;
        } else { //deluxe
            strMult = 0.333f;
            conMult = 0.667f;
            dexMult = 1.5f;
            lkMult = 1.5f;
            iqMult = 1.25f;
            wizMult = 1.5f;
        }
    }

    public String kinName() {
        return "Leprechaun";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}