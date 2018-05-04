package main

import (
    "log"
    "EPI_go/lib"
)


func main() {
    var x uint64     = 14
    parity          := lib.Parity(x)
    parityEfficient := lib.ParityEfficient(x)
    log.Printf("Parity = %d ParityEfficient = %d\n", parity, parityEfficient)

    var y int64      = 4567556
    log.Printf("Reverse of %d is %d\n", y, lib.ReverseDigits(y))

    testArrays()
}

func testArrays() {
    // Test addition of arbitrary precision numbers
    a := []int{2, 4, 5, 7}; b := []int{1, 3, 1, 0, 8, 2, 9, 9}
    log.Printf("SUM: %v + %v = %v\n", a, b, lib.AddNumbers(a, b))

    // Test arbitrary precision Fibonacci
    log.Printf("FIB(%d) = %v\n", 100, lib.Fibonacci(100))

    // Test rotate matrix
    mat1 := lib.MakeMatrix(4)
    log.Printf("ROTATED: %v\n", lib.RotateMatrix(mat1))

    // Spiral ordering of matrix
    mat2 := lib.MakeMatrix(5)
    log.Printf("SPIRAL: %v\n", lib.SpiralOrder(mat2))

    // Largest number formable from array of ints
    arr1 := []int{3, 30, 35, 34, 5, 9}
    log.Printf("LARGEST NUM: %v, %s\n", arr1, lib.LargestNumber(arr1))

    // return Pascal Triangle
    log.Printf("PASCAL TRIANGLE: %d, %v\n", 10, lib.PascalTriangle(10))

    // return MaxDistance i.e. max(j-i) for A[j] > A[i]
    arr2 := []int{10, 3, 1, 5, 2, 8, 9, 7, 3, 1}
    log.Printf("MAX DISTANCE: %v, %d\n", arr2, lib.MaxDistance(arr2))
}
