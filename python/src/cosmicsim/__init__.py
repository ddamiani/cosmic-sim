'''
cosmicsim - Package for all modules related to the cosmic ray simulation
'''

try:
    __version__ = __import__('pkg_resources').get_distribution(__name__).version
except ImportError:
    __version__ = 'unknown'
