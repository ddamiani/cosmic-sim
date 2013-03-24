package com.ddamiani.cosmicsim;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the RandomNumberHelper class.
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class RandomNumberHelperTest {
    private static final double DELTA = 1e-15;

    @Test
    public void testSetSeed() {
        RandomNumberHelper.setSeed(45);
        assertEquals("Test that pseudo random numbers work :)", 0.290066958745429, RandomNumberHelper.genRealOpen(), DELTA);
    }

    @Test
    public void testMeanFree() {
        // Non-deterministic unit tests for the win
        int limit = 10000;
        double mean = 5.1;
        double sum = 0.0;
        double range = 0.5;

        for (int i = 0; i < limit; i++) {
            sum += RandomNumberHelper.meanFree(mean);
        }

        assertEquals("Test the mean", mean, sum / (double) limit, range);
    }
}
