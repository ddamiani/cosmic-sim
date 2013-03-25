package com.ddamiani.cosmicsim.particle;

import com.ddamiani.cosmicsim.RandomNumberHelper;
import com.ddamiani.cosmicsim.ResultStore;

/**
 * Class representing an electron - inherits from Particle.
 */
public final class Electron extends Particle {
    protected static final double ELECTRON_RAD_LENGTH = 0.28;
    protected static final double ELECTRON_TERM_DECAY_LENGTH = 1.0;
    protected static final double ELECTRON_TERM_DECAY_ENERGY = 100.0;

    public Electron(double energy, double position, Particle parent, ResultStore results) {
        super(energy, position, ELECTRON_RAD_LENGTH, ELECTRON_TERM_DECAY_LENGTH, parent, results);
    }

    protected final DecayType terminalDecay() {
        if (mEnergy < ELECTRON_TERM_DECAY_ENERGY) {
            return DecayType.IONIZATION;
        }

        return DecayType.UNDECAYED;
    }

    protected final DecayType decay() {
        double bremEnergy = RandomNumberHelper.brem();

        mChildOne = new Electron(mEnergy * (1 - bremEnergy),
                mPosition,
                this,
                mResults);

        mChildTwo = new Photon(mEnergy * bremEnergy,
                mPosition,
                this,
                mResults);

        return DecayType.BREM;
    }

    protected final void countResult() {
        if (mResults != null) {
            mResults.incrementNumChargedParticles();
        }
    }
}
