package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #105 - Construct Binary Tree from Preorder and Inorder Traversal
 *
 * Given two integer arrays preorder and inorder where:
 *  - preorder is the preorder traversal of a binary tree
 *  - inorder is the inorder traversal of the same tree
 * Construct and return the binary tree.
 * tree: 1->2,1->3, 2->4, 2->5, 3->6
 * pre: 1,2,4,5,3,6,7
 * in: 4,2,5,1,6,3,7
 * 
 *       1
 *     /   \
 *    2     3
 *   /\    / \
 *  4  5  6   7
 * 
 * Example 1:
 *  preorder = [3,9,20,15,7]
 *  inorder  = [9,3,15,20,7]
 * Q: 3 -> take 3 from Q and take 9 as left and 20 as right from preorder. Add them to Q. Do:
 *      1- 9 exists before 3 in inorder. true
 *      2- 20 exists after 3 in inorder. true
 *      3- if both are true, set 9 and 20 to left and right of 3.
 * Q: 9,20 -> take 9 from Q and 15 and 7 from preorder. Add them to Q. Do:
 *      1- 15 exists before 9 in inorder. FALSE. Skip 9
 *      2- 7 exists after 9 in inorder. Skipped from before
 * Q 20,15,7 -> take 20 from Q. use existing 15 and 7 from before. Do:
 *      1- 15 exists before 20 in inorder. true
 *      2- 7 exists after 20 in inorder. true
 *      3- if both are true, set 15 and 7 to left and right of 20.
 * Q 15,7 -> take 15 from Q. "Seen" has the same size as inorder.length. return root.
 * 
 * 
 *  Output:
 *      3
 *     / \
 *    9  20
 *       / \
 *      15   7
 *
 * Example 2:
 *  preorder = [-1]
 *  inorder  = [-1]
 *  Output: [-1]
 *
 * Constraints:
 *  - 1 <= preorder.length <= 3000
 *  - preorder.length == inorder.length
 *  - -3000 <= preorder[i], inorder[i] <= 3000
 *  - preorder and inorder consist of unique values
 *  - Each value of inorder also appears in preorder
 *  - preorder is guaranteed to be the preorder traversal of the tree
 *  - inorder is guaranteed to be the inorder traversal of the tree
 */
public class ConstructBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode build(int[] pre, int preStart, int preEnd,
                                   int[] in,  int inStart,  int inEnd) {
        // Base case: empty subarray — no node to create
        if (preStart > preEnd) return null;

        // pre[preStart] is always the root of this subtree
        TreeNode root = new TreeNode(pre[preStart]);

        // Find root in inorder to split left / right subtrees
        int mid = inStart;
        while (in[mid] != root.val) mid++;

        // Everything in in[inStart..mid-1] is the left subtree
        // Everything in in[mid+1..inEnd]   is the right subtree
        int leftSize = mid - inStart;

        // Left subtree occupies the next leftSize elements in preorder
        root.left  = build(pre, preStart + 1, preStart + leftSize, in, inStart, mid - 1);
        // Right subtree occupies whatever is left in preorder after that
        root.right = build(pre, preStart + leftSize + 1, preEnd, in, mid + 1, inEnd);
        return root;
    }

    // --- helpers ---

    static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static void main(String[] args) {
        // Expected inorder: [9,3,15,20,7], preorder: [3,9,20,15,7]
        TreeNode t1 = buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
        System.out.println("preorder:  " + preorder(t1));   // [3,9,20,15,7]
        System.out.println("inorder:   " + inorder(t1));    // [9,3,15,20,7]

        // Single node
        TreeNode t2 = buildTree(new int[]{-1}, new int[]{-1});
        System.out.println("preorder:  " + preorder(t2));   // [-1]

        // Left-skewed: preorder=[1,2,3], inorder=[3,2,1]
        TreeNode t3 = buildTree(new int[]{1,2,3}, new int[]{3,2,1});
        System.out.println("preorder:  " + preorder(t3));   // [1,2,3]
        System.out.println("inorder:   " + inorder(t3));    // [3,2,1]

        // Right-skewed: preorder=[1,2,3], inorder=[1,2,3]
        TreeNode t4 = buildTree(new int[]{1,2,3}, new int[]{1,2,3});
        System.out.println("preorder:  " + preorder(t4));   // [1,2,3]
        System.out.println("inorder:   " + inorder(t4));    // [1,2,3]
    }
}

/*
 * Key insight:
 *  - preorder[0] is always the root
 *  - Find root in inorder → everything to its left is the left subtree,
 *    everything to its right is the right subtree
 *  - Recurse
*/