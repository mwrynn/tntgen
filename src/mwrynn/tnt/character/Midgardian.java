package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Midgardian extends Dwarf {
    Midgardian() {
    }

    public Midgardian(RulesSet rulesSet) {
        assert (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL ) : "Midgardian Dwarves only available in the Deluxe rules.";
        this.rulesSet = rulesSet;

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
