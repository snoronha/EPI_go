package main

import (
    "bufio"
    "go/build"
    "flag"
    "fmt"
    "io/ioutil"
    "log"
    "math/rand"
    "os"
    "sort"
    "strconv"
    "strings"
    "time"

    "EPI_go/lib"
)

const APPNAME     = "kmerge"
const POW2        = 16
const BUFFER_SIZE = 50 // number of (int) entries per file/bucket
const MAXINT      = 2147483647

type Node struct {
    Val         int
    Index       int
    IsLeftChild bool
    Deleted     bool
}

func main() {
    generateDataPtr := flag.Bool("gen", false, "generate data, an integer")
    flag.Parse()

    // testLinkedList()
    // testHeap()
    // testArrays()
    // testStrings()
    // testStackQueue()
    // testHashing()
    // testTrees()
    // testDynamicProgramming()
    testGraphs()
    return

    if *generateDataPtr {
        generateData()
        return
    }

    mergeData()
}

func testLinkedList() {
    a  := []int{}
    b  := []int{}
    for i := 1; i < 11; i++ {
        a = append(a, i)
        b = append(b, 22 - i * 2)
    }
    a     = lib.Shuffle(a)
    aPtr := lib.CreateLinkedList(a)
    bPtr := lib.CreateLinkedList(b)
    log.Printf("A:       %s\n", aPtr.ToStringList())
    log.Printf("B:       %s\n", bPtr.ToStringList())
    aPtr  = lib.ReverseList(aPtr)
    log.Printf("[Reverse]: %s\n", aPtr.ToStringList())
    aPtr  = lib.ReverseList(aPtr)
    log.Printf("[Reverse]: %s\n", aPtr.ToStringList())
    midPtr  := aPtr.GetMiddle()
    log.Printf("[Middle]:  %s\n", midPtr.ToString())
    cPtr := lib.MergeSort(bPtr)
    log.Printf("[Sorted]:  %s\n", cPtr.ToStringList())
}

func testHeap() {
    a  := []int{}
    for i := 1; i < 1001; i++ {
        a = append(a, i)
    }
    a     = lib.Shuffle(a)
    heap := lib.BinaryHeap{Min: true}
    for _, v := range a { heap.Insert(v); }
    sorted := []int{}
    start   := time.Now()
    for ! heap.IsEmpty() {
        sorted = append(sorted, heap.ExtractRoot())
    }
    log.Printf("[Sorted]: %v\n", sorted)
    elapsed := time.Since(start)
    log.Printf("elapsed: %s\n", elapsed)
}

func testArrays() {
    s := rand.NewSource(time.Now().UnixNano())
    r := rand.New(s)

    b  := []int{}
    for i := 1; i < 16; i++ { b = append(b, i); b = append(b, i+2); }
    b    = lib.Shuffle(b)
    log.Printf("[Delete]: before %v\n", b)
    lib.DeleteArrayElement(b, 10)
    log.Printf("[Delete]: after  %v\n", b)

    a := []int{}
    for i := 3; i < 27; i += 3 { a = append(a, i); }
    b  = []int{}
    for i := 2; i < 9; i += 2 { b = append(b, i); }
    median := lib.MedianOfTwoArrays(a, b)
    log.Printf("[Median]: seq1 %v, seq2 %v, median: %v\n", a, b, median)

    a = []int{}
    for i := 0; i < 30; i++ { a = append(a, r.Intn(30) + 1); }
    dups := lib.FindDuplicates(a)
    log.Printf("[FindDups]: seq: %v, dups: %v\n", a, dups)
}

