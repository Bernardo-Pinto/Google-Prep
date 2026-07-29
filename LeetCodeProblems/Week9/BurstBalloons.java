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
 *     0 1 2  3   4    5 
 *   0 0 0 3 30 159  167
*    1 - 0 0 15 135  159
 *   2 - - 0  0  40   48
 *   3 - - -  0   0   40
 *   4 - - -  -   0    0 
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
        //O(n^3) solution
        int[][] dp =  new int[nums.length+1][nums.length+2];
        int[] newNums = new int[nums.length+2];
        newNums[0] = 1;
        newNums[nums.length + 1] = 1;
        for (int i = 0; i < nums.length; i++) newNums[i + 1] = nums[i];

        // 1st it: (0,2),(1,3),(2,4)(3,5)
        // 2nd it: (0,3),(1,4),(2,5)
        // 3rd it: (0,4),(1,5)
        // 4th it: (0,5) //end
        int s = 0;
        int e = 2;
        while(e<nums.length+2){
            int i = s;
            int j = e;
            while(i < dp.length && j < dp[0].length){
                
                int maxKBurstValue = 0;
                for(int k=i+1;k<j;k++){
                    //this is for each position to burst last
                    //if k was burst last, take the value before k
                    int leftValue  = dp[i][k];
                    int rightValue = dp[k][j];
                    int burstKValue = newNums[i] * newNums[k] * newNums[j];
                    maxKBurstValue = Math.max(maxKBurstValue, leftValue+burstKValue+rightValue);
                }
                dp[i][j] = maxKBurstValue;
                i++;j++;
            }
            e++;
        }
        return dp[0][nums.length+1];
        //return permuter(nums, new HashSet<>(), 0);
    }

    
    // O(n!) : Each permuter calls permuter n then n-1, then n-2, n-i until 1 times.This is n!
    private static int permuter(int[] nums, HashSet<Integer> burst, int sum){
        if(burst.size() == nums.length){
            return sum;
        }

        int max = sum;
        for(int i=0;i<nums.length;i++){
            if(burst.contains(i)) continue;
            
            int leftIndx = i-1;
            while(leftIndx >= 0 && burst.contains(leftIndx)) leftIndx--;
            int left = leftIndx < 0 ? 1 : nums[leftIndx];

            int rightIndx = i+1;
            while(rightIndx < nums.length && burst.contains(rightIndx)) rightIndx++;
            int right = rightIndx >= nums.length ? 1 : nums[rightIndx];

            int newSum = sum + (left * nums[i] * right);

            burst.add(i);
            max = Math.max(max,permuter(nums, burst, newSum));
            burst.remove(i);
        }
        return max;
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
