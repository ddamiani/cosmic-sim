#!/usr/bin/python

# default to floating point division
from __future__ import division
from argparse import ArgumentParser


def get_args():
    parser = ArgumentParser(description='Command line parameters for the cosmic ray simulation')
    parser.add_argument('ph_energy', metavar='PHOTON_ENERGY', type=float,
                        help='the initial energy of the incoming cosmic ray (in MeV)')
    parser.add_argument('sample_position', metavar='PHOTON_ENERGY', type=float,
                        help='position from the top of the atmosphere in radiation lengths at which the shower is sampled')
    parser.add_argument('sim_number', metavar='NUMBER_OF_SHOWERS', type=int,
                        help='the number of times to run the cosmic ray shower simulation')
    parser.add_argument('-v', '--verbose', action="store_true", default=False,
                        help='always print full particle output for showers')
    parser.add_argument('-p', '--no-prune', action="store_true", default=False,
                        help='don\'t prune dead particles from the printout')
    
    return parser.parse_args()


def main():

    args = get_args()
    try:
        print 'I ran'
    except KeyboardInterrupt:
        pass