func testStrings() {
    txt := "abfghstwhfkdj"; srch := "fghs"
    log.Printf("[StrStr]: %s, %s, %v\n", txt, srch, lib.StrStr(txt, srch))

    log.Printf("[IntToRoman]: %d, %s\n", 3999, lib.IntegerToRoman(3999))
    log.Printf("[IntToRoman]: %d, %s\n", 94, lib.IntegerToRoman(94))
    log.Printf("[IntToRoman]: %d, %s\n", 15, lib.IntegerToRoman(15))

    log.Printf("[RomanToInt]: %s, %d\n", "MMMCMXCIX", lib.RomanToInteger("MMMCMXCIX"))
    log.Printf("[RomanToInt]: %s, %d\n", "XCIV", lib.RomanToInteger("XCIV"))

    log.Printf("[AtoI]: %s, %d\n", "35678", lib.AtoI("35678"))
    log.Printf("[AtoI]: %s, %d\n", "34534564567756767", lib.AtoI("34534564567756767"))

    log.Printf("[ValidIP]: %s, %v\n", "204.0.10.186", lib.ValidIPAddress("204.0.10.186"))
    log.Printf("[ValidIP]: %s, %v\n", "204.-1.10.186", lib.ValidIPAddress("204.-1.10.186"))
    log.Printf("[ValidIP]: %s, %v\n", "155.256.10.186", lib.ValidIPAddress("155.256.10.186"))

    str := "kwabbaw"
    start, maxLen := lib.LongestPalindromicSequenceDP(str)
    log.Printf("[LongestPalDP]: str: %s, start: %d, maxLen: %d\n", str, start, maxLen)

    str  = "I am a real crazy person"
    log.Printf("[ReverseString]: str: %s, Reverse: %s\n", str, lib.ReverseString(str))

    log.Printf("[IsPowerOf2]: n: %d, isPower2: %v\n", 65536, lib.IsPowerOf2(int64(65536)))
    log.Printf("[IsPowerOf2]: n: %d, isPower2: %v\n", 24566, lib.IsPowerOf2(int64(24566)))
}

func testStackQueue() {
    s := rand.NewSource(time.Now().UnixNano())
    r := rand.New(s)

    a := []int{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}
    log.Printf("[TrapRainWater]: Input: %v, Water: %d\n", a, lib.TrapRainWater(a))
    a  = []int{3, 1, 2, 4}
    log.Printf("[TrapRainWater]: Input: %v, Water: %d\n", a, lib.TrapRainWater(a))

    validPs := ""
    lib.ValidParentheses(3, 3, "", &validPs)
    log.Printf("[ValidParens]: n: %d, res: %s\n", 3, validPs)

    minStack := lib.MinStack{}
    for i := 0; i < 10; i++ { minStack.Push(r.Intn(30) + 1); }
    log.Printf("[MinStack]: stack: %v, top: %d, min: %d\n", minStack.Nodes, minStack.Top(), minStack.GetMin())
    for i := 0; i < 5; i++ { minStack.Pop(); }
    log.Printf("[MinStack]: stack: %v, top: %d, min: %d\n", minStack.Nodes, minStack.Top(), minStack.GetMin())

    str := "((a+(b))+(c+d))"
    log.Printf("[FIX RedundantParens]: str: %s, isRedundant: %v\n", str, lib.IsRedundantParens(str))
    str  = "((a+b)+(c+d))"
    log.Printf("[FIX RedundantParens]: str: %s, isRedundant: %v\n", str, lib.IsRedundantParens(str))

    a    = []int{1, 6, 4, 10, 2, 5}
    log.Printf("[NearestSmallerElem}: a: %v, smaller: %v\n", a, lib.NearestSmallerElement(a))
    a    = []int{1, 3, 0, 2, 5}
    log.Printf("[NearestSmallerElem}: a: %v, smaller: %v\n", a, lib.NearestSmallerElement(a))
}

