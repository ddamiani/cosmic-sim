package com.ddamiani.cosmicsim.particle;

import com.ddamiani.cosmicsim.RandomNumberHelper;
import com.ddamiani.cosmicsim.ResultStore;

/**
 * Class representing a photon - inherits from Particle.
 */
public final class Photon extends Particle {
    protected static final double PHOTON_RAD_LENGTH = 9.0 / 7.0;
    protected static final double PHOTON_TERM_DECAY_LENGTH = 0.0;
    protected static final double PHOTON_TERM_DECAY_ENERGY = 10.0;

    public Photon(double energy, double position, Particle parent, ResultStore results) {
        super(energy, position, PHOTON_RAD_LENGTH, PHOTON_TERM_DECAY_LENGTH, parent, results);
    }

    protected final DecayType terminalDecay() {
        if (mEnergy < PHOTON_TERM_DECAY_ENERGY) {
            return DecayType.COMPTON;
        }

        return DecayType.UNDECAYED;
    }

    protected final DecayType decay() {
        final double pairFraction = RandomNumberHelper.genRealOpen();

        mChildOne = new Electron(mEnergy * (1 - pairFraction),
                mPosition,
                this,
                mResults);

        mChildTwo = new Electron(mEnergy * pairFraction,
                mPosition,
                this,
                mResults);

        return DecayType.PAIR;
    }

    protected final void countResult() {
        if (mResults != null) {
            mResults.incrementNumNeutralParticles();
        }
    }
}
