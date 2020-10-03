package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Elf extends Character {
    Elf() {
    }

    public Elf(RulesSet rulesSet) {
        this.rulesSet = rulesSet;

        if ((this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL)) {
            iqMult = 1.5f;
            dexMult = 1.5f;
            conMult = 0.667f;
            chrMult = 2.0f;
        } else { // deluxe
            conMult = 0.667f;
            dexMult = 1.333f;
            iqMult = 1.5f;
            wizMult = 1.5f;
            chrMult = 1.5f;
        }
    }

    public String kinName() {
        return "Elf";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}