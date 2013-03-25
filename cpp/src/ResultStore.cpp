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
