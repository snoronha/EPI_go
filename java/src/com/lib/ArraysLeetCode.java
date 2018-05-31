// Java class for Array problems from LeetCode's top 150 interview problems

package com.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Stack;

public class ArraysLeetCode {

    // Given an array of integers, return indices of the two numbers
    // such that they add up to a specific target.
    public static List<Integer> twoSum(List<Integer> nums, int target) {
        List<Integer> result = new ArrayList<>(Arrays.asList());
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        // build a hash of {val: index}
        for(int i = 0; i < nums.size(); i++) {
            hash.put(nums.get(i), i);
        }
        for (Integer val : hash.keySet()) {
            if (hash.containsKey(target - val) && hash.get(val) >= 0) { // >= 0 for not yet processed
                result.add(hash.get(val));
                result.add(hash.get(target - val));
                hash.put(val, -1);           // mark as processed
                hash.put(target - val, -1);  // mark as processed
            }
        }
        return result;
    }

    // Given walls of different integer height, compute max water between any two walls
    // Outline: start with 0, n-1 as candidate (widest walls);
    // Remove min(a[0], a[n-1]) from considered set i.e. either i++ or j--
    public static List<Integer> maximumWater(List<Integer> A) {
        List<Integer> result = new ArrayList<>(Arrays.asList(-1, -1, 0));
        int i  = 0, j = A.size() - 1;
        int maxWater = 0, water = 0;
        while (i < j) {
            water = Math.min(A.get(i), A.get(j)) * (j - i);
            if (water > maxWater) {
                maxWater = water;
                result.set(0, i); result.set(1, j); result.set(2, maxWater);
            }
            if (A.get(i) < A.get(j)) {
                i++;
            } else {
                j--;
            }
        }
        return  result;
    }

