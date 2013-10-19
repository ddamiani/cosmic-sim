#####################################################
## Basic config information for virtualenv          #
## This is meant to be sourced in the other scripts #
#####################################################

# Base directory of the python project
BASE_DIR="$(cd "${SCRIPT_DIR}/../"; pwd -P)"

# Directory path to the source code
SRC_DIR="${BASE_DIR}/src"

# Name for the virtualenv directory
ENV_NAME="${BASE_DIR}/.venv"

# Options to pass to the virtual env
ENV_OPTS="--prompt=(cosmicsim-env)"

# Command to use when setting up the cosmic sim package (install, develop)
SETUP_CMD="develop"

# Python packages name
PACKAGES="cosmicsim"

# Testing opts
PEP8_OPTS="--max-line-length=120"
PYLINT_OPTS=""

# function for cleaning up set variables
venv_var_cleanup() {
    unset SCRIPT_DIR
    unset BASE_DIR
    unset SRC_DIR
    unset ENV_NAME
    unset ENV_OPTS
    unset SETUP_CMD
    unset PACKAGES
    unset PEP8_OPTS
    unset PYLINT_OPTS
}
