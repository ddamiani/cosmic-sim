#!/usr/bin/python

# default to floating point division
from __future__ import division

# normal imports
from argparse import ArgumentParser
from cosmicsim import __version__


def get_args():
    '''
    Parses the command line parameters using argparse
    '''

    parser = ArgumentParser(
        description='A quick and dirty simulation of a cosmic ray shower.'
    )

    parser.add_argument(
        'ph_energy',
        metavar='ENERGY',
        type=float,
        help='initial energy of the incoming cosmic ray photon (in MeV)'
    )

    parser.add_argument(
        'sample_position',
        metavar='POSITION',
        type=float,
        help='position from the top of the atmosphere at which the shower '
             'is sampled (in radiation lengths)'
    )
    parser.add_argument(
        'sim_counts',
        metavar='COUNT',
        type=int,
        help='number of times to run the cosmic ray shower simulation'
    )

    parser.add_argument(
        '-s',
        '--seed',
        metavar='SEED',
        type=int,
        help='fixes the seed used by the random number generator to the value '
             'specified (normally uses the time at start up)'
    )

    parser.add_argument(
        '-p',
        '--print-freq',
        metavar='PRINT_FREQUENCY',
        type=int,
        default=1000,
        help='sets the frequency at which progress is reported (default: 1000)'
    )

    parser.add_argument(
        '--version',
        action='version',
        version='%(prog)s ' + __version__
    )

    return parser.parse_args()


def main():

    args = get_args()
    try:
        print 'I ran'
    except KeyboardInterrupt:
        pass
