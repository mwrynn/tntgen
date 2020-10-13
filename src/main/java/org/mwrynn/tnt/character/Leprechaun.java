package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

@Deprecated
public class Leprechaun extends Character {
    public Leprechaun(RulesSet rulesSet) {
        super(rulesSet);

        if ((this.rulesSet == RulesSet.FIFTH)) {
            this.str.setMultiplier(0.5f);
            this.dex.setMultiplier(1.5f);
            this.iq.setMultiplier(1.5f);
            this.lk.setMultiplier(1.5f);
        } else { //DELUXE
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