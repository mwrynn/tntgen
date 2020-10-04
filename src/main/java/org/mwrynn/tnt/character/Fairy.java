package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Fairy extends Character {
    public Fairy(RulesSet rulesSet) {
        super(rulesSet);

        if ( (this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL) ) {
            strMult = 0.25f;
            dexMult = 1.5f;
            lkMult = 1.5f;
            conMult = 0.25f;
            chrMult = 2.0f;
        } else { //deluxe
            strMult = 0.25f;
            conMult = 0.25f;
            dexMult = 1.75f;
            lkMult = 1.5f;
            chrMult = 1.5f;
            wizMult = 2.0f;
        }
    }

    public String kinName() {
        return "Fairy";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}
