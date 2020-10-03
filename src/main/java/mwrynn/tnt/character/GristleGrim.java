package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class GristleGrim extends Dwarf {
    GristleGrim() {
    }

    public GristleGrim(RulesSet rulesSet) {
        assert (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL ) : "GristleGrim Dwarves only available in the Deluxe rules.";
        this.rulesSet = rulesSet;

        strMult = 2.0f;
        conMult = 2.0f;
        lkMult = 0.75f;
    }

    public String kinName() {
        return "GristleGrim Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet == RulesSet.DELUXE) || (rulesSet == RulesSet.DELUXE_W_LOW_REROLL);
    }
}
