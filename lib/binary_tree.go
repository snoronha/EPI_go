package lib

import (
    "log"
)

type Node struct {
    Value         int
    Index         int
    Sibling       *Node
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
        start := binTree.LevelStart[lvl-1]; end := binTree.LevelEnd[lvl-1]
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