func testHashing() {
    a := []int{1, 9, 3, 22, 10, 4, 20, 2, 23, 6, 5, 34, 24, 8, 25}
    log.Printf("[LongestConsecutiveSeq]: a: %v, longest: %d\n", a, lib.LongestConsecutiveSequence(a))

    a  = []int{1, 9, 3, 22, 10, 4, 20, 2, 23, 6, 5, 34, 24, 8, 25}
    log.Printf("[FourSum]: a: %v, target: %d, solutions: %v\n", a, 14, lib.FourSum(a, 14))

    strs := []string{"eat", "tea", "tan", "ate", "nat", "bat"}
    log.Printf("[GroupAnagrams]: strs: %v, groups: %v\n", strs, lib.GroupAnagrams(strs))

    log.Printf("[Fraction]: fraction %d/%d, str: %s\n", 23, 101, lib.Fraction(23, 101))

    x := []int{0, 2, 3, 1, 2, 3, 4, 5, 6, 4, 5}
    y := []int{0, 2, 3, 2, 4, 6, 8, 10, 12, 4, 5}
    pts := lib.PointsInLine(x, y)
    log.Printf("[PointsInLine]: x: %v\n", x)
    log.Printf("[PointsInLine]: y: %v, numPoints: %d\n", y, pts)

    a  = []int{1, 9, 3, 22, 10, 4, 20, 2, 23, 6, 5, 34, 24, 8, 25}
    log.Printf("[TwoSum]: a: %v, target: %d, solutions: %v\n", a, 14, lib.TwoSum(a, 14))

    strs = []string{
        "53..7....", "6..195...", ".98....6.",
        "8...6...3", "4..8.3..1", "7...2...6",
        ".6....28.", "...419..5", "....8..79",
    }
    log.Printf("[ValidSudoku]: strs: %v, valid: %v\n", strs, lib.ValidSudoku(strs))

    nodes := []lib.DeepCopyNode{}
    for i := 0; i < 3; i++ {
        nodes = append(nodes, lib.DeepCopyNode{Value: i})
    }
    nodes[0].Next = append(nodes[0].Next, &nodes[1])
    nodes[0].Next = append(nodes[0].Next, &nodes[2])
    nodes[1].Next = append(nodes[1].Next, &nodes[2])
    nodes[1].Next = append(nodes[1].Next, &nodes[0])
    log.Printf("[DeepCopyList]: nodes: %v, deepCopy: %v\n", nodes, lib.DeepCopyList(&nodes[0]))

    str := "abcabdcbbedcba"
    log.Printf("[LongestNonRepeatSubstring]: str: %s, maxLen: %d\n", str, lib.LongestNonRepeatSubstring(str))
}

