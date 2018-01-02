package lib

import (
    _ "log"
    "sort"
    "strings"
)

// plug numbers into a hash for efficient comparison by value
// identify the lowest value by checking if (value - 1) exists in hash
// start counting up from each lowest candidate
func LongestConsecutiveSequence(seq []int) int {
    seqMap := map[int]int{}
    ans    := 0
    for _, v := range seq { // build seq hash (map)
        if _, ok := seqMap[v]; !ok { seqMap[v] = 0; }
        seqMap[v]++
    }
    for _, v := range seq {
        if _, ok := seqMap[v-1]; !ok { // v - 1 does not exist in seqMap
            val := v
            done := false
            for ! done {
                _, ok := seqMap[val]
                if ok {
                    val++
                } else {
                    done = true
                }
            }
            if val - v > ans { ans = val - v; }
        }
    }
    return ans
}

// Given a sequence, and a target, find 4 numbers that sum up to the target
// Solution: store all pairs (O(N^2) space), making it a 2-sum problem for ~ N^2/2 numbers
func FourSum(seq []int, target int) [][]int {
    res        := [][]int{}
    seqPairMap := map[int][]int{} // { sum1: [i1, j1, i2, j2, ...], sum2: [i3, j3, i4, j4, ...], ... }
    for i := 0; i < len(seq) - 1; i++ {
        for j := i + 1; j < len(seq); j++ {
            sum := seq[i] + seq[j]
            if _, ok := seqPairMap[sum]; !ok { seqPairMap[sum] = []int{}; }
            seqPairMap[sum] = append(seqPairMap[sum], i)
            seqPairMap[sum] = append(seqPairMap[sum], j)
        }
    }
    // example: target = 14, sums are 5 + 9, 3 + 11 (map on smaller of the two i.e. 5, 3; other is implicit)
    sumMap := map[int][][]int{} // { 5: [[i1, j1, ...],[i5, j5, ...]], ...}  5 <-- [i1, j1, ...], 9 <-- [i5, j5, ...]
    for k, _ := range seqPairMap {
        if _, ok := seqPairMap[target-k]; ok {
            if k < target - k {
                sumMap[k] = [][]int{[]int{}, []int{}}
                sumMap[k][0] = seqPairMap[k]; sumMap[k][1] = seqPairMap[target-k];
            } else {
                sumMap[target-k] = [][]int{[]int{}, []int{}}
                sumMap[target-k][0] = seqPairMap[target-k]; sumMap[target-k][1] = seqPairMap[k];
            }
        }
    }
    
    for _, indexArr := range sumMap {
        arr0 := indexArr[0]; arr1 := indexArr[1]
        for m := 0; m < len(arr0); m += 2 {
            for n := 0; n < len(arr1); n += 2 {
                // check for distinctness of arr0[m], arr0[m+1], arr1[n], arr1[n+1]
                if arr0[m] != arr1[n] && arr0[m] != arr1[n+1] &&
                    arr0[m+1] != arr1[n] && arr0[m+1] != arr1[n+1] {
                    // still allows some dups to go through e.g. indexes [2 5 7 10], [2 7 5 10]
                    res = append(res, []int{arr0[m], arr0[m+1], arr1[n], arr1[n+1]})
                }
            }
        }
    }
    // returns arrays of indices, convert to actual values as seq[i] for each i 
    return res
}

// Sort each string, use sorted string as hash value
func GroupAnagrams(strs []string) [][]string {
    groups  := [][]string{}
    strHash := map[string][]string{}
    for _, str := range strs {
        sArr := strings.Split(str, ""); sort.Strings(sArr); s := strings.Join(sArr, "")
        if _, ok := strHash[s]; !ok { strHash[s] = []string{} }
        strHash[s] = append(strHash[s], str)
    }
    for _, sArr := range strHash {
        groups = append(groups, sArr)
    }
    return groups
}

    
