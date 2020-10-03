package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Fairy extends Character {
    Fairy() {
    }

    public Fairy(RulesSet rulesSet) {
        this.rulesSet = rulesSet;

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
