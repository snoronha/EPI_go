package lib

import (
    "log"
    "strings"
)

//---------- Start Definition for Min Stack -----------//
type MinStack struct {
    Nodes []int
    Min     int
}

func (this *MinStack) Push(x int)  {
    this.Nodes = append(this.Nodes, x)
    if len(this.Nodes) == 1 { // first node
        this.Min = x
    }
    if x < this.Min {
        this.Min = x
    }
}

func (this *MinStack) Pop() {
    prevTop := this.Top()
    l := len(this.Nodes)
    if l > 0 {
        this.Nodes = this.Nodes[0:l-1]
        if prevTop == this.Min { // recompute Min for remaining nodes
            this.Min = this.Nodes[0]
            for _, v := range this.Nodes { // can be made more efficient with additional min heap
                if v < this.Min {
                    this.Min = v
                }
            }
        }
    }
}

func (this *MinStack) Top() int {
    if len(this.Nodes) == 0 { return -1; }
    return this.Nodes[len(this.Nodes)-1]
}

func (this *MinStack) GetMin() int {
    return this.Min
}
//----------  End Definition for Min Stack  -----------//

//---------- Start Definition for String Stack -----------//
type StringStack struct {
    Nodes []string
}

func (this *StringStack) Push(x string)  {
    this.Nodes = append(this.Nodes, x)
}

func (this *StringStack) Pop() string {
    top := this.Top()
    l := len(this.Nodes)
    if l > 0 {
        this.Nodes = this.Nodes[0:l-1]
    }
    return top
}

func (this *StringStack) Top() string {
    if len(this.Nodes) == 0 { return ""; }
    return this.Nodes[len(this.Nodes)-1]
}
//----------  End Definition for String Stack  -----------//

//---------- Start Definition for Int Stack -----------//
type IntStack struct {
    Nodes []int
}

func (this *IntStack) Push(x int)  {
    this.Nodes = append(this.Nodes, x)
}

func (this *IntStack) Pop() int {
    top := this.Top()
    l := len(this.Nodes)
    if l > 0 {
        this.Nodes = this.Nodes[0:l-1]
    }
    return top
}

func (this *IntStack) Top() int {
    if len(this.Nodes) == 0 { return -1; }
    return this.Nodes[len(this.Nodes)-1]
}

func (this *IntStack) Empty() bool {
    return len(this.Nodes) <= 0
}
//----------  End Definition for Int Stack  -----------//


// Compute max height of elem to left of ith elem, and max height to right of ith elem
// Then (min(left[i], right[i]) - seq[i]) is the height of water above the ith bar
func TrapRainWater(seq []int) int {
    water := 0
    n     := len(seq)
    left  := make([]int, n) // left[i] contains height of tallest bar left of bar including current bar
    right := make([]int, n)
 
    // Fill left array - max value to the left of ith elem
    left[0] = seq[0];
    for i := 1; i < n; i++ {
        if left[i-1] < seq[i] {
            left[i] = seq[i]
        } else {
            left[i] = left[i-1]
        }
    }
    // Fill right array - max value to the right of the ith elem
    right[n-1] = seq[n-1];
    for i := n - 2; i >= 0; i-- {
        if right[i+1] < seq[i] {
            right[i] = seq[i]
        } else {
            right[i] = right[i+1]
        }
    }
 
    // accumulated water above ith element = min(left[i], right[i]) - seq[i] .
    for i := 0; i < n; i++ {
        if left[i] < right[i] {
            water += left[i] - seq[i]
        } else {
            water += right[i] - seq[i]
        }
    }
    return water;
}

// Recursively called with numOpenPs, numClosePs
// Each valid path is in currPath, accumulated into res (by reference to get past recursion)
// Couldn't frame as a mapping to binary numbers (too many other constraints)
func ValidParentheses(openP int, closeP int, currPath string, res *string) {
    if openP == 0 && closeP == 0 { // all opening and closing in currPath, return it
        if len(*res) == 0 {
            *res += currPath
        } else {
            *res += "," + currPath
        }
    }
    if openP > closeP { // num closing parens used > open ones used => invalid
        return
    }
    if openP > 0 {
        ValidParentheses(openP - 1, closeP, currPath + "(", res) // put "(" and decrement open count
    }
    if closeP > 0 {
        ValidParentheses(openP, closeP - 1, currPath + ")", res) // put ")" and decrement close count
    }
}

// Hokey - doesn't work on test cases.
// Either logic is flawed or implementation has a bug
// Example: "((a+b)+(c+d))" returns true, should return false
func IsRedundantParens(str string) bool {
    chars := strings.Split(str, "")
    stack := StringStack{}
    for _, char := range chars {
        if char == ")" {
            top := stack.Pop()
            if top == "(" { // if immediate char is "(", extra parens used
                return true
            } else {
                for stack.Top() != "(" {
                    top = stack.Pop()
                }
            }
        } else { // push "(" and operands other than ")" onto the stack
            stack.Push(char)
        }
        log.Printf("STACK: %v\n", stack.Nodes)
    }
    return false
}

// Find nearest left elem smaller than current seq[i].
// Can be done O(N^2) by scanning leftwards for each elem seq[i]
// O(N) needs a stack for keeping smaller elements left of current elem
func NearestSmallerElement(seq []int) []int {
    smaller := []int{}
    S       := IntStack{}
    for i := 0; i < len(seq); i++ {
        // Keep popping top element from S while the top elem >= arr[i]
        for ! S.Empty() && S.Top() >= seq[i] {
            S.Pop()
        }
 
        // If all elements in S > arr[i]
        if S.Empty() {
            smaller = append(smaller, -1)
        } else {  // add the nearest smaller element
            smaller = append(smaller, S.Top())
        }
        S.Push(seq[i]);
    }
    return smaller
}
