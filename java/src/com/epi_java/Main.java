package com.epi_java;

import com.lib.util;
import com.lib.ArraysEPI;
import com.lib.BinaryTree;
import com.lib.LinkedList;
import com.lib.Node;
import com.lib.PrimitiveTypes;

import com.lib.ArraysLeetCode;
import com.lib.BinaryTreeLeetCode;
import com.lib.DPLeetCode;
import com.lib.LinkedListLeetCode;
import com.lib.PrimitiveTypesLeetCode;
import com.lib.RecursionLeetCode;
import com.lib.StringsLeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // testUtil();
        // testLinkedList();
        // testBinaryTree();
        // testPrimitiveTypes(); // EPI: Chapter 4
        // testArrays();         // EPI: Chapter 5

        testLeetCode();       // LeetCode top 150 interview problems
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
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
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

        // test isPalindromeNumber
        System.out.printf("isPalindromeNumber of %d: %s\n", 23569, PrimitiveTypes.isPalindromeNumber(23569));
        System.out.printf("isPalindromeNumber of %d: %s\n", 235696532, PrimitiveTypes.isPalindromeNumber(235696532));
    }

    public static void testArrays() {
        // test dutchFlagPartition
        List<ArraysEPI.Color> A = new ArrayList<ArraysEPI.Color>();
        A.add(ArraysEPI.Color.BLUE);
        A.add(ArraysEPI.Color.WHITE);
        A.add(ArraysEPI.Color.RED);
        A.add(ArraysEPI.Color.BLUE);
        A.add(ArraysEPI.Color.WHITE);
        A.add(ArraysEPI.Color.RED);
        A.add(ArraysEPI.Color.WHITE);
        A.add(ArraysEPI.Color.RED);
        A.add(ArraysEPI.Color.RED);
        ArraysEPI.dutchFlagPartition(1, A);
        System.out.printf("dutchFlagPartition: %s\n", A.toString());

        // test plusOne for unlimited precision number (represented as array)
        List<Integer> B = new ArrayList<>(Arrays.asList(3, 2, 4, 4, 9, 9, 9));
        System.out.printf("plusOne: %s\n", ArraysEPI.plusOne(B).toString());

        // test arbitrary precision multiplication
        List<Integer> C = new ArrayList<>(Arrays.asList(-3, 2, 4, 4, 9, 9, 9));
        List<Integer> D = new ArrayList<>(Arrays.asList(4, 7, 0, 2, 1, 2, 3));
        System.out.printf("multiply: %s * %s = %s\n", C.toString(), D.toString(), ArraysEPI.multiply(C, D).toString());

        // test canReachEnd of List
        List<Integer> E = new ArrayList<>(Arrays.asList(2, 4, 1, 1, 0, 2, 3));
        System.out.printf("canReachEnd: %s, %s\n", E.toString(), ArraysEPI.canReachEnd(E));

        // test deleteDuplicates from sorted list with dups
        List<Integer> F = new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2, 3, 5, 5, 6, 7, 7));
        System.out.printf("deleteDuplicates: before: %s, index = %d, after: %s\n", F.toString(), ArraysEPI.deleteDuplicates(F), F.toString());

        // test computeMaxProfit i.e. buy/sell stock once
        List<Double> G = new ArrayList<Double>(Arrays.asList(310.0, 315.0, 275.0, 295.0, 260.0, 270.0, 290.0, 230.0, 255.0, 250.0));
        System.out.printf("computeMaxProfit: %s, maxProfit = %.1f\n", G.toString(), ArraysEPI.computeMaxProfit(G));

        // test buyAndSellStockTwice i.e. buy/sell stock twice
        List<Integer> H = new ArrayList<Integer>(Arrays.asList(12, 11, 13, 9, 12, 8, 14, 13, 15));
        System.out.printf("buyAndSellStockTwice: %s, maxProfit = %d\n", H.toString(), ArraysEPI.buyAndSellStockTwice(H));
    }

    public static void testLeetCode() {
        // test ArraysLeetCode.twoSum - see if two numbers in array add to target
        ArrayList<Integer> A = new ArrayList<>(Arrays.asList(2, 7, 11, 15, 3, 6, 8));
        System.out.printf("twoSum: %s\n", ArraysLeetCode.twoSum(A, 10).toString());

        // test LinkedListLeetCode.addTwoNumbers - add numbers represented as reversed LLs
        ArrayList<Integer> listB = new ArrayList<Integer>(Arrays.asList(7, 8, 1, 5));
        LinkedListLeetCode B = new LinkedListLeetCode(listB);
        ArrayList<Integer> listC = new ArrayList<Integer>(Arrays.asList(9, 6, 6, 4));
        LinkedListLeetCode C = new LinkedListLeetCode(listC);
        System.out.printf("List B: %s, List C: %s, sum: %s\n", B.toList(), C.toList(),
                LinkedListLeetCode.addTwoNumbers(B, C).toList());

        // compute longest substring
        String s = "aaababcaa"; // "cabacdc";
        System.out.printf("String: %s, MaxSubstring: %s\n", s, StringsLeetCode.longestSubstring(s));

        // compute longest palindrome
        s = "abacdffdcabz";
        System.out.printf("String: %s, LongPalindrome: %s\n", s, StringsLeetCode.longestPalindrome(s));

        // reverse integer
        int num = -74563;
        System.out.printf("int: %d, reverse: %d\n", num, PrimitiveTypesLeetCode.reverseInteger(num));

        // atoi
        s = "4193 with words";
        System.out.printf("str: %s, atoi: %d\n", s, PrimitiveTypesLeetCode.atoi(s));

        // regex match
        s = "aaabbbdacca";
        String p = "a*.*bdac*a";
        System.out.printf("str: %s, p: %s, match: %s\n", s, p, StringsLeetCode.regexMatch(s, p));

        // maximumWater
        ArrayList<Integer> listD = new ArrayList<Integer>(Arrays.asList(5, 8, 3, 9, 4, 2));
        System.out.printf("water: %s, maxWater: %s\n", listD.toString(), ArraysLeetCode.maximumWater(listD));

        // Roman to int
        s = "XCIX"; // "MCMXCIV";
        System.out.printf("roman: %s, int: %d\n", s, StringsLeetCode.romanToInt(s));

        // Longest common prefix
        ArrayList<String> listE = new ArrayList<String>(Arrays.asList("flower", "flow", "flight"));
        System.out.printf("list: %s, prefix: %s\n", listE, StringsLeetCode.longestCommonPrefix(listE));

        // Letter combinations of phone number
        s = "99";
        ArrayList<String> combos = new ArrayList<String>();
        System.out.printf("num: %s, combos: %s\n", s, RecursionLeetCode.phoneCombinations(s, combos));

        // Matching parentheses
        s = "([]){{}}";
        System.out.printf("input: %s, valid: %s\n", s, StringsLeetCode.validParentheses(s));

        // generate parentheses
        ArrayList<String> listF = new ArrayList<String>();
        StringsLeetCode.generateParentheses(listF, "", 0, 0, 4);
        System.out.printf("num: %d, parentheses: %s\n", 4, listF);

        // isValid Sudoku puzzle
        int puzzle1[][] = {
                {0,0,0,0,3,8,0,0,0},
                {0,9,0,0,0,7,0,1,0},
                {0,6,0,0,0,0,5,0,8},
                {0,4,0,3,0,1,0,0,0},
                {0,0,1,0,4,0,6,0,0},
                {0,0,0,8,0,9,0,2,0},
                {7,0,8,0,0,0,0,3,0},
                {0,2,0,7,0,0,0,6,0},
                {0,0,0,4,2,0,0,0,0}
        };
        System.out.printf("puzzle: %s, isValidSudoku: %s\n", Arrays.deepToString(puzzle1), ArraysLeetCode.isValidSudoku(puzzle1));
        int [][] solved1 = ArraysLeetCode.solveSudoku(puzzle1);
        for (int i = 0; i < solved1.length; i++) {
            System.out.printf("%s\n", Arrays.toString(solved1[i]));
        }

        int[] D = new int[]{1, 2, 3, 3, 4, 5, 5, 5, 6, 6, 6, 6, 7};
        System.out.printf("arr: %s, removedDups: %s\n", Arrays.toString(D), Arrays.toString(ArraysLeetCode.removeDuplicates(D)));

        int[] E = new int[]{11, 12, 13, 14, 17, 18, 19, 20, 21, 22, 23, 24, 25, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.printf("arr: %s, pivot: %d\n", Arrays.toString(E), ArraysLeetCode.findPivot(E));

        int[] F = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 17, 18, 19, 20, 21, 22};
        System.out.printf("arr: %s, target: %d\n", Arrays.toString(F), ArraysLeetCode.findTarget(F, 10));

        System.out.printf("arr: %s, searched target: %d\n", Arrays.toString(E), ArraysLeetCode.searchRotatedSorted(E, 3));

        int[] G = new int[]{-1, 0, 1, 2, -1, -4};
        System.out.printf("arr: %s, 3Sums: %s\n", Arrays.toString(G), ArraysLeetCode.threeSum(G).toString());

        int rotMat[][] = {
                {0,  1, 2, 3},
                {4,  5, 6, 7},
                {8,  9,10,11},
                {12,13,14,15}
        };
        System.out.printf("arr: %s\nrot: %s\n", Arrays.deepToString(rotMat), Arrays.deepToString(ArraysLeetCode.rotateMatrix(rotMat)));

        System.out.printf("n:5, numWays:%d\n", DPLeetCode.howManySteps(5));

        int[] H = new int[]{2, 0, 2, 1, 1, 0, 1, 2, 0, 0, 2, 1, 1};
        System.out.printf("arr: %s, partitioned: %s\n", Arrays.toString(H), Arrays.toString(ArraysLeetCode.dutchFlagPartition(H)));

        ArrayList<Integer> I = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        ArrayList<ArrayList<Integer>> POWER = new ArrayList<ArrayList<Integer>>();
        RecursionLeetCode.powerSet(I, POWER);
        System.out.printf("arr: %s, power set: %s\n", I, POWER);

        String[][] maze = new String[][]{
                {"A","B","C","E"},
                {"S","F","C","S"},
                {"A","D","E","E"}
        };
        String word = "SECBFC";
        System.out.printf("maze: %s, word: %s, found: %s\n", maze, word, ArraysLeetCode.wordSearch(maze, word));

        int[][] MATRIX = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.printf("matrix: %s\nspiral: %s\n", MATRIX, Arrays.toString(ArraysLeetCode.spiralOrder(MATRIX)));

        // number of unique paths in a grid (DP)
        System.out.printf("m x n: %d x %d, uniquePaths: %d\n", 7, 3, DPLeetCode.uniquePaths(7, 3));

        // minimum window string
        String S = "ADOBECODEBABZCNC", T = "ABC";
        System.out.printf("S: %s, T: %s, minWindow: %s\n", S, T, StringsLeetCode.minimumWindowSubstring(S, T));

        // Inorder and Preorder on a binary tree
        BinaryTreeLeetCode BT1 = new BinaryTreeLeetCode();
        BT1.root = new Node(4); BT1.root.left = new Node(2);
        BT1.root.right = new Node(5); BT1.root.left.left = new Node(1);
        BT1.root.left.right = new Node(3);
        ArrayList<Integer> INpath = new ArrayList<Integer>();
        BinaryTreeLeetCode.Inorder(BT1.root, INpath);
        ArrayList<Integer> PREpath = new ArrayList<Integer>();
        BinaryTreeLeetCode.Preorder(BT1.root, PREpath);
        ArrayList<Integer> PREpathIter = BinaryTreeLeetCode.PreorderIterate(BT1.root);
        ArrayList<Integer> INpathIter  = BinaryTreeLeetCode.InorderIterate(BT1.root);
        System.out.printf("INpath: %s, PREpath: %s, PREpathIter: %s, INpathIter: %s\n", INpath, PREpath, PREpathIter, INpathIter);

        // Check whether valid binary tree
        System.out.printf("isValidTree: %s\n", BinaryTreeLeetCode.isValid(BT1.root));
    }

}
