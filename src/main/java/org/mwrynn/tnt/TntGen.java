package org.mwrynn.tnt;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.character.Kin;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.options.OptionsReader;
import org.mwrynn.tnt.options.TntOptions;
import org.mwrynn.tnt.roller.Roller;
import org.mwrynn.tnt.rules.RulesSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TntGen {
    private TntOptions tntOptions;
    private static final String KIN_CLASS_PREFIX = "org.mwrynn.tnt.character.";

    ConcurrentHashMap<MapKey, Long> addsMap = new ConcurrentHashMap<>();

    class RulesPlusKin {
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

    }

    class MapKey {
        RulesPlusKin rulesPlusKind;
        int addsValue;

        MapKey(RulesPlusKin rulesPlusKind, int addsValue) {
            this.rulesPlusKind = rulesPlusKind;
            this.addsValue = addsValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(addsValue, rulesPlusKind);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            MapKey other = (MapKey)obj;

            return  (this.rulesPlusKind.equals(other.rulesPlusKind)) &&
                    (this.addsValue == other.addsValue);
        }

        public String toString() {
            return rulesPlusKind.rulesSet + "," + rulesPlusKind.kin + "," + addsValue;
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

                    generateLoop(c, rulesKin);
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
        addsMap.forEach((key,value) -> System.out.println(key + "," + value));
    }

    private void generateLoop(Character c, RulesPlusKin rulesKin) {
        Dice dice = new Dice(3, 6);
        Roller roller = new Roller(rulesKin.rulesSet, dice);
        RulesPlusKin rulePlusKin = new RulesPlusKin(rulesKin.rulesSet, rulesKin.kin);

        for (int i = 0; i < tntOptions.getNumRolls(); i++) {
            c.setStr(roller.rollAttribute(c.getChr()));
            c.setDex(roller.rollAttribute(c.getDex()));
            c.setCon(roller.rollAttribute(c.getCon()));
            c.setSpd(roller.rollAttribute(c.getSpd()));
            c.setIq(roller.rollAttribute(c.getIq()));
            c.setLk(roller.rollAttribute(c.getChr()));
            c.setChr(roller.rollAttribute(c.getStr()));
            c.setWiz(roller.rollAttribute(c.getChr()));

            MapKey mapKey = new MapKey(rulePlusKin, c.getAdds());

            Long existingAddsTally = addsMap.get(mapKey);

            if (existingAddsTally != null) {
                existingAddsTally++;
            } else {
                existingAddsTally = 1L;
            }
            addsMap.put(mapKey, existingAddsTally);
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
