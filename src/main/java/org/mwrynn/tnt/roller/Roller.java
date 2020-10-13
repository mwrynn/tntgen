package org.mwrynn.tnt.roller;

import org.mwrynn.tnt.character.attribute.Attribute;
import org.mwrynn.tnt.character.attribute.AttributeName;
import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.dice.DiceRollResult;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesSet;

public class Roller {
    private RulesSet rulesSet;
    private OptionalRules optionalRules;
    private Dice dice;
    private static final int REROLL_IF_LESS_THAN_THIS = 9; //for *_REROLL rulesSets only (rerollIfTooLow == false)
    private boolean rerollIfTooLow = false;

    public Roller(RulesSet rulesSet, OptionalRules optionalRules, Dice dice) {
        this.rulesSet = rulesSet;
        this.optionalRules = optionalRules;
        this.dice = dice;

        if (optionalRules == OptionalRules.LOW_REROLL) {
            this.rerollIfTooLow = true;
        }
    }

    public Attribute rollAttribute(Attribute attribute) {
        int runningTotal = 0;
        DiceRollResult diceRollResult;
        int finalRoll;
        float mult = 1;

        if ( (rulesSet == RulesSet.DELUXE) && (optionalRules == OptionalRules.LOW_REROLL) ) {

            //roll with TARO
            do {
                runningTotal = 0;
                do {
                    diceRollResult = rollDice();
                    runningTotal += diceRollResult.totalRolled;
                }
                while (diceRollResult.allSame); //TARO check
            } while (shouldReroll(runningTotal)); //reroll if result is too low


        } else if (rulesSet == RulesSet.DELUXE) {
            do {
                diceRollResult = rollDice();
                runningTotal += diceRollResult.totalRolled;
            }
            while (diceRollResult.allSame); //TARO


        } else if ( (rulesSet == RulesSet.FIFTH) && (optionalRules == OptionalRules.LOW_REROLL) ) {
            do {
                diceRollResult = rollDice();
                runningTotal = diceRollResult.totalRolled;
            } while (shouldReroll(runningTotal)); //reroll if result is too low, but apply TARO first


        } else { // FIFTH; no optional rules
            diceRollResult = rollDice();
            runningTotal = diceRollResult.totalRolled;
        }

        attribute.setValue(runningTotal);
        return attribute;
    }

    private DiceRollResult rollDice() {
        return dice.roll();
    }

    private boolean shouldReroll(int numRolled) {
        return (rerollIfTooLow && (numRolled < REROLL_IF_LESS_THAN_THIS));
    };


}
