package com.ddamiani.cosmicsim;

/**
 * An object for storing the results of a simulation run.
 */
public final class ResultStore {
    public static final double DEFAULT_SAMPLE_POINT = 28.0;
    private final double mSamplePoint;
    private int mNumCharged;
    private int mNumNeutral;

    public ResultStore() {
        this(DEFAULT_SAMPLE_POINT);
    }

    public ResultStore(double samplingPoint) {
        mSamplePoint = samplingPoint;
        mNumCharged = 0;
        mNumNeutral = 0;
    }

    public final void resetCounts() {
        mNumCharged = 0;
        mNumNeutral = 0;
    }

    public final double getSamplingPoint() {
        return mSamplePoint;
    }

    public final int getTotalNumParticles() {
        return mNumCharged + mNumNeutral;
    }

    public final int getNumChargedParticles() {
        return mNumCharged;
    }

    public final int getNumNeutralParticles() {
        return mNumNeutral;
    }

    public final void incrementNumChargedParticles() {
        mNumCharged += 1;
    }

    public final void incrementNumNeutralParticles() {
        mNumNeutral += 1;
    }
}
