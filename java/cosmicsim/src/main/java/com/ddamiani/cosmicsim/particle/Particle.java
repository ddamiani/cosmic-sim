package com.ddamiani.cosmicsim.particle;

import com.ddamiani.cosmicsim.RandomNumberHelper;
import com.ddamiani.cosmicsim.ResultStore;

/**
 * Base abstract class representing a particle. Electrons and photons are the
 * available implementations of this class.
 */
public abstract class Particle {

    public enum DecayType {
        UNDECAYED,
        BREM,
        IONIZATION,
        PAIR,
        COMPTON,
        END
    }

    protected static final double END_ENERGY = 0.;
    protected static final double END_POSITION = 28.;

    protected double mEnergy;
    protected double mPosition;
    protected double mRadiationLength;
    protected double mTerminalDistance;
    protected DecayType mDecay;
    protected Particle mParent;
    protected Particle mChildOne;
    protected Particle mChildTwo;
    protected ResultStore mResults;

    protected Particle(double energy, double position, double radiationLength, double terminalDistance,
            Particle parent, ResultStore results) {
        mEnergy = energy;
        mPosition = position;
        mRadiationLength = radiationLength;
        mTerminalDistance = terminalDistance;
        mDecay = DecayType.UNDECAYED;
        mParent = parent;
        mResults = results;
        mChildOne = null;
        mChildTwo = null;
    }

    protected abstract DecayType terminalDecay();

    protected abstract DecayType decay();

    protected abstract void countResult();


    public final double getEnergy() {
        return mEnergy;
    }

    public final void setEnergy(double energy) {
        mEnergy = energy;
    }

    public final double getPosition() {
        return mPosition;
    }

    public final double getTerminalDist() {
        return mTerminalDistance;
    }

    public final double getRadLength() {
        return mRadiationLength;
    }

    public final void setPosition(double position) {
        mPosition = position;
    }

    public final void propagate() {
        mDecay = terminalDecay();

        if (mDecay != DecayType.UNDECAYED) {
            double final_position = getTerminalDist() + mPosition;
            if (final_position < END_POSITION) {
                checkPositionResult(final_position);
                mPosition = final_position;
                mEnergy = END_ENERGY;
            } else {
                mPosition = END_POSITION;
                mDecay = DecayType.END;
            }

            return;
        }

        double new_position = RandomNumberHelper.meanFree(mRadiationLength) + mPosition;

        if (new_position < END_POSITION) {
            checkPositionResult(new_position);

            // Decay the particle and create the children
            mPosition = new_position;
            mDecay = decay();
            mEnergy = END_ENERGY;
            // Propagate the first child
            mChildOne.propagate();
            mChildOne = null;
            // Propagate the second child
            mChildTwo.propagate();
            mChildTwo = null;
        } else {
            mPosition = END_POSITION;
            mDecay = DecayType.END;
        }
    }

    private void checkPositionResult(double newPosition) {
        // If there is a results object see if we should write to it
        if (mResults != null) {
            double samplingPoint = mResults.getSamplingPoint();
            if (mPosition < samplingPoint && newPosition >= samplingPoint) {
                countResult();
            }
        }
    }
}
