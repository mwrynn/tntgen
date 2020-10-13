package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

@Deprecated
public class Fairy extends Character {
    public Fairy(RulesSet rulesSet) {
        super(rulesSet);

        if (this.rulesSet == RulesSet.FIFTH) {
            this.str.setMultiplier(0.25f);
            this.dex.setMultiplier(1.5f);
            this.lk.setMultiplier(1.5f);
            this.con.setMultiplier(0.25f);
            this.chr.setMultiplier(2.0f);
        } else { //DELUXE
            this.str.setMultiplier(0.25f);
            this.con.setMultiplier(0.25f);
            this.dex.setMultiplier(1.75f);
            this.lk.setMultiplier(1.5f);
            this.chr.setMultiplier(1.5f);
            this.wiz.setMultiplier(2.0f);
        }
    }

    public String kinName() {
        return "Fairy";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}
