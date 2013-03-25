#include "Aggregator.h"

#include <cmath>

Aggregator::Aggregator() :
  m_step(0),
  m_mean(0.0),
  m_sum_squares(0.0)
{}

Aggregator::~Aggregator() {}

void Aggregator::AggStep(double step_value) {
  m_step++;

  double diff = step_value - m_mean;
  m_mean += diff / m_step;
  m_sum_squares += diff * (step_value - m_mean);
}

double Aggregator::GetStdError() const {
  return sqrt(m_sum_squares / (double) ((m_step - 1) * m_step));
}
