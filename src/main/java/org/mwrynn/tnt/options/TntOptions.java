package org.mwrynn.tnt.options;

import org.mwrynn.tnt.stat.StatNames;

import java.util.ArrayList;
import java.util.List;

public class TntOptions {
    private int numRolls = 0;
    private int numThreads = 0;
    private boolean outputTiming = false;
    private String delimiter = ",";
    private List<String> statNameList;
    private StatNames validStatNames;
    private boolean header = false;

    public TntOptions(int numRolls, int numThreads, boolean outputTiming, List<String> statNameList) {
        validStatNames = new StatNames();
        this.numRolls = numRolls;
        this.numThreads = numThreads;
        this.outputTiming = outputTiming;
        this.statNameList = statNameList;
    }

    public TntOptions() {
        validStatNames = new StatNames();
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

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setStatNameList(List<String> statNameList) {
        for (String statName : statNameList) {
            addStatName(statName);
        }
    }

    public void addStatName(String statName) {
        if (statNameList == null) {
            statNameList = new ArrayList<String>();
        }
        if(!validStatNames.contains(statName)) {
            throw new RuntimeException("invalid stat argument: " + statName);
        }
        statNameList.add(statName);
    }

    public List<String> getStatNameList() {
        return statNameList;
    }

    public boolean getHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
