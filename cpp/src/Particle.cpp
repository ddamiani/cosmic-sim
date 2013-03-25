#include "Particle.h"

#include "RandomMT.h"
#include "ResultStore.h"

#include <cstddef>

Particle::Particle(double energy, double position, double rad_length, double term_dist,
                   Particle* parent, ResultStore* results) :
  m_energy(energy),
  m_position(position),
  m_rad_length(rad_length),
  m_term_dist(term_dist),
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

void Particle::Propagate() {
  m_decay = TerminalDecay();

  if(m_decay != UNDECAYED) {
    double final_position = m_term_dist + m_position;
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

  double new_position = RandomMT::MeanFree(m_rad_length) + m_position;
  
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

