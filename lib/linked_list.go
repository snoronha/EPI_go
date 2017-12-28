package lib

import (
    "fmt"
    _ "log"
    "strconv"
)

type LLNode struct {
    Value  int
    Next   *LLNode
}

func CreateLinkedList(nodeValues []int) *LLNode {
    headPtr := &LLNode{}
    prevPtr := &LLNode{}
    for i := range nodeValues { // populate linked list
        node    := LLNode{Value: nodeValues[i]}
        if i != 0 {
            prevPtr.Next = &node
        } else {
           headPtr = &node
        }
        prevPtr = &node
    }
    return headPtr
}

func ReverseList(headPtr *LLNode) *LLNode {
    prevPtr := &LLNode{}
    currPtr := headPtr
    i       := 0
    for currPtr.Next != nil {
        nextPtr := currPtr.Next
        if i != 0 {
            currPtr.Next = prevPtr
        } else {
            currPtr.Next = nil
        }
        prevPtr = currPtr
        currPtr = nextPtr
        i++
    }
    currPtr.Next = prevPtr
    headPtr = currPtr
    return headPtr
}

//------------ Start Sort functions -----------//
func (headPtr *LLNode) GetMiddle() *LLNode {
    currPtr  := headPtr
    curr2Ptr := headPtr
    for curr2Ptr.Next != nil && curr2Ptr.Next.Next != nil {
        currPtr  = currPtr.Next
        curr2Ptr = curr2Ptr.Next.Next
    }
    return currPtr
}

func MergeSort(headPtr *LLNode) *LLNode {
    if headPtr == nil || headPtr.Next == nil {
        return headPtr
    }
    middlePtr := headPtr.GetMiddle() // get the middle of the list
    sHalfPtr  := middlePtr.Next
    middlePtr.Next = nil   //split the list into two halfs
    return MergeSortedLists(MergeSort(headPtr), MergeSort(sHalfPtr));  //recurse on that
}

func MergeSortedLists(aPtr *LLNode, bPtr *LLNode) *LLNode {
    cPtr    := &LLNode{}
    currPtr := cPtr
    for aPtr != nil && bPtr != nil {
        if aPtr.Value <= bPtr.Value {
            currPtr.Next = aPtr
            aPtr = aPtr.Next
        } else {
            currPtr.Next = bPtr
            bPtr = bPtr.Next
        }
        currPtr = currPtr.Next
    }
    if aPtr == nil {
        currPtr.Next = bPtr
    } else {
        currPtr.Next = aPtr
    }
    return cPtr.Next
}
//------------- End Sort functions ------------//

func (headPtr *LLNode) ToStringList() string {
    listStr := ""
    currPtr := headPtr
    for currPtr.Next != nil {
        listStr += strconv.Itoa(currPtr.Value) + " -> "
        currPtr = currPtr.Next
    }
    listStr += strconv.Itoa(currPtr.Value)
    return listStr
}

func (node *LLNode) ToString() string {
    return fmt.Sprintf("%v", *node)
}
