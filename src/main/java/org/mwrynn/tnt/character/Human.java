package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

@Deprecated
public class Human extends Character {
    public Human(RulesEdition rulesEdition) {
        super(rulesEdition);
    }

    public String kinName() {
        return "Human";
    }

    public boolean isValidInRulesEdition() {
        return true;
    }
}
