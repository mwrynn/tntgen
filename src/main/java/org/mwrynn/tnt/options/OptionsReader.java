package org.mwrynn.tnt.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.List;

public class OptionsReader {
    private static final int NUM_THREADS_DEFAULT = 4;

    private CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private HelpFormatter formatter = new HelpFormatter();

    public TntOptions parse(String[] args) {
        options.addRequiredOption("r", "rolls", true,"number of rolls per combination of rules and kindred");
        options.addOption("t", "time",false, "display execution time");
        options.addOption("p",  "parallel", true, "number of parallel threads");
        options.addOption("d",  "delimiter", true, "output field delimiter; default is ,");
        Option statsOption = new Option("s", "stats", true, "the stats to collect and output");
        statsOption.setArgs(Option.UNLIMITED_VALUES);
        statsOption.setValueSeparator(',');
        statsOption.setType(List.class);
        options.addOption(statsOption);

        TntOptions tntOptions = new TntOptions();

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
                List<String> statStrList = Arrays.asList(line.getOptionValues("stats"));
                tntOptions.setStatNameList(statStrList);
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
