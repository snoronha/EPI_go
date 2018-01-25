// Utilities
package com.lib;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

// Class containing left and right child of current node and key value
public class util {

    // get random int
    public static int getRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max-min) + min;
    }

    public static void shuffle(ArrayList<Integer> arr) {
        Collections.shuffle(arr);
    }

}