func testTrees() {
    nodes := []lib.OrderNode{}
    for i := 0; i < 4; i++ { nodes = append(nodes, lib.OrderNode{Value: i}) }
    nodes[0].Right = &nodes[1]; nodes[1].Right = &nodes[2]; nodes[2].Right = &nodes[3]; 
    log.Printf("[InorderTraversal]: nodes: %v, result: %v\n", nodes, lib.InorderTraversal(&nodes[0]))

    nodes  = []lib.OrderNode{}
    for i := 0; i < 6; i++ { nodes = append(nodes, lib.OrderNode{Value: i}) }
    nodes[0].Left = &nodes[1]; nodes[0].Right = &nodes[2]
    nodes[1].Left = &nodes[3];
    nodes[2].Left = &nodes[4]; nodes[2].Right = &nodes[5]
    log.Printf("[InorderTraversal]: nodes: %v, result: %v\n", nodes, lib.InorderTraversal(&nodes[0]))

    result := []int{} // use nodes above
    lib.InorderTraversalRecurse(&nodes[0], &result)
    log.Printf("[InorderTraversalRecurse]: nodes: %v, result: %v\n", nodes, result)

    result  = lib.PreorderTraversal(&nodes[0]) // use nodes above
    log.Printf("[PrerderTraversalRecurse]: nodes: %v, result: %v\n", nodes, result)

    result  = []int{} // use nodes above
    lib.PreorderTraversalRecurse(&nodes[0], &result)
    log.Printf("[PreorderTraversalRecurse]: nodes: %v, result: %v\n", nodes, result)

    result  = lib.PostorderTraversal(&nodes[0]) // use nodes above
    log.Printf("[PostrderTraversalRecurse]: nodes: %v, result: %v\n", nodes, result)

    result  = []int{} // use nodes above
    lib.PostorderTraversalRecurse(&nodes[0], &result)
    log.Printf("[PostorderTraversalRecurse]: nodes: %v, result: %v\n", nodes, result)

    nodes  = []lib.OrderNode{}
    for i := 0; i < 16; i++ { nodes = append(nodes, lib.OrderNode{}) }
    nodes[0].Value  = 314; nodes[0].Left  = &nodes[1];  nodes[0].Right  = &nodes[2]
    nodes[1].Value  = 6;   nodes[1].Left  = &nodes[3];  nodes[1].Right  = &nodes[4]
    nodes[2].Value  = 6;   nodes[2].Left  = &nodes[5];  nodes[2].Right  = &nodes[6]
    nodes[3].Value  = 271; nodes[3].Left  = &nodes[7];  nodes[3].Right  = &nodes[8]
    nodes[4].Value  = 561;                              nodes[4].Right  = &nodes[9]
    nodes[5].Value  = 2;                                nodes[5].Right  = &nodes[10]
    nodes[6].Value  = 271;                              nodes[6].Right  = &nodes[11]
    nodes[7].Value  = 28;
    nodes[8].Value  = 0;
    nodes[9].Value  = 3;   nodes[9].Left  = &nodes[12];
    nodes[10].Value = 1;   nodes[10].Left = &nodes[13]; nodes[10].Right = &nodes[14]
    nodes[11].Value = 28;
    nodes[12].Value = 17;
    nodes[13].Value = 401;                              nodes[13].Right = &nodes[15]
    nodes[14].Value = 257;
    nodes[15].Value = 641;
    result  = lib.InorderTraversal(&nodes[0]) // use nodes above
    log.Printf("[InorderTraversalRecurse]: result: %v\n", result)

    path := []lib.OrderNode{}
    lib.FindPathRecurse(&nodes[0], 641, &path)
    log.Printf("[FindPathRecurse]: path: %v\n", path)

    // path  = lib.FindPath(&nodes[0], &nodes[15])
    // log.Printf("[FindPath]: path: %v\n", path)

    lca := lib.LeastCommonAncestor(&nodes[0], &nodes[12], &nodes[8])
    log.Printf("[LeastCommonAncestor]: LCA: %v\n", lca)


    n1 := []lib.OrderNode{}
    for i := 0; i < 6; i++ { n1 = append(n1, lib.OrderNode{}) }
    n1[0].Value = 0; n1[0].Left = &n1[1]; n1[0].Right = &n1[2]
    n1[1].Value = 1;                      n1[1].Right = &n1[3]
    n1[2].Value = 2; n1[2].Left = &n1[4]; n1[2].Right = &n1[5]
    n1[3].Value = 3;
    n1[4].Value = 4;
    n1[5].Value = 5;
    pre    := []lib.OrderNode{n1[0], n1[1], n1[3], n1[2], n1[4], n1[5]}
    in     := []lib.OrderNode{n1[1], n1[3], n1[0], n1[4], n1[2], n1[5]}
    post   := []lib.OrderNode{n1[3], n1[1], n1[4], n1[5], n1[2], n1[0]}
    newPreIn  := lib.ConstructTreeInorderPreorder(pre, in)
    newPostIn := lib.ConstructTreeInorderPostorder(post, in)
    log.Printf("[ConstructInPreOrder]:  tree: %v, oldRoot: %v\n", newPreIn, n1[0])
    log.Printf("[ConstructInPostOrder]: tree: %v, oldRoot: %v\n", newPostIn, n1[0])
    log.Printf("[InorderTraversal]:        tree: %v\n", lib.InorderTraversal(newPreIn))
    log.Printf("[InorderTraversal]:        tree: %v\n", lib.InorderTraversal(newPostIn))
    log.Printf("[InorderTraversal]: ACTUAL tree: %v\n", lib.InorderTraversal(&n1[0]))


    n2 := []lib.OrderNode{}
    for i := 0; i < 15; i++ { n2 = append(n2, lib.OrderNode{Value: i}) }
    n2[0].Left = &n2[1];  n2[0].Right = &n2[2]
    n2[1].Left = &n2[3];  n2[1].Right = &n2[4]
    n2[2].Left = &n2[5];  n2[2].Right = &n2[6]
    n2[3].Left = &n2[7];  n2[3].Right = &n2[8]
    n2[4].Left = &n2[9];  n2[4].Right = &n2[10] // comment out to test negative case
    n2[5].Left = &n2[11]; n2[5].Right = &n2[12]
    n2[7].Left = &n2[13]; n2[7].Right = &n2[14]
    bStatus := lib.CheckBalanced(&n2[0])
    log.Printf("[CheckBalanced]: bStatus: %v\n", bStatus)

    n3 := []lib.OrderNode{}
    for i := 0; i < 9; i++ { n3 = append(n3, lib.OrderNode{}) }
    n3[0].Value = 0; n3[0].Left = &n3[1];  n3[0].Right = &n3[2]
    n3[1].Value = 1;                       n3[1].Right = &n3[3]
    n3[2].Value = 1; n3[2].Left = &n3[4]
    n3[3].Value = 4; n3[3].Left = &n3[5];  n3[3].Right = &n3[6]
    n3[4].Value = 4; n3[4].Left = &n3[7];  n3[4].Right = &n3[8]
    n3[5].Value = 5; n3[6].Value = 6; n3[7].Value = 6; n3[8].Value = 5;
    bSymm := lib.CheckSymmetric(n3[0].Left, n3[0].Right)
    log.Printf("[CheckSymmetric]: bSymm: %v\n", bSymm)
}

