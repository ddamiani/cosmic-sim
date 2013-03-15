#include <iostream>
#include <iomanip>
#include <cstddef>
#include <cmath>
#include <sys/time.h>

#include "RandomMT.h"
#include "ResultStore.h"
#include "Photon.h"

using namespace std;

void print_usage() {
  cout << "Usage ./cosmic_sim.out <initial_energy> <initial_position> <number of runs>" << endl;
}

void print_line(string name, double value, double rms) {
  cout << left << setw(12) << name << value << " +/- " << rms << endl;
}

bool is_help_param(string param) {
  return param == "-h" || param == "--help";
}

void agg_step(int step, double step_val, double & mean, double & sum2) {
  double diff = step_val - mean;
  mean += diff / step;
  sum2 += diff * (step_val - mean);
}

double get_std_err(int num, double sum2) {
  // sig / sqrt(n) and sig2 = sum2 / (n-1) 
  return sqrt(sum2 / ((num -1) * num) );
}

int main (int argc, char *argv[]) {

  if(argc > 0 && is_help_param(argv[1])) {
    print_usage();

    return 0;
  }

  if(argc != 4) {
    cout << "Wrong number of input parameters!" << endl;
    print_usage();

    return 1;
  }

  double mean = 0, mean_c = 0, mean_n =0;
  double sum2 = 0, sum2_c = 0, sum2_n =0;
  int counts = atoi(argv[3]);
  double position = atof(argv[2]);
  double energy = atof(argv[1]);

  timeval t_seed;
  gettimeofday(&t_seed,NULL);
  RandomMT::Seed(t_seed.tv_usec);

  for(int i=0; i<counts;++i) {
    ResultStore* results = new ResultStore(position);

    Particle* initial = new Photon(energy, 0.0, NULL, results);
    initial->Propagate();

    // delete the initial particle
    delete initial; initial = NULL;

    agg_step(i+1, results->GetTotalNumParticles(), mean, sum2);
    agg_step(i+1, results->GetNumChargedParticles(), mean_c, sum2_c);
    agg_step(i+1, results->GetNumNeutralParticles(), mean_n, sum2_n);

    // delete the result holding object
    delete results; results = NULL;
  }

  cout << "Average numbers of particles sampled at " << position << " raditaion lengths:" << endl;
  print_line(" Charged:", mean_c, get_std_err(counts, sum2_c));
  print_line(" Neutral:", mean_n, get_std_err(counts, sum2_n));
  print_line(" Total:", mean, get_std_err(counts, sum2));

  return 0;
}
