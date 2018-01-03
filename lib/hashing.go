package lib

import (
    _ "log"
    "sort"
    "strconv"
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

func TwoSum(seq []int, target int) [][]int {
    res    := [][]int{}
    seqMap := map[int][]int{}
    for i, v := range seq {
        if _, ok := seqMap[v]; !ok { seqMap[v] = []int{}; }
        seqMap[v] = append(seqMap[v], i)
    }
    for i, v := range seq {
        if v <= target - v {
            if _, ok := seqMap[target-v]; ok {
                for _, j := range seqMap[target-v] {
                    res = append(res, []int{i, j, v, target-v}) // [i, j, vali, valj]
                }
            }
        }
    }
    return res
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

// Observation: for any fraction a/b, the decimal version MUST recur at a max of (b-1) digits
// Reason: the rem is in [0,b-1]. If the rem == 0, process terminates.
// Else it can cycle through a max of b-1 other remainders
// before it MUST cycle (causing recurring decimals)
func Fraction(num int, denom int) string {
    repeat := map[int]int{} // keep track of index at which rem starts repeating for each rem
    rem    := num
    whole  := strconv.Itoa(rem/denom) + "."
    frac   := ""
    for i := 0; i < denom + 1; i++ { // max cycle for remainders <= denom
        if rem == 0 { return frac }
        rem  = rem % denom
        if _, ok := repeat[rem]; !ok {
            repeat[rem] = i
        } else { // rem repeats => recurring at this rem, look up index at repeat[rem]
            return whole + frac[:repeat[rem]] + "(" + frac[repeat[rem]:] + ")"
        }
        rem *= 10
        frac += strconv.Itoa(rem/denom)
    }
    return whole + frac
}
    
func PointsInLine(x []int, y []int) int {
    // slopeHash = {"num/denom": {idx1: [idx2,idx4,idx5]}, ...}, e.g. {"3/26": {0:[3,6,7]}, ...}
    slopeHash := map[string]map[int][]int{}
    for i := 0; i < len(x) - 1; i++ {
        for j := i + 1; j < len(x); j++ {
            ydiff := y[j] - y[i]
            xdiff := x[j] - x[i]
            gcd   := GCDRemainder(ydiff, xdiff)
            if gcd > 1 {
                ydiff /= gcd
                xdiff /= gcd
            }
            hKey := strconv.Itoa(ydiff) + "/" + strconv.Itoa(xdiff)
            if _, ok := slopeHash[hKey]; !ok    { slopeHash[hKey]    = map[int][]int{} }
            if _, ok := slopeHash[hKey][i]; !ok { slopeHash[hKey][i] = []int{} }
            slopeHash[hKey][i] = append(slopeHash[hKey][i], j)
        }
    }
    maxPoints := 0
    for _, hMap := range slopeHash {
        for _, iArr := range hMap {
            if len(iArr) > maxPoints {
                // log.Printf("i: %d, j: %v\n", i, iArr)
                maxPoints = len(iArr)
            }
        }
    }
    return maxPoints
}

func ValidSudoku(strs []string) bool {
    strMap := map[string]int{".": 0, "1":1,"2":2,"3":3,"4":4,"5":5,"6":6,"7":7,"8":8,"9":9}
    _ = strMap
    board  := [][]int{}
    // populate board
    for i, str := range strs {
        board = append(board, []int{})
        for _, char := range strings.Split(str, "") {
            board[i] = append(board[i], strMap[char])
        }
    }
    // Check validity of board (rows, columns, sub-squares
    n := len(strs)
    for i := 0; i < n; i++ {
        countMap := map[int]int{1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0,9:0}
        for j := 0; j < n; j++ { // check row
            val  := board[i][j]
            if val > 0 { countMap[val]++ }
            if countMap[val] > 1 { return false } // invalid
        }
        countMap  = map[int]int{1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0,9:0}
        for j := 0; j < n; j++ { // check col
            val  := board[j][i]
            if val > 0 { countMap[val]++ }
            if countMap[val] > 1 { return false } // invalid
        }
        countMap  = map[int]int{1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0,9:0}
        for j := 0; j < n; j++ { // check subsquare
            isub := 3 * (i/3) + j/3
            jsub := 3 * (i%3) + j%3
            val  := board[isub][jsub]
            if val > 0 { countMap[val]++ }
            if countMap[val] > 1 { return false } // invalid
        }
    }
    return true
}
