package com.ddamiani.cosmicsim;

/**
 * An object for storing the results of a simulation run.
 */
public final class ResultStore {
    public static final double DEFAULT_SAMPLE_POINT = 28.0;
    private final double mSamplePoint;
    private int mNumCharged;
    private int mNumNeutral;

    protected ResultStore() {
        this(DEFAULT_SAMPLE_POINT);
    }

    protected ResultStore(double samplingPoint) {
        mSamplePoint = samplingPoint;
        mNumCharged = 0;
        mNumNeutral = 0;
    }

    protected double getSamplingPoint() {
        return mSamplePoint;
    }

    protected int getTotalNumParticles() {
        return mNumCharged + mNumNeutral;
    }

    protected int getNumChargedParticles() {
        return mNumCharged;
    }

    protected int getNumNeutralParticles() {
        return mNumNeutral;
    }

    protected void incrementNumChargedParticles() {
        mNumCharged += 1;
    }

    protected void incrementNumNeutralParticles() {
        mNumNeutral += 1;
    }
}
