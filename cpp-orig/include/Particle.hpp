#ifndef Particle_hpp
#define Particle_hpp

#include <iostream>
#include <sstream>
#include <sys/time.h>
#include <cstdlib>
#include <cmath>
#include <vector>
#include "MersenneTwister.hpp"

extern MersenneTwister rnd;

class Particle {
 public :
  Particle(); //null constructor
  Particle(int par_type, double par_eng,
	   double par_pos, double par_samp1,
	   int par_parent_id); //constructor
  ~Particle();
  
  int GetType();
  double GetEng();
  void SetEng(double new_eng);
  double GetPos();
  void SetPos(double new_pos);
  int GetParent();
  double GetSamp1E();
  int Prop(); //0 = brem, 1 = ion, 2 = pair, 3 = compton, 4 = end, 5 = stopped
 private :
  int type;    // 0 = photon, 1 = electron
  int parent_id; //particle id number of parent, -1 = seed particle
  double eng;  //units of MeV
  double pos;  //units of rad lengths
  double samp1; //sample point 1 - units of rad lengths
  double samp1_e; //energy at sample point 1
  bool samp1_pass; //flags true if the particle hasn't been sampled and was created before sample point
  static const double ENDP = 28;  //the surface

  double MeanFree(double lambda);
  double BremFunc(double x);     //brem distgives value from brem dist
  double Brem();
};// end class Particle

#endif
