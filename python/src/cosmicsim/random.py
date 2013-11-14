"""
Helper module for handling the generation of pseudo random numbers.
"""


def brem_func(num):
    """
    Takes a real number in the range [0, 1) and converts it to an energy
    fraction used for a brem.
    """
    return 1 - num**0.2
