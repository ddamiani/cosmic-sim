package com.ddamiani.cosmicsim;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the ResultStore class.
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class ResultStoreTest {
    private static final double DELTA = 1e-15;
    private ResultStore mDefaultResult;

    @Before
    public void init() {
        mDefaultResult = new ResultStore();
    }

    @Test
    public void testDefaultSamplePoint() {
        assertEquals(ResultStore.DEFAULT_SAMPLE_POINT, mDefaultResult.getSamplingPoint(), DELTA);
    }

    @Test
    public void testNonDefaultSamplePoint() {
        double samplePoint = 12.0;
        ResultStore nonDefault = new ResultStore(samplePoint);
        assertEquals(samplePoint, nonDefault.getSamplingPoint(), DELTA);
    }

    @Test
    public void testCounterReset() {
        final int numCharged = 5;
        final int numNeutral = 10;

        for (int i = 0; i < numCharged; i++) {
            mDefaultResult.incrementNumChargedParticles();
        }

        for (int i = 0; i < numNeutral; i++) {
            mDefaultResult.incrementNumNeutralParticles();
        }

        assertEquals("Check that the charged particle count is set", numCharged, mDefaultResult.getNumChargedParticles());
        assertEquals("Check that the neutral particle count is set", numNeutral, mDefaultResult.getNumNeutralParticles());

        mDefaultResult.resetCounts();

        assertEquals("Check that the charged particle count is reset", 0, mDefaultResult.getNumChargedParticles());
        assertEquals("Check that the neutral particle count is reset", 0, mDefaultResult.getNumNeutralParticles());
    }

    @Test
    public void testInitialCounerValues() {
        assertEquals("Test the total counter", 0, mDefaultResult.getTotalNumParticles());
        assertEquals("Test the neutral counter", 0, mDefaultResult.getNumChargedParticles());
        assertEquals("Test the charged counter", 0, mDefaultResult.getNumNeutralParticles());
    }

    @Test
    public void testIncrementNeutralCounter() {
        assertEquals("Test the total counter initial value", 0, mDefaultResult.getTotalNumParticles());
        assertEquals("Test the neutral counter initial value", 0, mDefaultResult.getNumChargedParticles());
        assertEquals("Test the charged counter initial value", 0, mDefaultResult.getNumNeutralParticles());

        mDefaultResult.incrementNumNeutralParticles();

        assertEquals("Test the neutral counter", 1, mDefaultResult.getNumNeutralParticles());

        mDefaultResult.incrementNumNeutralParticles();
        mDefaultResult.incrementNumNeutralParticles();

        assertEquals("Test the total counter final value", 3, mDefaultResult.getTotalNumParticles());
        assertEquals("Test the neutral counter final value", 0, mDefaultResult.getNumChargedParticles());
        assertEquals("Test the charged counter final value", 3, mDefaultResult.getNumNeutralParticles());
    }

    @Test
    public void testIncrementChargedCounter() {
        assertEquals("Test the total counter initial value", 0, mDefaultResult.getTotalNumParticles());
        assertEquals("Test the neutral counter initial value", 0, mDefaultResult.getNumChargedParticles());
        assertEquals("Test the charged counter initial value", 0, mDefaultResult.getNumNeutralParticles());

        mDefaultResult.incrementNumChargedParticles();

        assertEquals("Test the charged particle counter", 1, mDefaultResult.getNumChargedParticles());

        mDefaultResult.incrementNumChargedParticles();
        mDefaultResult.incrementNumChargedParticles();

        assertEquals("Test the total counter final value", 3, mDefaultResult.getTotalNumParticles());
        assertEquals("Test the neutral counter final value", 3, mDefaultResult.getNumChargedParticles());
        assertEquals("Test the charged counter final value", 0, mDefaultResult.getNumNeutralParticles());
    }

    @Test
    public void testTotalCounter() {
        assertEquals("Test the total counter initial value", 0, mDefaultResult.getTotalNumParticles());
        mDefaultResult.incrementNumNeutralParticles();
        mDefaultResult.incrementNumNeutralParticles();
        mDefaultResult.incrementNumChargedParticles();
        mDefaultResult.incrementNumChargedParticles();
        mDefaultResult.incrementNumChargedParticles();
        assertEquals("Test the total counter final value", 5, mDefaultResult.getTotalNumParticles());
    }
}
