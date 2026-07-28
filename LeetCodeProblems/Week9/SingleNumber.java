package LeetCodeProblems.Week9;

/**
 * LeetCode #136 - Single Number  [EASY]
 *
 * Every element in nums appears exactly twice except for one element which appears once.
 * Find that element. Must run in O(n) time and O(1) space.
 *
 * Example 1:
 *  nums = [2,2,1]  →  1
 *
 * Example 2:
 *  nums = [4,1,2,1,2]  →  4
 *
 * Example 3:
 *  nums = [1]  →  1
 *
 * Constraints:
 *  - 1 <= nums.length <= 3 * 10^4
 *  - -3 * 10^4 <= nums[i] <= 3 * 10^4
 *  - Each element appears twice except for one
 */
public class SingleNumber {

    public static int singleNumber(int[] nums) {
        // TODO: implement
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{2,2,1}));         // 1
        System.out.println(singleNumber(new int[]{4,1,2,1,2}));     // 4
        System.out.println(singleNumber(new int[]{1}));             // 1
        System.out.println(singleNumber(new int[]{0,1,0}));         // 1
        System.out.println(singleNumber(new int[]{-1,-1,-2}));      // -2
    }
}

/*
 * Key insight: XOR
 *  - x ^ x = 0  (same number cancels)
 *  - x ^ 0 = x  (zero is identity)
 *  XOR all numbers together → all pairs cancel → only the single number remains.
 */
