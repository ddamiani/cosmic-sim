package com.ddamiani.cosmicsim;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * The basic command line interface to run the Java version of the cosmic ray sim
 */
public final class SimulationMain {
    private static void printUsage(Options options) {
        final String spacer = "************************************************************";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("cosmic-sim [-h]", spacer, options, "", true);
    }

    private static void runSimulations(int count, double energy, double position) {
        System.out.println(String.format("Running the simulation %d times with an initial photon energy of %.0f GeV", count, energy / 1000.));
        System.out.println(String.format("The shower will be sampled at %.2f radiation lengths from the top of the atmosphere.", position));
    }

    /**
     * Returns the options for displaying help info.
     * <p/>
     * Note that suppress 'static-access' warnings are on because the apache commons is doing
     * some uncool things...
     *
     * @return the help option set
     */
    @SuppressWarnings("static-access")
    private static Options getHelpOpts() {
        Options helpOptions = new Options();
        helpOptions.addOption(OptionBuilder
                .withDescription("Displays help information")
                .withLongOpt("help")
                .create('h'));

        return helpOptions;
    }

    /**
     * Returns the main options set.
     * <p/>
     * Note that suppress 'static-access' warnings are on because the apache commons is doing
     * some uncool things...
     *
     * @return the main option set
     */
    @SuppressWarnings("static-access")
    private static Options getMainOpts() {
        Options mainOptions = new Options();
        mainOptions.addOption(OptionBuilder.withArgName("ENERGY")
                .isRequired(true)
                .hasArg()
                .withDescription("The initial energy of the incoming photon in MeV")
                .withLongOpt("energy")
                .create('e'));
        mainOptions.addOption(OptionBuilder.withArgName("POSITION")
                .isRequired(true)
                .hasArg()
                .withDescription("The position in radiation lengths at which to sample the shower")
                .withLongOpt("position")
                .create('p'));
        mainOptions.addOption(OptionBuilder.withArgName("COUNT")
                .isRequired(true)
                .hasArg()
                .withDescription("The number of times to run the shower simulation")
                .withLongOpt("count")
                .create('c'));
        mainOptions.addOption(OptionBuilder.withArgName("SEED")
                .hasArg()
                .withDescription("This option sets a specific seed for the simulation")
                .withLongOpt("seed")
                .create('s'));

        return mainOptions;
    }

    /**
     * Parses the cli commands.
     *
     * @param args Input parameters
     */
    public static void cliParse(String... args) throws ParseException {

        Options helpOptions = getHelpOpts();

        Options mainOptions = getMainOpts();

        CommandLineParser commandLineParser = new PosixParser();
        CommandLine commandLine = commandLineParser.parse(helpOptions, args, true);
        if (commandLine.hasOption('h')) {
            printUsage(mainOptions);
            return;
        }

        commandLine = commandLineParser.parse(mainOptions, args, true);

        if (commandLine.hasOption('s')) {
            RandomNumberHelper.setSeed(Long.parseLong(commandLine.getOptionValue('s')));
        } else {
            RandomNumberHelper.setSeed(System.currentTimeMillis());
        }

        runSimulations(Integer.parseInt(commandLine.getOptionValue('c')),
                Double.parseDouble(commandLine.getOptionValue('e')),
                Double.parseDouble(commandLine.getOptionValue('p')));
    }

    public static void main(String[] args) {
        try {
            cliParse(args);
        } catch (ParseException e) {
            System.err.println(e);
            System.exit(1);
        } catch (NumberFormatException ne) {
            System.err.println("Invalid input parameter type: " + ne);
            System.exit(1);
        }
    }
}
