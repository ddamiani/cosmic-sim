package com.ddamiani.cosmicsim;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.pow;

/**
 * Helper Class for handling the generation of pseudo random numbers.
 * <p/>
 * It uses the math3 package from Apache Commons for the random number
 * generator (a MersenneTwister in this case).
 */
public final class RandomNumberHelper {
    private static final RandomGenerator RANDOM_GENERATOR = new MersenneTwister();

    private static double bremFunc(double x) {
        return 1 - pow(x, 0.2);
    }

    public static void setSeed(long seed) {
        RANDOM_GENERATOR.setSeed(seed);
    }

    public static double meanFree(double lambda) {
        return -lambda * log(1 - RANDOM_GENERATOR.nextDouble());
    }

    public static double brem() {
        double func_val = -1, u, c = 1, x;

        while (func_val < 0) {
            u = RANDOM_GENERATOR.nextDouble();
            x = RANDOM_GENERATOR.nextDouble();
            if (u * c < bremFunc(x)) {
                func_val = x;
            }
        }
        return func_val;
    }

    public static double genRealOpen() {
        return RANDOM_GENERATOR.nextDouble();
    }
}
