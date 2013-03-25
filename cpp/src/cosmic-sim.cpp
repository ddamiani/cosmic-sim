#include <iostream>
#include <iomanip>
#include <cstddef>
#include <cmath>

#include "RandomMT.h"
#include "ResultStore.h"
#include "Photon.h"
#include "Aggregator.h"

using namespace std;

inline void print_usage() {
  cout << "Usage ./cosmic_sim.out <initial_energy> <sampling_position> <number of runs> [seed]" << endl;
}

inline void print_line(string name, double value, double rms) {
  cout << left << setw(12) << name << value << " +/- " << rms << endl;
}

inline bool is_help_param(string param) {
  return param == "-h" || param == "--help";
}

int main (int argc, char *argv[]) {

  if(argc > 1 && is_help_param(argv[1])) {
    print_usage();

    return 0;
  }

  if(argc < 4 || argc > 5) {
    cout << "Wrong number of input parameters!" << endl;
    print_usage();

    return 1;
  }

  int print_freq = 1000;

  int counts = atoi(argv[3]);
  double position = atof(argv[2]);
  double energy = atof(argv[1]);

  // set the seed value to the choosen one
  if(argc == 5) {
    RandomMT::Seed(atoi(argv[4]));
  }

  cout << "Running the simulation " << counts
       << " times with an initial photon energy of "
       << energy / 1000. << " GeV." << endl;
  cout << "The shower will be sampled at " << position
       << " radiation lengths from the top of the atmosphere." << endl;

  Particle* initial = NULL;
  ResultStore* results = new ResultStore(position);
  Aggregator* totAgg = new Aggregator();
  Aggregator* neutralAgg = new Aggregator();
  Aggregator* chargedAgg = new Aggregator();

  for(int i=0; i<counts;++i) {
    initial = new Photon(energy, 0.0, NULL, results);
    initial->Propagate();

    // delete the initial particle
    delete initial; initial = NULL;

    totAgg->AggStep(results->GetTotalNumParticles());
    neutralAgg->AggStep(results->GetNumNeutralParticles());
    chargedAgg->AggStep(results->GetNumChargedParticles());

    results->ResetCounts();

    if((i+1) % print_freq == 0) {
      cout << "Finished processing " << i+1 << " of " << counts
           << " steps so far." << endl;
    }
  }

  // delete the result holding object
  delete results; results = NULL;

  cout << "Average numbers of particles sampled at " << position
       << " radiation lengths:" << endl;
  print_line(" Charged:", chargedAgg->GetMean(), chargedAgg->GetStdError());
  print_line(" Neutral:", neutralAgg->GetMean(), neutralAgg->GetStdError());
  print_line(" Total:", totAgg->GetMean(), totAgg->GetStdError());

  // delete the aggregators
  delete totAgg; delete neutralAgg; delete chargedAgg;

  return 0;
}
