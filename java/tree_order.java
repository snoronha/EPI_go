// Java program for different tree traversals

import com.lib.Node;
import java.util.ArrayList;
 
class BinaryTree {
    Node root; // Root of Binary Tree
 
    BinaryTree() {
        root = null;
    }

    void Inorder(Node node, ArrayList<Integer> path) {
        if (node == null)
            return;
        Inorder(node.left, path);  // first recur on left child
        path.add(node.key);       // Add to path
        Inorder(node.right, path); // now recur on right child
    }    

    // Given a binary tree, print nodes in postorder
    void printPostorder(Node node) {
        if (node == null)
            return;
        printPostorder(node.left);  // first recur on left subtree
        printPostorder(node.right); // then recur on right subtree
        System.out.print(node.key + " "); // now deal with the node
    }

    // Given a binary tree, print its nodes in inorder
    void printInorder(Node node) {
        if (node == null)
            return;
        printInorder(node.left);  // first recur on left child
        System.out.print(node.key + " "); // then print the data of node
        printInorder(node.right); // now recur on right child
    }
 
    // Given a binary tree, print its nodes in preorder
    void printPreorder(Node node) {
        if (node == null)
            return;
        System.out.print(node.key + " "); // first print data of node
        printPreorder(node.left);  // then recur on left subtree
        printPreorder(node.right); // now recur on right subtree
    }

    // Wrappers over above recursive functions
    void printPostorder()  { printPostorder(root); }
    void printInorder()    { printInorder(root); }
    void printPreorder()   { printPreorder(root); }
    ArrayList<Integer> path = new ArrayList<>();
    void Inorder()         { Inorder(root, path); }
 
    // Driver method
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root            = new Node(1);
        tree.root.left       = new Node(2);
        tree.root.right      = new Node(3);
        tree.root.left.left  = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.println("Preorder traversal of binary tree is ");
        tree.printPreorder();
 
        System.out.println("\nInorder traversal of binary tree is ");
        tree.printInorder();
 
        System.out.println("\nPostorder traversal of binary tree is ");
        tree.printPostorder();
    }
}
