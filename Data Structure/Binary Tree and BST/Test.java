import MyTreePackage.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        BinaryNode<Integer> root = init1();
        ComparableBinaryTree<Integer> T1 = new ComparableBinaryTree<Integer>();
        // Note that the setRootNode() method is originally defined to be
        // protected, and would not be accessible outside the MyTreePackage.
        // However, for the purposes of this demo, the setRootNode() method
        // should be made public.  I have already done this in the provided
        // BinaryTree.java file.
        T1.setRootNode(root);

        
        root = init2();
        ComparableBinaryTree<Integer> T2 = new ComparableBinaryTree<Integer>();
        T2.setRootNode(root);
        T2.saveInorder("BST.dat");

        BinarySearchTree<Integer> T3 = new BinarySearchTree<Integer>();
        T3.buildInorder("BST.dat");
        // Trees T2 and T3 have the same contents.  However, T3 should be
        // optimally balanced (but not necessarily complete).

        root = init3();
        ComparableBinaryTree<Integer> T4 = new ComparableBinaryTree<Integer>();
        T4.setRootNode(root);

        BinarySearchTree<Integer> T5 = new BinarySearchTree<Integer>();
        for (int i = 1; i <= 15; i++)
        {
            T5.add(new Integer(i));
        }

        BinarySearchTree<String> T6 = new BinarySearchTree<String>();
        initBST(T6);

        //*******************************************************
        //*******************************************************
        // draw trees
        // TODO: you can modify the content of the array to show different trees
        // currently it shows T2, T3 and T5
        ComparableBinaryTree[] trees = {T2, T3, T5};
        for (ComparableBinaryTree tree : trees) {
            tree.draw();
        }
    }

    public static void show(String s) {
        System.out.println(s);
    }

    // This binary tree is not full and not a BST
    public static BinaryNode<Integer> init1()
    {
        BinaryNode<Integer> temp1 = new BinaryNode<Integer>(new Integer(60));
        BinaryNode<Integer> temp2 = new BinaryNode<Integer>(new Integer(30));
        BinaryNode<Integer> temp3 = new BinaryNode<Integer>(new Integer(80), temp1, temp2);
        temp1 = new BinaryNode<Integer>(new Integer(20));
        temp2 = new BinaryNode<Integer>(new Integer(15), temp1, temp3);
        temp3 = temp2;
        temp2 = new BinaryNode<Integer>(new Integer(50));
        temp1 = new BinaryNode<Integer>(new Integer(40), null, temp2);
        temp2 = new BinaryNode<Integer>(new Integer(75));
        BinaryNode<Integer> temp4 = new BinaryNode<Integer>(new Integer(65), temp1, temp2);
        temp1 = new BinaryNode<Integer>(new Integer(90), temp4, temp3);
        return temp1;
    }

    // This tree will be a binary search tree (BST), but not full. 
    public static BinaryNode<Integer> init2()
    {
        BinaryNode<Integer> temp1 = new BinaryNode<Integer>(new Integer(17));
        BinaryNode<Integer> temp2 = new BinaryNode<Integer>(new Integer(20), temp1, null);
        temp1 = new BinaryNode<Integer>(new Integer(10));
        BinaryNode<Integer> temp3 = new BinaryNode<Integer>(new Integer(15), temp1, temp2);
        temp2 = new BinaryNode<Integer>(new Integer(30));
        temp1 = new BinaryNode<Integer>(new Integer(25), temp3, temp2);
        temp3 = temp1;

        temp1 = new BinaryNode<Integer>(new Integer(55));
        temp2 = new BinaryNode<Integer>(new Integer(70));
        BinaryNode<Integer> temp4 = new BinaryNode<Integer>(new Integer(60), temp1, temp2);
        temp1 = new BinaryNode<Integer>(new Integer(80));
        temp2 = new BinaryNode<Integer>(new Integer(85), temp1, null);
        temp1 = new BinaryNode<Integer>(new Integer(75), temp4, temp2);

        temp4 = new BinaryNode<Integer>(new Integer(50), temp3, temp1);
        return temp4;
    }

    // This tree is full but not a BST
    public static BinaryNode<Integer> init3()
    {
        BinaryNode<Integer> temp1 = new BinaryNode<Integer>(new Integer(5));
        BinaryNode<Integer> temp2 = new BinaryNode<Integer>(new Integer(15));
        BinaryNode<Integer> temp3 = new BinaryNode<Integer>(new Integer(80), temp1, temp2);

        temp1 = new BinaryNode<Integer>(new Integer(40));
        temp2 = new BinaryNode<Integer>(new Integer(10));
        BinaryNode<Integer> temp4 = new BinaryNode<Integer>(new Integer(70), temp1, temp2);

        temp3 = new BinaryNode<Integer>(new Integer(50), temp3, temp4);
        return temp3;
    }

    // This tree will be a BST
    public static void initBST(BinarySearchTree<String> T)
    {
        T.add(new String("Outrageous"));
        T.add(new String("Zany"));
        T.add(new String("Bogus"));
        T.add(new String("Wacky"));
        T.add(new String("Weasel"));
        T.add(new String("Esoteric"));
        T.add(new String("Zippy"));
        T.add(new String("Uncertainty"));
        T.add(new String("Melancholy"));
    }
}
