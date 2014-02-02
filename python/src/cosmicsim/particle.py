from cosmicsim.utils import mean_free, brem, pair, Result


class DecayType(object):
    """
    An enum of particle decay statuses
    """

    UNDECAYED = 0
    BREM = 1
    IONIZATION = 2
    PAIR = 3
    COMPTON = 4
    END = 5


class Particle(object):
    """
    A class representing a particle
    """
    END_ENERGY = 0.
    END_POSITION = 28.

    def __init__(self,
                 energy,
                 position,
                 rad_length,
                 term_dist,
                 parent=None,
                 results=None,
                 incrementer=None):
        self.energy = energy
        self.position = position
        self.rad_length = rad_length
        self.term_distance = term_dist
        self.decay_type = DecayType.UNDECAYED
        self.parent = parent
        self.results = results
        self.child_1 = None
        self.child_2 = None
        self.incrementer = incrementer

    def terminal_decay(self):
        return self.decay_type

    def decay(self):
        return self.decay_type

    def propogate(self):
        self.decay_type = self.terminal_decay()

        if self.decay_type != DecayType.UNDECAYED:
            final_position = self.term_distance + self.position
            if final_position < Particle.END_POSITION:
                self.check_position_result(final_position)
                self.position = final_position
                self.energy = Particle.END_ENERGY
            else:
                self.position = Particle.END_POSITION
                self.decay_type = DecayType.END

        else:
            new_position = mean_free(self.rad_length) + self.position

            if new_position < Particle.END_POSITION:
                self.check_position_result(new_position)
                # Decay the particle and create the children
                self.position = new_position
                self.decay_type = self.decay()
                self.energy = Particle.END_ENERGY
                # Propagate the first child and remove ref so it can get gc'ed
                self.child_1.propogate()
                self.child_1 = None
                # Propagate the second child and remove ref so it can get gc'ed
                self.child_2.propogate()
                self.child_2 = None
            else:
                self.position = Particle.END_POSITION
                self.decay_type = DecayType.END

    def check_position_result(self, new_position):
        if self.results is not None and callable(self.incrementer):
            if self.position < self.results.sample_point <= new_position:
                self.incrementer(self.results)


class Electron(Particle):
    """
    Class representing an electron
    """
    RAD_LENGTH = 0.28
    TERM_DECAY_LENGTH = 1.0
    TERM_DECAY_ENERGY = 100.0

    def __init__(self,
                 energy,
                 position,
                 parent=None,
                 results=None):
        super(Electron, self).__init__(
            energy,
            position,
            Electron.RAD_LENGTH,
            Electron.TERM_DECAY_LENGTH,
            parent=parent,
            results=results,
            incrementer=Result.num_charged_inc
        )

    def terminal_decay(self):
        if self.energy < Electron.TERM_DECAY_ENERGY:
            return DecayType.IONIZATION

        return DecayType.UNDECAYED

    def decay(self):
        brem_energy = brem()

        self.child_1 = Electron(
            self.energy * (1 - brem_energy),
            self.position,
            self,
            self.results
        )

        self.child_2 = Photon(
            self.energy * brem_energy,
            self.position,
            self,
            self.results
        )

        return DecayType.BREM


class Photon(Particle):
    """
    Class representing a photon
    """
    RAD_LENGTH = 9.0 / 7.0
    TERM_DECAY_LENGTH = 0.0
    TERM_DECAY_ENERGY = 10.0

    def __init__(self,
                 energy,
                 position,
                 parent=None,
                 results=None):
        super(Photon, self).__init__(
            energy,
            position,
            Photon.RAD_LENGTH,
            Photon.TERM_DECAY_LENGTH,
            parent=parent,
            results=results,
            incrementer=Result.num_neutral_inc
        )

    def terminal_decay(self):
        if self.energy < Photon.TERM_DECAY_ENERGY:
            return DecayType.COMPTON

        return DecayType.UNDECAYED

    def decay(self):
        pair_fraction = pair()

        self.child_1 = Electron(
            self.energy * (1 - pair_fraction),
            self.position,
            self,
            self.results
        )

        self.child_2 = Electron(
            self.energy * pair_fraction,
            self.position,
            self,
            self.results
        )

        return DecayType.PAIR