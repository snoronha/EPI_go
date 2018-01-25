// Java program for different tree traversals
// Added a new comment

import com.lib.util;
import com.lib.Node;
import com.lib.LinkedList;
import java.util.ArrayList;
 
class LL {

    public static void main(String[] args) {
        ArrayList<Integer> listA = new ArrayList<>();
        for (int i = 0; i < 10; i++) listA.add(i);
        // util.shuffle(listA);
        LinkedList A = new LinkedList(listA);
        System.out.printf("List A: %s\n", A.toList());

        // Test reverse list
        // A.reverse();
        // System.out.printf("List Reversed: %s\n", A.toList());

        ArrayList<Integer> listB = new ArrayList<>();
        for (int i = 5; i < 15; i++) listB.add(i);
        // util.shuffle(listB);
        LinkedList B = new LinkedList(listB);
        System.out.printf("List B: %s\n", B.toList());

        // Test merge lists
        LinkedList C = LinkedList.merge(A, B);
        System.out.printf("List C: %s\n", C.toList());

    }
}
