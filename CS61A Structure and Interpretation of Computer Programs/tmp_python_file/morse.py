
class Tree:
    def __init__(self, label, branches=[]):
        for c in branches:
            assert isinstance(c, Tree)
        self.label = label
        self.branches = list(branches)

    def __repr__(self):
        if self.branches:
            branches_str = ', ' + repr(self.branches)
        else:
            branches_str = ''
        return 'Tree({0}{1})'.format(self.label, branches_str)

    def is_leaf(self):
        return not self.branches

    def __eq__(self, other):
        return type(other) is type(self) and self.label == other.label \
               and self.branches == other.branches
    
    def __str__(self):
        def print_tree(t, indent=0):
            tree_str = '  ' * indent + str(t.label) + "\n"
            for b in t.branches:
                tree_str += print_tree(b, indent + 1)
            return tree_str
        return print_tree(self).rstrip()

    def copy_tree(self):
        return Tree(self.label, [b.copy_tree() for b in self.branches])

abcde = {'a':'.-', 'b': '-...', 'c': '-.-', 'd':'-..', 'e':'.'}


def morse(abcde):
    # given the abcde dictionary, return a tree created from the content
    t = Tree(None) # create a new tree which the root label is empty
    for key in abcde:
        add(t, key, abcde[key])
    return t    

def add(tree, char, signals):
    if signals == '':
        tree.branches.append(Tree(char))
    else:
        right_branch = find_branch(tree, signals[0])
        if not right_branch:
            right_branch = Tree(signals[0])
            tree.branches.append(right_branch)
        add(right_branch, char, signals[1:])

def find_branch(tree, label):
    # given the label, find the branch whose label is the same, return none otherwise
    result_branches = [b for b in tree.branches if b.label == label]
    if result_branches:
        return result_branches[0]
    else:
        return None
    
def decode(signals, tree):
    for signal in signals:
        tree = [b for b in tree.branches if b.label == signal][0] # because there should be only one item in the list
    leaves = [b for b in tree.branches if not b.branches]
    assert len(leaves) == 1
    return leaves[0].label

t = morse(abcde)
print([decode(s, t) for s in ['.', '-..', '-.-', '-...', '.-']])
