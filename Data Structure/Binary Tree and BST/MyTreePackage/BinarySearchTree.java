// CS 0445 Spring 2018
// Modified BinarySearchTree class.  This class now extends
// ComparableBinaryTree rather than BinaryTree.
// Add your methods to the class so that it works as specified
// in the assignment.  Note: This class will work without any modifications,
// since it is not defining any new methods -- rather it is overriding already
// existing methods to be more efficient.  However, you will lose credit if
// you do not override the methods to take advantage of the fact that this is
// a BST, not a general ComparableBinaryTree.

package MyTreePackage;
import java.util.Iterator;
/**
  A class that implements the ADT binary search tree by extending BinaryTree.
  Recursive version.

  @author Frank M. Carrano
  @author Timothy M. Henry
  @version 4.0
 */
public class BinarySearchTree<T extends Comparable<? super T>>
    extends ComparableBinaryTree<T> 
    implements SearchTreeInterface<T>
{
    public BinarySearchTree()
    {
        super();
    } // end default constructor

    public BinarySearchTree(T rootEntry)
    {
        super();
        setRootNode(new BinaryNode<>(rootEntry));
    } // end constructor

    public void setTree(T rootData) // Disable setTree (see Segment 25.6)
    {
        throw new UnsupportedOperationException();
    } // end setTree

    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, 
            BinaryTreeInterface<T> rightTree)
    {
        throw new UnsupportedOperationException();
    } // end setTree

    public T getEntry(T entry)
    {
        return findEntry(getRootNode(), entry);
    } // end getEntry

    // Recursively finds the given entry in the binary tree rooted at the given node.
    private T findEntry(BinaryNode<T> rootNode, T entry)
    {	
        T result = null;

        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();

            if (entry.equals(rootEntry))
                result = rootEntry;
            else if (entry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), entry);
            else
                result = findEntry(rootNode.getRightChild(), entry);
        } // end if

        return result;
    } // end findEntry

    public boolean contains(T entry)
    {
        return getEntry(entry) != null;
    } // end contains

    public T add(T newEntry)
    {
        T result = null;

        if (isEmpty())
            setRootNode(new BinaryNode<>(newEntry));
        else
            result = addEntry(getRootNode(), newEntry);

        return result;
    } // end add

    // Adds newEntry to the nonempty subtree rooted at rootNode.
    private T addEntry(BinaryNode<T> rootNode, T newEntry)
    {
        assert rootNode != null;
        T result = null;
        int comparison = newEntry.compareTo(rootNode.getData());

        if (comparison == 0)
        {
            result = rootNode.getData();
            rootNode.setData(newEntry);
        }
        else if (comparison < 0)
        {
            if (rootNode.hasLeftChild())
                result = addEntry(rootNode.getLeftChild(), newEntry);
            else
                rootNode.setLeftChild(new BinaryNode<>(newEntry));
        }
        else
        {
            assert comparison > 0;

            if (rootNode.hasRightChild())
                result = addEntry(rootNode.getRightChild(), newEntry);
            else
                rootNode.setRightChild(new BinaryNode<>(newEntry));
        } // end if

        return result;
    } // end addEntry

    public T remove(T entry)
    {
        ReturnObject oldEntry = new ReturnObject(null);
        BinaryNode<T> newRoot = removeEntry(getRootNode(), entry, oldEntry);
        setRootNode(newRoot);

        return oldEntry.get();
    } // end remove

    // Removes an entry from the tree rooted at a given node.
    // Parameters:
    //    rootNode  A reference to the root of a tree.
    //    entry  The object to be removed.
    //    oldEntry  An object whose data field is null.
    // Returns:  The root node of the resulting tree; if entry matches
    //           an entry in the tree, oldEntry's data field is the entry
    //           that was removed from the tree; otherwise it is null.
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry,
            ReturnObject oldEntry)
    {
        if (rootNode != null)
        {
            T rootData = rootNode.getData();
            int comparison = entry.compareTo(rootData);

            if (comparison == 0)       // entry == root entry
            {
                oldEntry.set(rootData);
                rootNode = removeFromRoot(rootNode);
            }
            else if (comparison < 0)   // entry < root entry
            {
                BinaryNode<T> leftChild = rootNode.getLeftChild();
                BinaryNode<T> subtreeRoot = removeEntry(leftChild, entry, oldEntry);
                rootNode.setLeftChild(subtreeRoot);
            }
            else                       // entry > root entry
            {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                rootNode.setRightChild(removeEntry(rightChild, entry, oldEntry));
            } // end if
        } // end if

        return rootNode;
    } // end removeEntry

    // Removes the entry in a given root node of a subtree.
    // Parameter:
    //    rootNode  A reference to the root of the subtree.
    // Returns:  The root node of the revised subtree.
    private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode)
    {
        // Case 1: rootNode has two children 
        if (rootNode.hasLeftChild() && rootNode.hasRightChild())
        {
            // Find node with largest entry in left subtree
            BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
            BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

            // Replace entry in root
            rootNode.setData(largestNode.getData());

            // Remove node with largest entry in left subtree
            rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
        } // end if 

        // Case 2: rootNode has at most one child
        else if (rootNode.hasRightChild())
            rootNode = rootNode.getRightChild();
        else
            rootNode = rootNode.getLeftChild();

        // Assertion: If rootNode was a leaf, it is now null

        return rootNode; 
    } // end removeEntry

    // Finds the node containing the largest entry in a given tree.
    // Parameter:
    //    rootNode  A reference to the root node of the tree.
    // Returns:  The node containing the largest entry in the tree.
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
            rootNode = findLargest(rootNode.getRightChild());

        return rootNode;
    } // end findLargest

    // Removes the node containing the largest entry in a given tree.
    // Parameter:
    //    rootNode  A reference to the root node of the tree.
    // Returns:  The root node of the revised tree.
    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
        {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            rightChild = removeLargest(rightChild);
            rootNode.setRightChild(rightChild);
        }
        else 
            rootNode = rootNode.getLeftChild();

        return rootNode;
    } // end removeLargest

    private class ReturnObject
    {
        private T item;

        private ReturnObject(T entry)
        {
            item = entry;
        } // end constructor

        public T get()
        {
            return item;
        } // end get

        public void set(T entry)
        {
            item = entry;
        } // end set
    } // end ReturnObject

    // **************************************************
    // Override the methods specified in Assignment 4 below
    // **************************************************
    
    
    public T getMax() {
        return getMaxRec(getRootNode());
    }

    // find the right most node and get its data
    private T getMaxRec(BinaryNode<T> node) {
        if (node == null) {
            return null;
        } else {
            // get the data from right child, if it's null, then current node is the right most one
            T right = getMaxRec(node.getRightChild());
            if (right == null) {
                return node.getData();
            } else {
                return right;
            }
        }
    }

    public T getMin() {
        return getMinRec(getRootNode());
    }

    // find the left most node and get its data
    private T getMinRec(BinaryNode<T> node) {
        if (node == null) {
            return null;
        } else {
            // get the data from left child, if it's null, then current node is the left most one
            T left = getMinRec(node.getLeftChild());
            if (left == null) {
                return node.getData();
            } else {
                return left;
            }
        }
    }

    // should be always true
    public boolean isBST() {
        return true;
    }

    public int rank(T data) {
        return rankRec(data, getRootNode());
    }

    // find the rank of the given data from current node recursively
    private int rankRec(T data, BinaryNode<T> node) {
        if (node == null) { //base case
            return 0;
        } else if (data.compareTo(node.getData()) <= 0) { // could not get rank because it's smaller than current data and move left
            return rankRec(data, node.getLeftChild());
        } else { // greater than current node, search the right part
            if (node.getLeftChild() == null) { // if there is no left child, then the number of nodes from left is 0
                return 1 + rankRec(data, node.getRightChild());
            } else { // if there is left child, then the rank should be 1 + number from left + recursively final answer
                return 1 + node.getLeftChild().getNumberOfNodes() + rankRec(data, node.getRightChild()); 
            }
        }
    }

    public T get(int i) {
        if (i >= 0 && i < getNumberOfNodes()) {
            return getRec(i, getRootNode());
        } else {
            throw new IndexOutOfBoundsException("out of bounds");
        }
    }

    // recursively get data with rank i from the subtree start with given node
    private T getRec(int i, BinaryNode<T> node) {
        BinaryNode<T> left = node.getLeftChild();
        // get the number of nodes on the left from given node
        int numberOnLeft;
        if (left == null) {
            numberOnLeft = 0;
        } else {
            numberOnLeft = left.getNumberOfNodes();
        }

        if (numberOnLeft == i) { // if number of nodes on the left is exact the rank we want, return data
            return node.getData();
        } else if (numberOnLeft > i) { // if number of nodes on the left is greater than what we want, move left
            return getRec(i, left);
        } else { // if number of nodes on the left is less than what we want, move right, but exclude those numbers + 1 as the new argument
            return getRec(i-(1 + numberOnLeft), node.getRightChild());
        }
    }


} // end BinarySearchTree
