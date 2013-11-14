class Particle(object):
    """
    A class representing a particle
    """

    def __init__(self,
                 energy,
                 position,
                 rad_length,
                 term_dist,
                 parent=None,
                 results=None):
        self.energy = energy
        self.position = position
        self.rad_length = rad_length
        self.term_distance = term_dist
        self.parent = parent
        self.results = results
        self.child_1 = None
        self.child_2 = None
