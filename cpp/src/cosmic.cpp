#include <iostream>
#include <cstddef>
#include <sys/time.h>

#include "RandomMT.h"

using namespace std;

int main (int argc, char *argv[]) {
  timeval t_seed;
  gettimeofday(&t_seed,NULL);
  RandomMT::Seed(t_seed.tv_usec);

  cout << RandomMT::MeanFree(2.0) << endl;
  cout << RandomMT::MeanFree(2.0) << endl;
  cout << RandomMT::MeanFree(2.0) << endl;
  cout << RandomMT::MeanFree(2.0) << endl;

  return 0;
}
