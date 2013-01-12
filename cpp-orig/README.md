The Original C++ Version
========================

This is the original version of this simulation code. This was just written
using standard C++ with the addition of boost libraries for the random number
generator. The core of it is a C++ class I made called Particle.cpp (and its
header file Particle.h). I also used the Mersenne Twister implementation that
is provided as part of boost. There are two different source files containing
the executable part of the code, each one works slightly differently. They are
explained below.

* Note that all of the executables were compiled with g++ on an x86 Linux machine. I did import a
Linux/Unix specific header ("sys/time.h"), which allowed me to return the time since epoch in
microseconds. That was used as part of the seed for the random number generators. What this means is
that the source would need to be modified in order to compiled in Windows.

* Note on units. All energies are in MeV. Distances are in units of radiation lengths. Also X = 0 is the
top of the atmosphere and X = 28 is the surface.

Build Instructions
------------------

c_mc.cpp
--------

This first file does a single cosmic ray shower. The main function of c_mc.cpp takes three command
line parameters:

* **eng\_init:** initial energy of incoming photon.

* **meas:** position at which the shower is sampled (final positions of particles and final energies of
particles that "range out" of the atmosphere can also be accessed directly).

* **no\_dead:** -> true (1) prunes "dead" particles from the particle list printouts (does nothing to the particles
themselves, only effects printing to the screen, the final positions of "dead" particles is still
accessible).

Example: *./c_mc.out 10000 22.4 0* -> runs a simulation of a 10 GeV initial photon being sampled at 22.4 radiation
lengths from the top of the sky with full printing verbosity.

The function prints out info on each particle at each step through the main loop to stdout. The program
continues until all particles are "dead" or have reached the surface. The last set of outputs is the info on
the state of the particles when they crossed that point (obviously it only includes particles that passed
through the sampling point). Also counts the number of charged and uncharged particles at the sample
position.

Output format:

* column 1: Internal particle number

* column 2: Particle type -> 0 = photon, 1 = e+/e-

* column 3: Particle energy (MeV)

* column 4: Particle position (Rad Lengths)

* column 5: Particle number of the particles parent particle


c_mc_np.cpp
-----------

This file allows you to run multiple showers at one time, and suppresses most screen output.
The main function of c_mc_np.cpp takes three parameters:

* **num\_runs:** number of showers to run

* **eng\_init:** initial energy of incoming photon.

* **meas:** position at which the shower is sampled

The final output displays both the number of charged and the number neutral particles detected passing
through the sample point averaged over the runs. It also estimates the errors based on the spread.



MC Results: (Shower number = 10000)
===================================

Some example numbers derived from this version of the simulation.

<table border=1>
  <tr>
    <th>Energy (GeV)</th><th>Shower Max (RL(km))</th><th>Particles at S\_Max</th><th>Num Part at 22.4 RL (4km)</th>
  </tr>
  <tr>
    <td>10</td><td>5.75 RL (15.9km)</td><td>52.15&plusmn;0.19</td><td>0.06&plusmn;0.01</td>
  </tr>
  <tr>
    <td>30</td><td>7 RL (15km)</td><td>143.34&plusmn;0.41</td><td>0.43&plusmn;0.02</td>
  </tr>
  <tr>
    <td>100</td><td>8 RL (14.3km)</td><td>438.07&plusmn;1.11</td><td>4.08&plusmn;0.12</td>
  </tr>
  <tr>
    <td>300</td><td>9.5 RL (13.2km)</td><td>1224&plusmn;2.5</td><td>27.20&plusmn;0.51</td>
  </tr>
  <tr>
    <td>1000</td><td>10.5 RL (12.5km)</td><td>3846&plusmn;7.4</td><td>190.2&plusmn;2.5</td>
  </tr>
<table>

* Rad Lengths values measured from top of the atmosphere = 0 (so surface = 28) , and km values
measured in height from the surface.

* These values are for charged particles

