package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Dwarf extends Character {
    Dwarf() {
    }

    public Dwarf(RulesSet rulesSet) {
        assert isValidInRulesSet() : "Dwarves in the Deluxe rules must be created as Gristlegrim or Midgardian.";
        this.rulesSet = rulesSet;

        strMult = 2.0f;
        conMult = 2.0f;
        chrMult = 0.667f;
    }

    public String kinName() {
        return "Dwarf";
    }

    public boolean isValidInRulesSet() {
        return (rulesSet != RulesSet.DELUXE) && (rulesSet != RulesSet.DELUXE_W_LOW_REROLL);
    }
}
