package mwrynn.tnt.character;

import mwrynn.tnt.rules.RulesSet;

public class Human extends Character {
    public Human(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
    }

    public String kinName() {
        return "Human";
    }

    public boolean isValidInRulesSet() {
        return true;
    }
}
