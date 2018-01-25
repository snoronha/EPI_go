import util

class Node:
    """
    Tree node: left and right child + data which can be any object
    """
    def __init__(self, data):
        """
        Node constructor
        @param data node data object
        """
        self.left = None
        self.right = None
        self.data = data

    def insert(self, data):
        """
        Insert new node with data
        @param data node data object to insert
        """
        if self.data:
            if data < self.data:
                if self.left is None:
                    self.left = Node(data)
                else:
                    self.left.insert(data) # recursive insert
            elif data > self.data:
                if self.right is None:
                    self.right = Node(data)
                else:
                    self.right.insert(data) # recursive insert
        else:
            self.data = data        

    def print_inorder(self):
        if self.left:
            self.left.print_inorder()
        print self.data,
        if self.right:
            self.right.print_inorder()

    def inorder(self, path):
        if self.left:
            self.left.inorder(path)
        path.append(self.data)
        if self.right:
            self.right.inorder(path)