func testDynamicProgramming() {
    a   := []int{}
    for i := 1; i < 31; i++ { a = append(a, i); }
    a    = lib.Shuffle(a)
    lis := lib.LongestIncreasingSubsequenceDP(a) // compute LIS using DP
    log.Printf("[LIS]: seq: %v, lis = %d\n", a, lis)

    a    = []int{}
    for i := 1; i < 10; i++ { a = append(a, i); }
    a    = lib.Shuffle(a)
    a[5] = -a[5]; // a[9] = -a[9];
    log.Printf("[MaxProductSubarray]: seq: %v, maxSubArray = %d\n", a, lib.MaxProductSubarray(a))

    inp := "124312216"
    log.Printf("[WaysToDecode]: seq: %s, numWays = %d\n", inp, lib.WaysToDecode(inp))

    a    = []int{}
    for i := 1; i < 31; i++ { a = append(a, i); }
    a    = lib.Shuffle(a)
    log.Printf("[BuySell1]: seq: %v, maxprofit = %d\n", a, lib.BuySell1Stock(a))


    n1 := []lib.OrderNode{}
    for i := 0; i < 16; i++ { n1 = append(n1, lib.OrderNode{}) }
    n1[0].Value  = 10;  n1[0].Left  = &n1[1];  n1[0].Right  = &n1[2]
    n1[1].Value  = 2;   n1[1].Left  = &n1[3];  n1[1].Right  = &n1[4]
    n1[2].Value  = 10;                         n1[2].Right  = &n1[5]
    n1[3].Value  = 20;
    n1[4].Value  = 1;
    n1[5].Value  = -25; n1[5].Left  = &n1[6];  n1[5].Right  = &n1[7]
    n1[6].Value  = 3;
    n1[7].Value  = 4;
    sum  := 0
    lib.TreeMaxSumPath(&n1[0], &sum)
    log.Printf("[TreeMaxSumPath]: maxSum: %d\n", sum)

    inp = "ababbbabbababa"
    log.Printf("[MinPalindromicPartition]: numCuts: %d\n", lib.MinPalindromicPartion(inp))

    M  := [][]int{[]int{1, 3, 2}, []int{4, 3, 1}, []int{5, 6, 1}}
    log.Printf("[MinSumMatrixPath]: M: %v, minPath: %v\n", M, lib.MinSumMatrixPath(M))

    a   = []int{1, 3, 5, 2, 0, 2, 6, 7, 6, 8, 9}
    log.Printf("[MinJumps]: input: %v, numMinJumps: %d\n", a, lib.MinJumps(a))

    a   = []int{2, 3, 1, 1, 1, 0, 0}
    log.Printf("[JumpGame]: input: %v, win: %v\n", a, lib.JumpGame(a))

    log.Printf("[StairsProblem]: n: %d, numWays: %d\n", 8, lib.StairsProblem(8))
}

