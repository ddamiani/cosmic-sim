#include "Particle.h"

#include "RandomMT.h"
#include "ResultStore.h"

#include <cstddef>

Particle::Particle() :
  m_energy(-1.),
  m_position(-1.),
  m_decay(UNDECAYED),
  m_parent(NULL),
  m_child_1(NULL),
  m_child_2(NULL),
  m_results(NULL)
{}

Particle::Particle(double energy, double position, Particle* parent, ResultStore* results) :
  m_energy(energy),
  m_position(position),
  m_decay(UNDECAYED),
  m_parent(parent),
  m_child_1(NULL),
  m_child_2(NULL),
  m_results(results)
{}

Particle::~Particle() {
  if(m_child_1) {
    delete m_child_1;
    m_child_1 = NULL;
  }

  if(m_child_2) {
    delete m_child_2;
    m_child_2 = NULL;
  }
}

double Particle::GetEnergy() const {
  return m_energy;
}
void Particle::SetEnergy(double energy) {
  m_energy = energy;
}

double Particle::GetPosition() const {
  return m_position;
}

void Particle::SetPosition(double position) {
  m_position = position;
}

void Particle::Propagate() {
  m_decay = TerminalDecay();

  if(m_decay != UNDECAYED) {
    double final_position = GetTerminalDist() + m_position;
    if(final_position < END_POSITION) {
      CheckPositionResult(final_position);
      m_position = final_position;
      m_energy = END_ENERGY;
    } else {
      m_position = END_POSITION;
      m_decay = END;
    }

    return ;
  }

  double new_position = RandomMT::MeanFree(GetRadLength()) + m_position;
  
  if(new_position < END_POSITION) {
    CheckPositionResult(new_position);

    // Decay the particle and create the children
    m_position = new_position;
    m_decay = Decay();
    m_energy = END_ENERGY;
    // Propogate the first child
    m_child_1->Propagate();
    delete m_child_1;
    m_child_1 = NULL;
    // Propogate the second child
    m_child_2->Propagate();
    delete m_child_2;
    m_child_2 = NULL;
  } else {
    m_position = END_POSITION;
    m_decay = END;
  }
}

void Particle::CheckPositionResult(double new_position) {
  // If there is a results object see if we should write to it
  if(m_results) {
    int sampling_point = m_results->GetSamplingPoint();
    if(m_position < sampling_point && new_position >= sampling_point) {
      CountResult();
    }
  }
}

