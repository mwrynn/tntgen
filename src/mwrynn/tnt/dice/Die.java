package mwrynn.tnt.dice;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private int sides;
    private int faceup;

    public Die() {
        this(6);
    }

    public Die(int sides) {
        this.sides=sides;
    }

    public int roll() {
        faceup = ThreadLocalRandom.current().nextInt(1, sides + 1);
        return faceup;
    }

    public int getFaceup() {
        return faceup;
    }
}
