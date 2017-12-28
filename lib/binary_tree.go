package lib

import (
    "log"
)

type Node struct {
    Value         int
    Index         int
    Parent       *Node
    IsLeftSibling bool
}

type BinaryTree struct {
    Nodes        []Node
    LevelStart   []int
    LevelEnd     []int
}

func (binTree *BinaryTree) BuildFromLeafNodes(leafs []int) {
    isLeft   := true
    lvlCount := 0
    for i := range leafs { // populate leaf nodes
        node    := Node{Value: leafs[i], Index: i, IsLeftSibling: isLeft}
        binTree.Nodes = append(binTree.Nodes, node)
        isLeft   = ! isLeft
        lvlCount++
    }
    binTree.LevelStart = append(binTree.LevelStart, 0)
    binTree.LevelEnd   = append(binTree.LevelEnd, lvlCount)

    lvl     := 1
    done    := false
    isLeft   = true
    for ! done {
        lvlNumNodes := (binTree.LevelEnd[lvl-1] - binTree.LevelStart[lvl-1])/2
        if 2 * lvlNumNodes < binTree.LevelEnd[lvl-1] - binTree.LevelStart[lvl-1] {
            lvlNumNodes++
        }
        binTree.LevelStart = append(binTree.LevelStart, binTree.LevelEnd[lvl-1])
        binTree.LevelEnd   = append(binTree.LevelEnd, binTree.LevelEnd[lvl-1] + lvlNumNodes)
        if binTree.LevelStart[lvl] == binTree.LevelEnd[lvl] - 1 {
            done = true
            continue // abort loop if level has one node (at root)
        }
        start   := binTree.LevelStart[lvl-1]; end := binTree.LevelEnd[lvl-1]
        // currInd := end
        log.Printf("START: %d, END: %d, LVL: %d, LVL_NUM_NODES: %d\n", start, end, lvl, lvlNumNodes)
        for i := start; i < end; i += 2 {
            if i + 1 >= end { // end-of-level element
                node    := Node{Value: binTree.Nodes[i].Value, Index: binTree.Nodes[i].Index, IsLeftSibling: isLeft}
                binTree.Nodes = append(binTree.Nodes, node)
            } else { // set parent to be great of the children
                if binTree.Nodes[i].Value < binTree.Nodes[i+1].Value {
                    node    := Node{Value: binTree.Nodes[i].Value, Index: binTree.Nodes[i].Index, IsLeftSibling: isLeft}
                    binTree.Nodes = append(binTree.Nodes, node)
                } else {
                    node    := Node{Value: binTree.Nodes[i+1].Value, Index: binTree.Nodes[i+1].Index, IsLeftSibling: isLeft}
                    binTree.Nodes = append(binTree.Nodes, node)
                }
            }
            isLeft = ! isLeft
        }
        lvl++
    }
}

func (binTree *BinaryTree) PropagateLeafValue(k int, kthValue int) {
    binTree.Nodes[k].Value = kthValue
    // propagate any change up the tree starting at kth (leaf) node
    lvl      := binTree.GetLevel(k)
    startInd := binTree.LevelStart[lvl]
    endInd   := binTree.LevelEnd[lvl]
    currInd  := k; pow    := 2
    done     := false
    for ! done {
        parentInd  := endInd + k/pow
        if binTree.Nodes[currInd].IsLeftSibling {
            if currInd != endInd - 1 { // paired with RightSibling
                if binTree.Nodes[currInd].Value < binTree.Nodes[currInd+1].Value {
                    binTree.Nodes[parentInd].Value   = binTree.Nodes[currInd].Value
                    binTree.Nodes[parentInd].Index = binTree.Nodes[currInd].Index
                } else {
                    binTree.Nodes[parentInd].Value = binTree.Nodes[currInd+1].Value
                    binTree.Nodes[parentInd].Index = binTree.Nodes[currInd+1].Index
                }
            } else { // rightmost node has no right sibling, propagate to parent
                binTree.Nodes[parentInd].Value = binTree.Nodes[currInd].Value
                binTree.Nodes[parentInd].Index = binTree.Nodes[currInd].Index
            }
            // log.Printf("AFTER:  [%d]curr[%d] = %d, [%d]sib[%d] = %d, [%d]parent[%d] = %d\n", binTree.Nodes[currInd].Index, currInd, binTree.Nodes[currInd].Value, binTree.Nodes[currInd+1].Index, (currInd+1), binTree.Nodes[currInd+1].Value, binTree.Nodes[parentInd].Index, parentInd, binTree.Nodes[parentInd].Value)
        } else {
            if binTree.Nodes[currInd].Value < binTree.Nodes[currInd-1].Value {
                binTree.Nodes[parentInd].Value = binTree.Nodes[currInd].Value
                binTree.Nodes[parentInd].Index = binTree.Nodes[currInd].Index
            } else {
                binTree.Nodes[parentInd].Value = binTree.Nodes[currInd-1].Value
                binTree.Nodes[parentInd].Index = binTree.Nodes[currInd-1].Index
            }
        }

        // set up next iteration
        currInd    = parentInd
        pow       *= 2
        oldEndInd := endInd
        if (endInd - startInd) % 2 == 0 {
            endInd += (endInd - startInd)/2
        } else {
            endInd += (endInd - startInd)/2 + 1
        }
        startInd   = oldEndInd
        if startInd == endInd - 1 { // termination condition
            done = true
        }
    }
}

func (binTree *BinaryTree) GetLevel(k int) int {
    lvl   := -1
    for i := range binTree.LevelStart {
        if k >= binTree.LevelStart[i] && k < binTree.LevelEnd[i] {
            lvl = i
        }
    }
    return lvl
}
