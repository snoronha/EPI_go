package lib

import (
    _ "log"
)

type OrderNode struct { // binary tree
    Value int
    Left  *OrderNode
    Right *OrderNode
}

// Stack used in DeepCopyList
type OrderStack struct {
    Nodes []*OrderNode
}

func (this *OrderStack) Push(x *OrderNode)  {
    this.Nodes = append(this.Nodes, x)
}

func (this *OrderStack) Pop() *OrderNode {
    top := this.Top()
    l := len(this.Nodes)
    if l > 0 {
        this.Nodes = this.Nodes[0:l-1]
    }
    return top
}

func (this *OrderStack) Top() *OrderNode {
    if len(this.Nodes) == 0 { return nil; }
    return this.Nodes[len(this.Nodes)-1]
}

func (this *OrderStack) Empty() bool {
    return len(this.Nodes) <= 0
}

func InorderTraversalRecurse(root *OrderNode, result *[]int) {
    if root == nil { return }
    InorderTraversalRecurse((*root).Left, result)
    *result = append(*result, (*root).Value)
    InorderTraversalRecurse((*root).Right, result)
}

func PreorderTraversalRecurse(root *OrderNode, result *[]int) {
    if root == nil { return }
    *result = append(*result, (*root).Value)
    PreorderTraversalRecurse((*root).Left, result)
    PreorderTraversalRecurse((*root).Right, result)
}

func PostorderTraversalRecurse(root *OrderNode, result *[]int) {
    if root == nil { return }
    PostorderTraversalRecurse((*root).Left, result)
    PostorderTraversalRecurse((*root).Right, result)
    *result = append(*result, (*root).Value)
}

// Inorder traversal without recursion
// Set curr = root. For curr node, if no children, append to traversal list.
// If children, push right (if exists), root, and left (if exists) on stack (processed in reverse order)
// Mark root as processed to prevent repeated processing
func InorderTraversal(root *OrderNode) []int {
    result  := []int{}
    processed := map[*OrderNode]bool{} // nodes as process when children on stack
    stack     := OrderStack{}
    stack.Push(root)
    for ! stack.Empty() {
        curr := stack.Pop()
        if _, ok := processed[curr]; !ok { // not processed
            if (*curr).Right == nil && (*curr).Left == nil { // no children, add to result
                result = append(result, (*curr).Value)
            } else {
                if (*curr).Right != nil { // push right node if exists
                    stack.Push((*curr).Right)
                }
                stack.Push(curr)          // push back current node
                if (*curr).Left != nil {  // push left node if exists
                    stack.Push((*curr).Left)
                }
            }
            processed[curr] = true // mark processed

        } else { // processed
            result = append(result, (*curr).Value)
        }
    }
    return result
}

func PreorderTraversal(root *OrderNode) []int {
    result  := []int{}
    processed := map[*OrderNode]bool{} // nodes as process when children on stack
    stack     := OrderStack{}
    stack.Push(root)
    for ! stack.Empty() {
        curr := stack.Pop()
        if _, ok := processed[curr]; !ok { // not processed
            if (*curr).Right == nil && (*curr).Left == nil { // no children, add to result
                result = append(result, (*curr).Value)
            } else { // push on stack in opposite order to desired
                if (*curr).Right != nil { // push right node if exists
                    stack.Push((*curr).Right)
                }
                if (*curr).Left != nil {  // push left node if exists
                    stack.Push((*curr).Left)
                }
                stack.Push(curr)          // push back current node
            }
            processed[curr] = true // mark processed

        } else { // processed
            result = append(result, (*curr).Value)
        }
    }
    return result
}

func PostorderTraversal(root *OrderNode) []int {
    result  := []int{}
    processed := map[*OrderNode]bool{} // nodes as process when children on stack
    stack     := OrderStack{}
    stack.Push(root)
    for ! stack.Empty() {
        curr := stack.Pop()
        if _, ok := processed[curr]; !ok { // not processed
            if (*curr).Right == nil && (*curr).Left == nil { // no children, add to result
                result = append(result, (*curr).Value)
            } else { // push on stack in opposite order to desired
                stack.Push(curr)          // push back current node
                if (*curr).Right != nil { // push right node if exists
                    stack.Push((*curr).Right)
                }
                if (*curr).Left != nil {  // push left node if exists
                    stack.Push((*curr).Left)
                }
            }
            processed[curr] = true // mark processed

        } else { // processed
            result = append(result, (*curr).Value)
        }
    }
    return result
}

func FindPath(root *OrderNode, val int, path *[]OrderNode) bool {
    if root == nil { return false }
    *path = append(*path, *root)
    if (*root).Value == val { return true } // check if root.Value == val
    // Check if val is found in Left or Right subtrees
    if ( (*root).Left != nil && FindPath((*root).Left, val, path)) ||
        ( (*root).Right != nil && FindPath((*root).Right, val, path)) {
        return true
    }
    *path = (*path)[0:len(*path)-1] // Pop root if val nor present in subtree rooted at root
    return false
}

// find the LCA of nodes a, b with tree rooted at root
// ALternative: use FindPath to find path to a, b
// Then start at root (common ancestor), walk down both paths and find last node that is common
func LeastCommonAncestor(root *OrderNode, a *OrderNode, b *OrderNode) *OrderNode {
    if root == nil { return nil }
    if root == a || root == b { return root }
    leftLCA  := LeastCommonAncestor((*root).Left, a, b)  // recurse left
    rightLCA := LeastCommonAncestor((*root).Right, a, b) // recurse right
    if leftLCA != nil && rightLCA != nil { return root }
    if leftLCA != nil {
        return leftLCA
    } else {
        return rightLCA
    }
}
