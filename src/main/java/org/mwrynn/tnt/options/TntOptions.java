package org.mwrynn.tnt.options;

import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.StatNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TntOptions {
    private int numRolls = 0;
    private int numThreads = 0;
    private boolean outputTiming = false;
    private String delimiter = ",";
    private List<String> statNameList;
    private final StatNames validStatNames;
    private boolean header = false;

    private Set<String> validKinSet;
    private List<String> kinList;

    private List<OptionalRules> optionalList;
    private Set<OptionalRules> validOptionalSet;

    private List<RulesEdition> rulesEditionList;
    private Set<RulesEdition> validRulesEditionSet;

    private boolean isAggregatedOutput = true;

    public TntOptions() {
        validStatNames = new StatNames();
    }

    public TntOptions(Set<String> validKinSet, Set<OptionalRules> validOptionalSet,
                      Set<RulesEdition> validRulesEditionSet) {
        this();
        this.validKinSet = validKinSet;
        this.validOptionalSet = validOptionalSet;
        this.validRulesEditionSet = validRulesEditionSet;

        this.kinList = new ArrayList<>(validKinSet); //default to using them all

        this.optionalList = new ArrayList<>(); //default to using them all
        this.optionalList.addAll(Arrays.asList(OptionalRules.values()));

        this.rulesEditionList = new ArrayList<>(); //default to using them all
        this.rulesEditionList.addAll(Arrays.asList(RulesEdition.values()));
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

    public void addOptional(OptionalRules optionalRules) {
        if (optionalList == null) {
            optionalList = new ArrayList<>();
        }

        boolean found = false;
        for (OptionalRules validOptional : validOptionalSet) {
            if(optionalRules.equals(validOptional)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("invalid optional rules argument: " + optionalRules);
        }

        optionalList.add(optionalRules);
    }

    public void setOptionalList(List<OptionalRules> optionalList) {
        this.optionalList = null;
        for (OptionalRules optionalRules : optionalList) {
            addOptional(optionalRules);
        }
    }

    public List<OptionalRules> getOptionalList() {
        return optionalList;
    }

    public void addRulesEdition(RulesEdition rulesEdition) {
        if (rulesEditionList == null) {
            rulesEditionList = new ArrayList<>();
        }

        boolean found = false;
        for (RulesEdition validRulesEdition : validRulesEditionSet) {
            if(rulesEdition.equals(validRulesEdition)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("invalid rules edition argument: " + rulesEdition);
        }

        rulesEditionList.add(rulesEdition);
    }

    public void setRulesEditionList(List<RulesEdition> rulesEditionList) {
        this.rulesEditionList = null;
        for (RulesEdition rulesEdition : rulesEditionList) {
            addRulesEdition(rulesEdition);
        }
    }

    public List<RulesEdition> getRulesEditionList() {
        return rulesEditionList;
    }

    public boolean getAggregatedOutput() {
        return isAggregatedOutput;
    }

    public void setIsAggregatedOutput(boolean isAggregatedOutput) {
        this.isAggregatedOutput = isAggregatedOutput;
    }

}
