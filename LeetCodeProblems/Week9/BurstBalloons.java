package LeetCodeProblems.Week9;

import java.util.*;

/**
 * LeetCode #312 - Burst Balloons  [HARD]
 *
 * You are given n balloons indexed 0 to n-1. Each balloon has a number nums[i].
 * If you burst balloon i, you gain coins = nums[i-1] * nums[i] * nums[i+1].
 * If i-1 or i+1 is out of bounds, treat those as 1.
 * Burst all balloons to maximise total coins.
 *
 * Example 1:
 *  nums = [3,1,5,8]  →  167
 *  Order: burst 1 (3*1*5=15), burst 5 (3*5*8=120), burst 3 (1*3*8=24), burst 8 (1*8*1=8) → 167
 *
 * Example 2:
 *  nums = [1,5]  →  10
 *
 * Constraints:
 *  - n == nums.length
 *  - 1 <= n <= 300
 *  - 0 <= nums[i] <= 100
 */
public class BurstBalloons {

    public static int maxCoins(int[] nums) {
        // TODO: implement
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(maxCoins(new int[]{3,1,5,8})); // Expected: 167
        System.out.println(maxCoins(new int[]{1,5}));     // Expected: 10
        System.out.println(maxCoins(new int[]{5}));       // Expected: 5
        System.out.println(maxCoins(new int[]{1,1,1,1})); // Expected: 4 (each burst gives 1)
    }
}

/*
 * Key insight: think BACKWARDS — instead of "which balloon do I burst first?",
 * ask "which balloon do I burst LAST in interval [left, right]?"
 * The last balloon in the interval is surrounded by the boundaries, not its former neighbors.
 *
 * dp[i][j] = max coins from bursting all balloons strictly between index i and j
 *            (i and j are boundaries — not burst in this call)
 *
 * Recurrence: for each k in (i, j):
 *   dp[i][j] = max(nums[i] * nums[k] * nums[j] + dp[i][k] + dp[k][j])
 *
 * Pad nums with 1 on both ends: newNums = [1, ...nums, 1]
 */
