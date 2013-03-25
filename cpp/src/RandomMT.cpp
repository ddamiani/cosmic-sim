#include "RandomMT.h"

#include <cstddef>
#include <cmath>
#include <sys/time.h>

boost::mt19937* RandomMT::s_gen = NULL;
boost::uniform_01<boost::mt19937>* RandomMT::s_dist = NULL;
bool  RandomMT::s_initialized = false;

void RandomMT::Seed(unsigned int seed) {
  /* If it has been initialized before destroy the existing
     and recreate with the new seed value */
  if(s_initialized) {
    Free();
  }

  Init(seed);
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
  if(!s_dist) Init(GetCurrentTime());

  return (*s_dist)();
}

unsigned int RandomMT::GetCurrentTime() {
  timeval t_seed;
  gettimeofday(&t_seed,NULL);
  
  return t_seed.tv_usec;
}

void RandomMT::Init(unsigned int seed) {
  if(s_initialized) return;

  s_gen = new boost::mt19937(seed);
  s_dist = new boost::uniform_01<boost::mt19937>(*s_gen);
  s_initialized = true;
}

void RandomMT::Free() {
  if(s_gen) {
    delete s_gen; s_gen = NULL;
  }

  if(s_dist) {
    delete s_dist; s_dist = NULL;
  }

  s_initialized = false;
}