func testGraphs() {
    g1 := []lib.GraphNode{}
    for i := 0; i < 7; i++ { g1 = append(g1, lib.GraphNode{Label: i}) }
    g1[0].Nodes = []*lib.GraphNode{&g1[1], &g1[2], &g1[3]}
    g1[1].Nodes = []*lib.GraphNode{&g1[0], &g1[2]}
    g1[2].Nodes = []*lib.GraphNode{&g1[0], &g1[1], &g1[4], &g1[6]}
    g1[3].Nodes = []*lib.GraphNode{&g1[0], &g1[4], &g1[5]}
    g1[4].Nodes = []*lib.GraphNode{&g1[2], &g1[3], &g1[6]}
    g1[5].Nodes = []*lib.GraphNode{&g1[3]}
    g1[6].Nodes = []*lib.GraphNode{&g1[2], &g1[4]}
    log.Printf("[DepthFirstSearch]: graph: %v, dfspath: %v\n", (&g1[0]).ToStringLabelsDFS(), lib.DepthFirstSearch(&g1[0]))
    log.Printf("[BreadthFirstSearch]: bfspath: %v\n", (&g1[0]).ToStringLabelsBFS())

    log.Printf("[ClonedGraph]: originalGraph: {%s}, clonedGraph: {%s}\n", (&g1[0]).ToStringLabelsDFS(), lib.CloneGraph(&g1[0]).ToStringLabelsDFS())

    strBoard := []string{ "ABCE", "SFCS", "ADEE" }
    wordTarget := "ABFSAB"
    log.Printf("[WordSearch]: board: %v, target: %s, wordFound: %v\n", strBoard, wordTarget, lib.WordSearch(strBoard, wordTarget))

    strBoard  = []string{
        "XX0X000",
        "00XX0X0",
        "0X000X0",
    }
    log.Printf("[BlackShapes]: board: %v, numBlackShapes: %v\n", strBoard, lib.BlackShapes(strBoard))
}

func mergeData() {
    start   := time.Now()
    N       := POW2 * POW2 * POW2
    log.Printf("Merging data ...\n")
    err     := os.Chdir(build.Default.GOPATH + "/data"); check(err)
    fileCnt := make([]int, N)   // num elements in each file
    filePtr := make([]int, N)   // last element fetched to buffer
    bufPtr  := make([]int, N)   // element to be processed in merge in buffer
    buffers := make([][]int, N) // buffer for each bucket/file
    for i := range buffers {
        buffers[i] = make([]int, BUFFER_SIZE)
    }
    count   := 0
    for i := 0; i < POW2; i++ {
        leveli := APPNAME + "/" + strconv.Itoa(i)
        for j := 0; j < POW2; j++ {
            levelj := leveli + "/" + strconv.Itoa(j)
            for k := 0; k < POW2; k++ {
                filek := levelj + "/" + strconv.Itoa(k) + ".dat"
                fileCnt[count] = getLineCount(filek)
                bufPtr[count]  = 0
                count++
            }
        }
    }
    // initial "refill" for all buffers
    for i := range buffers {
        refillBuffer(i, &buffers, &filePtr)
    }

    leafs   := []int{}
    for i := range buffers {
        leafs = append(leafs, buffers[i][0])
    }
    bTree   := lib.BinaryTree{}
    bTree.BuildFromLeafNodes(leafs)
    _ = bTree
    return
    
    binTree := createBinaryTree(&buffers)
    log.Printf("len(Bintree): %v\n", len(binTree))

    // N-way merge, will refill when data in any bucket is depleted
    done  := false
    count  = 0
    for ! done {
        rootNode := getRootNode(&binTree)
        if rootNode.Val == MAXINT {
            done = true
            log.Printf("DONE: count = %d\n", count)
            continue
        }
        if bufPtr[rootNode.Index] == BUFFER_SIZE - 1 {
            log.Printf("ROOT: %v\n", rootNode)
            refillBuffer(rootNode.Index, &buffers, &filePtr)
            // log.Printf("AFTER REFILL:  %v\n", buffers[rootNode.Index])
            bufPtr[rootNode.Index] = 0
        } else {
            bufPtr[rootNode.Index]++
        }
        kthNode := binTree[rootNode.Index]
        kthNode.Val = buffers[rootNode.Index][bufPtr[rootNode.Index]]
        propagateLeafNode(kthNode, N, &binTree)
        count++
        // if count > 3060000 {
        // done = true
        // }
    }
    elapsed := time.Since(start)
    log.Printf("elapsed: %s\n", elapsed)
}

