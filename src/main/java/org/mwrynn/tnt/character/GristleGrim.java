package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class GristleGrim extends Dwarf {
    public GristleGrim(RulesEdition rulesEdition) {
        super(rulesEdition);

        assert (isValidInRulesEdition()) : "GristleGrim Dwarves only available in the Deluxe rules.";

        this.str.setMultiplier(2.0f);
        this.con.setMultiplier(2.0f);
        this.lk.setMultiplier(0.75f);
    }

    public String kinName() {
        return "GristleGrim Dwarf";
    }

    public boolean isValidInRulesEdition() {
        return rulesEdition == RulesEdition.DELUXE;
    }
}
