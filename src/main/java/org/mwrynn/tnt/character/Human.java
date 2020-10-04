package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;

public class Human extends Character {
    public Human(RulesSet rulesSet) {
        super(rulesSet);
    }

    public String kinName() {
        return "Human";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}
