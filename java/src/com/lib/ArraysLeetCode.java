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

    // Given walls of different integer height, compute max water between any two walls
    // Outline: start with 0, n-1 as candidate (widest walls);
    // Remove min(a[0], a[n-1]) from considered set i.e. either i++ or j--
    public static List<Integer> maximumWater(List<Integer> A) {
        List<Integer> result = new ArrayList<>(Arrays.asList(-1, -1, 0));
        int i  = 0, j = A.size() - 1;
        int maxWater = 0, water = 0;
        while (i < j) {
            water = Math.min(A.get(i), A.get(j)) * (j - i);
            if (water > maxWater) {
                maxWater = water;
                result.set(0, i); result.set(1, j); result.set(2, maxWater);
            }
            if (A.get(i) < A.get(j)) {
                i++;
            } else {
                j--;
            }
        }
        return  result;
    }

}
