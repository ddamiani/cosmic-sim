#include "Photon.h"

#include "Electron.h"
#include "RandomMT.h"
#include "ResultStore.h"

Photon::Photon() :
  Particle()
{}

Photon::Photon(double energy, double position, Particle* parent, ResultStore *results) :
  Particle(energy, position, parent, results)
{}

Photon::~Photon() {}

double Photon::GetRadLength() const {
  return PHOTON_RAD_LENGTH;
}

double Photon::GetTerminalDist() const {
  return PHOTON_TERM_DECAY_LENGTH;
}

Particle::DecayType Photon::TerminalDecay() const {
  if(GetEnergy() < PHOTON_TERM_DECAY_ENERGY) {
    return Particle::COMPTON;
  }

  return Particle::UNDECAYED;
}

Particle::DecayType Photon::Decay() {
  double pair_fraction = RandomMT::GenRealOpen();

  m_child_1 = new Electron(GetEnergy() * (1 - pair_fraction),
                           GetPosition(),
                           this,
                           m_results);

  m_child_2 = new Electron(GetEnergy() * pair_fraction,
                           GetPosition(),
                           this,
                           m_results);

  return Particle::PAIR;
}

void Photon::CountResult() {
  if(m_results) {
    m_results->IncrementNumNeutralParticles();
  }
}
