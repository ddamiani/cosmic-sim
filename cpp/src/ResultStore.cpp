#include "ResultStore.h"

ResultStore::ResultStore() :
  m_sample_point(DEFAULT_SAMPLE_POINT),
  m_num_charged(0),
  m_num_neutral(0)
{}

ResultStore::ResultStore(double sampling_point) :
  m_sample_point(sampling_point),
  m_num_charged(0),
  m_num_neutral(0)
{}

ResultStore::~ResultStore() {}

double ResultStore::GetSamplingPoint() const {
  return m_sample_point;
}

int ResultStore::GetTotalNumParticles() const {
  return m_num_charged + m_num_neutral;
}

int ResultStore::GetNumChargedParticles() const {
  return m_num_charged;
}

int ResultStore::GetNumNeutralParticles() const {
  return m_num_neutral;
}

void ResultStore::IncrementNumChargedParticles() {
  m_num_charged++;
}

void ResultStore::IncrementNumNeutralParticles() {
  m_num_neutral++;
}

void ResultStore::ResetCounts() {
  m_num_charged = 0;
  m_num_neutral = 0;
}
