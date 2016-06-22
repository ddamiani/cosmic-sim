#ifndef ELECTRON_H
#define ELECTRON_H

#include "Particle.h"

class Electron : public Particle {
 public:

  Electron(double energy, double position, Particle* parent, ResultStore* results);
  virtual ~Electron();

 protected:
   virtual Particle::DecayType TerminalDecay() const;
   virtual Particle::DecayType Decay();
   virtual void CountResult();

   static const double ELECTRON_RAD_LENGTH;
   static const double ELECTRON_TERM_DECAY_LENGTH;
   static const double ELECTRON_TERM_DECAY_ENERGY;
};

#endif
