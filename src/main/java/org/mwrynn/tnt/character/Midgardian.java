package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Midgardian extends Dwarf {
    public Midgardian(RulesSet rulesSet) {
        super(rulesSet);
        assert (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL ) : "Midgardian Dwarves only available in the Deluxe rules.";

        strMult = 2.0f;
        conMult = 2.0f;
        chrMult = 0.75f;
    }

    public String kinName() {
        return "Midgardian Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet == RulesSet.DELUXE) || (rulesSet == RulesSet.DELUXE_W_LOW_REROLL);
    }
}
