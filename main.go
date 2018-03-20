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

    testArrays()
}

func testArrays() {
    // Test addition of arbitrary precision numbers
    a := []int{2, 4, 5, 7}; b := []int{1, 3, 1, 0, 8, 2, 9, 9}
    fmt.Printf("SUM: %v + %v = %v\n", a, b, lib.AddNumbers(a, b))

    // Test arbitrary precision Fibonacci
    fmt.Printf("FIB(%d) = %v\n", 100, lib.Fibonacci(100))

    // Test rotate matrix
    mat1 := lib.MakeMatrix(4)
    fmt.Printf("ROTATED: %v\n", lib.RotateMatrix(mat1))

    // Spiral ordering of matrix
    mat2 := lib.MakeMatrix(5)
    fmt.Printf("SPIRAL: %v\n", lib.SpiralOrder(mat2))

    // Largest number formable from array of ints
    arr  := []int{3, 30, 35, 34, 5, 9}
    fmt.Printf("LARGEST NUM: %v, %s\n", arr, lib.LargestNumber(arr))
}
