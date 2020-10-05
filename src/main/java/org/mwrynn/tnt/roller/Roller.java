package org.mwrynn.tnt.roller;

import org.mwrynn.tnt.character.attribute.Attribute;
import org.mwrynn.tnt.character.attribute.AttributeName;
import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.dice.DiceRollResult;
import org.mwrynn.tnt.rules.RulesSet;

public class Roller {
    private RulesSet rulesSet;
    private Dice dice;
    private static final int REROLL_IF_LESS_THAN_THIS = 9; //for *_REROLL rulesSets only (rerollIfTooLow == false)
    private boolean rerollIfTooLow = false;

    public Roller(RulesSet rulesSet, Dice dice) {
        this.rulesSet = rulesSet;
        this.dice = dice;

        if ( (rulesSet == RulesSet.DELUXE_W_LOW_REROLL) || (rulesSet == RulesSet.FIFTH_W_LOW_REROLL) ) {
            this.rerollIfTooLow = true;
        }
    }

    /*
    public void rollAllAttributes() {
        character.str = rollAttribute(AttributeName.STR);
        character.dex = rollAttribute(AttributeName.DEX);
        character.con = rollAttribute(AttributeName.CON);
        character.spd = rollAttribute(AttributeName.SPD);
        character.iq = rollAttribute(AttributeName.IQ);
        character.lk = rollAttribute(AttributeName.LK);
        character.chr = rollAttribute(AttributeName.CHR);
        character.spd = rollAttribute(AttributeName.SPD);
    }
    */

    public Attribute rollAttribute(Attribute attribute) {
        int runningTotal = 0;
        DiceRollResult diceRollResult;
        int finalRoll;
        float mult = 1;

        if (rulesSet == RulesSet.DELUXE_W_LOW_REROLL) {

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


        } else if (rulesSet == RulesSet.FIFTH_W_LOW_REROLL) {
            do {
                diceRollResult = rollDice();
                runningTotal = diceRollResult.totalRolled;
            } while (shouldReroll(runningTotal)); //reroll if result is too low, but apply TARO first


        } else { // FIFTH
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
