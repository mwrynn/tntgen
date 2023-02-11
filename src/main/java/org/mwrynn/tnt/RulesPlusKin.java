package org.mwrynn.tnt;

import org.mwrynn.tnt.character.KinDef;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;

import java.util.Objects;

public class RulesPlusKin implements Comparable<RulesPlusKin> {
    public RulesEdition rulesEdition;
    public OptionalRules optionalRules;
    public KinDef kinDef;

    RulesPlusKin(RulesEdition rulesEdition, OptionalRules optionalRules, KinDef kinDef) {
        this.rulesEdition = rulesEdition;
        this.optionalRules = optionalRules;
        this.kinDef = kinDef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rulesEdition, optionalRules, kinDef);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        RulesPlusKin other = (RulesPlusKin)obj;

        return  (this.rulesEdition.equals(other.rulesEdition)) &&
                (this.optionalRules.equals(other.optionalRules)) &&
                (this.kinDef.equals(other.kinDef));
    }

    @Override
    public int compareTo(RulesPlusKin other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.rulesEdition.compareTo(other.rulesEdition) < 0) {
            return -1;
        }
        if (this.rulesEdition.compareTo(other.rulesEdition) > 0) {
            return 1;
        }
        if (this.optionalRules.compareTo(other.optionalRules) < 0) {
            return -1;
        }
        if (this.optionalRules.compareTo(other.optionalRules) > 0) {
            return 1;
        }

        return this.kinDef.compareTo(other.kinDef);
    }
}