package org.mwrynn.tnt.dice;

public class Dice {
    private final Die[] dice;
    private boolean allSame = false;
    private static final int DUMMY_ROLL = -999;

    Dice() {
        this(3);
    }

    public Dice(int num) {
        this(num, 6);
    }

    public Dice (int num, int sides) {
        dice = new Die[num];

        for (int i=0; i<num; i++) {
            dice[i] = new Die(sides);
        }
    }

    public DiceRollResult roll() {
        DiceRollResult diceRollResult = new DiceRollResult();
        int total = 0;
        boolean allSame = false;

        for (Die die : dice) {
            int currRoll = die.roll();
            total += currRoll;
        }

        //check if all dice same
        int lastRoll = DUMMY_ROLL;
        for (Die die : dice) {
            if (lastRoll == DUMMY_ROLL) { //first roll
                lastRoll = die.getFaceup();
            } else {
                if (die.getFaceup() != lastRoll) {
                    allSame = false;
                } else { //this die same as last die
                    allSame = true;
                    lastRoll = die.getFaceup();
                }
            }
        }

        this.allSame = allSame;

        diceRollResult.allSame = allSame;
        diceRollResult.totalRolled = total;

        return diceRollResult;
    }

    public boolean getAllSame() {
        return this.allSame;
    }
}
