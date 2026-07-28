package LeetCodeProblems.Week9;

import java.util.*;

/**
 * LeetCode #338 - Counting Bits  [EASY]
 *
 * Given an integer n, return an array ans of length n+1 such that
 * ans[i] = number of 1-bits in the binary representation of i.
 *
 * Must run in O(n) time — no O(n log n) approach (don't count bits for each number independently).
 *
 * Example 1:
 *  n = 2  →  [0,1,1]   (0=0b00, 1=0b01, 2=0b10)
 *
 * Example 2:
 *  n = 5  →  [0,1,1,2,1,2]
 *
 * Constraints:
 *  - 0 <= n <= 10^5
 */
public class CountingBits {

    public static int[] countBits(int n) {
        // TODO: implement in O(n)
        return new int[]{};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(countBits(2))); // [0,1,1]
        System.out.println(Arrays.toString(countBits(5))); // [0,1,1,2,1,2]
        System.out.println(Arrays.toString(countBits(0))); // [0]
        System.out.println(Arrays.toString(countBits(8))); // [0,1,1,2,1,2,2,3,1]
    }
}

/*
 * Key insight: DP with bit trick
 *  i & (i-1) clears the lowest set bit of i.
 *  So: ans[i] = ans[i & (i-1)] + 1
 *
 * Alternative insight:
 *  ans[i] = ans[i >> 1] + (i & 1)
 *  (number of 1s in i = number of 1s in i/2, plus whether i is odd)
 */
