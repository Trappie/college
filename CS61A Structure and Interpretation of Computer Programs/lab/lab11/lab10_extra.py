""" Optional Questions for Lab 10 """

from lab10 import *

# Q5
def hailstone(n):
    """
    >>> for num in hailstone(10):
    ...     print(num)
    ...
    10
    5
    16
    8
    4
    2
    1
    """
    "*** YOUR CODE HERE ***"
    yield n
    if n != 1:
        if n % 2 == 0:
            yield from hailstone(n // 2)
        else:
            yield from hailstone(n * 3 + 1)
    
    #if n == 1:
    #    yield 1
    #else:
    #    yield n
    #elif n % 2 == 0:
    #    result = n // 2
    #    yield result
    #else:
    #    result = n * 3 + 1
    #    yield result
    #yield from hailstone(result)

# Q6
def repeated(t, k):
    """Return the first value in iterable T that appears K times in a row.

    >>> repeated([10, 9, 10, 9, 9, 10, 8, 8, 8, 7], 2)
    9
    >>> repeated([10, 9, 10, 9, 9, 10, 8, 8, 8, 7], 3)
    8
    >>> s = [3, 2, 1, 2, 1, 4, 4, 5, 5, 5]
    >>> repeated(trap(s, 7), 2)
    4
    >>> repeated(trap(s, 10), 3)
    5
    >>> print(repeated([4, None, None, None], 3))
    None
    """
    assert k > 1
    "*** YOUR CODE HERE ***"
    result = None
    iterator = iter(t)
    curr = None
    count = 0
    while True:
        try:
            point = next(iterator)
            if point == curr:
                count += 1
                if count == k:
                    return curr
            else:
                curr = point
                count = 1
        except StopIteration:
            break

    return None

# Q7
def merge(s0, s1):
    """Yield the elements of strictly increasing iterables s0 and s1, removing
    repeats. Assume that s0 and s1 have no repeats. s0 or s1 may be infinite
    sequences.

    >>> m = merge([0, 2, 4, 6, 8, 10, 12, 14], [0, 3, 6, 9, 12, 15])
    >>> type(m)
    <class 'generator'>
    >>> list(m)
    [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15]
    >>> def big(n):
    ...    k = 0
    ...    while True: yield k; k += n
    >>> m = merge(big(2), big(3))
    >>> [next(m) for _ in range(11)]
    [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15]
    """
    i0, i1 = iter(s0), iter(s1) # s0 and s1 should not be none, but could be []
    e0, e1 = next(i0, None), next(i1, None)
    "*** YOUR CODE HERE ***"
    if e0 == None and e1 == None:
        pass
    else:
        if e0 == None:
            yield e1
            yield from i1
        elif e1 == None:
            yield e0
            yield from i0
        else:
            # both exist
            if e0 == e1:
                yield e1
                yield from merge(i0, i1)
            elif e0 < e1:
                yield e0
                yield from merge(merge(i0, [e1]), i1)
            else:
                yield e1
                yield from merge(merge([e0], i1), i0)

# Q8
def remainders_generator(m):
    """
    Yields m generators. The ith yielded generator yields natural numbers whose
    remainder is i when divided by m.

    >>> import types
    >>> [isinstance(gen, types.GeneratorType) for gen in remainders_generator(5)]
    [True, True, True, True, True]
    >>> remainders_four = remainders_generator(4)
    >>> for i in range(4):
    ...     print("First 3 natural numbers with remainder {0} when divided by 4:".format(i))
    ...     gen = next(remainders_four)
    ...     for _ in range(3):
    ...         print(next(gen))
    First 3 natural numbers with remainder 0 when divided by 4:
    4
    8
    12
    First 3 natural numbers with remainder 1 when divided by 4:
    1
    5
    9
    First 3 natural numbers with remainder 2 when divided by 4:
    2
    6
    10
    First 3 natural numbers with remainder 3 when divided by 4:
    3
    7
    11
    """
    "*** YOUR CODE HERE ***"
    def module_generator(i):
        assert i >= 0 and i < m
        natural_iter = naturals()    
        while True:
            curr = next(natural_iter)
            if curr % m == i:
                yield curr
    for i in range(m):
        yield module_generator(i)

# Q9
def zip_generator(*iterables):
    """
    Takes in any number of iterables and zips them together.
    Returns a generator that outputs a series of lists, each
    containing the nth items of each iterable.
    >>> z = zip_generator([1, 2, 3], [4, 5, 6], [7, 8])
    >>> for i in z:
    ...     print(i)
    ...
    [1, 4, 7]
    [2, 5, 8]
    """
    "*** YOUR CODE HERE ***"
    def get_zip_list(iters):
        result = []
        for iterator in iters:
            value = next(iterator, None)
            if value:
                result.append(value)
            else:
                return None
        return result
    iters = [iter(lst) for lst in iterables]
    zip_list = get_zip_list(iters)
    while zip_list:
        yield zip_list
        zip_list = get_zip_list(iters)
