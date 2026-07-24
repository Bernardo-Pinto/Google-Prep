package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #39 - Combination Sum
 *
 * Given an array of distinct integers candidates and a target integer target,
 * return all unique combinations of candidates where the chosen numbers sum to target.
 * The same number may be chosen from candidates an unlimited number of times.
 * Two combinations are unique if the frequency of at least one of the chosen numbers is different.
 *
 * Difference from #77 Combinations: you can reuse the same element, and you sum to a target
 * instead of picking exactly k elements.
 *
 * Example 1:
 *  candidates = [2,3,6,7], target = 7
 *  Output: [[2,2,3],[7]]
 *
 * Example 2:
 *  candidates = [2,3,5], target = 8
 *  Output: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * Example 3:
 *  candidates = [2], target = 1
 *  Output: []
 *
 * Constraints:
 *  - 1 <= candidates.length <= 30
 *  - 2 <= candidates[i] <= 40
 *  - All elements of candidates are distinct
 *  - 1 <= target <= 40
 */
public class CombinationSum {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        // TODO: implement
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        // [[2,2,3],[7]]
        System.out.println(combinationSum(new int[]{2,3,6,7}, 7));

        // [[2,2,2,2],[2,3,3],[3,5]]
        System.out.println(combinationSum(new int[]{2,3,5}, 8));

        // []
        System.out.println(combinationSum(new int[]{2}, 1));

        // [[1,1,1,1],[1,1,2],[2,2],[1,3]]  (order may vary)
        System.out.println(combinationSum(new int[]{1,2,3}, 4));
    }
}
