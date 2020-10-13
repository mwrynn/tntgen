package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

@Deprecated
public class Midgardian extends Dwarf {
    public Midgardian(RulesSet rulesSet) {
        super(rulesSet);
        assert (isValidInRulesSet()) : "Midgardian Dwarves only available in the Deluxe rules.";

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.chr.setMultiplier(0.75f);
    }

    public String kinName() {
        return "Midgardian Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet == RulesSet.DELUXE);
    }
}
