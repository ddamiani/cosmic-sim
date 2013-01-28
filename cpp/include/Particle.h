#ifndef PARTICLE_H
#define PARTICLE_H

class Particle {
 public:

  enum DecayType {
    UNDECAYED,
    BREM,
    IONIZATION,
    PAIR,
    COMPTON,
    END
  };

  Particle();
  Particle(double energy, double position, Particle* parent);
  virtual ~Particle();

  double GetEnergy();
  void SetEnergy(double energy);
  double GetPosition();
  void SetPosition(double position);
  void Propagate();

 protected:
  Particle* GetParent();
  Particle* GetLeftChild();
  Particle* GetRightChild();
  void SetLeftChild(Particle* child);
  void SetRightChild(Particle* child);

  virtual double GetRadLength() = 0;
  virtual double GetTerminalDist() = 0;
  virtual DecayType TerminalDecay() = 0;
  virtual DecayType Decay() = 0;

  static const double END_ENERGY = 0.;
  static const double END_POSITION = 28.;

 private :
  double m_energy;
  double m_position;
  DecayType m_decay;

  Particle* m_parent;
  Particle* m_child_1;
  Particle* m_child_2;
};

#endif
