package Problems;

import java.util.List;

public class MaximumSubArray {
    
    //Given an integer array, find the contiguous subarray with the largest sum and return that sum.
    /*
        Input:  [-2, 1, -3, 4, -1, 2, 1, -5, 4]
        Output: 6   (subarray [4, -1, 2, 1])

        Input:  [-1, -2, -3]
        Output: -1

        Input:  [1]
        Output: 1
    */
   //correct algorithm is Kadane's

    // Divide & Conquer — O(n log n)
    // T(n) = 2T(n/2) + O(n)  →  Case 2 of Master Theorem  →  O(n log n)
    public static int maxSubArray(int[] arr) {
        return solve(arr, 0, arr.length);
    }

    // [min, max) half-open
    private static int solve(int[] arr, int min, int max) {
        if (max - min == 1) return arr[min];          // base case: single element

        int mid = min + (max - min) / 2;

        int left  = solve(arr, min, mid);             // max subarray entirely in left half
        int right = solve(arr, mid, max);             // max subarray entirely in right half
        int cross = maxCrossing(arr, min, mid, max);  // max subarray crossing the midpoint

        return Math.max(Math.max(left, right), cross);
    }

    // O(n): expand outward from mid in both directions, take best left + best right
    private static int maxCrossing(int[] arr, int min, int mid, int max) {
        // expand LEFT from mid-1 toward min
        int leftMax = Integer.MIN_VALUE, sum = 0;
        for (int i = mid - 1; i >= min; i--) {
            sum += arr[i];
            leftMax = Math.max(leftMax, sum);
        }

        // expand RIGHT from mid toward max-1
        int rightMax = Integer.MIN_VALUE; sum = 0;
        for (int i = mid; i < max; i++) {
            sum += arr[i];
            rightMax = Math.max(rightMax, sum);
        }

        return leftMax + rightMax;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(maxSubArray(new int[]{-1, -2, -3}));                     // -1
        System.out.println(maxSubArray(new int[]{1}));                              // 1
        System.out.println(maxSubArray(new int[]{4, -1, 2, 1}));                    // 6
    }
}
