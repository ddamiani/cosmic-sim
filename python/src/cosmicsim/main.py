#!/usr/bin/python3

# normal imports
import time
from argparse import ArgumentParser
from cosmicsim.utils import seed, Result, Aggregator
from cosmicsim.particle import Photon
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
        default=int(time.time() * 1000),
        help='fix the seed of the utils.py number generator to the value '
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
        print('Running the simulation %d times with an initial photon energy of %.0f GeV'
              % (args.sim_counts, args.ph_energy / 1000.))
        print('The shower will be sampled at %.2f radiation lengths from the top of the atmosphere.'
              % args.sample_position)

        # set the random number seed
        seed(args.seed)

        total_aggregator = Aggregator()
        neutral_aggregator = Aggregator()
        charge_aggregator = Aggregator()
        results = Result(args.sample_position)

        for i in range(args.sim_counts):
            initial = Photon(args.ph_energy, 0.0, None, results)
            initial.propogate()

            total_aggregator.add_step(results.num_total)
            neutral_aggregator.add_step(results.num_neutral)
            charge_aggregator.add_step(results.num_charged)

            #Reset the result counters to zero
            results.reset_counts()

            if (i + 1) % args.print == 0:
                print('Finished processing %d of %d steps so far.' % (i + 1, args.sim_counts))

        print('Average numbers of particles sampled at %.2f radiation lengths:' % args.sample_position)
        output_str = ' %-8s %12.3f \u00B1 %.3f'
        print(output_str % ("Charged:", charge_aggregator.mean, charge_aggregator.std_error))
        print(output_str % ("Neutral:", neutral_aggregator.mean, neutral_aggregator.std_error))
        print(output_str % ("Total:", total_aggregator.mean, total_aggregator.std_error))
    except KeyboardInterrupt:
        return 1


if __name__ == '__main__':
    main()
