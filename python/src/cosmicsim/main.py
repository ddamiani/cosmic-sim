#!/usr/bin/python3

# normal imports
from argparse import ArgumentParser
from cosmicsim import __version__


def get_args():
    """
    Parses the command line parameters using argparse
    """

    parser = ArgumentParser(
        description='A quick and dirty simulation of a cosmic ray shower.'
    )

    parser.add_argument(
        'ph_energy',
        metavar='ENERGY',
        type=float,
        help='initial energy of the cosmic ray photon initiating the shower (in MeV).'
    )

    parser.add_argument(
        'sample_position',
        metavar='POSITION',
        type=float,
        help='position from the top of the atmosphere at which the shower '
             'is sampled (in radiation lengths).'
    )
    parser.add_argument(
        'sim_counts',
        metavar='COUNT',
        type=int,
        help='number of times to run the cosmic ray shower simulation.'
    )

    parser.add_argument(
        '-s',
        '--seed',
        metavar='SEED',
        type=int,
        help='fix the seed of the random.py number generator to the value '
             'specified (defaults to the timestamp at up).'
    )

    parser.add_argument(
        '-p',
        '--print',
        metavar='PRINT',
        type=int,
        default=1000,
        help='Sets the frequency at which progress is reported (default: %(default)d).'
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
        print('I ran')
    except KeyboardInterrupt:
        return 1


if __name__ == '__main__':
    main()
