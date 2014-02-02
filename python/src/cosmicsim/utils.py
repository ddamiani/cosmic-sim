"""
Helper module for handling the generation of pseudo random numbers.
"""
import math
import random


def seed(seed_val):
    """
    Stupid function wrapper for setting the seed for the builtin rng
    """
    random.seed(seed_val)


def mean_free(lambd):
    """
    Gives a random distance traveled based on a distribution with a mean free path of lambd
    """
    return - lambd * math.log(1 - random.random())


def brem():
    """
    Takes a real number in the range [0, 1) and converts it to an energy
    fraction used for a brem.
    """
    while True:
        u = random.random()
        x = random.random()
        if u < 1 - x ** 0.2:
            return x


def pair():
    """
    Fraction used for pair production
    """
    return random.random()


class Result(object):
    """
    Class for holding the simulation results
    """

    def __init__(self, sample_point=28.0):
        self.__sample_point = sample_point
        self.__num_charged = 0
        self.__num_neutral = 0

    @property
    def num_neutral(self):
        return self.__num_neutral

    @property
    def num_charged(self):
        return self.__num_charged

    @property
    def num_total(self):
        return self.__num_charged + self.__num_neutral

    @property
    def sample_point(self):
        return self.__sample_point

    def num_charged_inc(self):
        self.__num_charged += 1

    def num_neutral_inc(self):
        self.__num_neutral += 1

    def reset_counts(self):
        self.__num_charged = 0
        self.__num_neutral = 0


class Aggregator(object):
    """
    Simple class for doing a running aggregation the results of multiple simulation runs
    """

    def __init__(self):
        self.__step = 0
        self.__mean = 0.0
        self.__sum_square = 0.0

    def add_step(self, step_value):
        self.__step += 1
        diff = step_value - self.__mean
        self.__mean += diff / self.__step
        self.__sum_square += diff * (step_value - self.__mean)

    @property
    def count(self):
        return self.__step

    @property
    def mean(self):
        return self.__mean

    @property
    def std_error(self):
        return math.sqrt(self.__sum_square / ((self.__step - 1) * self.__step))