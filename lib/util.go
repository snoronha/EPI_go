package lib

import (
    _ "log"
    "math/rand"
    "time"
)

func Shuffle(vals []int) []int {  
    r := rand.New(rand.NewSource(time.Now().Unix()))
    ret := make([]int, len(vals))
    perm := r.Perm(len(vals))
    for i, randIndex := range perm {
        ret[i] = vals[randIndex]
    }
    return ret
}

func GCDRemainder(a, b int) int {
	for b != 0 {
		a, b = b, a%b
	}
	return a
}

func Max(a, b int) int {
    if a > b {
        return a
    } else {
        return b
    }
}

func Min(a, b int) int {
    if a < b {
        return a
    } else {
        return b
    }
}
