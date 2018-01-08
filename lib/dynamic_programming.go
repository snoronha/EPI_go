package lib

import (
    _ "log"
    "math"
)

// Use DP to keep track of LIS up until ith elem.
// O(n^2) algorithm
func LongestIncreasingSubsequenceDP(seq []int) int {
    if len(seq) == 0 { return 0; }
    if len(seq) == 1 { return 1; }
    LISarr := make([]int, len(seq))
    LISarr[0] = 1
    // for curr elem (i), look back at each prev elem (j)
    // check if prev elem (j) < curr elem (i), compute max(LISarr[j]) for 0 <= j < i
    for i := 1; i < len(seq); i++ {
        maxj := 0
        for j := 0; j < i; j++ {
            if seq[j] < seq[i] && LISarr[j] > maxj { // ith elem > jth elem
                LISarr[i] = LISarr[j] + 1
                maxj = LISarr[j]
            }
        }
        if LISarr[i] == 0 { LISarr[i] = 1; } // smallest yet, not part of any LIS
    }
    lis := 0
    for _, lisVal := range LISarr {
        if lisVal > lis {
            lis = lisVal
        }
    }
    return lis
}

func MaxProductSubarray(seq []int) int {
    maxEndingHere := 1 // max positive product ending at the current position
    minEndingHere := 1 // min negative product ending at the current position
    maxSoFar      := 1 // Initialize overall max product
 
    // Traverse through the array. Following values are maintained after the i'th iteration:
    // maxEndingHere is always 1 or some positive product ending with seq[i]
    // minEndingHere is always 1 or some negative product ending with seq[i]
    for i := 0; i < len(seq); i++ {
        // Update minEndingHere only if minEndingHere is negative
        if seq[i] > 0 {
            maxEndingHere = maxEndingHere * seq[i]
            if minEndingHere * seq[i] < 1 {
                minEndingHere = minEndingHere * seq[i]
            } else {
                minEndingHere = 1
            }
        } else if seq[i] == 0 {
            // Assumption: Output is alway greater than or equal to 1.
            maxEndingHere = 1
            minEndingHere = 1
        } else {
            // If element < 0 negative: maxEndingHere can either be 1 or > 0
            // minEndingHere can either be 1 or < 0. next minEndingHere will always be prev. 
            // maxEndingHere * seq[i] next maxEndingHere =  1 if prev minEndingHere == 1,
            // else next maxEndingHere = prev minEndingHere * seq[i]
            temp := maxEndingHere
            if minEndingHere * seq[i] > 1 {
                maxEndingHere = minEndingHere * seq[i]
            } else {
                maxEndingHere = 1
            }
            minEndingHere = temp * seq[i]
        }
        // update maxSoFar, if needed
        if maxSoFar <  maxEndingHere {
            maxSoFar = maxEndingHere
        }
    }
    return maxSoFar;
}

// 'A':1, 'B': 2, ... so "12" can be "AB" or "L" i.e. 2 ways to decode
func WaysToDecode(inp string) int {
    n     := len(inp)
    count := make([]int, n + 1) // store possible counts up until i
    count[0] = 1
    count[1] = 1
    for i := 2; i < n + 1; i++ {
        if (string(inp[i-1]) > "0") { count[i] = count[i-1] }
        if string(inp[i-2]) == "1" || ( string(inp[i-2]) == "2" && string(inp[i-1]) <= "6 ") {
            count[i] += count[i-2]
        }
    }
    return count[n]
}

// Keep track on minSoFar, and maxProfitSoFar (max(currPrice - minSoFar))
// Update each at each elem of the array
func BuySell1Stock(seq []int) int {
    if len(seq) == 0 { return 0; }
    if len(seq) == 1 { return 0; }
    minSoFar  := seq[0]
    maxProfit := 0
    for _, price := range seq {
        minSoFar  = Min(minSoFar, price)
        maxProfit = Max(maxProfit, price - minSoFar)
    }
    return maxProfit
}

func TreeMaxSumPath(root *OrderNode, res *int) int {
    if root == nil { return 0 }
    // l and r store maximum path sum going through left and
    // right child of root respectively
    l := TreeMaxSumPath(root.Left, res)
    r := TreeMaxSumPath(root.Right, res)
 
    // Max path for parent call of root. This path must include at-most one child of root
    maxSingle := Max(Max(l, r) + root.Value, root.Value)
 
    // maxTop = sum when the Node under consideration is root of the maxsum path
    // and no ancestors of root are there in max sum path
    maxTop := Max(maxSingle, l + r + root.Value)
    *res = Max(*res, maxTop) // Store the Maximum Result.
    return maxSingle;
}

