// Java program for different tree traversals

package com.lib;

import com.lib.Node;
import java.util.ArrayList;
 
public class BinaryTree {
    public Node root; // Root of Binary Tree
 
    public BinaryTree() {
        root = null;
    }

    public void Inorder(Node node, ArrayList<Integer> path) {
        if (node == null)
            return;
        Inorder(node.left, path);  // first recur on left child
        path.add(node.key);       // Add to path
        Inorder(node.right, path); // now recur on right child
    }    

    // Given a binary tree, print nodes in postorder
    public void printPostorder(Node node) {
        if (node == null)
            return;
        printPostorder(node.left);  // first recur on left subtree
        printPostorder(node.right); // then recur on right subtree
        System.out.print(node.key + " "); // now deal with the node
    }

    // Given a binary tree, print its nodes in inorder
    public void printInorder(Node node) {
        if (node == null)
            return;
        printInorder(node.left);  // first recur on left child
        System.out.print(node.key + " "); // then print the data of node
        printInorder(node.right); // now recur on right child
    }
 
    // Given a binary tree, print its nodes in preorder
    public void printPreorder(Node node) {
        if (node == null)
            return;
        System.out.print(node.key + " "); // first print data of node
        printPreorder(node.left);  // then recur on left subtree
        printPreorder(node.right); // now recur on right subtree
    }

    // Wrappers over above recursive functions
    public void printPostorder()  { printPostorder(root); }
    public void printInorder()    { printInorder(root); }
    public void printPreorder()   { printPreorder(root); }
    public ArrayList<Integer> path = new ArrayList<>();
    public void Inorder()         { Inorder(root, path); }

}
