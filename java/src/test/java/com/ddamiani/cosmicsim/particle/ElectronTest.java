package com.ddamiani.cosmicsim.particle;

import com.ddamiani.cosmicsim.ResultStore;
import com.ddamiani.cosmicsim.particle.Particle.DecayType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Electron class.
 * <p/>
 * All java classes must have many unit tests so they can be much with enterpiseness.
 */
public class ElectronTest {
    private static final double DELTA = 1e-15;
    private static final double DEFAULT_ENERGY = 30000.0;
    private static final double DEFAULT_POSITION = 0.0;
    private Electron mElectron;
    private ResultStore mResult;

    @Before
    public void init() {
        mResult = new ResultStore();
        mElectron = new Electron(DEFAULT_ENERGY, DEFAULT_POSITION, null, mResult);
    }

    @Test
    public void testGetElectronDecayType() {
        assertEquals("Check electron decay type", DecayType.UNDECAYED, mElectron.getDecayType());
    }

    @Test
    public void testGetAndSetElectronEnergy() {
        final double altEnergy = 45000.0;

        assertEquals("Test initial energy", DEFAULT_ENERGY, mElectron.getEnergy(), DELTA);
        mElectron.setEnergy(altEnergy);
        assertEquals("Test alternative energy", altEnergy, mElectron.getEnergy(), DELTA);
    }

    @Test
    public void testGetAndSetElectronPosition() {
        final double altPosition = 10.0;

        assertEquals("Test initial position", DEFAULT_POSITION, mElectron.getPosition(), DELTA);
        mElectron.setPosition(altPosition);
        assertEquals("Test alternative position", altPosition, mElectron.getPosition(), DELTA);
    }

    @Test
    public void testGetElectronTerminalDist() {
        assertEquals("Test that the terminal distance is correct for an electron", Electron.ELECTRON_TERM_DECAY_LENGTH,
                mElectron.getTerminalDist(), DELTA);
    }

    @Test
    public void testGetElectronRadiationLength() {
        assertEquals("Test that the radiation length is correct for an electron", Electron.ELECTRON_RAD_LENGTH,
                mElectron.getRadLength(), DELTA);
    }

    @Test
    public void testElectronTerminalDecay() {
        assertEquals("Test that a " + DEFAULT_ENERGY + " electron won't terminal decay", DecayType.UNDECAYED,
                mElectron.terminalDecay());

        mElectron.setEnergy(Electron.ELECTRON_TERM_DECAY_ENERGY);
        assertEquals("Test that an electron at the terminal energy won't terminal decay", DecayType.UNDECAYED,
                mElectron.terminalDecay());

        mElectron.setEnergy(Electron.ELECTRON_TERM_DECAY_ENERGY - 0.1);
        assertEquals("Test that an electron below terminal energy will terminal decay", DecayType.IONIZATION,
                mElectron.terminalDecay());
    }

    @Test
    public void testElectronDecay() {
        assertEquals("Test electron decay type", DecayType.BREM, mElectron.decay());

        assertTrue("First child particle is an electron", mElectron.mChildOne instanceof Electron);
        assertTrue("First child particle is a photon", mElectron.mChildTwo instanceof Photon);

        assertEquals("Test energy conservation :)", mElectron.getEnergy(),
                mElectron.mChildOne.getEnergy() + mElectron.mChildTwo.getEnergy(), DELTA);
    }

    @Test
    public void testElectronCountResult() {
        assertEquals("Test that there are zero charged particles", 0, mResult.getNumChargedParticles());
        mElectron.countResult();
        assertEquals("Test that there is one charged particle", 1, mResult.getNumChargedParticles());
    }

    @Test
    public void testElectronCheckPositionResult() {
        assertEquals("Test that there are zero charged particles", 0, mResult.getNumChargedParticles());
        mElectron.checkPositionResult(ResultStore.DEFAULT_SAMPLE_POINT - 0.1);
        assertEquals("Tet that the counter wasn't incremented", 0, mResult.getNumChargedParticles());
        mElectron.checkPositionResult(ResultStore.DEFAULT_SAMPLE_POINT);
        assertEquals("Tet that the counter was incremented", 1, mResult.getNumChargedParticles());
    }

    @Test
    public void testElectronPropagateNormalDecay() {
        mElectron.propagate();
        assertEquals("Electron should have brem'ed", DecayType.BREM, mElectron.getDecayType());
        assertEquals("Electron should have no energy", Electron.END_ENERGY, mElectron.getEnergy(), DELTA);
    }

    @Test
    public void testElectronPropagateTerminalDecay() {
        mElectron.setEnergy(Electron.ELECTRON_TERM_DECAY_ENERGY - 0.1);
        mElectron.propagate();
        assertEquals("Electron should have lost its energy to ionization", DecayType.IONIZATION, mElectron.getDecayType());
        assertEquals("Electron should have no energy", Electron.END_ENERGY, mElectron.getEnergy(), DELTA);
    }

    @Test
    public void testElectronPropagateRangeOut() {
        mElectron.setPosition(Particle.END_POSITION + 0.1);
        final double originalEnergy = mElectron.getEnergy();
        mElectron.propagate();
        assertEquals("Electron should have reached the end", DecayType.END, mElectron.getDecayType());
        assertEquals("Electron should have its original energy", originalEnergy, mElectron.getEnergy(), DELTA);
    }
}
