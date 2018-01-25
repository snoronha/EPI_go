import util

class Node(object):
    def __init__(self, data=None, next_node=None):
        self.data = data
        self.next_node = next_node

    def __str__(self):
        next_node = self.next_node.data if self.next_node else ""
        return "{d: %s, next: %s}" % (self.data, next_node)
        
    def get_data(self):
        return self.data

    def get_next(self):
        return self.next_node

    def set_next(self, new_next):
        self.next_node = new_next

class LinkedList(object):
    def __init__(self, head=None):
        self.head = head

    def __str__(self):
        res     = ""
        current = self.head
        while current:
            res = res + str(current)
            current = current.get_next()
        return res
        
    def insert(self, data):
        new_node = Node(data)
        new_node.set_next(self.head)
        self.head = new_node

    def size(self):
        current = self.head
        count = 0
        while current:
            count += 1
            current = current.get_next()
        return count

    def search(self, data):
        current = self.head
        found = False
        while current and found is False:
            if current.get_data() == data:
                found = True
            else:
                current = current.get_next()
        if current is None:
            raise ValueError("Data not in list")
        return current

    def delete(self, data):
        current = self.head
        previous = None
        found = False
        while current and found is False:
            if current.get_data() == data:
                found = True
            else:
                previous = current
                current = current.get_next()
        if current is None:
            raise ValueError("Data not in list")
        if previous is None:
            self.head = current.get_next()
        else:
            previous.set_next(current.get_next())    

    def reverse(self):
        current  = self.head
        previous = None
        while current.get_next():
            next     = current.get_next()
            if previous == None:
                current.set_next(None)
            else:
                current.set_next(previous)
            previous = current
            current  = next
        current.set_next(previous)
        self.head = current

    def rotate(self, k):
        if k <= 0:
            return
        head     = self.head
        current  = self.head
        previous = None
        i        = 0
        while current.get_next():
            if i == k - 1:
                previous = current
            if i == k:
                newhead = current
                print "i: %d, curr: %s" % (i, current)
            current = current.get_next()
            i += 1
        current.set_next(head)
        self.head = newhead
        previous.set_next(None)
        
