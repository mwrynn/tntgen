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
        String kindred;

        RulesPlusKin(RulesSet rulesSet, String kindred) {
            this.rulesSet = rulesSet;
            this.kindred = kindred;
        }

        @Override
        public int hashCode() {
            int result = (rulesSet != null ? rulesSet.hashCode() : 0);
            result = 15 * result + (kindred != null ? kindred.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            RulesPlusKin other = (RulesPlusKin)obj;

            return  (this.rulesSet.equals(other.rulesSet)) &&
                    (this.kindred.equals(other.kindred));
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
            return (addsValue ^ (addsValue >>> 16)) + rulesPlusKind.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            MapKey other = (MapKey)obj;

            return  (this.rulesPlusKind.rulesSet.equals(other.rulesPlusKind.rulesSet)) &&
                    (this.rulesPlusKind.kindred.equals(other.rulesPlusKind.kindred)) &&
                    (this.addsValue == other.addsValue);
        }

        public String toString() {
            return rulesPlusKind.rulesSet + "," + rulesPlusKind.kindred + "," + addsValue;
        }
    }

    private List<RulesPlusKin> makeAllRulesKinCombos() {
        ArrayList<RulesPlusKin> rulesKinList = new ArrayList<>();
        for (RulesSet rulesSet : RulesSet.values()) {
            for (Kin kin : Kin.values()) {
                rulesKinList.add(new RulesPlusKin(rulesSet, kin.toString()));
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
                    Class<?> clazz = Class.forName(KIN_CLASS_PREFIX + rulesKin.kindred);
                    Constructor<?> ctor = clazz.getConstructor(RulesSet.class);
                    c = (Character)ctor.newInstance(rulesKin.rulesSet);

                    generateLoop(c);
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
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

    private void generateLoop(Character c) {
        Roller roller;
        Dice dice = new Dice(3, 6);

        if (!c.isValidInRulesSet()) {
            return;
        }

        for (int i = 0; i < tntOptions.getNumRolls(); i++) {
            roller = new Roller(c.rulesSet, c, dice);
            roller.rollAllAttributes();
            MapKey mapKey = new MapKey(new RulesPlusKin(c.rulesSet, c.kinName()), c.getAdds());
            Long existingAddsTally = addsMap.get(mapKey);

            if (existingAddsTally != null) {
                existingAddsTally++;
            } else {
                existingAddsTally=1L;
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
