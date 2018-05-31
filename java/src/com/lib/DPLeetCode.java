package com.lib;



public class DPLeetCode {

    // Jump 1 or 2 steps. How many ways can you do n steps?
    // Note this is permutations, not combinations i.e.
    // n = 4 has [1,1,1,1], [1,1,2], [1,2,1], [2,1,1], [2,2] as solutions
    // turns out to be Fib(n). Logic: num of ways to get to (n-2) (and take a 2-step) +
    // num ways to get to (n-1) (and take a 1-step)
    public static int howManySteps(int n) {
        int a = 1, b = 1;
        while (n-- > 0) {
            int tmp = a;
            a = b;
            b = b + tmp;
        }
        return a;
    }

    // Unique paths: given m row x n col grid, start at top-left
    // How many paths exist from top-left -> bottom-right if only
    // moves allowed are right, and down
    public static int uniquePaths(int m, int n) {
        int[][] grid = new int[m][n];
        for (int j = 1; j < n; j++) { // set top row to 1's
            grid[0][j] = 1;
        }
        for (int i = 1; i < m; i++) { // set left col to 1's
            grid[i][0] = 1;
        }
        for (int i = 1; i < m; i++) { // grid[i][j] = grid[i-1][j] + grid[i][j-1]
            for (int j = 1; j < n; j++) {
                grid[i][j] = grid[i-1][j] + grid[i][j-1];
            }
        }
        return grid[m-1][n-1];
    }
}
