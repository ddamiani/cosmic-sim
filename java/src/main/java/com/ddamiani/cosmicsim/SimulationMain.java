package com.ddamiani.cosmicsim;

import com.ddamiani.cosmicsim.particle.Particle;
import com.ddamiani.cosmicsim.particle.Photon;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * The basic command line interface to run the Java version of the cosmic ray sim
 */
public final class SimulationMain {

    /**
     * Simple inner class for aggregating the results of multiple simulation runs.
     */
    protected static final class Aggregator {
        private int mStep;
        private double mMean;
        private double mSumSquare;

        public Aggregator() {
            mStep = 0;
            mMean = 0.0;
            mSumSquare = 0.0;
        }

        public final void addStep(double stepValue) {
            mStep += 1;

            double diff = stepValue - mMean;
            mMean += diff / mStep;
            mSumSquare += diff * (stepValue - mMean);
        }

        public final int getCounts() {
            return mStep;
        }

        public final double getMean() {
            return mMean;
        }

        public final double getStdError() {
            return StrictMath.sqrt(mSumSquare / ((mStep - 1) * mStep));
        }
    }

    private static int printFrequency = 1000;

    private static void printOutputLine(String header, double mean, double error) {
        System.out.format(" %-8s %12.3f \u00B1 %.3f\n", header, mean, error);
    }

    private static void runSimulations(int count, double energy, double position) {
        System.out.println(String.format("Running the simulation %d times with an initial photon energy of %.0f GeV", count, energy / 1000.));
        System.out.println(String.format("The shower will be sampled at %.2f radiation lengths from the top of the atmosphere.", position));

        final Aggregator totalAggregator = new Aggregator();
        final Aggregator neutralAggregator = new Aggregator();
        final Aggregator chargeAggregator = new Aggregator();
        final ResultStore results = new ResultStore(position);

        final String progressStatus = "Finished processing %d of %d steps so far.\n";

        for (int i = 0; i < count; i++) {
            final Particle initial = new Photon(energy, 0.0, null, results);
            initial.propagate();

            totalAggregator.addStep(results.getTotalNumParticles());
            neutralAggregator.addStep(results.getNumNeutralParticles());
            chargeAggregator.addStep(results.getNumChargedParticles());

            //Reset the result counters to zero
            results.resetCounts();

            if ((i + 1) % printFrequency == 0) {
                System.out.format(progressStatus, i + 1, count);
            }
        }

        System.out.format("Average numbers of particles sampled at %.2f radiation lengths:\n", position);
        printOutputLine("Charged:", chargeAggregator.getMean(), chargeAggregator.getStdError());
        printOutputLine("Neutral:", neutralAggregator.getMean(), neutralAggregator.getStdError());
        printOutputLine("Total:", totalAggregator.getMean(), totalAggregator.getStdError());
    }

    /**
     * Parses the cli commands.
     *
     * @param args Input parameters
     */
    public static void cliParse(String... args) throws ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("cosmic-sim")
                .defaultHelp(true)
                .description("A quick and dirty simulation of a cosmic ray shower.");

        parser.addArgument("energy")
                .metavar("ENERGY")
                .type(Double.class)
                .help("The initial energy of the incoming photon in MeV");

        parser.addArgument("position")
                .metavar("POSITION")
                .type(Double.class)
                .help("The position in radiation lengths from the top of the atmosphere at which to sample the shower");

        parser.addArgument("count")
                .metavar("COUNT")
                .type(Integer.class)
                .help("The number of times to run the shower simulation");

        parser.addArgument("-s", "--seed")
                .metavar("SEED")
                .type(Long.class)
                .setDefault(System.currentTimeMillis())
                .help("This option sets a specific seed for the simulation");

        parser.addArgument("-p", "--print")
                .dest("print_frequency")
                .metavar("PRINT")
                .type(Integer.class)
                .setDefault(printFrequency)
                .help("This option sets the frequency at which progress is reported");

        Namespace ns = parser.parseArgs(args);

        RandomNumberHelper.setSeed(ns.getLong("seed"));
        printFrequency = ns.get("print_frequency");
        runSimulations(ns.getInt("count"), ns.getDouble("energy"), ns.getDouble("position"));
    }

    public static void main(String[] args) {
        try {
            cliParse(args);
        } catch (ArgumentParserException e) {
            e.getParser().handleError(e);
            System.exit(1);
        }
    }
}
