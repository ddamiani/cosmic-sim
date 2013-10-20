The New C++ Version
========================

This is a newer C++ version of this simulation code. This was just written
using mostly standard C++. It requires one external dependency; a Mersenne 
Twister pseudo-random number generator from the Boost library. This is just
intended to be a 'better' implementation of the original C++ version. It
is structurally fairly close to the Java implementation.


Build Instructions
------------------

The code itself can be built using make. Boost is also needed since this code relies on a
random number generator provided by it. On a Linux machine installing g++ and the boost
should be sufficient. The make file for this version is very similar to the one for the
original. There is one executable file built *cosmic-sim*.

Note that all of the executables were compiled with g++ on an x86 Linux machine. I have
tested this code in OSX as well, and it builds fine using either gcc or clang. I did import
a Linux/Unix specific header ("sys/time.h"), which allowed me to return the time since epoch
in microseconds. That was used as part of the seed for the random number generators. What this
means is that the source would need to be modified in order to compiled in Windows.


cosmic-sim
----------

This version works very similarly to the *c_mc_np* executable of the original C++ version.
The input pattern is as follows:

```
cosmic-sim [-h] ENERGY POSITION COUNT [SEED]
```

There are three required position input parameters:

* **ENERGY :** The initial energy of incoming photon in MeV

* **POSITION :** The position at which the shower is sampled in radiation lengths

* **COUNT :** The number of times to run the shower simulation

Optional Parameters:

* **-h :** Prints help/usage information

* **SEED :** Fixes the seed used by the random number generator to the value specified 
(normally uses the time at start up).

The final output displays both the number of charged and the number neutral particles detected passing
through the sample point averaged over the runs. It also estimates the errors based on the spread.

Example: *target/cosmic-sim 30000 7 10000* -> runs a simulation of a 30 GeV initial photon
being sampled at 7 radiation lengths from the top of the sky. The simulation is repeated 10000 times.

MC Results: (Shower number = 10000)
===================================

Some example numbers derived from this version of the simulation.

Charged Particles
-----------------

| Energy (GeV) | Shower Max (RL(km)) | Particles at S\_Max | Num Part at 22.4 RL (4km) |
|--------------|:-------------------:|:-------------------:|:-------------------------:|
| 10           |  5.75 RL (15.9km)   |   48.06&plusmn;0.17 |   0.049&plusmn;0.005      |
| 30           |  7 RL (15km)        |  131.96&plusmn;0.38 |   0.49&plusmn;0.03        |
| 100          |  8 RL (14.3km)      |  403.42&plusmn;1.02 |   3.81&plusmn;0.10        |
| 300          |  9.5 RL (13.2km)    | 1131&plusmn;2.3     |  25.69&plusmn;0.49        |
| 1000         | 10.5 RL (12.5km)    | 3548&plusmn;6.8     | 181.09&plusmn;2.41        |

* Rad Lengths values measured from top of the atmosphere = 0 (so surface = 28) , and km values
measured in height from the surface.

* The shower maximum was not recalculated the value used was taken from the original.

Neutral Particles
-----------------

| Energy (GeV) | Shower Max (RL(km)) | Particles at S\_Max | Num Part at 22.4 RL (4km) |
|--------------|:-------------------:|:-------------------:|:-------------------------:|
| 10           |  5.75 RL (15.9km)   |   26.19&plusmn;0.08 |   0.021&plusmn;0.002      |
| 30           |  7 RL (15km)        |  71.21&plusmn;0.18  |   0.21&plusmn;0.01        |
| 100          |  8 RL (14.3km)      |  221.61&plusmn;0.50 |   1.75&plusmn;0.05        |
| 300          |  9.5 RL (13.2km)    | 616.05&plusmn;1.14  |  11.75&plusmn;0.24        |
| 1000         | 10.5 RL (12.5km)    | 1948&plusmn;3.3     | 84.70&plusmn;1.19         |

* Rad Lengths values measured from top of the atmosphere = 0 (so surface = 28) , and km values
measured in height from the surface.

* The shower maximum was not recalculated the value used was taken from the original.
