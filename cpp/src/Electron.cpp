#include "Electron.h"

#include "Photon.h"
#include "RandomMT.h"
#include "ResultStore.h"

Electron::Electron() :
  Particle()
{}

Electron::Electron(double energy, double position, Particle* parent, ResultStore *results) :
  Particle(energy, position, parent, results)
{}

Electron::~Electron() {}

double Electron::GetRadLength() const {
  return ELECTRON_RAD_LENGTH;
}

double Electron::GetTerminalDist() const {
  return ELECTRON_TERM_DECAY_LENGTH;
}

Particle::DecayType Electron::TerminalDecay() const {
  if(GetEnergy() < ELECTRON_TERM_DECAY_ENERGY) {
    return Particle::IONIZATION;
  }

  return Particle::UNDECAYED;
}

Particle::DecayType Electron::Decay() {
  double brem_energy = RandomMT::Brem();

  m_child_1 = new Electron(GetEnergy() * (1 - brem_energy),
                           GetPosition(),
                           this,
                           m_results);

  m_child_2 = new Photon(GetEnergy() * brem_energy,
                         GetPosition(),
                         this,
                         m_results);

  return Particle::BREM;
}

void Electron::CountResult() {
  if(m_results) {
    m_results->IncrementNumChargedParticles();
  }
}