func generateData() {
    log.Printf("Generating data ...\n")
    err     := os.Chdir(build.Default.GOPATH + "/data"); check(err)
    err      = os.RemoveAll(APPNAME); check(err)
    err      = os.MkdirAll(APPNAME, 0700); check(err)
    for i := 0; i < POW2; i++ {
        leveli := APPNAME + "/" + strconv.Itoa(i)
        err = os.Mkdir(leveli, 0700); check(err)
        for j := 0; j < POW2; j++ {
            levelj := leveli + "/" + strconv.Itoa(j)
            err = os.Mkdir(levelj, 0700); check(err)
            for k := 0; k < POW2; k++ {
                filek := levelj + "/" + strconv.Itoa(k) + ".dat"
                createFile(filek)
            }
        }
    }
}

func createFile(filePath string) {
    numNums  := rand.Intn(500) + 500
    arr      := make([]int, numNums)
    for i := range arr {
        arr[i] = rand.Intn(100000) + 1
    }
    sort.Ints(arr)
    arrStr   := strings.Trim(strings.Replace(fmt.Sprint(arr), " ", "\n", -1), "[]")
    arrBytes := []byte(arrStr)
    err      := ioutil.WriteFile(filePath, arrBytes, 0644)
    check(err)
}

func refillBuffer(n int, buffers *[][]int, filePtr *[]int) {
    filePath    := getFilePathFromN(n)
    file, err   := os.Open(filePath); check(err)
    fileScanner := bufio.NewScanner(file)
    lineCount   := 0
    bufArr      := (*buffers)[n]
    bufLen      := len(bufArr)
    fileStart   := (*filePtr)[n]; fileEnd := fileStart + bufLen
    for fileScanner.Scan() {
        numStr   := strings.TrimSpace(fileScanner.Text())
        num, err := strconv.Atoi(numStr)
        if err == nil {
            if lineCount >= fileStart && lineCount < fileEnd {
                bufArr[lineCount-fileStart] = num
                (*filePtr)[n] += 1
            }
            lineCount++
        }
    }
    // Handle case when we're out of entries in nth file
    if lineCount < fileEnd {
        if lineCount >= fileStart {
            for i := lineCount; i < fileEnd; i++ {
                bufArr[i-fileStart] = MAXINT
                (*filePtr)[n] += 1
            }
        } else {
            for i := fileStart; i < fileEnd; i++ {
                bufArr[i-fileStart] = MAXINT
                (*filePtr)[n] += 1
            }
        }
        // log.Printf("Insufficient remaining entries: %s, lineCount: %d, fileEnd: %d\n", filePath, lineCount, fileEnd)
    }
    file.Close()
}

func createBinaryTree(buffers *[][]int) []Node {
    // build initial binary tree
    binTree := []Node{}
    isLeft  := true
    for i := range *buffers {
        node      := Node{Val: (*buffers)[i][0], Index: i, Deleted: false, IsLeftChild: isLeft}
        binTree    = append(binTree, node)
        isLeft     = ! isLeft
    }
    done := false
    startInd := 0; endInd := len(binTree)
    for ! done {
        isLeft = true
        for j := startInd; j < endInd; j += 2 {
            if j + 1 >= endInd { // unpaired element at end
                node      := Node{Val: binTree[j].Val, Index: binTree[j].Index, Deleted: false, IsLeftChild: isLeft}
                binTree    = append(binTree, node)
            } else {
                if binTree[j].Val < binTree[j+1].Val {
                    node      := Node{Val: binTree[j].Val, Index: binTree[j].Index, Deleted: false, IsLeftChild: isLeft}
                    binTree    = append(binTree, node)
                } else {
                    node      := Node{Val: binTree[j+1].Val, Index: binTree[j+1].Index, Deleted: false, IsLeftChild: isLeft}
                    binTree    = append(binTree, node)
                }
            }
            isLeft = ! isLeft
        }
        startInd = endInd; endInd = len(binTree)
        if startInd == endInd - 1 {
            done = true
        }
    }
    return binTree
}

