#include "RandomMT.h"

#include <cmath>
#include <boost/random/uniform_01.hpp>

boost::mt19937 RandomMT::s_gen;

void RandomMT::Seed(unsigned int seed) {
  s_gen.seed(seed);
}

double RandomMT::MeanFree(double lambda) {
  return -lambda*log(1-GenRealOpen());
}

double RandomMT::BremFunc(double x) {
  return 1-pow(x,0.2);
}

double RandomMT::Brem() {
  double func_val=-1,u=0,c=1,x=0;
  
  while(func_val<0) {
    u=GenRealOpen();
    x=GenRealOpen();
    if(u*c<BremFunc(x)) {
      func_val=x;
    }
  }
  return func_val;
}

double RandomMT::GenRealOpen() {
  boost::uniform_01<boost::mt19937&> dist(s_gen);
  return dist();
}
