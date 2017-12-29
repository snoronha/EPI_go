package lib

import (
    "fmt"
    _ "log"
)

type HNode struct {
    Value  int
}

type BinaryHeap struct {
    Nodes []HNode
    Min   bool
}

func (heap *BinaryHeap) GetParentIndex(k int) int {
    if k == 0 { return -1; }
    if k % 2 == 1 {
        return k/2
    } else {
        return (k/2 - 1)
    }
}

func (heap *BinaryHeap) GetParentValue(k int) int {
    pIdx := heap.GetParentIndex(k)
    if pIdx >= 0 {
        return heap.Nodes[pIdx].Value
    } else {
        return -1
    }
}

func (heap *BinaryHeap) GetChildIndices(k int) []int {
    lenNodes  := len(heap.Nodes)
    childIdxs := []int{}
    if k * 2 + 1 < lenNodes {
        childIdxs = append(childIdxs, k * 2 + 1)
    }
    if k * 2 + 2 < lenNodes {
        childIdxs = append(childIdxs, k * 2 + 2)
    }
    return childIdxs
}

func (heap *BinaryHeap) GetChildValues(k int) []int {
    childIdxs   := heap.GetChildIndices(k)
    childValues := []int{}
    for _, childIdx := range childIdxs {
        childValues = append(childValues, heap.Nodes[childIdx].Value)
    }
    return childValues
}

func (heap *BinaryHeap) SwapNodeValues(i int, j int) {
    tmp := heap.Nodes[i].Value
    heap.Nodes[i].Value = heap.Nodes[j].Value
    heap.Nodes[j].Value = tmp
}

func (heap *BinaryHeap) Insert(val int) {
    // create and add new node
    node := HNode{Value: val}
    heap.Nodes = append(heap.Nodes, node) // append to the end of heap.Nodes
    // percolate up
    currIdx   := len(heap.Nodes) - 1
    for heap.GetParentIndex(currIdx) >= 0 && heap.Cmp(heap.Nodes[currIdx].Value, heap.GetParentValue(currIdx)) {
        parentIdx := heap.GetParentIndex(currIdx)
        heap.SwapNodeValues(currIdx, parentIdx) // swap parent/curr values
        currIdx    = parentIdx
    }
}

func (heap *BinaryHeap) ExtractRoot() int {
    if heap.Len() == 0 {
        return -1
    }
    rootVal := heap.Nodes[0].Value
    if heap.Len() == 1 {
        heap.Nodes = heap.Nodes[0:0]
        return rootVal
    }
    lastIdx := len(heap.Nodes) - 1
    heap.Nodes[0].Value = heap.Nodes[lastIdx].Value     // Make last element root
    heap.Nodes = heap.Nodes[0:lastIdx]                // Delete last element from Nodes array
    // percolate down
    done      := false
    currIdx   := 0
    for ! done {
        // percolate down
        currVal   := heap.Nodes[currIdx].Value
        childVals := heap.GetChildValues(currIdx)
        childIdxs := heap.GetChildIndices(currIdx)
        if len(childVals) == 0 { // no children
            done = true
            continue
        }
        if len(childVals) == 1 { // 1 child
            if heap.Cmp(childVals[0], currVal) {
                heap.SwapNodeValues(currIdx, childIdxs[0]) // swap curr/child values
                currIdx = childIdxs[0]
            } else {
                done = true
                continue
            }
        }
        if len(childVals) == 2 { // 2 children
            minChildVal := childVals[0]; minChildIdx := childIdxs[0]
            if heap.Cmp(childVals[1], childVals[0]) {
                minChildVal = childVals[1]; minChildIdx = childIdxs[1]
            }
            if heap.Cmp(minChildVal, currVal) {
                heap.SwapNodeValues(currIdx, minChildIdx) // swap curr/child values
                currIdx = minChildIdx
            } else {
                done = true
                continue
            }
        }
    }
    return rootVal
}

func (heap *BinaryHeap) GetRoot() int {
    if heap.Len() > 0 {
        return heap.Nodes[0].Value
    } else {
        return -1
    }
}

func (heap *BinaryHeap) Len() int {
    return len(heap.Nodes)
}

func (heap *BinaryHeap) Cmp(vali int, valj int) bool {
    if heap.Min {
        return vali < valj
    } else {
        return vali > valj
    }
}

func (heap *BinaryHeap) IsEmpty() bool {
    return len(heap.Nodes) == 0
}

func (heap *BinaryHeap) ToString() string {
    return fmt.Sprintf("%v", *heap)
}
