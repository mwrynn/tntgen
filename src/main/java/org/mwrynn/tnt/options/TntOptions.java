package org.mwrynn.tnt.options;

public class TntOptions {
    private int numRolls = 0;
    private int numThreads = 0;
    private boolean outputTiming = false;

    public TntOptions(int numRolls, int numThreads, boolean outputTiming) {
        this.numRolls = numRolls;
        this.numThreads = numThreads;
        this.outputTiming = outputTiming;
    }

    public TntOptions() {

    }

    public void setNumRolls(int numRolls) {
        this.numRolls = numRolls;
    }

    public int getNumRolls() {
        return numRolls;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setOutputTiming(boolean outputTiming) {
        this.outputTiming = outputTiming;
    }

    public boolean getOutputTiming() {
        return outputTiming;
    }

}
