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

    public double getSamplingPoint() {
        return mSamplePoint;
    }

    public int getTotalNumParticles() {
        return mNumCharged + mNumNeutral;
    }

    public int getNumChargedParticles() {
        return mNumCharged;
    }

    public int getNumNeutralParticles() {
        return mNumNeutral;
    }

    public void incrementNumChargedParticles() {
        mNumCharged += 1;
    }

    public void incrementNumNeutralParticles() {
        mNumNeutral += 1;
    }
}
