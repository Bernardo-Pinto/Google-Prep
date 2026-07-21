package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #78 - Subsets
 *
 * Given an integer array nums of unique elements, return all possible subsets
 * (the power set). The solution set must not contain duplicate subsets.
 * Return in any order.
 *
 * Example 1:
 *  Input: [1,2,3]
 *  Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 *  Input: [0]
 *  Output: [[],[0]]
 *
 * Constraints:
 *  - 1 <= nums.length <= 10
 *  - -10 <= nums[i] <= 10
 *  - All numbers in nums are unique
 *
 * Hint: Two clean approaches:
 *  1. Backtracking — at index i, choose to include or exclude nums[i].
 *  2. Iterative — start with [[]], for each number add it to every existing subset.
 *
 * Note: 2^n subsets total (including empty set).
 */
public class Subsets {

    public static List<List<Integer>> subsets(int[] nums) {
        // TODO: implement
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        // Expected: 8 subsets (2^3)
        List<List<Integer>> r1 = subsets(new int[]{1, 2, 3});
        System.out.println("Count: " + r1.size() + " → " + r1);

        // Expected: [[],[0]]
        List<List<Integer>> r2 = subsets(new int[]{0});
        System.out.println("Count: " + r2.size() + " → " + r2);

        // Expected: 16 subsets (2^4)
        List<List<Integer>> r3 = subsets(new int[]{1, 2, 3, 4});
        System.out.println("Count: " + r3.size() + " → " + r3);
    }
}
