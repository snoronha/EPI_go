package lib

import (
    _ "log"
    "strconv"
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

func (this *OrderStack) ToString() string {
    str := ""
    for _, node := range this.Nodes {
        str += strconv.Itoa(node.Value) + ","
    }
    return str
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
// Set curr = root. For curr node, if no children, append to result.
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

/*
func FindPath(root *OrderNode, target *OrderNode) []OrderNode {
    result  := []OrderNode{}
    processed := map[*OrderNode]bool{} // nodes as process when children on stack
    stack     := OrderStack{}
    stack.Push(root)
    for ! stack.Empty() {
        curr := stack.Pop()
        if curr == target {
            // reverse order stack nodes into an array and return
            for ! stack.Empty() {
                result = append(result, (*stack.Pop()))
            }
            return result
        }
        if _, ok := processed[curr]; !ok { // not processed
            if (*curr).Left != nil || (*curr).Right != nil { // at least 1 child
                stack.Push(curr)         // push back current node
                if (*curr).Left != nil {
                if (*curr).Left != nil { // process left children as far as possible
                    if _, okl := processed[(*curr).Left]; !okl { stack.Push((*curr).Left)
                    log.Printf("LEFT:  %s, curr: %d\n", stack.ToString(), (*curr).Value)
                } else {
                    stack.Push((*curr).Right)
                    log.Printf("RIGHT: %s\n", stack.ToString())
                }
            } else {
                processed[curr] = true // mark processed
            }

        }
    }
    return result // node not found, empty result
}
*/

func FindPathRecurse(root *OrderNode, val int, path *[]OrderNode) bool {
    if root == nil { return false }
    *path = append(*path, *root)
    if (*root).Value == val { return true } // check if root.Value == val
    // Check if val is found in Left or Right subtrees
    if ( (*root).Left != nil && FindPathRecurse((*root).Left, val, path)) ||
        ( (*root).Right != nil && FindPathRecurse((*root).Right, val, path)) {
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

func ConstructTreeInorderPreorder(pre []OrderNode, in []OrderNode) *OrderNode {
    if len(pre) == 1 {
        root := OrderNode{Value: pre[0].Value} // clone pre[0]
        return &root
    } else {
        root := OrderNode{Value: pre[0].Value} // clone root is first node in preorder
        inRootIdx := 0
        for i, inNode := range in { // find root node in inorder, left => leftsubtree, right => rightsubstree
            if inNode == pre[0] { inRootIdx = i }
        }
        if len(in[0:inRootIdx]) > 0 {
            root.Left  = ConstructTreeInorderPreorder(pre[1:inRootIdx+1], in[0:inRootIdx])
        }
        if len(in[inRootIdx+1:]) > 0 {
            root.Right = ConstructTreeInorderPreorder(pre[inRootIdx+1:], in[inRootIdx+1:])
        }
        return &root
    }
}

func ConstructTreeInorderPostorder(post []OrderNode, in []OrderNode) *OrderNode {
    if len(post) == 1 {
        root := OrderNode{Value: post[0].Value} // clone post[last]
        return &root
    } else {
        root := OrderNode{Value: post[len(post)-1].Value} // clone root is last node in postorder
        inRootIdx := 0
        for i, inNode := range in { // find root node in inorder, left => leftsubtree, right => rightsubstree
            if inNode == post[len(post)-1] { inRootIdx = i } // CAUTION: post[last] != root (root is cloned)
        }
        if len(in[0:inRootIdx]) > 0 {
            root.Left  = ConstructTreeInorderPostorder(post[0:inRootIdx], in[0:inRootIdx])
        }
        if len(in[inRootIdx:len(post)-1]) > 0 {
            root.Right = ConstructTreeInorderPostorder(post[inRootIdx:len(post)-1], in[inRootIdx:len(post)-1])
        }
        return &root
    }
}

type BalancedStatus struct {
    Balanced bool
    Height   int
}

// A tree is (1-)balanced when the difference in height between any two left/right subtrees is <= 1
func CheckBalanced(root *OrderNode) BalancedStatus {
    if root == nil {
        return BalancedStatus{Balanced: true, Height: -1}
    }
    // recursively check whether left, right trees are balanced
    leftResult := CheckBalanced((*root).Left)
    if ! leftResult.Balanced {
        return BalancedStatus{Balanced: false, Height: 0}
    }
    rightResult := CheckBalanced((*root).Right)
    if ! rightResult.Balanced {
        return BalancedStatus{Balanced: false, Height: 0}
    }
    // compute Balanced and new Height
    isBalanced := false; height := 0
    if leftResult.Height > rightResult.Height {
        isBalanced = (leftResult.Height - rightResult.Height) <= 1
        height     = leftResult.Height + 1
    } else {
        isBalanced = (rightResult.Height - leftResult.Height) <= 1
        height     = rightResult.Height + 1
    }
    return BalancedStatus{Balanced: isBalanced, Height: height}
}
