package com.lib;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StringsLeetCode {

    // Outer for loop moves the end ptr
    // Inner while loop increments start ptr once a duplicate character has been detected
    // hash stores {char: current_count}. current_count tells us whether we've seen
    // the character in the current window [start, end). Note interval is open on end side.
    public static String longestSubstring(String str) {
        String[] strArr = str.split("", -1);
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        int start = 0, end = 1, maxlen = 0;
        String longStr = "";
        for (end = 1; end < strArr.length; end++) {
            if (hash.containsKey(strArr[end - 1])) {
                hash.put(strArr[end - 1], hash.get(strArr[end - 1]) + 1); // increment
            } else {
                hash.put(strArr[end - 1], 1);
            }
            if (hash.get(strArr[end - 1]) > 1) {
                // Character at end -1 has appeared twice. Move start ptr till no duplicate characters occur
                while (!strArr[start].equals(strArr[end - 1])) {
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

    // regex matching between string s and pattern p (contains '.', '*', 'a-z')
    public static boolean regexMatch(String s, String p) {
        // System.out.printf("ENTERED: s=%s, p=%s\n", s, p);
        if (p.length() == 0 && s.length() == 0) {
            return true;
        }
        if ((p.length() == 0 && s.length() != 0) || (p.length() != 0 && s.length() == 0)) {
            return false;
        }
        if (Character.isLetter(p.charAt(0))) { // handle [a-z] in pattern p
            if (p.length() > 1) {
                if (p.charAt(1) == '*') {
                    return (p.charAt(0) == s.charAt(0) &&
                            (regexMatch(s.substring(1), p.substring(2))) || regexMatch(s.substring(1), p)) ||
                            regexMatch(s, p.substring(2));
                } else {
                    return (p.charAt(0) == s.charAt(0) && regexMatch(s.substring(1), p.substring(1)));
                }
            } else {
                return s.equals(p);
            }
        } else if (p.charAt(0) == '.') { // handle '.' in pattern p
            if (p.length() > 1) {
                if (p.charAt(1) == '*') {
                    return regexMatch(s.substring(1), p.substring(2)) || regexMatch(s.substring(1), p) ||
                            regexMatch(s, p.substring(2));
                } else {
                    return regexMatch(s.substring(1), p.substring(1));
                }
            } else {
                return s.equals(p);
            }
        }
        return s.equals(p);
    }

    // Roman to int e.g. "IX" => 9, "LVIII" => 58, "MCMXCIV" = 1994
    // I can occur before {V, X}, X can occur before {L, C}, C can occur before {D, M}
    public static int romanToInt(String roman) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        int intval = 0, i = 0;
        while (i < roman.length()) {
            switch (roman.charAt(i)) {
                case 'I':
                    if (i < roman.length() - 1 &&
                            (roman.charAt(i + 1) == 'V' || roman.charAt(i + 1) == 'X')) {
                        intval += map.get(roman.substring(i + 1, i + 2)) - 1;
                        i++;
                    } else {
                        intval += map.get(roman.substring(i, i + 1));
                    }
                    break;

                case 'X':
                    if (i < roman.length() - 1 &&
                            (roman.charAt(i + 1) == 'L' || roman.charAt(i + 1) == 'C')) {
                        intval += map.get(roman.substring(i + 1, i + 2)) - 10;
                        i++;
                    } else {
                        intval += map.get(roman.substring(i, i + 1));
                    }
                    break;

                case 'C':
                    if (i < roman.length() - 1 &&
                            (roman.charAt(i + 1) == 'D' || roman.charAt(i + 1) == 'M')) {
                        intval += map.get(roman.substring(i + 1, i + 2)) - 100;
                        i++;
                    } else {
                        intval += map.get(roman.substring(i, i + 1));
                    }
                    break;

                default:
                    intval += map.get(roman.substring(i, i + 1));
                    break;

            }
            i++;
        }
        return intval;
    }

    // Longest common prefix: e.g. ["flower","flow","flight"] => "fl"
    public static String longestCommonPrefix(List<String> L) {
        String prefix = "";
        boolean done = false;
        int maxIter = L.get(0).length(); // use length of first string as maxIter (could be less)
        for (int i = 0; i < maxIter; i++) {
            Character c = L.get(0).charAt(i);
            for (int j = 1; j < L.size(); j++) {
                if (i >= L.get(j).length()) {
                    return prefix;
                }
                if (c != L.get(j).charAt(i)) {
                    return prefix;
                }
            }
            prefix += c;
        }
        return prefix;
    }

    // Valid parentheses: watch for "([)]" - this is invalid
    public static boolean validParentheses(String s) {
        Stack stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                case ')':
                    if ((char) stack.pop() != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if ((char) stack.pop() != '[') {
                        return false;
                    }
                    break;
                case '}':
                    if ((char) stack.pop() != '{') {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public static void generateParentheses(ArrayList<String> result, String cur, int open, int closed, int n) {
        if (cur.length() == n * 2) {
            result.add(cur);
            return;
        }

        if (open < n)
            generateParentheses(result, cur + "(", open + 1, closed, n);
        if (closed < open)
            generateParentheses(result, cur + ")", open, closed + 1, n);
    }

    // Minimum Window Substring: find the shortest length string that contains
    // all the characters in a target string T.
    // e.g. S = "ADOBECODEBANC", T = "ABC" outputs "BANC"
    // Logic: move start endPtr till at least one instance of all characters are in window
    // move startPtr to find minWindow with all chars. Once one character is excluded move endPtr
    // again till all chars appear. Keep track of shortest window with all in
    public static String minimumWindowSubstring(String S, String T) {
        String minStr = "";
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String c : T.split("")) map.put(c, 0); // build hashmap of chars in T
        int startPtr = 0, endPtr = 0;
        int mapCount = 0; // number of unique chars in window
        while (endPtr < S.length()) {
            // "ADOBECODEBACNC"
            String chEnd = S.substring(endPtr, endPtr + 1);
            if (map.containsKey(chEnd)) {
                map.put(chEnd, map.get(chEnd) + 1);
                if (map.get(chEnd) == 1) mapCount++;
            }
            while (mapCount == T.length()) {
                if (minStr.isEmpty() || endPtr - startPtr < minStr.length()) { // set minStr if len < len(minStr)
                    minStr = S.substring(startPtr, endPtr + 1);
                }
                String chStart = S.substring(startPtr, startPtr + 1);
                if (map.containsKey(chStart)) {
                    map.put(chStart, map.get(chStart) - 1);
                    if (map.get(chStart) == 0) mapCount--;
                }
                startPtr++;
            }
            endPtr++;
        }
        return minStr;
    }

}
