#!/bin/bash
# Sets up a virtual env for the python code

SCRIPT_DIR="$(readlink -f "$(dirname "${BASH_SOURCE[0]}")")"
CONFIG_FILE="${SCRIPT_DIR}/config_venv.sh"
if [ -f "${CONFIG_FILE}" ]; then
    source "${CONFIG_FILE}"
else
    echo "Missing config file: ${CONFIG_FILE}"
    exit 1
fi

# check if virtualenv is installed
hash virtualenv 2>/dev/null
if [ $? -ne 0 ]; then
    echo "here"
fi

# check if there is a valid python project
if [ ! -f "${BASE_DIR}/setup.py" ]; then
    echo "The directory ${BASE_DIR} does not appear to contain Python project!"
    exit 1
fi

# Remove existing virtualenv
if [ -d "${ENV_NAME}" ]; then
    echo "Removing existing virtualenv."
    rm -r "${ENV_NAME}"
fi

# setup the virtualenv and install the package
set -e
virtualenv "${ENV_NAME}"
source "${ENV_NAME}/bin/activate"
python setup.py "${SETUP_CMD}"
