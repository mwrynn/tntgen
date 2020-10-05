package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Elf extends Character {

    public Elf(RulesSet rulesSet) {
        super(rulesSet);

        if ((this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL)) {
            this.iq.setMultiplier(1.5f);
            this.dex.setMultiplier(1.5f);
            this.con.setMultiplier(0.667f);
            this.chr.setMultiplier(2.0f);
        } else { // deluxe
            this.con.setMultiplier(0.667f);
            this.dex.setMultiplier(1.333f);
            this.iq.setMultiplier(1.5f);
            this.wiz.setMultiplier(1.5f);
            this.chr.setMultiplier(1.5f);
        }
    }

    public String kinName() {
        return "Elf";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}