func getRootNode(binTree *[]Node) Node {
    l := len(*binTree)
    // log.Printf("TAIL BINTREE: %v\n", (*binTree)[l-7:])
    return (*binTree)[l-1]
}

func propagateLeafNode(kthNode Node, N int, binTree *[]Node) {
    k        := kthNode.Index
    (*binTree)[k] = kthNode
    // propagate any change up the tree starting at kth (leaf) node
    startInd := 0; endInd := N
    currInd  := k; pow    := 2
    done     := false
    for ! done {
        parentInd  := endInd + k/pow
        if (*binTree)[currInd].IsLeftChild {
            if currInd != endInd - 1 { // paired with RightSibling
                if (*binTree)[currInd].Val < (*binTree)[currInd+1].Val {
                    (*binTree)[parentInd].Val   = (*binTree)[currInd].Val
                    (*binTree)[parentInd].Index = (*binTree)[currInd].Index
                } else {
                    (*binTree)[parentInd].Val   = (*binTree)[currInd+1].Val
                    (*binTree)[parentInd].Index = (*binTree)[currInd+1].Index
                }
            } else { // rightmost node has no right sibling, propagate to parent
                (*binTree)[parentInd].Val   = (*binTree)[currInd].Val
                (*binTree)[parentInd].Index = (*binTree)[currInd].Index
            }
            // log.Printf("AFTER:  [%d]curr[%d] = %d, [%d]sib[%d] = %d, [%d]parent[%d] = %d\n", (*binTree)[currInd].Index, currInd, (*binTree)[currInd].Val, (*binTree)[currInd+1].Index, (currInd+1), (*binTree)[currInd+1].Val, (*binTree)[parentInd].Index, parentInd, (*binTree)[parentInd].Val)
        } else {
            // log.Printf("BEFORE: [%d]curr[%d] = %d, [%d]sib[%d] = %d, [%d]parent[%d] = %d\n", (*binTree)[currInd].Index, currInd, (*binTree)[currInd].Val, (*binTree)[currInd-1].Index, (currInd-1), (*binTree)[currInd-1].Val, (*binTree)[parentInd].Index, parentInd, (*binTree)[parentInd].Val)
            if (*binTree)[currInd].Val < (*binTree)[currInd-1].Val {
                (*binTree)[parentInd].Val   = (*binTree)[currInd].Val
                (*binTree)[parentInd].Index = (*binTree)[currInd].Index
            } else {
                (*binTree)[parentInd].Val   = (*binTree)[currInd-1].Val
                (*binTree)[parentInd].Index = (*binTree)[currInd-1].Index
            }
            // log.Printf("AFTER:  [%d]curr[%d] = %d, [%d]sib[%d] = %d, [%d]parent[%d] = %d\n", (*binTree)[currInd].Index, currInd, (*binTree)[currInd].Val, (*binTree)[currInd-1].Index, (currInd-1), (*binTree)[currInd-1].Val, (*binTree)[parentInd].Index, parentInd, (*binTree)[parentInd].Val)
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

func getFilePathFromN(n int) string {
    filek    := n % POW2
    rem      := n / POW2
    dirj     := rem % POW2
    diri     := rem / POW2
    return APPNAME + "/" + strconv.Itoa(diri) + "/" + strconv.Itoa(dirj) + "/" + strconv.Itoa(filek) + ".dat"
}

func getLineCount(filePath string) int {
    file, err := os.Open(filePath); check(err)
    fileScanner := bufio.NewScanner(file)
    lineCount   := 0
    for fileScanner.Scan() {
        lineCount++
    }
    file.Close()
    return lineCount
}

func check(e error) {
    if e != nil {
        panic(e)
    }
}
