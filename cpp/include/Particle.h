#ifndef PARTICLE_H
#define PARTICLE_H

// forward declarations
class ResultStore;

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
  Particle(double energy, double position, Particle* parent, ResultStore *results);
  virtual ~Particle();

  double GetEnergy() const;
  void SetEnergy(double energy);
  double GetPosition() const;
  void SetPosition(double position);
  void Propagate();

 protected:

  virtual double GetRadLength() const = 0;
  virtual double GetTerminalDist() const = 0;
  virtual DecayType TerminalDecay() const = 0;
  virtual DecayType Decay() = 0;
  virtual void CountResult() = 0;

  static const double END_ENERGY = 0.;
  static const double END_POSITION = 28.;

  Particle* m_parent;
  Particle* m_child_1;
  Particle* m_child_2;
  ResultStore* m_results;

 private :

  void CheckPositionResult(double new_position);

  double m_energy;
  double m_position;
  DecayType m_decay;
};

#endif
