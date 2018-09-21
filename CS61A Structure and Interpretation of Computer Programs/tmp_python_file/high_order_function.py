def make_adder(n):
    """ Return an adder function that takes an K as the only argument and add N on it

    >>> add_three = make_adder(3)
    >>> add_three(4)
    7
    >>> add_three(8)
    11
    """
    def adder(k):
        return n + k
    return adder
