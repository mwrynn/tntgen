package org.mwrynn.tnt.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionsReader {
    private static final int NUM_THREADS_DEFAULT = 4;

    private CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private HelpFormatter formatter = new HelpFormatter();

    public TntOptions parse(String[] args) {
        options.addRequiredOption("r", "rolls", true,"number of rolls per combination of rules and kindred");
        options.addOption("t", "time",false, "display execution time");
        options.addOption("p",  "parallel", true, "number of parallel threads");

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
