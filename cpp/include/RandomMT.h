#ifndef RANDOM_MT_H
#define RANDOM_MT_H

#include <boost/random/mersenne_twister.hpp>
#include <boost/random/uniform_01.hpp>

class RandomMT {
 public:
  static void Seed(unsigned int seed);
  static double GenRealOpen();
  static double MeanFree(double lambda);
  static double BremFunc(double x);
  static double Brem();
 private:
  static unsigned int GetCurrentTime();
  static void Init(unsigned int seed);
  static void Free();
  static boost::mt19937* s_gen;
  static boost::uniform_01<boost::mt19937>* s_dist;
  static bool s_initialized;
};

#endif
