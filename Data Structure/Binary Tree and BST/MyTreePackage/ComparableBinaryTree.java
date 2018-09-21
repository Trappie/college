package MyTreePackage;

@SuppressWarnings("unchecked")
public class ComparableBinaryTree<T extends Comparable<? super T>> extends BinaryTree<T> implements ComparableTreeInterface<T> {


    public ComparableBinaryTree() {
        super();
    }

    public ComparableBinaryTree(T rootData) {
        super();
        setRootNode(new BinaryNode<>(rootData));
    }

    // get the max data from the tree
    public T getMax() {
        return getMaxRec(getRootNode());
    }

    // get the max data recursively from the given node and its decendants
    private T getMaxRec(BinaryNode<T> startNode) {
        if (startNode == null) {
            return null;
        } else {
            T leftMax = getMaxRec(startNode.getLeftChild());
            T rightMax = getMaxRec(startNode.getRightChild());
            T current = startNode.getData();
            return getBigger(getBigger(leftMax, rightMax), current);
        }
    }

    // get the bigger one when one or two of the input could be null
    private T getBigger(T a, T b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else {
            return a.compareTo(b) < 0 ? b : a;
        }
    }

    // get the min data from the tree
    public T getMin() {
        return getMinRec(getRootNode());
    }

    
    // get the min data recursively from the given node and its decendants
    private T getMinRec(BinaryNode<T> startNode) {
        if (startNode == null) {
            return null;
        } else {
            T leftMin = getMinRec(startNode.getLeftChild());
            T rightMin = getMinRec(startNode.getRightChild());
            T current = startNode.getData();
            return getSmaller(getSmaller(leftMin, rightMin), current);
        }
    }

    // get the smaller one when one or two of the input could be null
    private T getSmaller(T a, T b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else {
            return a.compareTo(b) < 0 ? a : b;
        }
    }

    // return true if the tree is a Binary Search Tree
    public boolean isBST() {
        return isBSTRec(getRootNode());
    }

    // determine whether the subtree from given node is BST recursively
    private boolean isBSTRec(BinaryNode<T> startNode) {
        if (startNode == null) {
            return true;
        } else if (nodeIsInorder(startNode)) {
            return (isBSTRec(startNode.getLeftChild()) && isBSTRec(startNode.getRightChild()));
        } else {
            return false;
        }
    }

    // to verify whether the data in given node and its children are in order, the children could be null
    // notice that left child could be equal to parent one, but parent should be less than right child
    private boolean nodeIsInorder(BinaryNode<T> node) {
        assert node != null;
        BinaryNode<T> left = node.getLeftChild();
        BinaryNode<T> right = node.getRightChild();
        if ( left == null && right == null) {
            return true;
        } else if (left == null) {
            if (node.getData().compareTo(right.getData()) < 0) {
                return true;
            } else {
                return false;
            }
        } else if (right == null) {
            if (node.getData().compareTo(left.getData()) >= 0) {
                return true;
            } else { 
                return false;
            }
        } else { 
            T leftData = left.getData();
            T rightData = right.getData();
            T currentData = node.getData();
            return leftData.compareTo(currentData) <= 0 && currentData.compareTo(rightData) < 0;
        }
    }

    // get the rank of the given data in the tree
    public int rank(T data) {
        return rankRec(data, getRootNode());
    }

    // get the rank from the subtree start from the given node recursively
    // rank actually equal to number of data smaller than the given data
    private int rankRec(T data, BinaryNode<T> startNode) {
        if (startNode == null) {
            return 0;
        } else { 
            int leftRank = rankRec(data, startNode.getLeftChild());
            int rightRank = rankRec(data, startNode.getRightChild());
            T current = startNode.getData();
            if (current.compareTo(data) < 0) {
                return (1 + leftRank + rightRank);
            } else {
                return leftRank + rightRank;
            }
        }
    }

    public T get(int i) {
        int length = getNumberOfNodes();
        if (i == 0) { // if want to find the smallest, from the design, it's a special case
            return getMin();
        } else if (i < length && i > 0) {
            T[] array = (T[]) (new Comparable[i + 1]); // create an array with the size of i+1, which is long enough to hold all data smaller than the final answer
            insertInorder(array, getRootNode()); // traversal the tree and insert every node into the array in order
            return array[i]; // return the ith data of the array, which is rank(i)
        } else {
            throw new IndexOutOfBoundsException("out of bounds");
        }
    }

    // find the proper position for the incomming node
    // it's like insertion sorting
    private void insertInorder(T[] array, BinaryNode<T> node) {
        if (node != null) {
            T data = node.getData();
            int index = array.length - 1;
            boolean done = false;
            while (array[index] == null && index > 0) { // find the last not null position
                index--;
            }
            if (index == array.length - 1) {
                if (data.compareTo(array[index]) < 0) {  
                    index--;
                } else {
                    insertInorder(array, node.getLeftChild());
                    insertInorder(array, node.getRightChild());
                    return;
                }
            }
            while (!done && index >= 0) {
                if (array[index] == null || data.compareTo(array[index]) < 0) {
                    array[index + 1] = array[index];
                    index--;
                } else {
                    done = true;
                }
            }
            array[index + 1] = data;
            insertInorder(array, node.getLeftChild());
            insertInorder(array, node.getRightChild());
        }
    }
}
