package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class Midgardian extends Dwarf {
    public Midgardian(RulesEdition rulesEdition) {
        super(rulesEdition);
        assert (isValidInRulesEdition()) : "Midgardian Dwarves only available in the Deluxe rules.";

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.chr.setMultiplier(0.75f);
    }

    public String kinName() {
        return "Midgardian Dwarf";
    }

    public boolean isValidInRulesEdition() {
        return (rulesEdition == RulesEdition.DELUXE);
    }
}
