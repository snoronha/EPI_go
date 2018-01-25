import linked_list
import tree
import util

def testLinkedLists():
    l = linked_list.LinkedList()
    for i in range(10, 50, 10):
        l.insert(i)
    print "LIST: %s" % l
    l.reverse()
    print "REVERSE LIST: %s" % l
    l.rotate(0)
    print "ROTATE LIST: %s" % l

    root = tree.Node(8); root.insert(3); root.insert(10); root.insert(1); root.insert(5)
    root.print_inorder()
    path = []
    root.inorder(path)
    print "INORDER: %s" % (path)
    
    
testLinkedLists()
    
