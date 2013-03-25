#ifndef PHOTON_H
#define PHOTON_H

#include "Particle.h"

class Photon : public Particle {
 public:

  Photon(double energy, double position, Particle* parent, ResultStore* results);
  virtual ~Photon();

 protected:
   virtual Particle::DecayType TerminalDecay() const;
   virtual Particle::DecayType Decay();
   virtual void CountResult();

   static const double PHOTON_RAD_LENGTH =  9.0 / 7.0;
   static const double PHOTON_TERM_DECAY_LENGTH = 0.0;
   static const double PHOTON_TERM_DECAY_ENERGY = 10.0;
};

#endif
