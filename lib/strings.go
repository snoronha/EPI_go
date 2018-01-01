package lib

import (
    _ "log"
    "strconv"
    "strings"
)

// Use Rabin-Karp algorithm: create hash functions for needle (s), and substrings from target t
// Hash function must be incrementally computable
func StrStr(txt string, srch string) int {
    const kBase = 26
    const kMod  = 997
    if len(srch) > len(txt) { return -1 }
    tHash := 0; sHash := 0 // hash codes for srch and substrings of txt
    powerS := 1            // modulo result of kBase ^ |srch|
    for i := 0; i < len(srch); i++ {
        if i > 0 {
            powerS = (powerS * kBase) % kMod
        } else {
            powerS = 1
        }
        tHash = (tHash * kBase + int(txt[i])) % kMod
        sHash = (sHash * kBase + int(srch[i])) % kMod
    }
    for i := len(srch); i < len(txt); i++ {
        // check for string equality to if hashes are equal
        if tHash == sHash && strings.Compare(txt[i-len(srch):i], srch) == 0 {
            return i - len(srch)
        }
        // Use rolling hash to compute new hash code
        tHash -= (int(txt[i - len(srch)]) * powerS) % kMod
        if tHash < 0 { tHash += kMod }
        tHash = (tHash * kBase + int(txt[i])) % kMod
    }

    // Try match last remaining substring of txt
    if tHash == sHash && strings.Compare(txt[len(txt)-len(srch):len(txt)], srch) == 0 {
        return len(txt) - len(srch)
    }
    
    return -1
}

func IntegerToRoman(n int) string {
    ones      := []string{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}
    tens      := []string{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}
    hundreds  := []string{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}
    thousands := []string{"", "M", "MM", "MMM", "MMMM"}
    thouIdx   := n/1000; n %= 1000
    hundIdx   := n/100;  n %= 100
    tenIdx    := n/10;   n %= 10
    oneIdx    := n
    return thousands[thouIdx] + hundreds[hundIdx] + tens[tenIdx] + ones[oneIdx]
}

func RomanToInteger(r string) int {
    rmap := map[string]int{"I": 1, "V": 5, "X": 10, "L": 50, "C": 100, "D": 500, "M": 1000}
    res  := 0
    for i := 0; i < len(r); i++ {
        s1 := rmap[string(r[i])] // Getting value of symbol s[i]
        if i + 1 < len(r) { // Get value of symbol s[i+1]
            s2 := rmap[string(r[i+1])]
            if s1 >= s2 { // Value of current symbol is greater or equal to the next symbol
                res += s1
            } else { // Value of current symbol is less than the next symbol
                res += s2 - s1
                i++; 
            }
        } else {
            res += s1
            i++
        }
    }
    return res
}

// Works up to the precision of int64
// Does not work with -ve nums, exponent notation etc.
func AtoI(str string) int64 {
    var n int64 = 0
    digitMap :=  map[string]int64{"0": 0, "1": 1, "2": 2, "3": 3, "4": 4, "5": 5, "6": 6, "7": 7, "8": 8, "9": 9}
    var pow10 int64 = 1
    for i := len(str) - 1; i >= 0; i-- {
        n += digitMap[string(str[i])] * pow10
        pow10 *= 10
    }
    return n
}

// Split on ".", return if numComponents != 4, then check range of each component
func ValidIPAddress(ip string) bool {
    comps := strings.Split(ip, ".")
    if len(comps) != 4 { return false }
    for _, comp := range comps {
        intComp, err := strconv.Atoi(comp)
        if err != nil { return false }
        if intComp < 0 || intComp > 255 { return false }
    }
    return true
}

// Use DP to solve. Fill in diagonal (single elem), dp[i][i+1] (above diagonal) for 2-elems
// For upper triangle, if seq[i] == seq[j] and dp[i+1][j-1] == True, then dp[i]pj = True
func LongestPalindromicSequenceDP(seq string) (int, int) {
    maxLen := 1
    start  := 0
    n  := len(seq)
    dp := make([][]bool, n)
    for i := range dp {
        dp[i] = make([]bool, n)
    }
    for i := range dp { // single elems are palindromes (diagonal elems)
        dp[i][i] = true
    }
    for i := 0; i < n - 1; i++ { // 2 adjacent equals elems are palindromes
        if seq[i] == seq[i+1] {
            dp[i][i+1] = true
            maxLen = 2
            start  = i
        }
    }
    
    for k := 2; k < n; k++ {
        for i := 0; i < n - k; i++ {
            j := i + k
            if seq[i] == seq[j] && dp[i+1][j-1] {
                dp[i][j] = true
                start    = i
                maxLen   = k + 1
            }
        }
    }
    return start, maxLen
}
