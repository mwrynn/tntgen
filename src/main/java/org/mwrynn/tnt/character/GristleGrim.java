package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class GristleGrim extends Dwarf {
    public GristleGrim(RulesSet rulesSet) {
        super(rulesSet);

        assert (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL ) : "GristleGrim Dwarves only available in the Deluxe rules.";

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.lk.setMultiplier(0.75f);
    }

    public String kinName() {
        return "GristleGrim Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet == RulesSet.DELUXE) || (rulesSet == RulesSet.DELUXE_W_LOW_REROLL);
    }
}