package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #46 - Permutations
 *
 * Given an array of distinct integers, return all possible permutations
 * in any order.
 *
 * Example 1:
 *  Input: [1,2,3]
 *  Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * Example 2:
 *  Input: [0,1]
 *  Output: [[0,1],[1,0]]
 *
 * Example 3:
 *  Input: [1]
 *  Output: [[1]]
 *
 * Constraints:
 *  - 1 <= nums.length <= 6
 *  - -10 <= nums[i] <= 10
 *  - All integers in nums are unique
 *
 * Hint: Backtracking — at each step, pick one unused number, add it to the
 *  current path, recurse, then remove it (backtrack).
 *  Use a boolean[] used array or swap-in-place to track which are taken.
 */
public class Permutations {

    static List<List<Integer>> permutations;
    public static List<List<Integer>> permute(int[] nums) {
        permutations =  new ArrayList<>();
        permuter(nums, new ArrayList<>(), new boolean[nums.length]);
        return permutations;
    }

    private static void permuter(int[] nums, List<Integer> path,boolean[] inPath){
        // base case: path is complete (path size is equal to nums length)
        if(path.size() == nums.length){
            permutations.add(new ArrayList<>(path));
        }

        for(int i=0;i<nums.length;i++){
            if(inPath[i]) continue;
            inPath[i] = true;
            path.add(nums[i]);
            permuter(nums, path, inPath);
            path.removeLast();
            inPath[i] = false;
        }
    }

    public static void main(String[] args) {
        // Expected: 6 permutations of [1,2,3]
        List<List<Integer>> r1 = permute(new int[]{1, 2, 3});
        System.out.println("Count: " + r1.size() + " → " + r1);

        // Expected: [[0,1],[1,0]]
        List<List<Integer>> r2 = permute(new int[]{0, 1});
        System.out.println("Count: " + r2.size() + " → " + r2);

        // Expected: [[1]]
        List<List<Integer>> r3 = permute(new int[]{1});
        System.out.println("Count: " + r3.size() + " → " + r3);
    }
}
