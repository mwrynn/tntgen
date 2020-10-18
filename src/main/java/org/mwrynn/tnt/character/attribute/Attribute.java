package org.mwrynn.tnt.character.attribute;

import org.mwrynn.tnt.rules.RulesEdition;

public class Attribute {
    private AttributeName name;
    private int value;
    private float multiplier;
    private RulesEdition rulesEdition;

    public Attribute(RulesEdition rulesEdition, AttributeName name, int value, float multiplier) {
        this.rulesEdition = rulesEdition;
        this.setName(name);
        this.setValue(value);
        this.setMultiplier(multiplier);
    }

    public AttributeName getName() {
        return name;
    }

    public void setName(AttributeName name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int unmultipliedValue) {
        if (rulesEdition == RulesEdition.DELUXE) {
            if (multiplier > 1) { //round down "good" multipliers in Deluxe
                value = (int)Math.floor(unmultipliedValue * multiplier);
            } else { //round up "bad" multipliers in Deluxe
                value = (int)Math.ceil(unmultipliedValue * multiplier);
            }
        } else { //fifth edition round up: TODO verify this
            value = (int)Math.ceil(unmultipliedValue * multiplier);
        }
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

}
