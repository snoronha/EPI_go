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

    testLinkedList()
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
    aPtr := lib.CreateLinkedList(a)
    bPtr := lib.CreateLinkedList(b)
    log.Printf("A:       %s\n", aPtr.ToStringList())
    log.Printf("B:       %s\n", bPtr.ToStringList())
    aPtr  = lib.ReverseList(aPtr)
    log.Printf("REVERSE: %s\n", aPtr.ToStringList())
    aPtr  = lib.ReverseList(aPtr)
    log.Printf("REVERSE: %s\n", aPtr.ToStringList())
    midPtr  := aPtr.GetMiddle()
    log.Printf("MIDDLE:  %s\n", midPtr.ToString())
    cPtr := lib.MergeSort(bPtr)
    log.Printf("SORTED:  %s\n", cPtr.ToStringList())
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
