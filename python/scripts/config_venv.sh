#####################################################
## Basic config information for virtualenv          #
## This is meant to be sourced in the other scripts #
#####################################################

# Base directory of the python project
BASE_DIR="$(readlink -f "${SCRIPT_DIR}/../")"

# Name for the virtualenv directory
ENV_NAME="${BASE_DIR}/cosmicsim-env"

# Command to use when setting up the cosmic sim package (install, develop)
SETUP_CMD="develop"

# function for cleaning up set variables
venv_var_cleanup() {
    unset SCRIPT_DIR
    unset BASE_DIR
    unset ENV_NAME
    unset SETUP_CMD
}
