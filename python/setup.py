from setuptools import setup, find_packages

setup(
    name="cosmicsim",
    version="0.0.1",
    author="Daniel Damiani",
    author_email="dsdamiani@gmail.com",
    description="A simple cosmic ray shower simulator.",
    license="BSD",
    keywords="cosmic simulation",
    url="https://github.com/ddamiani/cosmic-sim",
    package_dir={'': 'src'},
    packages=find_packages('src'),
    entry_points={
        'console_scripts': [
            'cosmic-sim = cosmicsim.main:main',
        ],
    }
)
