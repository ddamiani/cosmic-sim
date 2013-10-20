# Defaults to g++ for the c++ compiler if CXX not set
CXX?=gcc

SRC_DIR = src
INCLUDES = include
TARGET = target
CLEAN = clean

SRC_MAIN = $(SRC_DIR)/cosmic-sim.cpp
SRC = $(wildcard $(SRC_DIR)/*.cpp)
OBJ = $(SRC:$(SRC_DIR)/%.cpp=$(TARGET)/%.o)
DEPS = $(OBJ:.o=.d)
EXE = $(SRC_MAIN:$(SRC_DIR)/%.cpp=$(TARGET)/%)

CXXFLAGS = -I$(INCLUDES)
ifneq ($(CXX),g++)
	CXXFLAGS += -Wno-gnu
endif

all: $(TARGET) $(OBJ) $(EXE)

%: %.o $(OBJ)
	$(CXX) $^ -o $@

$(TARGET)/%.o: $(SRC_DIR)/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@
	@$(CXX) $(CXXFLAGS) -MM -MT $@ $< -MF $(@:.o=.d)

$(TARGET):
	@mkdir -p $@

$(CLEAN):
	@rm -rf $(TARGET)

.PHONY: all $(CLEAN)

-include $(DEPS)
