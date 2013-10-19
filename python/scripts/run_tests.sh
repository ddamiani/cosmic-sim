#!/bin/bash
# Runs pep8, pylint, and unittests
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"; pwd -P)"
CONFIG_FILE="${SCRIPT_DIR}/config_venv.sh"
STATUS=0

exit_msg() {
    if [ $? -eq 0 ]; then
        echo "All tests succeeded."
    else
        echo "Exiting on test failures."
    fi
}

# Load config info
if [ -f "${CONFIG_FILE}" ]; then
    source "${CONFIG_FILE}"
else
    echo "Missing config file: ${CONFIG_FILE}"
    exit 1
fi

# Activate the virtualenv if one isn't active
if [ -z "${VIRTUAL_ENV}" ]; then
    if [ -d "${ENV_NAME}" ]; then
        echo -n "Activating the virtual environment... "
        source "${ENV_NAME}/bin/activate"
        echo "done"
    else
        echo "No virtualenv to activate!"
        exit 1
    fi
fi

# test if pep8 exists
if ! hash pep8 2>/dev/null; then
    echo "pep8 is not on the path!"
    exit 1   
fi

# test if pylint exists
if ! hash pylint 2>/dev/null; then
    echo "pylint is not on the path!"
    exit 1
fi

# test if nose exists
if ! hash nosetests 2>/dev/null; then
    echo "nosetests is not on the path!"
    exit 1
fi

# Run all the tests
trap exit_msg EXIT
set -e
for package in ${PACKAGES}
do
    pep8 ${PEP8_OPTS} "${SRC_DIR}/${package}"
    pylint ${PYLINT_OPTS} "${SRC_DIR}/${package}"
    nosetests "${BASE_DIR}"
done
