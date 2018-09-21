def print_all(s):
    print(s)
    return print_all

#print_all(1)(2)(3)

def print_sum(x):
    print(x)
    def next_sum(y):
        return print_sum(x + y)
    return next_sum

# all of the following funcitons are next_sum functions, when called, they will return a new next_sum function
a = print_sum(1) # a is next_sum function where x is 1
b = a(2) # b is next_sum function where x is 3
c = b(3) # c is next_sum function where x is 6
d = c(4) # d is next_sum function where x is 10

# when b called, two things happened, one is print_sum(3 + y) is called, which printx + y, then return the new next_sum function, in which x = 3 + y



