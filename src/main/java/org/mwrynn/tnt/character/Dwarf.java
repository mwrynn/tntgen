package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class Dwarf extends Character {
    public Dwarf(RulesEdition rulesEdition) {
        super(rulesEdition);
        assert isValidInRulesEdition() : "Dwarves in the Deluxe rules must be created as Gristlegrim or Midgardian.";
        this.rulesEdition = rulesEdition;

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.chr.setMultiplier(0.667f);
    }

    public String kinName() {
        return "Dwarf";
    }

    public boolean isValidInRulesEdition() {
        return rulesEdition != RulesEdition.DELUXE;
    }
}
