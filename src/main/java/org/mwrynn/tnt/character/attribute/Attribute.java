package org.mwrynn.tnt.character.attribute;

import org.mwrynn.tnt.rules.RulesSet;

public class Attribute {
    private AttributeName name;
    private int value;
    private float multiplier;
    private RulesSet rulesSet;

    public Attribute(RulesSet rulesSet, AttributeName name, int value, float multiplier) {
        this.rulesSet = rulesSet;
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

    public void setValue(int unmultipliedVlaue) {
        if (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL) {
            if (multiplier > 1) { //round down "good" multipliers in Deluxe
                value = (int)Math.floor(unmultipliedVlaue);
            } else { //round up "bad" multipliers in Deluxe
                value = (int)Math.ceil(unmultipliedVlaue);
            }
        } else { //fifth edition round up: TODO verify this
            value = (int)Math.ceil(unmultipliedVlaue);
        }
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

}