// determine the min number of cuts needed to make palindromes of all strings
func MinPalindromicPartion(str string) int {
    n := len(str)
    // C[i] = Minimum number of cuts needed for palindrome partitioning of substring str[0..i]
    // P[i][j] = true if substring str[i..j] is palindrome, else false
    // Note that C[i] is 0 if P[0][i] is true
    C := make([]int, n)
    P := make([][]bool, n)
    for i := 0; i < n; i++ { P[i] = make([]bool, n) }
    
    for i := 0; i < n; i++ { // Every substring of length 1 is a palindrome
        P[i][i] = true;
    }
    // L is substring length. Build the solution in bottom up manner by
    // considering all substrings of length starting from 2 to n
    for L := 2; L <= n; L++ {
        for i := 0; i < n - L + 1; i++ { // For substring of length L, set different possible starting indexes
            j := i + L -1 // Set ending index
            // If L == 2, then compare two characters. Else
            // need to check two corner characters and value of P[i+1][j-1]
            if L == 2 {
                P[i][j] = (str[i] == str[j])
            } else {
                P[i][j] = (str[i] == str[j]) && P[i+1][j-1]
            }
        }
    }
    for i := 0; i < n; i++ {
        if P[0][i] {
            C[i] = 0;
        } else {
            C[i] = math.MaxInt32
            for j := 0; j < i; j++ {
                if P[j+1][i] && 1 + C[j] < C[i] {
                    C[i] = 1 + C[j]
                }
            }
        }
    }
    return C[n-1] // Return the min cut value for complete string. i.e., str[0..n-1]
}

// Compute C[i][j] = min(C[i-1][j], C[i][j-1]) + M[i][j]
func MinSumMatrixPath(M [][]int) []int {
    n := len(M)
    C := make([][]int, n) // Min cost to (i, j)
    for i := 0; i < n; i++ { C[i] = make([]int, n) }

    C[0][0] = M[0][0]
    for i := 1; i < n; i++ { C[i][0] = M[i][0] + C[i-1][0] } // initialize top row
    for j := 1; j < n; j++ { C[0][j] = M[0][j] + C[0][j-1] } // initialize left col

    // Compute C row by row
    for i := 1; i < n; i++ {
        for j := 1; j < n; j++ {
            C[i][j] = M[i][j] + Min(C[i-1][j], C[i][j-1])
        }
    }
    // To get minPath, trace back from the end C[n-1][n-1] (don't start from C[0][0])
    minPath := []int{}
    i := n - 1; j := n - 1
    for i >= 0 && j >= 0 {
        minPath = append([]int{M[i][j]}, minPath...) // prepend (because we're going backwards)
        if i == 0 { // reached top row, keep going left
            j--
            continue
        }
        if j == 0 { // reached leftmost col, keep going up
            i--
            continue
        }
        if C[i-1][j] == C[i][j] - M[i][j] { // minPath goes up
            i--
        } else if C[i][j-1] == C[i][j] - M[i][j] { // minPath goes left
            j--
        }
    }
    return minPath
}

// minJmps[i] is the min jumps needed from 0 -> i
// For each i, iterate over j in (0, i - 1), and find Min( minJmps[i], minJumps[j] + 1 )
func MinJumps(jmp []int) int {
    n := len(jmp)
    minJmps := make([]int, n)
    for i, _ := range minJmps { minJmps[i] = math.MaxInt32 }
    minJmps[0] = 0
    for i := 0; i < n; i++ {
        for j := 0; j < i; j++ {
            if i <= j + jmp[j] && minJmps[j] != math.MaxInt32 {
                minJmps[i] = Min(minJmps[i], minJmps[j] + 1);
                continue
            }
        }
    }
    return minJmps[n-1]
}

// Given a jump array, can user make it to the end?
// For each i find the max j (j >=i ) that user can reach
// If max j >= n - 1, user can make it, else not
func JumpGame(jmp []int) bool {
    n := len(jmp)
    maxReach := make([]int, n)
    maxReach[0] = jmp[0]
    for i := 1; i < n; i++ {
        if maxReach[i-1] >= i { // if ith elem is reachable
            maxReach[i] = Max(i + jmp[i], maxReach[i-1])
        } else { // ith elem NOT reachable
            return false
        }
    }
    return maxReach[n-1] >= n - 1
}

// Someone can climb 1 stair or 2 stairs at a time. How many WAYS can the person climb n stairs?
// Turns out to be a Fibonacci sequence C(i) = C(i-1) + C(i-2), C(1) = 1, C(2) = 2
func StairsProblem(n int) int {
    // Can be optimized to use O(1) storage, but we use O(n) here
    C := make([]int, n)
    C[0] = 1; C[1] = 2
    for i := 2; i < n; i++ {
        C[i] = C[i-1] + C[i-2]
    }
    return C[n-1]
}

// Given bool literals {T, F} and binary operators {&, |, ^}
// how many ways can order of operators be arranged to give final answer T?
// e.g. T^F&T becomes T for ((T^F)&T) and (T^(F&T)), so numWays = 2
func EvaluateExpressionToTrue(expr string) {
    
}
