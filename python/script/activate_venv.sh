#!/bin/bash
# This is a wrapper for activating the virtualenv - it should be sourced
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"; pwd -P)"

# source the config and activate
source "${SCRIPT_DIR}/config_venv.sh"
echo "Activating virtualenv in ${ENV_NAME}."
source "${ENV_NAME}/bin/activate"

# cleanup some of the set vars
venv_var_cleanup
unset -f venv_var_cleanup