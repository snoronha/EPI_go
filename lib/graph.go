package lib

import (
    _ "log"
    "strconv"
    "strings"
)

type GraphNode struct { // undirected graph node
    Label int
    Nodes []*GraphNode
}

// Print Label:[neighbor1, neighbor12 ...], in DFS order
func (this *GraphNode) ToStringLabelsDFS() string {
    dfsList := DepthFirstSearch(this)
    res     := ""
    for _, node := range dfsList {
        nodeStr := ""
        for _, neighbor := range node.Nodes {
            if len(nodeStr) == 0 {
                nodeStr += strconv.Itoa(neighbor.Label)
            } else {
                nodeStr += "," + strconv.Itoa(neighbor.Label)
            }
        }
        nodeStr = strconv.Itoa(node.Label) + ":[" + nodeStr + "]"
        if len(res) == 0 {
            res += nodeStr
        } else {
            res += ", " + nodeStr
        }
    }
    return res
}

// Print Label:[neighbor1, neighbor12 ...], in BFS order
func (this *GraphNode) ToStringLabelsBFS() string {
    bfsList := BreadthFirstSearch(this)
    res     := ""
    for _, node := range bfsList {
        nodeStr := ""
        for _, neighbor := range node.Nodes {
            if len(nodeStr) == 0 {
                nodeStr += strconv.Itoa(neighbor.Label)
            } else {
                nodeStr += "," + strconv.Itoa(neighbor.Label)
            }
        }
        nodeStr = strconv.Itoa(node.Label) + ":[" + nodeStr + "]"
        if len(res) == 0 {
            res += nodeStr
        } else {
            res += ", " + nodeStr
        }
    }
    return res
}

type GraphStack struct {
    Nodes []*GraphNode
}

func (this *GraphStack) Push(x *GraphNode)  {
    this.Nodes = append(this.Nodes, x)
}

func (this *GraphStack) Pop() *GraphNode {
    top := this.Top()
    l := len(this.Nodes)
    if l > 0 {
        this.Nodes = this.Nodes[0:l-1]
    }
    return top
}

func (this *GraphStack) Top() *GraphNode {
    if len(this.Nodes) == 0 { return nil; }
    return this.Nodes[len(this.Nodes)-1]
}

func (this *GraphStack) Empty() bool {
    return len(this.Nodes) <= 0
}

func (this *GraphStack) ToString() string {
    str := ""
    for _, node := range this.Nodes {
        str += strconv.Itoa(node.Label) + ","
    }
    return str
}

type GraphQueue struct {
    Nodes []*GraphNode
}

func (this *GraphQueue) Enqueue(x *GraphNode)  {
    this.Nodes = append(this.Nodes, x)
}

func (this *GraphQueue) Dequeue() *GraphNode {
    var head *GraphNode
    l := len(this.Nodes)
    if l > 0 {
        head = this.Nodes[0]
        this.Nodes = this.Nodes[1:]
    }
    return head
}

func (this *GraphQueue) Empty() bool {
    return len(this.Nodes) <= 0
}

func (this *GraphQueue) ToString() string {
    str := ""
    for i := len(this.Nodes) - 1; i >= 0; i-- {
        str += strconv.Itoa(this.Nodes[i].Label) + ","
    }
    return str
}

// DFS: non-recursive using a stack
func DepthFirstSearch(startNode *GraphNode) []*GraphNode {
    result  := []*GraphNode{}
    visited := map[*GraphNode]bool{} // nodes as process when children on stack
    stack   := GraphStack{}
    stack.Push(startNode); visited[startNode] = true
    for ! stack.Empty() {
        curr := stack.Pop()
        result = append(result, curr)
        for _, node := range curr.Nodes {
            if _, ok := visited[node]; !ok { // not visited neighbor
                stack.Push(node); visited[node] = true // mark node visited
            }
        }
    }
    return result
}

// DFS: non-recursive using a stack
func BreadthFirstSearch(startNode *GraphNode) []*GraphNode {
    result  := []*GraphNode{}
    visited := map[*GraphNode]bool{} // nodes as process when children on stack
    queue   := GraphQueue{}
    queue.Enqueue(startNode); visited[startNode] = true
    for ! queue.Empty() {
        curr := queue.Dequeue()
        result = append(result, curr)
        for _, node := range curr.Nodes {
            if _, ok := visited[node]; !ok { // not visited neighbor
                queue.Enqueue(node); visited[node] = true // mark node visited
            }
        }
    }
    return result
}

func CloneGraph(startNode *GraphNode) *GraphNode {
    nodeMap := map[*GraphNode]*GraphNode{} // map original -> cloned nodes
    visited := map[*GraphNode]bool{} // nodes "visited" when pushed on stack
    stack   := GraphStack{}
    stack.Push(startNode); visited[startNode] = true
    for ! stack.Empty() {
        curr   := stack.Pop()
        cloned := GraphNode{Label: curr.Label}
        nodeMap[curr] = &cloned
        for _, node := range curr.Nodes {
            if _, ok := visited[node]; !ok { // not visited neighbor
                stack.Push(node); visited[node] = true // mark node visited
            }
        }
    }
    // Iterate over clonedNodes, and establish neighbors (from original node.Nodes)
    for origNode, clonedNode := range nodeMap {
        for _, node := range origNode.Nodes {
            clonedNode.Nodes = append(clonedNode.Nodes, nodeMap[node])
        }
    }
    return nodeMap[startNode]
}

