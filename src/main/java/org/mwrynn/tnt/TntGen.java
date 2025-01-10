package org.mwrynn.tnt;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.character.KinConf;
import org.mwrynn.tnt.character.KinConfReader;
import org.mwrynn.tnt.character.KinDef;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.options.OptionsReader;
import org.mwrynn.tnt.options.TntOptions;
import org.mwrynn.tnt.output.CharacterOutputter;
import org.mwrynn.tnt.output.Outputter;
import org.mwrynn.tnt.output.StatsOutputter;
import org.mwrynn.tnt.roller.Roller;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.StatsCollector;
import org.mwrynn.tnt.stat.StatsMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TntGen {
    private TntOptions tntOptions;
    private KinConf kinConf;

    StatsMap statsMap = new StatsMap();
    List<Character> characterList = Collections.synchronizedList(new LinkedList<>());
    Outputter outputter;

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

    public void generate(boolean aggregate) {
        ExecutorService executorService = Executors.newFixedThreadPool(tntOptions.getNumThreads());

        //set up combinations of rulesEdition + kin
        List<RulesPlusKin> rulesKinList = makeRulesKinCombos();

        for (RulesPlusKin rulesKin : rulesKinList) {
            executorService.execute(() -> {
                for (int i = 0; i < tntOptions.getNumRolls(); i++) {
                    Character c = new Character(rulesKin.rulesEdition, rulesKin.optionalRules);
                    c.setKinDef(rulesKin.kinDef);
                    rollAndPopulateCharacterAttributes(c, rulesKin);
                    if (aggregate) {
                        collectAgg(c, rulesKin);
                    } else {
                        collectChar(c);
                    }
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

        if(aggregate) {
            outputter = new StatsOutputter(statsMap);
        } else {
            outputter = new CharacterOutputter(characterList);
        }
        outputter.output(tntOptions.getHeader(), tntOptions.getDelimiter());
    }

    private void rollAndPopulateCharacterAttributes(Character c, RulesPlusKin rulesPlusKin) {
        Dice dice = new Dice(3);
        Roller roller = new Roller(rulesPlusKin.rulesEdition, rulesPlusKin.optionalRules, dice);

        //for (int i = 0; i < tntOptions.getNumRolls(); i++) {
            c.setStr(roller.rollAttribute(c.getStr()));
            c.setDex(roller.rollAttribute(c.getDex()));
            c.setCon(roller.rollAttribute(c.getCon()));
            c.setSpd(roller.rollAttribute(c.getSpd()));
            c.setIq(roller.rollAttribute(c.getIq()));
            c.setLk(roller.rollAttribute(c.getLk()));
            c.setChr(roller.rollAttribute(c.getChr()));
            c.setWiz(roller.rollAttribute(c.getWiz()));
        //}
    }

    private void collectAgg(Character c, RulesPlusKin rulesPlusKin) {
        StatsCollector statsCollector = new StatsCollector(statsMap, tntOptions.getStatNameList());
        statsCollector.collectAllStats(c, rulesPlusKin);
    }

    private void collectChar(Character c) {
        characterList.add(c);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        TntGen tntGen = new TntGen();

        final Set<OptionalRules> validOptionalRulesSet = new HashSet<>(Arrays.asList(OptionalRules.values()));
        final Set<RulesEdition> validRulesEditionSet = new HashSet<>(Arrays.asList(RulesEdition.values()));

        OptionsReader optionsReader = new OptionsReader(validOptionalRulesSet, validRulesEditionSet);
        tntGen.tntOptions = optionsReader.parse(args);
        if(tntGen.tntOptions == null) {
            optionsReader.printHelp();
            System.exit(1);
        }

        try {
            KinConfReader kinConfReader;
            if (tntGen.tntOptions.getKinConfPath() != null) {
                kinConfReader = new KinConfReader(tntGen.tntOptions.getKinConfPath(), true); // Load from file
            } else {
                kinConfReader = new KinConfReader(); //use default
            }
            tntGen.kinConf = kinConfReader.getKinConf();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final Set<String> validKinSet = tntGen.kinConf.getKinDefs().stream().map(KinDef::getKinName).collect(Collectors.toSet());

        tntGen.generate(tntGen.tntOptions.getAggregatedOutput());

        tntGen.tntOptions.setValidKinSet(validKinSet);

        if (tntGen.tntOptions.getOutputTiming()) {
            long endTime = System.nanoTime();;
            long timeElapsed = (endTime - startTime) / 1000000L;

            System.out.println("execution time: " + timeElapsed + " ms");
        }
    }
}
