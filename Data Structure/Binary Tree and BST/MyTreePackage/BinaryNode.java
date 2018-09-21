// CS 0445 Spring 2018
// BinaryNode class for Assignment 4.  Add the methods specified in the
// assignment sheet so that this class works correctly.  I have included
// them with empty bodies below.
package MyTreePackage;
import java.awt.*;
import java.lang.Math;
/**
  A class that represents nodes in a binary tree.

  @author Frank M. Carrano
  @author Timothy M. Henry
  @version 4.0
 */
public class BinaryNode<T>
{
    private T             data;
    private BinaryNode<T> leftChild;  // Reference to left child
    private BinaryNode<T> rightChild; // Reference to right child

    public BinaryNode()
    {
        this(null); // Call next constructor
    } // end default constructor

    public BinaryNode(T dataPortion)
    {
        this(dataPortion, null, null); // Call next constructor
    } // end constructor

    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
            BinaryNode<T> newRightChild)
    {
        data = dataPortion;
        leftChild = newLeftChild;
        rightChild = newRightChild;
    } // end constructor

    /** Retrieves the data portion of this node.
      @return  The object in the data portion of the node. */
    public T getData()
    {
        return data;
    } // end getData

    /** Sets the data portion of this node.
      @param newData  The data object. */
    public void setData(T newData)
    {
        data = newData;
    } // end setData

    /** Retrieves the left child of this node.
      @return  The node’s left child. */
    public BinaryNode<T> getLeftChild()
    {
        return leftChild;
    } // end getLeftChild

    /** Sets this node’s left child to a given node.
      @param newLeftChild  A node that will be the left child. */
    public void setLeftChild(BinaryNode<T> newLeftChild)
    {
        leftChild = newLeftChild;
    } // end setLeftChild

    /** Detects whether this node has a left child.
      @return  True if the node has a left child. */
    public boolean hasLeftChild()
    {
        return leftChild != null;
    } // end hasLeftChild

    /** Retrieves the right child of this node.
      @return  The node’s right child. */
    public BinaryNode<T> getRightChild()
    {
        return rightChild;
    } // end getRightChild

    /** Sets this node’s right child to a given node.
      @param newRightChild  A node that will be the right child. */
    public void setRightChild(BinaryNode<T> newRightChild)
    {
        rightChild = newRightChild;
    } // end setRightChild

    /** Detects whether this node has a right child.
      @return  True if the node has a right child. */
    public boolean hasRightChild()
    {
        return rightChild != null;
    } // end hasRightChild

    /** Detects whether this node is a leaf.
      @return  True if the node is a leaf. */
    public boolean isLeaf()
    {
        return (leftChild == null) && (rightChild == null);
    } // end isLeaf

    /** Counts the nodes in the subtree rooted at this node.
      @return  The number of nodes in the subtree rooted at this node. */
    public int getNumberOfNodes()
    {
        int leftNumber = 0;
        int rightNumber = 0;

        if (leftChild != null)
            leftNumber = leftChild.getNumberOfNodes();

        if (rightChild != null)
            rightNumber = rightChild.getNumberOfNodes();

        return 1 + leftNumber + rightNumber;
    } // end getNumberOfNodes

    /** Computes the height of the subtree rooted at this node.
      @return  The height of the subtree rooted at this node. */
    public int getHeight()
    {
        return getHeight(this); // Call private getHeight
    } // end getHeight

    private int getHeight(BinaryNode<T> node)
    {
        int height = 0;

        if (node != null)
            height = 1 + Math.max(getHeight(node.getLeftChild()),
                    getHeight(node.getRightChild()));

        return height;
    } // end getHeight

    /** Copies the subtree rooted at this node.
      @return  The root of a copy of the subtree rooted at this node. */
    public BinaryNode<T> copy()
    {
        BinaryNode<T> newRoot = new BinaryNode<>(data);

        if (leftChild != null)
            newRoot.setLeftChild(leftChild.copy());

        if (rightChild != null)
            newRoot.setRightChild(rightChild.copy());

        return newRoot;
    } // end copy

    // **********************************
    // Complete the additional methods below
    // **********************************

    public boolean isFull()	// If the tree is a full tree, return true
    {						// Otherwise, return false.  See notes for
        // definition of full.
        
        // becasue the isBalance(k) method traversal the tree only once, so it's fast enough to call it here
        return isBalanced(0);
    }

    public boolean isBalanced(int k) // Return true if 1) the difference
    {	// in height between the left and right subtrees is at most k,
        // and 2) the left and right subtrees are both recursively
        // k-balanced; return false otherwise

        
        return getBalanceDataRec(this).diff <= k;

        // the normal recursion version of isBalance, I think it's slow, so I tried another version, as below

        //if ((leftChild == null || leftChild.isBalanced(k)) && (rightChild == null || rightChild.isBalanced(k))){
        //    int diff = getHeight(leftChild) - getHeight(rightChild);
        //    return diff <= k && diff >= -k;
        //} else {
        //    return false;
        //}
    }

    // another way to implement is balance, which should be faster because it only traversal the tree once
    private BalanceData getBalanceDataRec(BinaryNode<T> node) {
        if (node == null) { // base case
            return new BalanceData(0, 0);
        } else {
            // get information from left child
            BalanceData left = getBalanceDataRec(node.getLeftChild());
            // get information from right child
            BalanceData right = getBalanceDataRec(node.getRightChild());
            // get the new height to return
            int newHeight = Math.max(left.height, right.height) + 1;
            // get the difference between the height from left and right child (there is no traversal here!!!
            int currDiff = Math.abs(left.height - right.height);
            // get the max difference from two sub tree and current node
            int maxDiff = Math.max(Math.max(left.diff, right.diff), currDiff);
            // form the new paired information object and return 
            return new BalanceData(newHeight, maxDiff);
        }
    }

    // paired data used to return height and balance information at the same time at a given node
    private class BalanceData {
        // the height of the subtree
        int height;
        // the max difference in the sub tree
        int diff;

        private BalanceData(int height, int diff) {
            this.height = height;
            this.diff = diff;
        }
    }


    public void draw(Graphics g, int x, int y, int rad, int width) { // draw the current node at (x,y) and draw all subtrees in given width
        // draw itself at the given place with the given rad
        g.drawOval(x-rad, y-rad, 2 * rad, 2 * rad);
        String str = getData().toString();
        g.drawString(str, x-(4 * str.length()), y+4);

        int leftX = x - (width / 4);
        int rightX = x + (width / 4);
        int newY = y + (rad * 4);
        int newWidth = width / 2;

        // draw sub trees
        if (leftChild != null) {
            leftChild.draw(g, leftX, newY, rad, newWidth);
            g.drawLine(x-rad, y+rad, leftX+rad, newY-rad);
        }

        if (rightChild != null) {
            rightChild.draw(g, rightX, newY, rad, newWidth);
            g.drawLine(x+rad, y+rad, rightX-rad, newY-rad);
        }
    }


} // end BinaryNode