// Strategy: Model board as a graph - adjacent nodes connected to each other
// Goal: Find a path in the graph with path shown
// Push all occurrences of first char (i.e. [i,j] location) onto stack
// Pop each location, and look for nextChar in top/bottom left/right locations
// If found, push the newPath onto the stack and continue
func WordSearch(strBoard []string, strTarget string) bool {
    board  := [][]string{}
    target := strings.Split(strTarget, "")
    stack  := [][]int{} // stores path coords e.g. {[i0,j0],[i0,j0,i1,j1],[i1,j1,i2,j2 ...]} 
    for _, str := range strBoard {
        board = append(board, strings.Split(str, ""))
    }
    // Find starting points i.e. first letter occurrences on board and push on stack
    for i := 0; i < len(board); i++ {
        for j := 0; j < len(board[0]); j++ {
            if board[i][j] == target[0] {
                stack = append(stack, []int{i, j}) // push each starting point onto stack
            }
        }
    }
    // start popping and replacing with longer paths, or not (if path not possible)
    for len(stack) > 0 {
        path     := stack[len(stack)-1]   // top path in stack
        stack     = stack[0:len(stack)-1] // pop this path from stack
        lenp     := len(path)
        nextChar := target[lenp/2]
        i        := path[lenp-2]; j := path[lenp-1]
        for idash := i - 1; idash <= i + 1; idash++ { // do top, this, bottom locations
            if idash >= 0 && idash < len(board) {
                if board[idash][j] == nextChar { // match next char
                    if lenp/2 + 1 == len(target) { return true } // we found one
                    newPath := path; newPath = append(newPath, idash); newPath = append(newPath, j)
                    stack = append(stack, newPath)
                }
            }
        }
        for jdash := j - 1; jdash <= j + 1; jdash++ { // do left, right (this loc done above)
            if jdash == j { continue } // bypass [i, j]
            if jdash >= 0 && jdash < len(board[0]) {
                if board[i][jdash] == nextChar { // match next char
                    if lenp/2 + 1 == len(target) { return true } // we found one
                    newPath := path; newPath = append(newPath, i); newPath = append(newPath, jdash)
                    stack = append(stack, newPath)
                }
            }
        }
    }
    return false
}

// Problem: Find black shapes in an M x N grid with 0, X (black)           0 0 0 X 0 0 0
// i.e. find connected (black) components.                                 0 0 X X 0 X 0
// Approach: Create a hashmap of all X's. Pick one. Do DFS.         .      0 X 0 0 0 X 0
// Remove from hashmap as visited.
// Keep repeating will we have no more connected black shapes (components)
func BlackShapes(strBoard []string) int {
    board  := [][]string{}
    xmap   := map[string][]int{} // Map "i,j" -> [i,] if visited
    shapes := 0
    for _, str := range strBoard {
        board = append(board, strings.Split(str, ""))
    }
    for i := 0; i < len(board); i++ {
        for j := 0; j < len(board[0]); j++ {
            if board[i][j] == "X" {
                xmap[strconv.Itoa(i) + "," + strconv.Itoa(j)] = []int{i, j}
            }
        }
    }
    // Iterate over members of hashmap with X's. 
    for k, v := range xmap {
        stack   := [][]int{}
        visited := map[string]bool{}
        stack    = append(stack, v) // push [i, j] onto stack
        visited[k] = true
        for len(stack) > 0 { // all connected X's are part of this shape, do effective DFS
            curr  := stack[len(stack)-1]   // top X ([i,j] coords) in stack
            stack  = stack[0:len(stack)-1] // pop this X from stack
            i     := curr[0]; j := curr[1]
            if i > 0 && board[i-1][j] == "X" { // top node is X?
                key   := strconv.Itoa(i-1) + "," + strconv.Itoa(j)
                if _, ok := visited[key]; !ok {
                    stack  = append(stack, []int{i-1, j})
                }
                visited[key] = true
                delete(xmap, key) // delete from xmap
            }
            if i < len(board)-1 && board[i+1][j] == "X" { // bottom node is X?
                key   := strconv.Itoa(i+1) + "," + strconv.Itoa(j)
                if _, ok := visited[key]; !ok {
                    stack  = append(stack, []int{i+1, j})
                }
                visited[key] = true
                delete(xmap, strconv.Itoa(i+1) + "," + strconv.Itoa(j))
            }
            if j > 0 && board[i][j-1] == "X" { // left node is X?
                key   := strconv.Itoa(i) + "," + strconv.Itoa(j-1)
                if _, ok := visited[key]; !ok {
                    stack  = append(stack, []int{i, j-1})
                }
                visited[key] = true
                delete(xmap, strconv.Itoa(i) + "," + strconv.Itoa(j-1))
            }
            if j < len(board[0])-1 && board[i][j+1] == "X" { // right node is X?
                key   := strconv.Itoa(i) + "," + strconv.Itoa(j+1)
                if _, ok := visited[key]; !ok {
                    stack  = append(stack, []int{i, j+1})
                }
                visited[key] = true
                delete(xmap, strconv.Itoa(i) + "," + strconv.Itoa(j+1))
            }
        }
        // Finished connected component, increment number of shapes (components)
        shapes++
    }
    return shapes
}
