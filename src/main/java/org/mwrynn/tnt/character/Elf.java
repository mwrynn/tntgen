package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class Elf extends Character {

    public Elf(RulesEdition rulesEdition) {
        super(rulesEdition);

        if (this.rulesEdition == RulesEdition.FIFTH) {
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

    public boolean isValidInRulesEdition() {
        return true;
    }
}