package org.mwrynn.tnt;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.character.KinConf;
import org.mwrynn.tnt.character.KinConfReader;
import org.mwrynn.tnt.character.KinDef;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.options.OptionsReader;
import org.mwrynn.tnt.options.TntOptions;
import org.mwrynn.tnt.roller.Roller;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.Stat;
import org.mwrynn.tnt.stat.StatNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mwrynn.tnt.character.attribute.AttributeName.*;

public class TntGen {
    private TntOptions tntOptions;
    private KinConf kinConf;

    ConcurrentHashMap<MapKey, Long> statsMap = new ConcurrentHashMap<>();

    class RulesPlusKin implements Comparable<RulesPlusKin> {
        RulesEdition rulesEdition;
        OptionalRules optionalRules;
        KinDef kinDef;

        RulesPlusKin(RulesEdition rulesEdition, OptionalRules optionalRules, KinDef kinDef) {
            this.rulesEdition = rulesEdition;
            this.optionalRules = optionalRules;
            this.kinDef = kinDef;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rulesEdition, optionalRules, kinDef);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            RulesPlusKin other = (RulesPlusKin)obj;

            return  (this.rulesEdition.equals(other.rulesEdition)) &&
                    (this.optionalRules.equals(other.optionalRules)) &&
                    (this.kinDef.equals(other.kinDef));
        }

        @Override
        public int compareTo(RulesPlusKin other) {
            if (this.equals(other)) {
                return 0;
            }
            if (this.rulesEdition.compareTo(other.rulesEdition) < 0) {
                return -1;
            }
            if (this.rulesEdition.compareTo(other.rulesEdition) > 0) {
                return 1;
            }
            if (this.optionalRules.compareTo(other.optionalRules) < 0) {
                return -1;
            }
            if (this.optionalRules.compareTo(other.optionalRules) > 0) {
                return 1;
            }

            return this.kinDef.compareTo(other.kinDef);
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
            //format is basically EDITION,KIN,STATNAME,STATVAL but delimiter can be different
            return rulesPlusKin.rulesEdition + tntOptions.getDelimiter() + rulesPlusKin.optionalRules + tntOptions.getDelimiter() +
                    rulesPlusKin.kinDef.getKinName() + tntOptions.getDelimiter() + stat.getStatName() + tntOptions.getDelimiter() + stat.getValue();
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

    private List<RulesPlusKin> makeRulesKinCombos() {
        ArrayList<RulesPlusKin> rulesKinList = new ArrayList<>();
        
        List<KinDef> applicableKinDefs = kinConf.getKinDefs()
                .stream()
                .filter(k -> tntOptions.getKinList().contains(k.getKinName().toUpperCase()))
                .filter(k -> tntOptions.getRulesEditionList().contains(k.getRulesEdition()))
                .collect(Collectors.toList()); ;

        for (OptionalRules optionalRules : tntOptions.getOptionalList()) {
            for (KinDef kinDef : applicableKinDefs) {
                rulesKinList.add(new RulesPlusKin(kinDef.getRulesEdition(), optionalRules, kinDef));
            }
        }
        return rulesKinList;
    }

    public void generate() {
        ExecutorService executorService = Executors.newFixedThreadPool(tntOptions.getNumThreads());

        //set up combinations of rulesEdition + kin
        List<RulesPlusKin> rulesKinList = makeRulesKinCombos();

        for (RulesPlusKin rulesKin : rulesKinList) {
            executorService.execute(() -> {
                Character c = new Character(rulesKin.rulesEdition);
                c.setKinDef(rulesKin.kinDef);
                generateLoop(c, rulesKin);
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
            System.out.println("EDITION" + tntOptions.getDelimiter() + "OPTIONALRULES" + tntOptions.getDelimiter() + "KIN" + tntOptions.getDelimiter() + "STAT" + tntOptions.getDelimiter() + "VALUE" + tntOptions.getDelimiter() + "COUNT");
        }

        for (Map.Entry<MapKey, Long> entry : mapSorted.entrySet()) {
            System.out.println(entry.getKey() + tntOptions.getDelimiter() + entry.getValue());
        }
    }

    private void generateLoop(Character c, RulesPlusKin rulesPlusKin) {
        Dice dice = new Dice(3, 6);
        Roller roller = new Roller(rulesPlusKin.rulesEdition, rulesPlusKin.optionalRules, dice);

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

        try {
            KinConfReader kinConfReader = new KinConfReader();
            tntGen.kinConf = kinConfReader.getKinConf();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Set<String> validKinSet = tntGen.kinConf.getKinDefs().stream().map(KinDef::getKinName).collect(Collectors.toSet());
        Set<OptionalRules> validOptionalRulesSet = new HashSet<>(Arrays.asList(OptionalRules.values()));
        Set<RulesEdition> validRulesEditionSet = new HashSet<>(Arrays.asList(RulesEdition.values()));

        OptionsReader optionsReader = new OptionsReader(validKinSet, validOptionalRulesSet, validRulesEditionSet);
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
