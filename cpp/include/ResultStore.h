#ifndef RESULTSTORE_H
#define RESULTSTORE_H

class ResultStore {
 public:

  ResultStore();
  ResultStore(double sampling_point);
  virtual ~ResultStore();

  double GetSamplingPoint() const;
  int GetTotalNumParticles() const;
  int GetNumChargedParticles() const;
  int GetNumNeutralParticles() const;

  void IncrementNumChargedParticles();
  void IncrementNumNeutralParticles();

  void ResetCounts();

 private:

  static const double DEFAULT_SAMPLE_POINT = 28.0;

  const double m_sample_point;
  int m_num_charged;
  int m_num_neutral;
};

#endif
