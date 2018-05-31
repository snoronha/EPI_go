// Java class for Array problems from LeetCode's top 150 interview problems

package com.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class RecursionLeetCode {

    // Given an array of integers, return indices of the two numbers
    // such that they add up to a specific target.
    public static List<String> phoneCombinations(String num, ArrayList<String> result) {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        map.put("2", new ArrayList(Arrays.asList("a", "b", "c")));
        map.put("3", new ArrayList(Arrays.asList("d", "e", "f")));
        map.put("4", new ArrayList(Arrays.asList("g", "h", "i")));
        map.put("5", new ArrayList(Arrays.asList("j", "k", "l")));
        map.put("6", new ArrayList(Arrays.asList("m", "n", "o")));
        map.put("7", new ArrayList(Arrays.asList("p", "q", "r", "s")));
        map.put("8", new ArrayList(Arrays.asList("t", "u", "v")));
        map.put("9", new ArrayList(Arrays.asList("w", "x", "y", "z")));

        if (num.length() == 0) {
            return result;
        }
        if (result.size() == 0) {
            for (String s: map.get(num.substring(0, 1))) {
                result.add(s);
            }
            return phoneCombinations(num.substring(1), result);
        } else {
            ArrayList<String> newList = new ArrayList<String>();
            for (String s: map.get(num.substring(0, 1))) {
                for (String partial: result) {
                    newList.add(partial + s);
                }
            }
            return phoneCombinations(num.substring(1), newList);
        }
    }

    // Generate the power set i.e. all subsets of a set
    // There are 2^n subsets for an n-element set
    public static void powerSet(ArrayList<Integer> A, ArrayList<ArrayList<Integer>> L) {
        if (A.size() == 0) {
            L.add(A);
        } else if (A.size() == 1) {
            L.add(A); L.add(new ArrayList<Integer>());
        } else {
            int head = A.get(0);
            A.remove(0);
            powerSet(A, L);
            ArrayList<ArrayList<Integer>> addToL = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> l : L) {
                ArrayList<Integer> clone = (ArrayList<Integer>)l.clone();
                clone.add(head);
                addToL.add(clone);
            }
            for (ArrayList<Integer> l : addToL) {
                L.add(l);
            }
        }
    }

    // Decode ways: "226" -> ["BZ", "VF", "BBF"]
    public static int decodeWays(String S, ArrayList<String> L) {
        int numWays = 0;

        return numWays;
    }

}
