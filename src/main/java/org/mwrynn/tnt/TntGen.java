package org.mwrynn.tnt;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.character.Kin;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.options.OptionsReader;
import org.mwrynn.tnt.options.TntOptions;
import org.mwrynn.tnt.roller.Roller;
import org.mwrynn.tnt.rules.RulesSet;
import org.mwrynn.tnt.stat.Stat;
import org.mwrynn.tnt.stat.StatNames;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mwrynn.tnt.character.attribute.AttributeName.*;

public class TntGen {
    private TntOptions tntOptions;
    private static final String KIN_CLASS_PREFIX = "org.mwrynn.tnt.character.";

    ConcurrentHashMap<MapKey, Long> statsMap = new ConcurrentHashMap<>();

    class RulesPlusKin implements Comparable<RulesPlusKin> {
        RulesSet rulesSet;
        Kin kin;

        RulesPlusKin(RulesSet rulesSet, Kin kin) {
            this.rulesSet = rulesSet;
            this.kin = kin;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rulesSet, kin);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            RulesPlusKin other = (RulesPlusKin)obj;

            return  (this.rulesSet.equals(other.rulesSet)) &&
                    (this.kin.equals(other.kin));
        }

        @Override
        public int compareTo(RulesPlusKin other) {
            if (this.equals(other)) {
                return 0;
            }
            if (this.rulesSet.compareTo(other.rulesSet) < 0) {
                return -1;
            }
            if (this.rulesSet.compareTo(other.rulesSet) > 0) {
                return 1;
            }
            return this.kin.compareTo(other.kin);
        }
    }

    class MapKey implements Comparable<MapKey> {
        RulesPlusKin rulesPlusKin;
        Stat stat;

        MapKey(RulesPlusKin rulesPlusKin, Stat stat) {
            this.rulesPlusKin = rulesPlusKin;
            this.stat = stat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(stat, rulesPlusKin);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            MapKey other = (MapKey)obj;

            return  (this.rulesPlusKin.equals(other.rulesPlusKin)) &&
                    (this.stat.equals(other.stat));
        }

        @Override
        public String toString() {
            //format is basically RULESSET,KIN,STATNAME,STATVAL but delimiter can be different
            return rulesPlusKin.rulesSet + tntOptions.getDelimiter() + rulesPlusKin.kin + tntOptions.getDelimiter() +
                    stat.getStatName() + tntOptions.getDelimiter() + stat.getValue();
        }

        @Override
        public int compareTo(MapKey other) {
            if(this.equals(other)) {
                return 0;
            }
            if(this.rulesPlusKin.equals(other.rulesPlusKin)) {
                return this.stat.compareTo(other.stat);
            }

            return this.rulesPlusKin.compareTo(other.rulesPlusKin);
        }
    }

    private List<RulesPlusKin> makeAllRulesKinCombos() {
        ArrayList<RulesPlusKin> rulesKinList = new ArrayList<>();
        for (RulesSet rulesSet : RulesSet.values()) {
            for (Kin kin : Kin.values()) {
                rulesKinList.add(new RulesPlusKin(rulesSet, kin));
            }
        }
        return rulesKinList;
    }

    public void generate() {
        ExecutorService executorService = Executors.newFixedThreadPool(tntOptions.getNumThreads());

        //set up all combinations of rulesSet + kin
        List<RulesPlusKin> rulesKinList = makeAllRulesKinCombos();

        for (RulesPlusKin rulesKin : rulesKinList) {
            executorService.execute(() -> {
                try {
                    Character c;

                    Class<?> clazz = rulesKin.kin.getKinClass();
                    Constructor<?> ctor = clazz.getConstructor(RulesSet.class);
                    c = (Character)ctor.newInstance(rulesKin.rulesSet);

                    if(c.isValidInRulesSet()) {
                        generateLoop(c, rulesKin);
                    }
                } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //TODO is this a good way to handle the InterruptedException?
            throw new RuntimeException(e);
        }

        printResults();
    }

    private void printResults() {
        Stream<Map.Entry<MapKey, Long>> stream = statsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey());

        Map<MapKey, Long> mapSorted = stream.collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)
        );

        if(tntOptions.getHeader()) {
            System.out.println("RULESSET" + tntOptions.getDelimiter() + "KIN" + tntOptions.getDelimiter() + "STAT" + tntOptions.getDelimiter() + "COUNT");
        }

        for (Map.Entry<MapKey, Long> entry : mapSorted.entrySet()) {
            System.out.println(entry.getKey() + tntOptions.getDelimiter() + entry.getValue());
        }

        //collect.forEach((key,value) -> System.out.println(key + tntOptions.getDelimiter() + value));
    }

    private void generateLoop(Character c, RulesPlusKin rulesKin) {
        Dice dice = new Dice(3, 6);
        Roller roller = new Roller(rulesKin.rulesSet, dice);
        RulesPlusKin rulesPlusKin = new RulesPlusKin(rulesKin.rulesSet, rulesKin.kin);

        for (int i = 0; i < tntOptions.getNumRolls(); i++) {
            c.setStr(roller.rollAttribute(c.getStr()));
            c.setDex(roller.rollAttribute(c.getDex()));
            c.setCon(roller.rollAttribute(c.getCon()));
            c.setSpd(roller.rollAttribute(c.getSpd()));
            c.setIq(roller.rollAttribute(c.getIq()));
            c.setLk(roller.rollAttribute(c.getLk()));
            c.setChr(roller.rollAttribute(c.getStr()));
            c.setWiz(roller.rollAttribute(c.getChr()));

            collectAllStats(c, rulesPlusKin);
        }
    }

    private void collectAllStats(Character c, RulesPlusKin rulesPlusKin) {
        for(String stat : tntOptions.getStatNameList()) {
            MapKey mapKey = null;
            switch (stat) {
                case "STR":
                    mapKey = new MapKey(rulesPlusKin, new Stat(STR.toString(), c.getStr().getValue()));
                    break;

                case "DEX":
                    mapKey = new MapKey(rulesPlusKin, new Stat(DEX.toString(), c.getDex().getValue()));
                    break;

                case "CON":
                    mapKey = new MapKey(rulesPlusKin, new Stat(CON.toString(), c.getCon().getValue()));
                    break;

                case "SPD":
                    mapKey = new MapKey(rulesPlusKin, new Stat(SPD.toString(), c.getSpd().getValue()));
                    break;

                case "IQ":
                     mapKey = new MapKey(rulesPlusKin, new Stat(IQ.toString(), c.getIq().getValue()));
                    break;

                case "LK":
                    mapKey = new MapKey(rulesPlusKin, new Stat(LK.toString(), c.getLk().getValue()));
                    break;

                case "CHR":
                    mapKey = new MapKey(rulesPlusKin, new Stat(CHR.toString(), c.getChr().getValue()));
                    break;

                case "WIZ":
                    mapKey = new MapKey(rulesPlusKin, new Stat(WIZ.toString(), c.getWiz().getValue()));
                    break;

                case StatNames.ADDS:
                    mapKey = new MapKey(rulesPlusKin, new Stat(StatNames.ADDS, c.getAdds()));
                    break;

            }

            Long tally = statsMap.get(mapKey);

            if (tally != null) {
                tally++;
            } else {
                tally = 1L;
            }

            statsMap.put(mapKey, tally);

        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        TntGen tntGen = new TntGen();
        OptionsReader optionsReader = new OptionsReader();
        tntGen.tntOptions = optionsReader.parse(args);
        if(tntGen.tntOptions == null) {
            optionsReader.printHelp();
            System.exit(1);
        }

        tntGen.generate();

        if (tntGen.tntOptions.getOutputTiming()) {
            long endTime = System.nanoTime();;
            long timeElapsed = (endTime - startTime) / 1000000L;

            System.out.println("execution time: " + timeElapsed + " ms");
        }
    }
}
