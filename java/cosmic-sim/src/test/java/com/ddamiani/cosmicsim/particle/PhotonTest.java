package com.ddamiani.cosmicsim.particle;

import com.ddamiani.cosmicsim.ResultStore;
import com.ddamiani.cosmicsim.particle.Particle.DecayType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Photon class.
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class PhotonTest {
    private static final double DELTA = 1e-15;
    private static final double DEFAULT_ENERGY = 30000.0;
    private static final double DEFAULT_POSITION = 0.0;
    private Photon mPhoton;
    private ResultStore mResult;

    @Before
    public void init() {
        mResult = new ResultStore();
        mPhoton = new Photon(DEFAULT_ENERGY, DEFAULT_POSITION, null, mResult);
    }

    @Test
    public void testGetPhotonDecayType() {
        assertEquals("Check photon decay type", DecayType.UNDECAYED, mPhoton.getDecayType());
    }

    @Test
    public void testGetAndSetPhotonEnergy() {
        final double altEnergy = 45000.0;

        assertEquals("Test initial energy", DEFAULT_ENERGY, mPhoton.getEnergy(), DELTA);
        mPhoton.setEnergy(altEnergy);
        assertEquals("Test alternative energy", altEnergy, mPhoton.getEnergy(), DELTA);
    }

    @Test
    public void testGetAndSetPhotonPosition() {
        final double altPosition = 10.0;

        assertEquals("Test initial position", DEFAULT_POSITION, mPhoton.getPosition(), DELTA);
        mPhoton.setPosition(altPosition);
        assertEquals("Test alternative position", altPosition, mPhoton.getPosition(), DELTA);
    }

    @Test
    public void testGetPhotonTerminalDist() {
        assertEquals("Test that the terminal distance is correct for a photon", Photon.PHOTON_TERM_DECAY_LENGTH,
                mPhoton.getTerminalDist(), DELTA);
    }

    @Test
    public void testGetPhotonRadiationLength() {
        assertEquals("Test that the radiation length is correct for a photon", Photon.PHOTON_RAD_LENGTH,
                mPhoton.getRadLength(), DELTA);
    }

    @Test
    public void testPhotonTerminalDecay() {
        assertEquals("Test that a " + DEFAULT_ENERGY + " photon won't terminal decay", DecayType.UNDECAYED,
                mPhoton.terminalDecay());

        mPhoton.setEnergy(Photon.PHOTON_TERM_DECAY_ENERGY);
        assertEquals("Test that a photon at the terminal energy won't terminal decay", DecayType.UNDECAYED,
                mPhoton.terminalDecay());

        mPhoton.setEnergy(Photon.PHOTON_TERM_DECAY_ENERGY - 0.1);
        assertEquals("Test that a photon below terminal energy will terminal decay", DecayType.COMPTON,
                mPhoton.terminalDecay());
    }

    @Test
    public void testPhotonDecay() {
        assertEquals("Test photon decay type", DecayType.PAIR, mPhoton.decay());

        assertTrue("First child particle is an electron", mPhoton.mChildOne instanceof Electron);
        assertTrue("First child particle is an electron", mPhoton.mChildTwo instanceof Electron);

        assertEquals("Test energy conservation :)", mPhoton.getEnergy(),
                mPhoton.mChildOne.getEnergy() + mPhoton.mChildTwo.getEnergy(), DELTA);
    }

    @Test
    public void testPhotonCountResult() {
        assertEquals("Test that there are zero neutral particles", 0, mResult.getNumNeutralParticles());
        mPhoton.countResult();
        assertEquals("Test that there is one neutral particle", 1, mResult.getNumNeutralParticles());
    }

    @Test
    public void testPhotonCheckPositionResult() {
        assertEquals("Test that there are zero neutral particles", 0, mResult.getNumNeutralParticles());
        mPhoton.checkPositionResult(ResultStore.DEFAULT_SAMPLE_POINT - 0.1);
        assertEquals("Tet that the counter wasn't incremented", 0, mResult.getNumNeutralParticles());
        mPhoton.checkPositionResult(ResultStore.DEFAULT_SAMPLE_POINT);
        assertEquals("Tet that the counter was incremented", 1, mResult.getNumNeutralParticles());
    }

    @Test
    public void testPhotonPropagateNormalDecay() {
        mPhoton.propagate();
        assertEquals("Photon should have pair produced", DecayType.PAIR, mPhoton.getDecayType());
        assertEquals("Photon should have no energy", Photon.END_ENERGY, mPhoton.getEnergy(), DELTA);
    }

    @Test
    public void testPhotonPropagateTerminalDecay() {
        mPhoton.setEnergy(Photon.PHOTON_TERM_DECAY_ENERGY - 0.1);
        mPhoton.propagate();
        assertEquals("Photon should have lost its energy to ionization", DecayType.COMPTON, mPhoton.getDecayType());
        assertEquals("Photon should have no energy", Photon.END_ENERGY, mPhoton.getEnergy(), DELTA);
    }

    @Test
    public void testPhotonPropagateRangeOut() {
        mPhoton.setPosition(Particle.END_POSITION + 0.1);
        final double originalEnergy = mPhoton.getEnergy();
        mPhoton.propagate();
        assertEquals("Photon should have reached the end", DecayType.END, mPhoton.getDecayType());
        assertEquals("Photon should have its original energy", originalEnergy, mPhoton.getEnergy(), DELTA);
    }
}
