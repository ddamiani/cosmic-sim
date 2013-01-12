SRC_DIR = src
INCLUDES = include
TARGET = target

SRC_MAIN = $(SRC_DIR)/c_mc.cpp $(SRC_DIR)/c_mc_np.cpp
OBJ_MAIN = $(patsubst $(SRC_DIR)/%.cpp,$(TARGET)/%.o,$(SRC_MAIN))
EXE = $(patsubst $(SRC_DIR)/%.cpp,$(TARGET)/%.out,$(SRC_MAIN))
SRC = $(SRC_DIR)/Particle.cpp
OBJ = $(patsubst $(SRC_DIR)/%.cpp,$(TARGET)/%.o,$(SRC))
#SRC += $(SRC_MAIN)

CXXFLAGS = -I$(INCLUDES)
#-MD -MP

#-include $(patsubst $(SRC_DIR)/%.cpp,$(TARGET)/%.d,$(SRC))

all: makedir $(OBJ) $(OBJ_MAIN) $(EXE)

$(TARGET)/%.out: $(TARGET)/%.o $(OBJ)
	$(CXX) $< $(OBJ) -o $@

$(TARGET)/%.o: $(SRC_DIR)/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

makedir:
	mkdir -p $(TARGET)

clean:
	rm -rf $(TARGET)
