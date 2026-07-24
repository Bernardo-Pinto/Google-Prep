package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #236 - Lowest Common Ancestor of a Binary Tree
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes p and q.
 * The LCA is the lowest node that has both p and q as descendants
 * (a node can be a descendant of itself).
 *
 * Example 1:
 *          6
 *         / \
 *        2   8
 *       / \ / \
 *      0  4 7  9
 *        / \
 *       3   5
 *  LCA(2, 8) = 6
 *  LCA(2, 4) = 2
 *
 * Example 2:
 *  root = [2,1], p=2, q=1  →  LCA = 2
 *
 * 3:
 *  *       6
 *         / \
 *        2   8
 *       / \ / \
 *      0  4 7  9
 *        / \    \
 *       3   5   12
 *              /  \
 *             1   7
 * 
 * Constraints:
 *  - Number of nodes: [2, 10^5]
 *  - -10^9 <= Node.val <= 10^9
 *  - All Node.val are unique
 *  - p != q, and both p and q exist in the tree
 */
public class LowestCommonAncestor {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static HashSet<Integer> toFind;
    static TreeNode lca;
    static TreeNode lastAncestor;
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);        

        if(left != null && right != null) return root;
        return left != null ? left : right;
    }


    // ---------- helpers ----------

    static TreeNode build(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNode node = queue.poll();
            if (i < vals.length && vals[i] != null) { node.left  = new TreeNode(vals[i]); queue.offer(node.left);  } i++;
            if (i < vals.length && vals[i] != null) { node.right = new TreeNode(vals[i]); queue.offer(node.right); } i++;
        }
        return root;
    }

    static TreeNode find(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        TreeNode l = find(root.left, val);
        return l != null ? l : find(root.right, val);
    }

    public static void main(String[] args) {
        //          6
        //         / \
        //        2   8
        //       / \ / \
        //      0  4 7  9
        //        / \
        //       3   5
        TreeNode t1 = build(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);

        // Expected: 6
        System.out.println(lowestCommonAncestor(t1, find(t1,2), find(t1,8)).val);

        // Expected: 2
        System.out.println(lowestCommonAncestor(t1, find(t1,2), find(t1,4)).val);

        // Expected: 6 (LCA of leaves 0 and 9)
        System.out.println(lowestCommonAncestor(t1, find(t1,0), find(t1,9)).val);

        // root itself is p
        TreeNode t2 = build(2, 1);
        // Expected: 2
        System.out.println(lowestCommonAncestor(t2, find(t2,2), find(t2,1)).val);
    }
}
