package lib

import (
    _ "log"
)


func Parity(x uint64) uint64 {
    var result uint64 = 0
    for x != 0 {
        result += (x & 1)
        x >>= 1
    }
    return result % 2
}

func ParityEfficient(x uint64) uint64 {
    var result uint64 = 0
    for x != 0 {
        result ^= 1  // XOR with 1
        x &= (x - 1) // drops lowest set bit
    }
    return result
}

func ReverseDigits(x int64) int64 {
    var isNegative bool = x < 0
    var result int64 = 0
    var xRemaining int64 = x
    if x < 0 {
        xRemaining = -x
    }
    for xRemaining != 0 {
        result = result * 10 + xRemaining % 10
        xRemaining /= 10
    }
    if isNegative {
        return -result
    } else {
        return result
    }
}

