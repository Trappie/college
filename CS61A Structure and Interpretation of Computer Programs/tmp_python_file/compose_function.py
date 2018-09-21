def compose(f, g):
    def h(x):
        return f(x) + g(x)
    return h

def double(x):
    return 2 * x

def trible(x):
    return 3 * x

def square(x):
    return x * x

def cube(x):
    return x * x * x

def out(some_string):
    print(some_string)
    return len(some_string)

