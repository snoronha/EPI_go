package com.lib;

import java.util.HashMap;

public class StringsLeetCode {

    // Outer for loop moves the end ptr
    // Inner while loop increments start ptr once a duplicate character has been detected
    // hash stores {char: current_count}. current_count tells us whether we've seen
    // the character in the current window [start, end). Note interval is open on end side.
    public static String longestSubstring(String str) {
        String[] strArr = str.split("", -1);
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        int start = 0, end = 1, maxlen = 0;
        String longStr  = "";
        for ( end = 1; end < strArr.length; end++ ) {
            if (hash.containsKey(strArr[end-1])) {
                hash.put(strArr[end-1], hash.get(strArr[end-1]) + 1); // increment
            } else {
                hash.put(strArr[end-1], 1);
            }
            if (hash.get(strArr[end-1]) > 1) {
                // Character at end -1 has appeared twice. Move start ptr till no duplicate characters occur
                while (!strArr[start].equals(strArr[end-1])) {
                    hash.put(strArr[start], hash.get(strArr[start]) - 1); // decrement
                    start++;
                }
                hash.put(strArr[start], hash.get(strArr[start]) - 1); // decrement (equals end - 1)
                start++;
            }
            if (maxlen < end - start) {
                maxlen = end - start;
                longStr = str.substring(start, end);
            }
        }
        return longStr;
    }

    // at each of 2n - 1 starting points (even-length palindromes are centered between chars)
    // expand out to find the
    public static String longestPalindrome(String s) {
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }
}
