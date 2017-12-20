package main

import (
    "fmt"
    "EPI_go/lib"
)


func main() {
    var x uint64     = 14
    parity          := lib.Parity(x)
    parityEfficient := lib.ParityEfficient(x)
    fmt.Printf("Parity = %d ParityEfficient = %d\n", parity, parityEfficient)

    var y int64      = 4567556
    fmt.Printf("Reverse of %d is %d\n", y, lib.ReverseDigits(y))
    
}
