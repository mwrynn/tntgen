package org.mwrynn.tnt;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.character.KinConf;
import org.mwrynn.tnt.character.KinConfReader;
import org.mwrynn.tnt.character.KinDef;
import org.mwrynn.tnt.dice.Dice;
import org.mwrynn.tnt.options.OptionsReader;
import org.mwrynn.tnt.options.TntOptions;
import org.mwrynn.tnt.output.StatsOutputter;
import org.mwrynn.tnt.roller.Roller;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.Stat;
import org.mwrynn.tnt.stat.StatNames;
import org.mwrynn.tnt.stat.StatsCollector;
import org.mwrynn.tnt.stat.StatsMap;

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

    StatsMap statsMap = new StatsMap();

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

        new StatsOutputter(statsMap, tntOptions.getHeader(), tntOptions.getDelimiter()).output();
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

            new StatsCollector(statsMap, tntOptions.getStatNameList()).collectAllStats(c, rulesPlusKin);
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

        final Set<String> validKinSet = tntGen.kinConf.getKinDefs().stream().map(KinDef::getKinName).collect(Collectors.toSet());
        final Set<OptionalRules> validOptionalRulesSet = new HashSet<>(Arrays.asList(OptionalRules.values()));
        final Set<RulesEdition> validRulesEditionSet = new HashSet<>(Arrays.asList(RulesEdition.values()));

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
