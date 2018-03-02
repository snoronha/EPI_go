// Java class for Primitive Types, Chapter 4 from EPI

package com.lib;

public class PrimitiveTypes {

    public static short countBits(int x) {
        short numBits = 0;
        while (x != 0) {
            numBits += (x & 1);
            x >>>= 1;
        }
        return numBits;
    }

    public static short parity(long x) {
        short result = 0;
        while (x != 0) {
            result ^= (x & 1);
            x >>>= 1;
        }
        return result;
    }

    public static short parityQuick(long x) {
        short result = 0;
        while (x != 0) {
            result ^= 1;
            x &= (x - 1); // drop lowest bit
        }
        return result;
    }

    // swaps bits i, j in a long (64-bit integer)
    public static long swapBits(long x, int i, int j) {
        if (((x >>> i) & 1) != ((x >>> j) & 1)) { // ith, jth bits differ
            // create bitMask with 1's in ith, jth positions, and XOR with x
            long bitMask = (1L << i) | (1L << j);
            x ^= bitMask;
        }
        return x;
    }

    public static boolean isPalindromeNumber(long x) {
        if (x <= 0) {
            return x == 0;
        }
        int numDigits = (int)(Math.floor(Math.log10(x))) + 1;
        int msdMask = (int)Math.pow(10, numDigits - 1);
        for (int i = 0; i < (numDigits / 2); ++i) {
            if (x / msdMask != x % 10) { // compare current LSD and MSD
                return false;
            }
            x %= msdMask; // remove MSD
            x /= 10; // remove LSD
            msdMask /= 100; // removed 2 digits, i.e. LSD and MSD
        }
        return true;
    }
}
