package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class Hobb extends Character {
    public Hobb(RulesEdition rulesEdition) {
        super(rulesEdition);

        if (this.rulesEdition == RulesEdition.FIFTH) {
            this.str.setMultiplier(0.5f);
            this.con.setMultiplier(2.0f);
            this.dex.setMultiplier(1.5f);
        } else { //DELUXE
            this.str.setMultiplier(0.5f);
            this.con.setMultiplier(2.0f);
            this.dex.setMultiplier(1.5f);
            this.lk.setMultiplier(1.5f);
        }
    }

    public String kinName() {
        return "Hobb";
    }

    public boolean isValidInRulesEdition() {
        return true;
    }
}