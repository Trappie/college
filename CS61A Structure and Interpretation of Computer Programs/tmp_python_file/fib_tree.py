class Tree:
    def __init__(self, label, branches=[]):
        self.label = label
        for branch in branches:
            assert isinstance(branch, Tree)
        self.branches = list(branches) # create a new copy of branches?

    def __repr__(self):
        if self.branches:
            branch_str = ', ' + repr(self.branches)
        else:
            branch_str = ''
        return 'Tree({0}{1})'.format(self.label, branch_str)

    def __str__(self):
        return '\n'.join(self.indented()) # every indented lines in different lines, join by \n

    def indented(self):
        lines = [] # all lines to show for self object
        for b in self.branches: # for all branches
            for line in b.indented(): # for all lines in sub branches, 
                lines.append('  ' + line) #create a new indented version of those lines and add them to current lines array.
        return [str(self.label)] + lines

    def is_leaf(self):
        return not self.branches


tree = Tree(3, [Tree(4, [Tree(5), Tree(6)])])
# tree = Tree(3)
# print(repr(tree))
# print(str(tree))
# print(tree) # so the print function actually print the return value of __str__ method of the object

# create a memo version of the given function, which should take one formal parameter
def memo(f):
    cache = {}
    def memo_func(n):
        if n not in cache:
            cache[n] = f(n)
        return cache[n]
    return memo_func

@memo
def fib_tree(n):
    if n == 0 or n == 1:
        return Tree(n)
    else:
        left = fib_tree(n - 1)
        right = fib_tree(n - 2)
        fib_n = left.label + right.label
        return Tree(fib_n, [left, right])

tree = fib_tree(30)
