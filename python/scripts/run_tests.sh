#!/bin/bash
# Runs pep8, pylint, and unittests
BASE_DIR="$(readlink -f "$(dirname "${BASH_SOURCE[0]}")/../src")"
PACKAGES="cosmicsim"
STATUS=0

exit_msg() {
    if [ $? -eq 0 ]; then
        echo "All tests succeeded."
    else
        echo "Exiting on test failures."
    fi
}

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
    pep8 --max-line-length=120 "${BASE_DIR}/${package}"
    pylint "${BASE_DIR}/${package}"
    nosetests "${BASE_DIR}/${package}"
done
