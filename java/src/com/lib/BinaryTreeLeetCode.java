package com.lib;

import java.util.ArrayList;
import java.util.Stack;

public class BinaryTreeLeetCode {

    public Node root; // Root of Binary Tree

    public BinaryTreeLeetCode() {
        root = null;
    }

    public static void Preorder(Node n, ArrayList<Integer> path) {
        if (n == null) return;
        path.add(n.key);
        Preorder(n.left, path);
        Preorder(n.right, path);
    }

    // iterative preorder traversal using a Stack
    public static ArrayList<Integer> PreorderIterate(Node n) {
        ArrayList<Integer> L = new ArrayList<Integer>();
        Stack<Node> stack = new Stack<Node>();
        if (n != null) stack.push(n);
        while (!stack.empty()) {
            Node curr = stack.pop();
            L.add(curr.key);
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        return L;
    }

    // iterative inorder traversal using a Stack
    // if (curr.left != null) { stack.push(curr); curr = curr.left(); }
    public static ArrayList<Integer> InorderIterate(Node n) {
        ArrayList<Integer> L = new ArrayList<Integer>();
        Stack<Node> stack = new Stack<Node>();
        Node curr = n;
        while (curr != null) { // populate stack with curr -> curr.left as far as possible
            stack.push(curr);
            curr = curr.left;
        }
        while (!stack.empty() || curr != null) {
            if (curr != null) stack.push(curr);
            while (curr != null) {
                if (curr.left != null) stack.push(curr.left);
                curr = curr.left; // do even if curr.left == null
            }
            // pop stack, check for right child
            Node popped = stack.pop();
            L.add(popped.key);
            curr = popped.right; // could be null
        }
        return L;
    }

    public static void Inorder(Node n, ArrayList<Integer> path) {
        if (n == null) return;
        Inorder(n.left, path);
        path.add(n.key);
        Inorder(n.right, path);
    }

    public static void Postorder(Node n, ArrayList<Integer> path) {
        if (n == null) return;
        Postorder(n.left, path);
        Postorder(n.right, path);
        path.add(n.key);
    }

    public static boolean isValid(Node n) {
        if (n == null) return true;
        if (n.left == null && n.right == null) return true;
        if (n.left == null) {
            return n.key < n.right.key && isValid(n.right);
        }
        if (n.right == null) {
            return n.key > n.left.key && isValid(n.left);
        }
        return n.key > n.left.key && n.key < n.right.key && isValid(n.left) && isValid(n.right);
    }
}
