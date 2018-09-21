HW_SOURCE_FILE = 'hw04.py'

###############
#  Questions  #
###############

def intersection(st, ave):
    """Represent an intersection using the Cantor pairing function."""
    return (st+ave)*(st+ave+1)//2 + ave

def street(inter):
    return w(inter) - avenue(inter)

def avenue(inter):
    return inter - (w(inter) ** 2 + w(inter)) // 2

w = lambda z: int(((8*z+1)**0.5-1)/2)

def taxicab(a, b):
    """Return the taxicab distance between two intersections.

    >>> times_square = intersection(46, 7)
    >>> ess_a_bagel = intersection(51, 3)
    >>> taxicab(times_square, ess_a_bagel)
    9
    >>> taxicab(ess_a_bagel, times_square)
    9
    """
    "*** YOUR CODE HERE ***"
    return abs(street(a) - street(b)) + abs(avenue(a) - avenue(b))

def squares(s):
    """Returns a new list containing square roots of the elements of the
    original list that are perfect squares.

    >>> seq = [8, 49, 8, 9, 2, 1, 100, 102]
    >>> squares(seq)
    [7, 3, 1, 10]
    >>> seq = [500, 30]
    >>> squares(seq)
    []
    """
    "*** YOUR CODE HERE ***"
    ans = []
    for item in s:
        root = square_root(item)
        if root is not False and root not in ans:
            ans.append(root)
    return ans


def square_root(n):
    """
    calculate the square root of a given integer
    if the sqrt is perfect, return the root
    or return False
    >>> square_root(0)
    0
    >>> square_root(1)
    1
    >>> square_root(2)
    False
    >>> square_root(4)
    2
    >>> square_root(16)
    4
    """
    assert n >= 0
    left = 0
    right = n
    r = 0
    while left <= right:
        r = (left + right) // 2 # the middle of the range to try
        if r * r == n:
            return r
        elif r * r < n:
            left = r + 1
        else:
            right = r - 1
    return False


def g(n):
    """Return the value of G(n), computed recursively.

    >>> g(1)
    1
    >>> g(2)
    2
    >>> g(3)
    3
    >>> g(4)
    10
    >>> g(5)
    22
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'g', ['While', 'For'])
    True
    """
    "*** YOUR CODE HERE ***"
    if n <= 3:
        return n
    else:
        return g(n - 1) + 2 * g(n - 2) + 3 * g(n - 3)

def g_iter(n):
    """Return the value of G(n), computed iteratively.

    >>> g_iter(1)
    1
    >>> g_iter(2)
    2
    >>> g_iter(3)
    3
    >>> g_iter(4)
    10
    >>> g_iter(5)
    22
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'g_iter', ['Recursion'])
    True
    """
    "*** YOUR CODE HERE ***"
    if n <= 3:
        return n
    else:
        g1 = 3
        g2 = 2
        g3 = 1
        i = 4
        while i <= n:
            g1, g2, g3, i = g1 + g2 * 2 + g3 * 3, g1, g2, i + 1
        return g1

def pingpong(n):
    """Return the nth element of the ping-pong sequence.

    >>> pingpong(7)
    7
    >>> pingpong(8)
    6
    >>> pingpong(15)
    1
    >>> pingpong(21)
    -1
    >>> pingpong(22)
    0
    >>> pingpong(30)
    6
    >>> pingpong(68)
    2
    >>> pingpong(69)
    1
    >>> pingpong(70)
    0
    >>> pingpong(71)
    1
    >>> pingpong(72)
    0
    >>> pingpong(100)
    2
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'pingpong', ['Assign', 'AugAssign'])
    True
    """
    "*** YOUR CODE HERE ***"
    """
    i = 1
    incre = 1
    curr = 1    
    while i < n:
        if i % 7 == 0 or has_seven(i):
            incre = -incre
        i, curr = i + 1, curr + incre
    return curr
    """
    return pingpong_loop_rec(1, n, 1, 1)

def pingpong_loop_rec(curr_index, target_index, curr_value, incre):
    """use this function as a loop"""
    if target_index <= 7:
        return target_index
    if curr_index == target_index:
        return curr_value
    else:
        if curr_index % 7 == 0 or has_seven(curr_index):
            return pingpong_loop_rec(curr_index + 1, target_index, curr_value - incre, -incre)
        else:
            return pingpong_loop_rec(curr_index + 1, target_index, curr_value + incre, incre)
    
def pingpong_rec(index):
    if index <= 2:
        return index
    else:
        if has_seven(index - 1) or (index - 1) % 7 == 0:
            return pingpong_rec(index - 2)
        else:
            return 2 * pingpong_rec(index - 1) - pingpong_rec(index - 2)

def has_seven(k):
    """Returns True if at least one of the digits of k is a 7, False otherwise.

    >>> has_seven(3)
    False
    >>> has_seven(7)
    True
    >>> has_seven(2734)
    True
    >>> has_seven(2634)
    False
    >>> has_seven(734)
    True
    >>> has_seven(7777)
    True
    """
    if k % 10 == 7:
        return True
    elif k < 10:
        return False
    else:
        return has_seven(k // 10)

def count_change(amount):
    """Return the number of ways to make change for amount.

    >>> count_change(7)
    6
    >>> count_change(10)
    14
    >>> count_change(20)
    60
    >>> count_change(100)
    9828
    """
    "*** YOUR CODE HERE ***"
    # first, find the biggest exponent of 2 less than or equal to amount:
    ex = find_biggest_ex(amount)
    return count_change_rec(amount, ex)

def find_biggest_ex(amount):
    assert amount >= 0
    ex = 0
    while 2 ** ex <= amount:
        ex += 1
    ex -= 1
    return ex
    

def count_change_rec(amount, ex):
    """AMOUNT is the amount of money to get changed
    EX is the highest exponent of 2 to be used in the process"""
    if amount == 0: # base case: the amount is 0
        return 1
    if 2 ** ex > amount:# if the exponent is not proper
        return count_change_rec(amount, find_biggest_ex(amount))
    if ex == 0: # base case: if the component is all 1, then there is only one way to do di
        return 1
    else:
        without_ex = count_change_rec(amount, ex - 1)
        with_ex = 0
        tmp = 2 ** ex
        i = 1
        while i * tmp <= amount:
            with_ex += count_change_rec(amount - i * tmp, ex - 1)
            i += 1
        return without_ex + with_ex
    
def trace(f):
    def traced_function(a, b):
        print(f + 'with ' + a + b)
        return f(a, b)
    return traced_function
###################
# Extra Questions #
###################

from operator import sub, mul

def make_anonymous_factorial():
    """Return the value of an expression that computes factorial.

    >>> make_anonymous_factorial()(5)
    120
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'make_anonymous_factorial', ['Assign', 'AugAssign', 'FunctionDef', 'Recursion'])
    True
    """
    return 

print(make_anonymous_factorial()(1))
print(make_anonymous_factorial()(2))
    
