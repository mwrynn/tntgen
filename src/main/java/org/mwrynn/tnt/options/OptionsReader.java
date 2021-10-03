package org.mwrynn.tnt.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.StatNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class OptionsReader {
    private static final int NUM_THREADS_DEFAULT = 4;

    private CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private HelpFormatter formatter = new HelpFormatter();
    private Set<String> validKinSet;
    private Set<OptionalRules> validOptionalRulesSet;
    private Set<RulesEdition> validRulesEditionSet;

    public OptionsReader(final Set<String> validKinSet, final Set<OptionalRules> validOptionalRulesSet, final Set<RulesEdition> validRulesEditionSet) {
        this.validKinSet = validKinSet;
        this.validOptionalRulesSet = validOptionalRulesSet;
        this.validRulesEditionSet = validRulesEditionSet;
    }

    public TntOptions parse(String[] args) {
        options.addRequiredOption("r", "rolls", true,"number of rolls per combination of rules and kindred");
        options.addOption("t", "time",false, "display execution time");
        options.addOption("p",  "parallel", true, "number of parallel threads");
        options.addOption("d",  "delimiter", true, "output field delimiter; default is ,");

        Option statsOption = new Option("s", "stats", true, "comma-separated list of the stats to collect " +
                "and output; ignored if individual character output is enabled; Valid stats: ADDS,STR,DEX,CON,SPD,IQ,LK,CHR,WIZ");
        statsOption.setArgs(Option.UNLIMITED_VALUES);
        statsOption.setValueSeparator(',');
        statsOption.setType(List.class);
        options.addOption(statsOption);

        Option kinOption = new Option("k", "kin", true, "comma-separated list of the kindred to collect stats for");
        kinOption.setArgs(Option.UNLIMITED_VALUES);
        kinOption.setValueSeparator(',');
        kinOption.setType(List.class);
        options.addOption(kinOption);

        options.addOption("h",  "header", false, "output column headers");

        Option optionalOption = new Option("o", "optional", true, "comma-separated list of the optional rules to apply");
        optionalOption.setArgs(Option.UNLIMITED_VALUES);
        optionalOption.setValueSeparator(',');
        optionalOption.setType(List.class);
        options.addOption(optionalOption);

        Option rulesEditionOption = new Option("e", "edition", true, "comma-separated list of the rules editions to use");
        rulesEditionOption.setArgs(Option.UNLIMITED_VALUES);
        rulesEditionOption.setValueSeparator(',');
        rulesEditionOption.setType(List.class);
        options.addOption(rulesEditionOption);

        options.addOption("c",  "char-output", false, "whether to output individual character stats or aggregated stats; default is false (aggregated)");

        TntOptions tntOptions = new TntOptions(validKinSet, validOptionalRulesSet, validRulesEditionSet);

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            tntOptions.setNumRolls(Integer.valueOf(line.getOptionValue("rolls")));

            if (line.hasOption("parallel")) {
                tntOptions.setNumThreads(Integer.valueOf(line.getOptionValue("parallel")));
            } else {
                tntOptions.setNumThreads(NUM_THREADS_DEFAULT);
            }

            if (line.hasOption("time")) {
                tntOptions.setOutputTiming(true);
            }

            if (line.hasOption("delimiter")) {
                tntOptions.setDelimiter((line.getOptionValue("delimiter")));
            }

            if (line.hasOption("stats")) {
                List<String> stats = Arrays.asList(line.getOptionValues("stats"));
                tntOptions.setStatNameList(stats);
            } else {
                tntOptions.addStatName(StatNames.ADDS);
            }

            if (line.hasOption("header")) {
                tntOptions.setHeader(true);
            }

            if (line.hasOption("char-output")) {
                tntOptions.setIsAggregatedOutput(false);
            }

            if (line.hasOption("kin")) {
                List<String> kin = Arrays.asList(line.getOptionValues("kin"));
                tntOptions.setKinList(kin);
            }

            if (line.hasOption("optional")) {
                List<String> optionalRulesStrList = Arrays.asList(line.getOptionValues("optional"));
                List<OptionalRules> optionalRulesList = new ArrayList<>();
                for(String optionalRulesStr : optionalRulesStrList) {
                    optionalRulesList.add(OptionalRules.valueOf(optionalRulesStr));
                }
                tntOptions.setOptionalList(optionalRulesList);
            }

            if (line.hasOption("edition")) {
                List<String> rulesEditionStrList = Arrays.asList(line.getOptionValues("edition"));
                List<RulesEdition> rulesEditionList = new ArrayList<>();
                for(String rulesEditionStr : rulesEditionStrList) {
                    rulesEditionList.add(RulesEdition.valueOf(rulesEditionStr));
                }
                tntOptions.setRulesEditionList(rulesEditionList);
            }

        }
        catch( ParseException exp ) {
            exp.printStackTrace();
            return null;
        }

        return tntOptions;
    }

    public void printHelp() {
        formatter.printHelp("tntgen", options);
    }
}
