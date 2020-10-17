package org.mwrynn.tnt.options;

import org.mwrynn.tnt.stat.StatNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TntOptions {
    private int numRolls = 0;
    private int numThreads = 0;
    private boolean outputTiming = false;
    private String delimiter = ",";
    private List<String> statNameList;
    private StatNames validStatNames;
    private boolean header = false;
    private Set<String> validKinSet;
    private List<String> kinList;

    public TntOptions() {
        validStatNames = new StatNames();
    }

    public TntOptions(Set<String> validKinSet) {
        this();
        this.validKinSet = validKinSet;
        this.kinList = new ArrayList(validKinSet); //default to using them all
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

    public void addKin(String kin) {
        if (kinList == null) {
            kinList = new ArrayList<>();
        }
        if(!validKinSet.stream().anyMatch(kin::equalsIgnoreCase)) {
            throw new RuntimeException("invalid kin argument: " + kin);
        }

        kinList.add(kin.toUpperCase());
    }

    public void setKinList(List<String> kinList) {
        this.kinList = null;
        for (String kin : kinList) {
            addKin(kin);
        }
    }

    public List<String> getKinList() {
        return kinList;
    }

    public void addStatName(String statName) {
        if (statNameList == null) {
            statNameList = new ArrayList<>();
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
