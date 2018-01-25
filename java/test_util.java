// Java program for different tree traversals

import com.lib.util;
import java.util.ArrayList;
 
class TestUtil {
    public static void main(String[] args) {
        // test getRandom
        int rand = util.getRandom(1, 50);
        System.out.printf("Random number: %d\n", rand);

        // test shuffle (ArrayList of Integer)
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        util.shuffle(list);
        System.out.printf("Random number: %s\n", list);
    }
}
