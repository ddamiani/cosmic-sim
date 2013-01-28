#ifndef RANDOM_MT_H
#define RANDOM_MT_H

#include <boost/random/mersenne_twister.hpp>

class RandomMT {
 public:
  static void Seed(unsigned int seed);
  static double GenRealOpen();
  static double MeanFree(double lambda);
  static double BremFunc(double x);
  static double Brem();
 private:
  static boost::mt19937 s_gen;
};

#endif
