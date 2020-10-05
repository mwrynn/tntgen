package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Dwarf extends Character {
    public Dwarf(RulesSet rulesSet) {
        super(rulesSet);
        assert isValidInRulesSet() : "Dwarves in the Deluxe rules must be created as Gristlegrim or Midgardian.";
        this.rulesSet = rulesSet;

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.chr.setMultiplier(0.667f);
    }

    public String kinName() {
        return "Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet != RulesSet.DELUXE) && (rulesSet != RulesSet.DELUXE_W_LOW_REROLL);
    }
}
