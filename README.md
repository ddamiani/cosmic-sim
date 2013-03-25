cosmic-sim
==========

A quick and dirty simulation of a cosmic ray shower written in C++.

This is a small project I wrote for a class in grad school that simulates a cosmic ray shower initiated by a single
high energy photon incident on the atmosphere. I'm resurrecting this now as a learning tool to myself. The goal is
to try re-implementing it in different languages particularly ones I have not tried before. I plan to do some
performance comparisons as well.

The Physics
===========

The cosmic rays considered for this simulation are high-energy photons incident on the upper atmosphere.
The only particles considered in this simulation are electrons and photons. Two interaction types were considered
for photons: pair production and Compton scattering. For electrons energy loss via bremsstrahlung and ionization are
considered. 

A mean free path of ~1.29 radiation lengths is used for photons.
In the simulation photons with an energy less than 10 MeV are not further simulated. They are assumed to
lose all their energy via Compton scattering over a short distance (in terms of the distance scales of this simulation).
Otherwise when photons interact they produce a pair of electrons.

For electrons a mean free path of 0.28 radiation lengths is used. Electrons with an energy of less than 100 MeV are
considered for the purposes of this simulation to lose all their remaining energy to ionization over 1 radiation length.
Higher energy electrons that interact do so via bremsstrahlung, emitting a photon with a portion of the electrons
original energy.

The simulation itself does not take into account any lateral spread of the shower. It a primary purpose was
to roughly simulate the depth into the atmosphere that a shower would penetrate and the particle multiplicities
of the shower at various depths in atmosphere.

* Note on units. All energies are in MeV. Distances are in units of radiation lengths. Also X = 0 is the
top of the atmosphere and X = 28 is the surface.


The Code
========

The current purpose of this project is to try my hand at implementing this simulation in many different
languages, mostly as a learning tool for myself. The build instructions for the various versions are
included in README files in their corresponding directories.

* Note that all of the executables were compiled/run on an x86 Linux machines.
I have not tested any of the versions under Windows, but the interpreted and
JVM language versions should likely run with no problems. The versions that


Currently Implemented Versions
------------------------------

 &lt;language&gt;: &lt;subdirectory&gt; &lt;description&gt;

* **C++:**  &emsp; cpp-orig &emsp;&emsp;&emsp; The original C++ implementation of this code
* **C++:**  &emsp; cpp      &emsp;&emsp;&emsp;&emsp;&emsp;&ensp; The current C++ implementation of this code
* **Java:** &emsp; java     &emsp;&emsp;&emsp;&emsp;&emsp;&thinsp; The current Java implementation of this code

Implementation TO-DO List
-------------------------

In no particular order:

* Java
* Scala
* Clojure
* Python
* Fortran
* Common Lisp


Example Results: (Shower number = 10000)
===================================

These are some numbers given by the original C++ implementation of this simulation code.

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


Benchmarking
============

TODO
