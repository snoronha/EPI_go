// Java program for different tree traversals

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
}
