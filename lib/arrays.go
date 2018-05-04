package lib

import (
    _ "log"
    "sort"
    "strconv"
)

// add two arbitrary precision numbers
func AddNumbers(a []int, b []int) []int {
    la := len(a); lb := len(b)
    l  := la
    if lb > la {
        l = lb
    }
    c := make([]int, l)
    for i := 0; i < l; i++ {
        c[l-1-i] = 0
        if la-1-i >= 0 {
            c[l-1-i] += a[la-1-i]
        }
        if lb-1-i >= 0 {
            c[l-1-i] += b[lb-1-i]
        }
    }
    for i := l-1; i > 0; i-- {
        if c[i] >= 10 {
            c[i]   -= 10  // handle carry
            c[i-1] += 1
        }
    }
    if c[0] >= 10 {
        c[0] -= 10
        c     = append([]int{1}, c...) // prepend 1 in MSB i.e. as new c[0]
    }
    return c
}

func DeleteArrayElement(seq []int, deleteVal int) []int {
    jump := 0
    for i, val := range seq {
        if val == deleteVal {
            jump++
        } else {
            seq[i - jump] = val
        }
    }
    l := len(seq) - 1
    for i := 0; i < jump; i++ {
        seq[l - i] = -1
    }
    return seq
}

// Merge arrays, take middle element(s), compute median
// Doesn't actually merge arrays. Just does the merge operation and counts
func MedianOfTwoArrays(seq1 []int, seq2 []int) []int {
    median := []int{}
    var prevVal, currVal int
    tlen := len(seq1) + len(seq2)
    ptr1 := 0; ptr2 := 0
    for i := 0; i < tlen/2 + 1; i++ {
        if ptr1 >= len(seq1) {
            currVal = seq2[ptr2]
            ptr2++
        } else if ptr2 >= len(seq2) {
            currVal = seq1[ptr1]
            ptr1++
        } else {
            if seq1[ptr1] < seq2[ptr2] {
                currVal = seq1[ptr1]
                ptr1++
            } else {
                currVal = seq2[ptr2]
                ptr2++
            }
        }
        if i != tlen/2 {
            prevVal = currVal
        }
    }
    if tlen % 2 == 1 {
        median = []int{currVal}
    } else {
        median = []int{prevVal, currVal}
    }
    return median
}

// Use a hash to store number of occurrences
func FindDuplicates(seq []int) map[int]int {
    dupMap := map[int]int{}
    seqMap := map[int]int{}
    for _, val := range seq {
        if _, ok := seqMap[val]; !ok { seqMap[val] = 0; } // initialize map[val] = 0
        seqMap[val]++
    }
    for k, v := range seqMap {
        if v > 1 { dupMap[k] = v; }
    }
    return dupMap
}

// Rotate an matrix by 90 degrees in place with no extra space
func RotateMatrix(matrix [][]int) [][]int {
    N := len(matrix)
    for i := 0; i < N / 2; i++ {
        // Consider elements in group of 4 in current square
        for  j := i; j < N - i - 1; j++ {
            temp                := matrix[i][j]         // store current cell in temp variable
            matrix[i][j]         = matrix[N-1-j][i]     // copy value from bottom-left to top
            matrix[N-1-j][i]     = matrix[N-1-i][N-1-j] // copy value from bottom-right to bottom-left
            matrix[N-1-i][N-1-j] = matrix[j][N-1-i]     // copy value from top-right to bottom-right
            matrix[j][N-1-i]     = temp                 // assign temp to top-right
        }
    }
    return matrix
}

func SpiralOrder(matrix [][]int) []int {
    order := []int{}
    N     := len(matrix)
    i := 0; j := 0
    for l := N-1; l > 0; l -= 2 {
        for k := 0; k < l; k++ {
            order = append(order, matrix[i][j]); j++; // go right
        }
        for k := 0; k < l; k++ {
            order = append(order, matrix[i][j]); i++; // go down
        }
        for k := 0; k < l; k++ {
            order = append(order, matrix[i][j]); j--; // go left
        }
        for k := 0; k < l; k++ {
            order = append(order, matrix[i][j]); i--; // go up
        }
        i++; j++
    }
    if N % 2 == 1 { // add center element
        order = append(order, matrix[i][j])
    }
    return order
}

// Largest number formable from numbers in an array
// e.g. for [3, 30, 34, 5, 9], the largest formed number is 9534330
// approach: sort array lexically (numbers treated as strings) and append
// in example: ["9", "5", "34", "3", "30"]
type byNumber []string // needed: string sort puts "30" before "3"

func (s byNumber) Len() int { return len(s) }
func (s byNumber) Swap(i, j int) { s[i], s[j] = s[j], s[i] }
func (s byNumber) Less(i, j int) bool {
    return s[i] + s[j] > s[j] + s[i] // this is key. Append both ways and check
}

func LargestNumber(arr []int) string {
    result := ""
    strArr := []string{}
    for _, num := range arr {
        strArr = append(strArr, strconv.Itoa(num))
    }
    // custom sort needed. Basic lexical sort has issues as detailed above
    sort.Sort(byNumber(strArr))
    for _, str := range strArr {
        result += str
    }
    return result
}

// Given n, return a Pascal Triangle e.g. for n = 5
// [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]
func PascalTriangle(n int) [][]int {
    tri   := make([][]int, n)
    tri[0] = []int{1}
    tri[1] = []int{1, 1}
    for i := 2; i < n; i++ {
        tri[i] = []int{1} // init new row
        for j := 1; j < len(tri[i-1]); j++ {
            tri[i] = append(tri[i], tri[i-1][j-1] + tri[i-1][j])
        }
        tri[i] = append(tri[i], 1)
    }
    return tri
}

// Given an array A of integers, find the maximum of j - i subjected to the constraint of A[i] <= A[j].
// e.g. A : [3 5 4 2], output should be 2 for the pair (3, 4)
// Strategy: LMin[i] holds the smallest elem to the left of arr[i] including arr[i]
// RMax[j] holds the largest element to the right of arr[j] including arr[j]
// Traverse LMin, RMax from left (i), right (j) respectively and find the first time LMin[i] < RMax[j]
func MaxDistance(A []int) int {
    N    := len(A)
    LMin := make([]int, N)
    RMax := make([]int, N)
    LMin[0] = A[0]; RMax[N-1] = A[N-1]
    for i := 1; i < N; i++ {
        j := N - i - 1
        LMin[i] = Min(A[i], LMin[i-1])
        RMax[j] = Max(A[j], RMax[j+1])
    }
    // traverse with two pointers and find max(j-i) for LMin[i] < RMax[j]
    i := 0; j := 0; maxDiff := -1
    for i < N && j < N {
        if (LMin[i] < RMax[j]) {
            maxDiff = Max(maxDiff, j-i)
            j++
        } else {
            i++
        }
    }
    return maxDiff
}

// Fibonacci using arbitrary precision addition implemented above
func Fibonacci(n int) []int {
    prev1 := []int{1}; prev2 := []int{1}
    curr  := []int{}
    for i := 2; i < n; i++ {
        curr  = AddNumbers(prev1, prev2)
        prev2 = AddNumbers(prev1, []int{0})
        prev1 = AddNumbers(curr, []int{0})
    }
    return curr
}
