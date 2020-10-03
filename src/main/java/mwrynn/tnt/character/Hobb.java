package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Hobb extends Character {
    Hobb() {
    }

    public Hobb(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
        if ((this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL)) {
            strMult = 0.5f;
            conMult = 2.0f;
            dexMult = 1.5f;
        } else { //deluxe
            strMult = 0.5f;
            conMult = 2.0f;
            dexMult = 1.5f;
            lkMult = 1.5f;
        }
    }

    public String kinName() {
        return "Hobb";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}