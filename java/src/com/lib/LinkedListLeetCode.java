// Java class for Array problems from LeetCode's top 150 interview problems

package com.lib;
import java.util.ArrayList;

public class LinkedListLeetCode {
    public Node head; // head of linked list

    public LinkedListLeetCode() {
        head = null;
    }

    public LinkedListLeetCode(ArrayList<Integer> L) {
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

    // Add two numbers. Each number is a reversed linked-list
    // i.e. 123 => 3->2->1
    public static LinkedListLeetCode addTwoNumbers(LinkedListLeetCode A, LinkedListLeetCode B) {
        LinkedListLeetCode C = new LinkedListLeetCode();
        Node a = A.head;
        Node b = B.head;
        Node c = null;
        Node next;
        int carry = 0;
        while (a != null || b != null) {
            if (a == null) { //
                next  = new Node((b.key + carry) % 10);
                carry = (b.key + carry) / 10;
                b = b.next;
            } else if (b == null) {
                next  = new Node((a.key + carry) % 10);
                carry = (a.key + carry) / 10;
                a = a.next;
            } else {
                next  = new Node((a.key + b.key + carry) % 10);
                carry = (a.key + b.key + carry) / 10;
                a = a.next;
                b = b.next;
            }
            if (c == null) { C.head = next; }
            else { c.next = next; }
            c = next;
        }
        if (carry > 0) { // handle final carry if it exists
            next   = new Node(1);
            c.next = next;
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