    // remove duplicates from sorted array
    public static int[] removeDuplicates(int[] A) {
        int ptr = 0, prev = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != prev) {
                A[ptr] = A[i];
                ptr++;
            }
            prev = A[i];
        }
        for (int i = ptr; i < A.length; i++) {
            A[i] = Integer.MIN_VALUE;
        }
        return A;
    }

    // Check whether a Suodku puzzle is valid
    public static boolean isValidSudoku(int[][] puzzle) {

        // Check each row
        for (int i = 0; i < 9; i++) {
            HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] > 0) {
                    if (map.containsKey(puzzle[i][j])) return false;
                    else map.put(puzzle[i][j], true);
                }
            }
        }
        // Check each col
        for (int j = 0; j < 9; j++) {
            HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
            for (int i = 0; i < 9; i++) {
                if (puzzle[i][j] > 0) {
                    if (map.containsKey(puzzle[i][j])) return false;
                    else map.put(puzzle[i][j], true);
                }
            }
        }
        // Check each square
        for (int si = 0; si < 9; si += 3) {
            for (int sj = 0; sj < 9; sj += 3) {
                HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
                for (int i = 0; i < 9 / 3; i++) {
                    for (int j = 0; j < 9 / 3; j++) {
                        if (puzzle[si+i][sj+j] > 0) {
                            if (map.containsKey(puzzle[si + i][sj + j])) return false;
                            else map.put(puzzle[si + i][sj + j], true);
                        }
                    }
                }
            }
        }
        return true;
    }

    // Solve Sudoku given a valid puzzle
    public static int[][] solveSudoku(int[][] puzzle) {
        long startTime = System.nanoTime();
        int[][] shadow = new int[9][9];
        // initialize shadow <- puzzle
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                shadow[i][j] = puzzle[i][j];
            }
        }
        int i = 0, j = 0;
        int move[] = new int[2];
        while (i < 9 && j < 9) {
            // if number exists in initial puzzle, we know it's correct
            if (puzzle[i][j] > 0) {
                // System.out.printf("\tPuzzle[%d][%d] = %d\n", i, j, puzzle[i][j]);
                move = moveSudoku(i, j, 1); i = move[0]; j = move[1];
            } else {
                boolean backtrack = true;
                for (int k = shadow[i][j] + 1; k <= 9; k++) {
                    if (k == 0) continue;
                    shadow[i][j] = k;
                    if (isValidSudoku(shadow)) {
                        backtrack = false;
                        move = moveSudoku(i, j, 1); i = move[0]; j = move[1];
                        break;
                    } else {
                        shadow[i][j] = puzzle[i][j];
                    }
                }
                if (backtrack) {
                    // System.out.printf("\tBACKTRACK[%d][%d] = %d\n", i, j, shadow[i][j]);
                    shadow[i][j] = puzzle[i][j];
                    move = moveSudoku(i, j, -1); i = move[0]; j = move[1];
                    while (puzzle[i][j] > 0) { // skip backward over numbers in initial puzzle
                        shadow[i][j] = puzzle[i][j];
                        move = moveSudoku(i, j, -1); i = move[0]; j = move[1];
                    }
                }
            }
        }
        System.out.printf("Executed in: %d ms\n", (System.nanoTime() - startTime)/1000000);
        return shadow;
    }

    // dir == 1 -> forward, -1 -> backward
    private static int[] moveSudoku(int i, int j, int dir) {
        int res[] = new int[2];
        if (dir == 1) {
            j++;
            if (j == 9) {
                if (i < 8) { i++; j = 0; }
            }
            res[0] = i; res[1] = j;
        } else {
            j--;
            if (j < 0) {
                if (i > 0) { i--; j = 8; }
            }
            res[0] = i; res[1] = j;
        }
        return res;
    }

    // Search in a rotated sorted array
    // find the pivot element and then search in the sorted section containing the target
    public static int searchRotatedSorted(int[] A, int target) {
        int pivot = findPivot(A), tindex;
        int[] partial;
        if (target >= A[0] && target <= A[pivot]) {
            partial = Arrays.copyOfRange(A, 0, pivot+1);
            tindex = findTarget(partial, target);
        } else {
            partial = Arrays.copyOfRange(A, pivot+1, A.length);
            tindex = findTarget(partial, target);
            if (tindex >= 0) tindex += pivot;
        }
        return tindex;
    }

    public static int findTarget(int[] A, int target) {
        int left = 0, right = A.length - 1, mid = A.length/2;
        boolean found = false;
        while (!found) {
            // System.out.printf("Enter: left=%d mid=%d right=%d\n", left, mid, right);
            if (target == A[mid]) {
                found = true;
                break;
            } else if (left == mid) { // target does not exist
                return -1;
            }
            if (target < A[mid]) {
                right = mid;
                mid = left + (mid - left)/2;
            } else {
                left = mid;
                mid = mid + (right - mid)/2;
            }
            // System.out.printf("Exit:  left=%d mid=%d right=%d\n\n", left, mid, right);
        }
        return mid;
    }

    // binary search to find pivot element for a rotated sorted array
    public static int findPivot(int[] A) {
        int left = 0, right = A.length - 1, mid = A.length/2;
        boolean found = false;
        while (!found) {
            if (left >= right - 1) {
                found = true;
                break;
            }
            if (A[left] > A[mid]) {
                right = mid;
                mid = left + (mid - left)/2;
            } else {
                left = mid;
                mid = mid + (right - mid)/2;
            }
        }
        return mid;
    }

    // solve threeSum i.e. a + b + c = 0 in array
    public static ArrayList<int[]> threeSum(int[] A) {
        ArrayList<int[]> res = new ArrayList<int[]>();
        Arrays.sort(A);
        HashMap<Integer, ArrayList<Integer>> map1 = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<String, Boolean> uniqueTriple = new HashMap<String, Boolean>();

        for (int i = 0; i < A.length; i++) { // build map1, hash of single values
            if (map1.containsKey(A[i])) {
                ArrayList<Integer> l = map1.get(A[i]);
                l.add(i);
                map1.put(A[i],l);
            } else {
                map1.put(A[i], new ArrayList(Arrays.asList(i)));
            }
        }
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                // A[j] >= A[i]
                int twoSumVal = A[i] + A[j];
                if (map1.containsKey(-twoSumVal)) {
                    ArrayList<Integer> ks = map1.get(-twoSumVal);
                    for (int k : ks) {
                        if (i != k && j != k) {
                            if (k < i) {
                                String s = Integer.toString(A[k]) + Integer.toString(A[i]) + Integer.toString(A[j]);
                                if (!uniqueTriple.containsKey(s)) {
                                    uniqueTriple.put(s, true);
                                    System.out.printf("FOUND: (%d, %d, %d) = (%d, %d, %d)\n", i, j, k, A[i], A[j], A[k]);
                                    res.add(new int[]{A[k], A[i], A[j]});
                                }
                            } else if (k > i && k < j) {
                                String s = Integer.toString(A[i]) + Integer.toString(A[k]) + Integer.toString(A[j]);
                                if (!uniqueTriple.containsKey(s)) {
                                    uniqueTriple.put(s, true);
                                    System.out.printf("FOUND: (%d, %d, %d) = (%d, %d, %d)\n", i, j, k, A[i], A[j], A[k]);
                                    res.add(new int[]{A[i], A[k], A[j]});
                                }
                            } else {
                                String s = Integer.toString(A[i]) + Integer.toString(A[j]) + Integer.toString(A[k]);
                                if (!uniqueTriple.containsKey(s)) {
                                    uniqueTriple.put(s, true);
                                    System.out.printf("FOUND: (%d, %d, %d) = (%d, %d, %d)\n", i, j, k, A[i], A[j], A[k]);
                                    res.add(new int[]{A[i], A[j], A[k]});
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    // Rotate a 2D matrix in place
    // (i, j) -> (j, n-i-1) -> (n-i-1, n-j-1) -> (n-j-1, i)
    // closure among sets of 4 elements each
    public static int[][] rotateMatrix(int[][] A) {
        int si = 0, sj = 0;
        for (int n = A.length; n > 0; n -= 2) {
            int i = 0;
            for (int j = 0; j < n - 1; j++) { // top row
                int tmp = A[i+si][j+sj];
                A[i+si][j+sj] = A[n-j-1+si][i+sj];
                A[n-j-1+si][i+sj] = A[n-i-1+si][n-j-1+sj];
                A[n-i-1+si][n-j-1+sj] = A[j+si][n-i-1+sj];
                A[j+si][n-i-1+sj] = tmp;
            }
            si++; sj++;
        }
        return A;
    }

    // Dutch Flag partition.
    // Input [2,0,2,1,1,0] -> [0,0,1,1,2,2]
    public static int[] dutchFlagPartition(int[] A) {
        // push all 2's to the end
        int ptr1 = 0, ptr2 = A.length - 1;
        while (ptr1 < ptr2) {
            if (A[ptr2] == 2) {
                ptr2--;
            } else if (A[ptr1] == 2) {
                int tmp = A[ptr1]; A[ptr1] = A[ptr2]; A[ptr2] = tmp;
                ptr2--;
            } else {
                ptr1++;
            }
        }
        // push all 0's to left
        ptr1 = 0; ptr2 = A.length - 1;
        while (ptr1 < ptr2) {
            if (A[ptr1] == 0) {
                ptr1++;
            } else if (A[ptr2] == 0) {
                int tmp = A[ptr1]; A[ptr1] = A[ptr2]; A[ptr2] = tmp;
                ptr1++;
            } else {
                ptr2--;
            }
        }
        return A;
    }

    // Word search: find a word moving horizontally or vertically
    // without reusing squares. Match each square to see if it's the start
    // of the word. If so do a DFS from that point. If success return true
    // else move on to the next occurrence of start letter
    public static boolean wordSearch(String[][]maze, String word) {
        for (int si = 0; si < maze.length; si++) {
            for (int sj = 0; sj < maze[0].length; sj++) {
                if (maze[si][sj].equals(word.substring(0, 1))) { // first letter matches
                    Stack<int[]> stack = new Stack<int[]>();
                    HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
                    stack.push(new int[]{si, sj, 0}); // push first elem onto stack
                    while (!stack.empty()) {
                        // System.out.printf("STACK: %s\n", Arrays.deepToString(stack.toArray()));
                        int[] elem = stack.pop(); // elem = [i, j, index in word]
                        int i = elem[0], j = elem[1], wordIdx = elem[2];
                        if (elem[2] < word.length() - 1) { // more letters to go
                            // Look at top, bottom, left, right
                            if (i > 0) {
                                String top = Integer.toString(i - 1) + Integer.toString(j);
                                if (!visited.containsKey(top) && word.substring(wordIdx + 1, wordIdx + 2).equals(maze[i - 1][j])) {
                                    stack.push(new int[]{i - 1, j, wordIdx + 1});
                                }
                            }
                            if (i < maze.length - 1) {
                                String bottom = Integer.toString(i + 1) + Integer.toString(j);
                                if (!visited.containsKey(bottom) && word.substring(wordIdx + 1, wordIdx + 2).equals(maze[i + 1][j])) {
                                    stack.push(new int[]{i + 1, j, wordIdx + 1});
                                }
                            }
                            if (j > 0) {
                                String left = Integer.toString(i) + Integer.toString(j - 1);
                                if (!visited.containsKey(left) && word.substring(wordIdx + 1, wordIdx + 2).equals(maze[i][j - 1])) {
                                    stack.push(new int[]{i, j - 1, wordIdx + 1});
                                }
                            }
                            if (j < maze[0].length - 1) {
                                String right = Integer.toString(i) + Integer.toString(j + 1);
                                if (!visited.containsKey(right) && word.substring(wordIdx + 1, wordIdx + 2).equals(maze[i][j + 1])) {
                                    stack.push(new int[]{i, j + 1, wordIdx + 1});
                                }
                            }
                        } else {
                            return true;
                        }
                        visited.put(Integer.toString(i) + Integer.toString(j), true);
                    }
                }
            }
        }
        return false;
    }

    // spiral order in a matrix
    public static int[] spiralOrder(int[][] M) {
        int m = M.length, n = M[0].length; // m rows x n cols
        int[] S = new int[m*n];
        int si = 0, sj = 0, sidx = 0;
        while (m > 0 && n > 0) {
            for (int j = 0; j < n - 1; j++) { // top row except last elem
                S[sidx] = M[0+si][j+sj]; sidx++;
            }
            for (int i = 0; i < m - 1; i++) { // right col except bottom elem
                S[sidx] = M[i+si][n-1+sj]; sidx++;
            }
            for (int j = n - 1; j > 0; j--) { // bottom row except first elem, backwards
                S[sidx] = M[m-1+si][j+sj]; sidx++;
            }
            for (int i = m - 1; i > 0; i--) { // left col except first elem, upwards
                S[sidx] = M[i+si][0+sj]; sidx++;
            }
            m -= 2; n -= 2;
            si++; sj++;
        }
        return S;
    }
}
