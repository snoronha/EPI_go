// Java program for different tree traversals

package com.lib;

import com.lib.Node;
import java.util.ArrayList;
 
public class LinkedList {
    public Node head; // head of linked list
 
    public LinkedList() {
        head = null;
    }

    public LinkedList(ArrayList<Integer> L) {
        head = null;
        Node curr = null;
        Node prev = null;
        for (Integer elem: L) {
            curr = new Node(elem);
            if (prev != null) {
                prev.next = curr;
            } else {
                head = curr;
            }
            prev = curr;
        }
    }

    // Reverses in place
    public void reverse() {
        Node prev2  = null;
        Node prev   = null;
        Node curr   = head;
        while (curr != null) {
            prev2 = prev;  // keep the train going (3 statements)
            prev  = curr;
            curr  = curr.next;
            prev.next = prev2; // reverse link to prev node
        }
        head = prev; // set head to last elem in list
    }

    // Merges 2 lists, returns a new list
    public static LinkedList merge(LinkedList A, LinkedList B) {
        LinkedList C = new LinkedList();
        Node a = A.head;
        Node b = B.head;
        Node c = null;
        Node next;
        while (a != null || b != null) {
            if (a == null) { // 
                next = new Node(b.key);
                b = b.next;
            } else if (b == null) {
                next = new Node(a.key);
                a = a.next;
            } else {
                if (a.key <= b.key) {
                    next = new Node(a.key);
                    a = a.next;
                } else {
                    next = new Node(b.key);
                    b = b.next;
                }
            }
            if (c == null) { C.head = next; }
            else { c.next = next; }
            c = next;
        }
        return C;
    }

    public ArrayList<Integer> toList() {
        ArrayList<Integer> list  = new ArrayList<>();
        Node curr   = head;
        while (curr != null) {
            list.add(curr.key);
            curr = curr.next;
        }
        return list;
    }
}
