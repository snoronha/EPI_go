// Java class for Array problems from LeetCode's top 150 interview problems

package com.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;


public class ArraysLeetCode {

    // Given an array of integers, return indices of the two numbers
    // such that they add up to a specific target.
    public static List<Integer> twoSum(List<Integer> nums, int target) {
        List<Integer> result = new ArrayList<>(Arrays.asList());
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        // build a hash of {val: index}
        for(int i = 0; i < nums.size(); i++) {
            hash.put(nums.get(i), i);
        }
        for (Integer val : hash.keySet()) {
            if (hash.containsKey(target - val) && hash.get(val) >= 0) { // >= 0 for not yet processed
                result.add(hash.get(val));
                result.add(hash.get(target - val));
                hash.put(val, -1);           // mark as processed
                hash.put(target - val, -1);  // mark as processed
            }
        }
        return result;
    }

}
