#include "Photon.h"

#include "Electron.h"
#include "RandomMT.h"
#include "ResultStore.h"

Photon::Photon(double energy, double position,
               Particle* parent, ResultStore *results) :
  Particle(energy, position, PHOTON_RAD_LENGTH,
           PHOTON_TERM_DECAY_LENGTH, parent, results)
{}

Photon::~Photon() {}

Particle::DecayType Photon::TerminalDecay() const {
  if(m_energy < PHOTON_TERM_DECAY_ENERGY) {
    return Particle::COMPTON;
  }

  return Particle::UNDECAYED;
}

Particle::DecayType Photon::Decay() {
  double pair_fraction = RandomMT::GenRealOpen();

  m_child_1 = new Electron(m_energy * (1 - pair_fraction),
                           m_position,
                           this,
                           m_results);

  m_child_2 = new Electron(m_energy * pair_fraction,
                           m_position,
                           this,
                           m_results);

  return Particle::PAIR;
}

void Photon::CountResult() {
  if(m_results) {
    m_results->IncrementNumNeutralParticles();
  }
}
