package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Leprechaun extends Character {
    public Leprechaun(RulesSet rulesSet) {
        super(rulesSet);

        if ((this.rulesSet == RulesSet.FIFTH) || (this.rulesSet == RulesSet.FIFTH_W_LOW_REROLL)) {
            this.str.setMultiplier(0.5f);
            this.dex.setMultiplier(1.5f);
            this.iq.setMultiplier(1.5f);
            this.lk.setMultiplier(1.5f);
        } else { //deluxe
            this.str.setMultiplier(0.333f);
            this.con.setMultiplier(0.667f);
            this.dex.setMultiplier(1.5f);
            this.lk.setMultiplier(1.5f);
            this.iq.setMultiplier(1.25f);
            this.wiz.setMultiplier(1.5f);
        }
    }

    public String kinName() {
        return "Leprechaun";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}