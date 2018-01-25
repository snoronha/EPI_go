// Java program for different tree traversals

package com.lib;

// Class containing left and right child of current node and key value
public class Node {
    public int key;
    public Node left, right;
    public Node next, previous;

    public Node() {
        key  = 0;
        left = right = null;
        next = previous = null;
    }

    public Node(int item) {
        key  = item;
        left = right = null;
        next = previous = null;
    }
}

