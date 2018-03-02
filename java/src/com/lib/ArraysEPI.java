// Java class for Arrays, Chapter 5 from EPI

package com.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class ArraysEPI {

    public static enum Color {RED, WHITE, BLUE}

    public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
        Color pivot = A.get(pivotIndex);
        // First pass: group elements smaller than pivot
        int smaller = 0;
        for (int i = 0; i < A.size(); ++i) {
            if (A.get(i).ordinal() < pivot.ordinal()) {
                Collections.swap(A, smaller++, i);
            }
        }
        // Second pass: group elements larger than pivot
        int larger = A.size() - 1;
        for (int i = A.size() - 1; i >= 0 && A.get(i).ordinal() >= pivot.ordinal(); --i) {
            if (A.get(i).ordinal() > pivot.ordinal()) {
                Collections.swap(A, larger--, i);
            }
        }
    }

    // Add one to an unlimited precision number
    public static List<Integer> plusOne(List<Integer> A) {
        int n = A.size() - 1;
        A.set(n, A.get(n) + 1); // add 1 to LSD
        for (int i = n; i > 0 && A.get(i) == 10; --i) {
            A.set(i, 0);
            A.set(i - 1, A.get(i - 1) + 1);
        }
        if (A.get(0) == 10) {
            A.set(0, 1);
            A.add(0);
        }
        return A;
    }

    // multiply 2 arbitrary precision numbers (represented as lists)
    public static List<Integer> multiply(List<Integer> num1, List<Integer> num2) {
        int sign = num1.get(0) < 0 ^ num2.get(0) < 0 ? -1 : 1; // save sign of product
        num1.set(0, Math.abs(num1.get(0))); // abs of num1's MSD
        num2.set(0, Math.abs(num2.get(0))); // abs of num2's MSD
        List<Integer> result = new ArrayList<>(Collections.nCopies(num1.size() + num2.size(), 0));
        for (int i = num1.size() - 1; i >= 0; --i) {
            for (int j = num2.size() - 1; j >= 0; --j) {
                result.set(i + j + 1, result.get(i + j + 1) + num1.get(i) * num2.get(j)); // multiply digits
                result.set(i + j, result.get(i + j) + result.get(i + j + 1) / 10); // handle carry to next digit
                result.set(i + j + 1, result.get(i + j + 1) % 10); // handle carry if this digit >= 10
            }
        }
        // remove leading zeroes
        int first_not_zero = 0;
        while (first_not_zero < result.size() && result.get(first_not_zero) == 0) {
            ++first_not_zero;
        }
        result = result.subList(first_not_zero, result.size());
        if (result.isEmpty()) {
            return Arrays.asList(0);
        }
        result.set(0, result.get(0) * sign);
        return result;
    }

    // advancing through an array that has value of index i be numSteps you can take from i
    public static boolean canReachEnd(List<Integer> maxSteps) {
        int furthestReachSoFar = 0, lastIndex = maxSteps.size() - 1;
        for (int i = 0; i <= furthestReachSoFar && furthestReachSoFar < lastIndex; ++i) {
            furthestReachSoFar = Math.max(furthestReachSoFar, i + maxSteps.get(i));
        }
        return furthestReachSoFar >= lastIndex;
    }

    // return number of entries in sorted list after deduping
    public static int deleteDuplicates(List<Integer> A) {
        if (A.isEmpty()) {
            return 0;
        }
        int writeIndex = 1;
        for (int i = 0; i < A.size(); ++i) {
            if (!A.get(writeIndex - 1).equals(A.get(i))) {
                A.set(writeIndex++, A.get(i));
            }
        }
        return writeIndex;
    }

    // buy, sell a stock once
    public static double computeMaxProfit(List<Double> prices) {
        double minPrice = Double.MAX_VALUE, maxProfit = 0.0;
        for (Double price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice  = Math.min(minPrice, price);
        }
        return maxProfit;
    }

    // buy, sell a stock twice
    public static int buyAndSellStockTwice(List<Integer> prices) {
        int maxTotalProfit = 0;
        List<Integer> firstBuySellProfits = new ArrayList<>();
        int minPriceSoFar = Integer.MAX_VALUE;

        // forward phase: max profit if we sell on day[i]
        for (int i = 0; i < prices.size(); ++i) {
            minPriceSoFar = Math.min(minPriceSoFar, prices.get(i));
            maxTotalProfit = Math.max(maxTotalProfit, prices.get(i) - minPriceSoFar);
            firstBuySellProfits.add(maxTotalProfit);
        }

        // backward phase: find max profit if we make second buy on day[i]
        int maxPriceSoFar = Integer.MIN_VALUE;
        for (int i = prices.size() - 1; i > 0; --i) {
            maxPriceSoFar = Math.max(maxPriceSoFar, prices.get(i));
            maxTotalProfit = Math.max(maxTotalProfit, maxPriceSoFar - prices.get(i) + firstBuySellProfits.get(i - 1));
        }
        return maxTotalProfit;
    }

}