package LeetCodeProblems.Week9;

import java.util.*;

/**
 * LeetCode #84 - Largest Rectangle in Histogram  [HARD]
 *
 * Given an array of bar heights, find the area of the largest rectangle
 * that can be formed within the histogram.
 *
 * Example 1:
 *  heights = [2,1,5,6,2,3]
 *  Output: 10  (rectangle formed by bars of height 5 and 6, width 2)
 *
 * Example 2:
 *  heights = [2,4]
 *  Output: 4
 *
 * Constraints:
 *  - 1 <= heights.length <= 10^5
 *  - 0 <= heights[i] <= 10^4
 */
public class LargestRectangleHistogram {

    public static int largestRectangleArea(int[] heights) {
        // TODO: implement with monotonic stack
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[]{2,1,5,6,2,3})); // Expected: 10
        System.out.println(largestRectangleArea(new int[]{2,4}));         // Expected: 4
        System.out.println(largestRectangleArea(new int[]{1}));           // Expected: 1
        System.out.println(largestRectangleArea(new int[]{0}));           // Expected: 0
        System.out.println(largestRectangleArea(new int[]{6,7,5,2,4,5,9,3})); // Expected: 16
    }
}

/*
 * Key insight: monotonic increasing stack of indices.
 * For each bar i: if it's shorter than the top of the stack, the bar at the top
 * can no longer extend right. Its width spans from the new stack top to i.
 * Pop and compute area = height[popped] * width.
 *
 * Trick: append a sentinel 0 at the end to flush all remaining bars from the stack.
 */
