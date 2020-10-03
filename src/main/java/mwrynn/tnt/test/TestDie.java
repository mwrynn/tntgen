package mwrynn.tnt.test;

import mwrynn.tnt.dice.Die;

public class TestDie {
    //test if we run a ton of these, the distribution is what is expected

    public static void main(String args[]) {
        //common rule of thumb is you need to roll at least 5 times as many sides to test for validity, so 30 for a D6
        final int NUM_ROLLS = 300000;
        final int NUM_DICE_FACES = 6;
        final int EXPECTED = NUM_ROLLS / NUM_DICE_FACES;
        final int DEGREES_FREEDOM = NUM_DICE_FACES-1; //would be used for a lookup if we were looking CRIT_VAL (below) up in a table, note this ChiSq stuff probably exists in Math or other package; just thought I'd learn it myself
        final double CRIT_VAL = 20.515; //unfortunately hardcoded for D6 for now; this is the value that the ChiSq total must be less than to ensure 0.999 confidence

        Die die = new Die();

        int rolled[] = new int[NUM_DICE_FACES];

        System.out.println("Rolling " + NUM_ROLLS + " dice...");

        for (int i=1; i<NUM_ROLLS; i++) {
            rolled[die.roll() - 1]++;
        }

        //output
        for (int i=1; i <= NUM_DICE_FACES; i++) {
            System.out.println("Number of " + i + ":\t" + rolled[i-1]);
        }

        double chiSq[] = new double[NUM_DICE_FACES];
        for (int i=0; i<NUM_DICE_FACES-1; i++) {
            chiSq[i] = Math.pow((rolled[i] - EXPECTED), 2) / EXPECTED;
        }

        //add 'em up
        double sumChiSq = 0.0;
        for (int i=0; i<NUM_DICE_FACES-1; i++) {
            System.out.println("chiSq for " + (i+1) + ":\t" + chiSq[i]);
            sumChiSq += chiSq[i];
        }

        System.out.println("sumChiSq:\t" + sumChiSq);

        assert (sumChiSq < CRIT_VAL) : "die rolls are either bad, too few rolled, or this is the 0.001 case";
    }
}
