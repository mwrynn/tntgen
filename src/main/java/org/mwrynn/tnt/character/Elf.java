package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

@Deprecated
public class Elf extends Character {

    public Elf(RulesSet rulesSet) {
        super(rulesSet);

        if (this.rulesSet == RulesSet.FIFTH) {
            this.iq.setMultiplier(1.5f);
            this.dex.setMultiplier(1.5f);
            this.con.setMultiplier(0.667f);
            this.chr.setMultiplier(2.0f);
        } else { // DELUXE
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