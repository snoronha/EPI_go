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
