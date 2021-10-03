package org.mwrynn.tnt.output;

import org.mwrynn.tnt.stat.StatsMap;
import org.mwrynn.tnt.stat.StatsMap.StatsMapKey;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatsOutputter implements Outputter {
    StatsMap statsMap;

    public StatsOutputter(StatsMap statsMap) {
        this.statsMap = statsMap;
    }

    public void output(boolean hasHeader, String delimiter) {
        Stream<Map.Entry<StatsMapKey, Long>> stream = statsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey());

        Map<StatsMapKey, Long> mapSorted = stream.collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)
        );

        if(hasHeader) {
            System.out.println("EDITION" + delimiter + "OPTIONALRULES" + delimiter + "KIN" + delimiter + "STAT" + delimiter + "VALUE" + delimiter + "COUNT");
        }

        for (Map.Entry<StatsMapKey, Long> entry : mapSorted.entrySet()) {
            System.out.println(keyString(entry.getKey(), delimiter) + delimiter + entry.getValue());
        }
    }

    private String keyString(StatsMapKey key, String delimiter) {
        return key.rulesPlusKin.rulesEdition + delimiter + key.rulesPlusKin.optionalRules + delimiter +
                key.rulesPlusKin.kinDef.getKinName() + delimiter + key.stat.getStatName() + delimiter + key.stat.getValue();
    }
}
