package com.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimitiveTypesLeetCode {

    // create a List with the digits of num in reverse order
    // handles negative/positive. Needs to handle overflow i.e. rnum > 2^31 - 1, underflow i.e. rnum < -2^31
    public static int reverseInteger(int num) {
        boolean isNegative = (num < 0);
        List<Integer> l = new ArrayList<>(Arrays.asList());
        if (isNegative) {
            num = -num;
        }
        while (num > 0) {
            l.add(num % 10); // get current LSD
            num /= 10;       // move to next
        }
        int rnum = 0, pow10 = 1;
        for (int i = l.size() - 1; i >= 0; i-- ) {
            rnum  += l.get(i) * pow10;
            pow10 *= 10;
        }
        return isNegative ? -rnum: rnum;
    }

    public static int atoi(String s) {
        boolean isNegative = false;
        s = s.trim(); // trim white space
        if (s.charAt(0) == '-') { // take care of negative
            s = s.substring(1);
            isNegative = true;
        }
        if (s.charAt(0) == '+') { // take care of positive
            s = s.substring(1);
        }
        if (!Character.isDigit(s.charAt(0))) { // handle case where string starts with non-digit
            return 0;
        }
        s = s.replaceAll("[^\\d]", ""); // remove non-numeric chars

        // take care of overflow/underflow
        int sval  = 0;
        int pow10 = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            sval  += Character.getNumericValue(s.charAt(i)) * pow10;
            pow10 *= 10;
        }
        if (isNegative) {
            return -sval;
        } else {
            return sval;
        }
    }
}
