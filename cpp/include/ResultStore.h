#ifndef RESULTSTORE_H
#define RESULTSTORE_H

class ResultStore {
 public:

  ResultStore();
  explicit ResultStore(double sampling_point);
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

inline double ResultStore::GetSamplingPoint() const {
  return m_sample_point;
}

inline int ResultStore::GetTotalNumParticles() const {
  return m_num_charged + m_num_neutral;
}

inline int ResultStore::GetNumChargedParticles() const {
  return m_num_charged;
}

inline int ResultStore::GetNumNeutralParticles() const {
  return m_num_neutral;
}

inline void ResultStore::IncrementNumChargedParticles() {
  m_num_charged++;
}

inline void ResultStore::IncrementNumNeutralParticles() {
  m_num_neutral++;
}

inline void ResultStore::ResetCounts() {
  m_num_charged = 0;
  m_num_neutral = 0;
}

#endif
