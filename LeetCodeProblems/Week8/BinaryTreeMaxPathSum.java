package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #124 - Binary Tree Maximum Path Sum
 *
 * A path in a binary tree is a sequence of nodes where each pair of adjacent
 * nodes has an edge. A node can appear at most once. The path does not need
 * to pass through the root.
 *
 * Return the maximum sum of any non-empty path.
 *
 * Example 1:
 *     1
 *    / \
 *   2   3
 *  Output: 6  (2 → 1 → 3)
 *
 * Example 2:
 *    -10
 *    / \
 *   9  20
 *      / \
 *     15   7
 * -10->-1
 * -1->19->
 *  Output: 42  (15 → 20 → 7)
 *
 * Example 3:
 *   [-3]
 *  Output: -3  (single node, can't avoid it)
 *
 * Constraints:
 *  - Number of nodes: [1, 3*10^4]
 *  - -1000 <= Node.val <= 1000
 *
 */
public class BinaryTreeMaxPathSum {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    private static int maxSum;
    public static int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxPathSumHelper(root);
        return maxSum;
    }

    private static int maxPathSumHelper(TreeNode node){
        if(node == null) return 0;

        int left = Math.max(0,maxPathSumHelper(node.left));
        int right = Math.max(0,maxPathSumHelper(node.right));
        int allSum = node.val + left + right;
        maxSum = Math.max(maxSum, allSum);
        return node.val + Math.max(left, right);
    }

    // ---------- helpers ----------

    static TreeNode build(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left  = new TreeNode(vals[i]); q.offer(node.left);  } i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.offer(node.right); } i++;
        }
        return root;
    }

    public static void main(String[] args) {
        // Expected: 6  (2+1+3)
        System.out.println(maxPathSum(build(1, 2, 3)));

        // Expected: 42  (15+20+7)
        System.out.println(maxPathSum(build(-10, 9, 20, null, null, 15, 7)));

        // Expected: -3  (only node)
        System.out.println(maxPathSum(build(-3)));

        // Expected: 6  (path: 1->2->3 not possible since 3 is right child of 2, not connected to 1 that way)
        //    2
        //   / \
        //  1   3
        // Actually: left arm=1, right arm=3, node=2 -> 1+2+3=6
        System.out.println(maxPathSum(build(2, 1, 3)));
        
        // Expected: 48
        //      5
        //     / \
        //    4   8
        //   /   / \
        //  11  13   4
        //  / \       \
        // 7   2       1
        // Path: 7->11->4->5->8->13 = 48? Let's check: 7+11+4+5+8+13=48
        System.out.println(maxPathSum(build(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1)));

        System.out.println(maxPathSum(build(-1, -2, -3)));
        System.out.println(maxPathSum(build(3, null,-1,null,null, null,4)));
    }
}

/*

 * Hint: at each node you have 4 choices for a path that bends here:
 *  1. just the node itself
 *  2. node + left subtree arm
 *  3. node + right subtree arm
 *  4. left arm + node + right arm  (can't propagate this one upward)*/