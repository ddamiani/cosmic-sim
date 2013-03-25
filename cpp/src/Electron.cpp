#include "Electron.h"

#include "Photon.h"
#include "RandomMT.h"
#include "ResultStore.h"

Electron::Electron(double energy, double position,
                   Particle* parent, ResultStore *results) :
  Particle(energy, position, ELECTRON_RAD_LENGTH,
           ELECTRON_TERM_DECAY_LENGTH, parent, results)
{}

Electron::~Electron() {}

Particle::DecayType Electron::TerminalDecay() const {
  if(m_energy < ELECTRON_TERM_DECAY_ENERGY) {
    return Particle::IONIZATION;
  }

  return Particle::UNDECAYED;
}

Particle::DecayType Electron::Decay() {
  double brem_energy = RandomMT::Brem();

  m_child_1 = new Electron(m_energy * (1 - brem_energy),
                           m_position,
                           this,
                           m_results);

  m_child_2 = new Photon(m_energy * brem_energy,
                         m_position,
                         this,
                         m_results);

  return Particle::BREM;
}

void Electron::CountResult() {
  if(m_results) {
    m_results->IncrementNumChargedParticles();
  }
}
