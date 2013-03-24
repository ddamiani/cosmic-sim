package com.ddamiani.cosmicsim;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for the command line interface
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class SimulationMainTest {

    @Test
    public void testHelp() throws ParseException {
        SimulationMain.cliParse("-h");
        SimulationMain.cliParse("-h", "-p", "2.0", "-c", "1");
        assertTrue(true);
    }

    @Test
    public void testRequiredParameters() throws ParseException {
        SimulationMain.cliParse("-e", "3.0", "-p", "2.0", "-c", "1");
        SimulationMain.cliParse("--energy", "3.0", "-p", "2.0", "-c", "1");
        SimulationMain.cliParse("-e", "3.0", "-p", "2.0", "-c", "1", "-s", "2134");
        assertTrue(true);
    }

    @Test
    public void testMissingEnergyParam() {
        try {
            SimulationMain.cliParse("-p", "2.0", "-c", "1");
            fail("The parse should have failed because the 'e' param is missing.");
        } catch (ParseException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "-p", "2.0", "-c", "1");
            fail("The parse should have failed because the 'e' param is missing its value.");
        } catch (ParseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMissingPositionParam() {
        try {
            SimulationMain.cliParse("-e", "3.0", "-c", "1");
            fail("The parse should have failed because the 'p' param is missing.");
        } catch (ParseException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "-c", "1");
            fail("The parse should have failed because the 'p' param is missing its value.");
        } catch (ParseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMissingCountParam() {
        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "2.0");
            fail("The parse should have failed because the 'c' param is missing.");
        } catch (ParseException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "2.0", "-c");
            fail("The parse should have failed because the 'c' param is missing its value.");
        } catch (ParseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testWrongParameterTypes() throws ParseException {
        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "2.0", "-c", "1.2");
            fail("c parameter should not have parsed as an int");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "3.s0", "-p", "2.0", "-c", "1");
            fail("e parameter should not have parsed as a double");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "a2.0", "-c", "1");
            fail("p parameter should not have parsed as a double");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }

        try {
            SimulationMain.cliParse("-e", "3.0", "-p", "2.0", "-c", "1", "-s", "4.55");
            fail("s parameter should not have parsed as a long");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }
}
