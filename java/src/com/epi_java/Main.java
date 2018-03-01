package com.epi_java;

import com.lib.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        testUtil();
        testLinkedList();
        testBinaryTree();
        testPrimitiveTypes();
    }

    // Test Util class
    public static void testUtil() {
        // test getRandom
        int rand = util.getRandom(1, 50);
        System.out.printf("Random number: %d\n", rand);

        // test shuffle (ArrayList of Integer)
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        util.shuffle(list);
        System.out.printf("Random number: %s\n", list);
    }

    public static void testLinkedList() {
        ArrayList<Integer> listA = new ArrayList<>();
        for (int i = 0; i < 10; i++) listA.add(i);
        // util.shuffle(listA);
        LinkedList A = new LinkedList(listA);
        System.out.printf("List A: %s\n", A.toList());

        // Test reverse list
        // A.reverse();
        // System.out.printf("List Reversed: %s\n", A.toList());

        ArrayList<Integer> listB = new ArrayList<>();
        for (int i = 5; i < 15; i++) listB.add(i);
        // util.shuffle(listB);
        LinkedList B = new LinkedList(listB);
        System.out.printf("List B: %s\n", B.toList());

        // Test merge lists
        LinkedList C = LinkedList.merge(A, B);
        System.out.printf("List C: %s\n", C.toList());
    }

    public static void testBinaryTree() {
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

    public static void testPrimitiveTypes() {
        // test countBits
        System.out.printf("\nnumbits in %d = %d\n", 127, PrimitiveTypes.countBits(127));

        // test parity, parityQuick
        System.out.printf("parity of %d = %d\n", 14, PrimitiveTypes.parity(14));
        System.out.printf("parity of %d = %d\n", 21, PrimitiveTypes.parityQuick(21));

        // test swapBits
        System.out.printf("swapBits of %d (i=%d, j=%d): %d\n", 23569, 0, 33, PrimitiveTypes.swapBits(23569, 0, 33));
    }
}
