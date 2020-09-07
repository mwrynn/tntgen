package mwrynn.tnt.roller;

import mwrynn.tnt.character.Character;
import mwrynn.tnt.character.Attribute;
import mwrynn.tnt.dice.Dice;
import mwrynn.tnt.dice.DiceRollResult;
import mwrynn.tnt.rules.RulesSet;

public class Roller {

    private Character character;
    private RulesSet rulesSet;
    private Dice dice;
    private static final int REROLL_IF_LESS_THAN_THIS = 9;
    private boolean rerollIfTooLow = false;

    public Roller(RulesSet rulesSet, Character character, Dice dice) {
        this.rulesSet = rulesSet;
        this.character = character;
        this.dice = dice;

        if ( (rulesSet == RulesSet.DELUXE_W_LOW_REROLL) || (rulesSet == RulesSet.FIFTH_W_LOW_REROLL) ) {
            this.rerollIfTooLow = true;
        }
    }

    public void rollAllAttributes() {
        character.str = rollAttribute(Attribute.STR);
        character.dex = rollAttribute(Attribute.DEX);
        character.con = rollAttribute(Attribute.CON);
        character.spd = rollAttribute(Attribute.SPD);
        character.iq = rollAttribute(Attribute.IQ);
        character.lk = rollAttribute(Attribute.LK);
        character.chr = rollAttribute(Attribute.CHR);
        character.spd = rollAttribute(Attribute.SPD);
    }

    private int rollAttribute(Attribute attribute) {
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

        switch (attribute) {
            case STR:
                mult = character.strMult;
                break;
            case DEX:
                mult = character.dexMult;
                break;
            case CON:
                mult = character.conMult;
                break;
            case SPD:
                mult = character.spdMult;
                break;
            case IQ:
                mult = character.iqMult;
                break;
            case LK:
                mult = character.lkMult;
                break;
            case CHR:
                mult = character.chrMult;
                break;
            case WIZ:
                mult = character.wizMult;
        }

        float runningTotalMultiplied = (float)runningTotal * mult;

        if (rulesSet == RulesSet.DELUXE || rulesSet == RulesSet.DELUXE_W_LOW_REROLL) {
            if (mult > 1) { //round down "good" multipliers in Deluxe
                finalRoll = (int)Math.floor(runningTotalMultiplied);
            } else { //round up "bad" multipliers in Deluxe
                finalRoll = (int)Math.ceil(runningTotalMultiplied);
            }
        } else { //fifth edition round up: TODO verify this
            finalRoll = (int)Math.ceil(runningTotalMultiplied);
        }

        return finalRoll;
    }

    private DiceRollResult rollDice() {
        return dice.roll();
    }

    private boolean shouldReroll(int numRolled) {
        return (rerollIfTooLow && (numRolled < REROLL_IF_LESS_THAN_THIS));
    };


}
