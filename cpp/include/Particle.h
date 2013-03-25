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

  virtual ~Particle();

  double GetEnergy() const;
  void SetEnergy(double energy);
  double GetPosition() const;
  void SetPosition(double position);
  DecayType GetDecayType() const;
  void Propagate();

 protected:

  Particle(double energy, double position, double rad_length, double term_dist,
           Particle* parent, ResultStore *results);

  virtual DecayType TerminalDecay() const = 0;
  virtual DecayType Decay() = 0;
  virtual void CountResult() = 0;

  static const double END_ENERGY = 0.;
  static const double END_POSITION = 28.;

  double m_energy;
  double m_position;

  Particle* m_parent;
  Particle* m_child_1;
  Particle* m_child_2;
  ResultStore* m_results;

 private :

  void CheckPositionResult(double new_position);

  double m_rad_length;
  double m_term_dist;
  DecayType m_decay;
};

#endif
