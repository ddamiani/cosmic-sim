SRC_DIR = src
INCLUDES = include
TARGET = target
CLEAN = clean
MKDIR = mkdir

SRC_MAIN = $(SRC_DIR)/cosmic-sim.cpp
SRC = $(wildcard $(SRC_DIR)/*.cpp)
OBJ = $(SRC:$(SRC_DIR)/%.cpp=$(TARGET)/%.o)
DEPS = $(SRC:$(SRC_DIR)/%.cpp=$(TARGET)/%.d)
EXE = $(SRC_MAIN:$(SRC_DIR)/%.cpp=$(TARGET)/%.out)

CXXFLAGS = -I$(INCLUDES)

all: $(MKDIR) $(OBJ) $(EXE)

%.out: %.o $(OBJ)
	$(CXX) $^ -o $@

$(TARGET)/%.o: $(SRC_DIR)/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@
	$(CXX) $(CXXFLAGS) -MM -MT $@ $< -MF $(@:.o=.d)

$(MKDIR):
	@$(MKDIR) -p $(TARGET)

$(CLEAN):
	@rm -rf $(TARGET)

.PHONY: $(CLEAN) $(MKDIR) all

-include $(DEPS)
