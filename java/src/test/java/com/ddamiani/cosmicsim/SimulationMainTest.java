package com.ddamiani.cosmicsim;

import com.ddamiani.cosmicsim.SimulationMain.Aggregator;

import net.sourceforge.argparse4j.inf.ArgumentParserException;

import org.apache.commons.math3.stat.StatUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for the command line interface
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class SimulationMainTest {
    private static final double DELTA = 1e-15;

    @Test
    public void testHelp() {
        try {
            SimulationMain.cliParse("-h");
            fail("Help text should have been printed");
        } catch (ArgumentParserException e) {
            assertEquals("Help Screen", e.getMessage());
        }

        try {
            SimulationMain.cliParse("-h", "2.0", "1");
            fail("Help text should have been printed");
        } catch (ArgumentParserException e) {
            assertEquals("Help Screen", e.getMessage());
        }
    }

    @Test
    public void testRequiredParameters() throws ArgumentParserException {
        SimulationMain.cliParse("3.0", "2.0", "1");
        SimulationMain.cliParse("3.0", "2.0", "1", "-p", "1200");
        SimulationMain.cliParse("3.0", "2.0", "1", "-s", "2134");
        SimulationMain.cliParse("3.0", "2.0", "1", "-p", "1200", "-s", "2134");
        assertTrue(true);
    }

    @Test
    public void testExtraParam() {
        try {
            SimulationMain.cliParse("3.0", "2.0", "1", "2");
            fail("The parse should have failed because of an extra positional parameter.");
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testUnknownParam() {
        try {
            SimulationMain.cliParse("3.0", "2.0", "1", "-f", "2");
            fail("The parse should have failed because of an extra 'f' parameter.");
        } catch (ArgumentParserException e) {
            assertEquals("unrecognized arguments: '-f'", e.getMessage());
        }
    }

    @Test
    public void testMissingEnergyParam() {
        try {
            SimulationMain.cliParse("2.0", "1");
            fail("The parse should have failed because the energy param is missing.");
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMissingPositionParam() {
        try {
            SimulationMain.cliParse("3.0", "1");
            fail("The parse should have failed because the position param is missing.");
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMissingCountParam() {
        try {
            SimulationMain.cliParse("3.0", "2.0");
            fail("The parse should have failed because the count param is missing.");
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testWrongParameterTypes() throws ArgumentParserException {
        try {
            SimulationMain.cliParse("3.0", "2.0", "1.2");
            fail("c parameter should not have parsed as an int");
        } catch (ArgumentParserException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }

        try {
            SimulationMain.cliParse("3.s0", "2.0", "1");
            fail("e parameter should not have parsed as a double");
        } catch (ArgumentParserException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }

        try {
            SimulationMain.cliParse("3.0", "a2.0", "1");
            fail("p parameter should not have parsed as a double");
        } catch (ArgumentParserException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }

        try {
            SimulationMain.cliParse("3.0", "2.0", "1", "-s", "4.55");
            fail("s parameter should not have parsed as a long");
        } catch (ArgumentParserException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }

        try {
            SimulationMain.cliParse("3.0", "2.0", "1", "-p", "foo");
            fail("p parameter should not have parsed as a int");
        } catch (ArgumentParserException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }
    }

    @Test
    public void testAggregatorGetterValues() {
        final Aggregator agg = new Aggregator();
        assertEquals("Test counter", 0, agg.getCounts());
        assertEquals("Test mean", 0.0, agg.getMean(), DELTA);
        assertEquals("Test error", Double.NaN, agg.getStdError(), DELTA);

        agg.addStep(1.0);
        agg.addStep(1.0);

        assertEquals("Test counter", 2, agg.getCounts());
        assertEquals("Test mean", 1.0, agg.getMean(), DELTA);
        assertEquals("Test error", 0.0, agg.getStdError(), DELTA);
    }

    @Test
    public void testAggregationAlgorithm() {
        final double[] values = {1.0, 2.4, 4.1, 0.1, 2.3, 2.2, 1.8};
        final double expectedMean = StatUtils.mean(values);
        final double expectedError = StrictMath.sqrt(StatUtils.variance(values) / values.length);
        final Aggregator agg = new Aggregator();

        for (double value : values) {
            agg.addStep(value);
        }

        assertEquals("Test the number of steps", values.length, agg.getCounts());
        assertEquals("Test the mean", expectedMean, agg.getMean(), DELTA);
        assertEquals("Test the error", expectedError, agg.getStdError(), DELTA);
    }
}
