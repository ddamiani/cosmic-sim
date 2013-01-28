#include "Particle.h"
#include "RandomMT.h"

#include <cstddef>

Particle::Particle() :
  m_energy(-1.),
  m_position(-1.),
  m_decay(UNDECAYED),
  m_parent(NULL),
  m_child_1(NULL),
  m_child_2(NULL)
{}

Particle::Particle(double energy, double position, Particle* parent) :
  m_energy(energy),
  m_position(position),
  m_decay(UNDECAYED),
  m_parent(parent),
  m_child_1(NULL),
  m_child_2(NULL)
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

double Particle::GetEnergy() {
  return m_energy;
}
void Particle::SetEnergy(double energy) {
  m_energy = energy;
}

double Particle::GetPosition() {
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
    m_position = new_position;
    m_energy = END_ENERGY;

    // Decay the particle and create the children
    m_decay = Decay();
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

Particle* Particle::GetParent() {
  return m_parent;
}

Particle* Particle::GetLeftChild() {
  return m_child_1;
}

Particle* Particle::GetRightChild() {
  return m_child_2;
}

void Particle::SetLeftChild(Particle* child) {
  m_child_1 = child;
}

void Particle::SetRightChild(Particle* child) {
  m_child_2 = child;
}
