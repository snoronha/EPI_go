package lib

import (
    _ "log"
)

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
