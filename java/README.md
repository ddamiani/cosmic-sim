The Java Version
========================

This is the Java version of this simulation code. This was just written
using standard Java (specifically targeting Java 1.6). It requires two
external dependencies from Apache Commons - commons-cli and commons-math.
Commons-math is used for the pseudo-random number generator (a Mersenne
Twister) and for some of the unit tests. The structure of this version is
very similar to the updated C++ version.


Build Instructions
------------------

The build system used for this version is Maven. Building the package requires that Maven be
installed (and obviously a compatible version of the JDK). Maven handles all the fetching of
the externally dependencies which are in the central Maven repository. Does mean you needs an
Internet connection to build though. Ah Maven...

To build the package simply run (from the directory containing the *pom.xml* file):

```
mvn package
```

This will build a jar file with all the needed dependencies included. For convenience there is
a wrapper script named *cosmic-sim* located in the *scripts* directory to simplify running
the simulation.

Note that this was only test on an x86 Linux machine, but there shouldn't be anything in this code
that isn't portable.

cosmic-sim
----------

This version works very similarly to the new C++ version. There are three required input parameters:

* **-c, --count :** The number of times to run the shower simulation

* **-e, --energy :** The initial energy of incoming photon in MeV

* **-p, --position :** The position at which the shower is sampled in radiation lengths

Optional Parameters:

* **-h, --help :** Prints help/usage information

* **-s, --seed :** Fixes the seed used by the random number generator to the value specified 
(normally uses the time at start up).

The final output displays both the number of charged and the number neutral particles detected passing
through the sample point averaged over the runs. It also estimates the errors based on the spread.

Example: *./scripts/cosmic-sim -e 30000 -p 7 -c 10000* -> runs a simulation of a 30 GeV initial photon
being sampled at 7 radiation lengths from the top of the sky. The simulation is repeated 10000 times.

MC Results: (Shower number = 10000)
===================================

Some example numbers derived from this version of the simulation.

Charged Particles
-----------------

<table border=1>
  <tr>
    <th>Energy (GeV)</th><th>Shower Max (RL(km))</th><th>Particles at S\_Max</th><th>Num Part at 22.4 RL (4km)</th>
  </tr>
  <tr>
    <td>10</td><td>5.75 RL (15.9km)</td><td>48.16&plusmn;0.17</td><td>0.050&plusmn;0.005</td>
  </tr>
  <tr>
    <td>30</td><td>7 RL (15km)</td><td>131.32&plusmn;0.41</td><td>0.39&plusmn;0.02</td>
  </tr>
  <tr>
    <td>100</td><td>8 RL (14.3km)</td><td>401.53&plusmn;1.11</td><td>3.76&plusmn;0.10</td>
  </tr>
  <tr>
    <td>300</td><td>9.5 RL (13.2km)</td><td>1133&plusmn;2.5</td><td>25.72&plusmn;0.45</td>
  </tr>
  <tr>
    <td>1000</td><td>10.5 RL (12.5km)</td><td>3554&plusmn;7.4</td><td>180.78&plusmn;2.29</td>
  </tr>
<table>

* Rad Lengths values measured from top of the atmosphere = 0 (so surface = 28) , and km values
measured in height from the surface.

* The shower maximum was not recalculated the value used was taken from the original.

Neutral Particles
-----------------

<table border=1>
  <tr>
    <th>Energy (GeV)</th><th>Shower Max (RL(km))</th><th>Particles at S\_Max</th><th>Num Part at 22.4 RL (4km)</th>
  </tr>
  <tr>
    <td>10</td><td>5.75 RL (15.9km)</td><td>26.17&plusmn;0.19</td><td>0.025&plusmn;0.003</td>
  </tr>
  <tr>
    <td>30</td><td>7 RL (15km)</td><td>71.30&plusmn;0.41</td><td>0.17&plusmn;0.01</td>
  </tr>
  <tr>
    <td>100</td><td>8 RL (14.3km)</td><td>220.75&plusmn;1.11</td><td>1.71&plusmn;0.05</td>
  </tr>
  <tr>
    <td>300</td><td>9.5 RL (13.2km)</td><td>615.37&plusmn;2.5</td><td>11.77&plusmn;0.21</td>
  </tr>
  <tr>
    <td>1000</td><td>10.5 RL (12.5km)</td><td>1951&plusmn;7.4</td><td>84.14&plusmn;1.12</td>
  </tr>
<table>

* Rad Lengths values measured from top of the atmosphere = 0 (so surface = 28) , and km values
measured in height from the surface.

* The shower maximum was not recalculated the value used was taken from the original.
