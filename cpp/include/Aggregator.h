#ifndef AGGREGATOR_H
#define AGGREGATOR_H

class Aggregator {
 public:

  Aggregator();
  virtual ~Aggregator();

  void AggStep(double step_value);
  int GetCounts() const;
  double GetMean() const;
  double GetStdError() const;

 private:

  int m_step;
  double m_mean;
  double m_sum_squares;

};

inline int Aggregator::GetCounts() const {
  return m_step;
}

inline double Aggregator::GetMean() const {
  return m_mean;
}

#endif
