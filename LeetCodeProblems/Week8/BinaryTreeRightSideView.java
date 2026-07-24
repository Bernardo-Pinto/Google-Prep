package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #199 - Binary Tree Right Side View
 *
 * Given the root of a binary tree, imagine yourself standing on the right side
 * of it. Return the values of the nodes you can see ordered from top to bottom.
 *
 * Example 1:
 *      1
 *     / \
 *    2   3
 *     \   \
 *      5   4
 *  Input: root = [1,2,3,null,5,null,4]
 *  Output: [1,3,4]
 *
 * Example 2:
 *  Input: root = [1,2]
 *  Output: [1,2]   (2 is the only node on level 2)
 *
 * Example 3:
 *  Input: root = []
 *  Output: []
 *
 * Constraints:
 *  - The number of nodes is in [0, 100]
 *  - -100 <= Node.val <= 100
 */
public class BinaryTreeRightSideView {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static List<Integer> rightSideView(TreeNode root) {
        // TODO: implement
        return new ArrayList<>();
    }

    // --- helpers ---

    static TreeNode build(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < vals.length) {
            TreeNode node = q.poll();
            if (i < vals.length && vals[i] != null) { node.left = new TreeNode(vals[i]); q.add(node.left); }
            i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); q.add(node.right); }
            i++;
        }
        return root;
    }

    public static void main(String[] args) {
        // [1,3,4]
        System.out.println(rightSideView(build(1, 2, 3, null, 5, null, 4)));

        // [1,2]  — left-only child visible from right side on level 2
        System.out.println(rightSideView(build(1, 2)));

        // []
        System.out.println(rightSideView(null));

        // [1]
        System.out.println(rightSideView(build(1)));

        // Deep left-skewed: [1,2,3,4]
        //     1
        //    /
        //   2
        //  /
        // 3
        System.out.println(rightSideView(build(1, 2, null, 3)));
    }
}
