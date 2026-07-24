package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #105 - Construct Binary Tree from Preorder and Inorder Traversal
 *
 * Given two integer arrays preorder and inorder where:
 *  - preorder is the preorder traversal of a binary tree
 *  - inorder is the inorder traversal of the same tree
 * Construct and return the binary tree.
 *
 * Key insight:
 *  - preorder[0] is always the root
 *  - Find root in inorder → everything to its left is the left subtree,
 *    everything to its right is the right subtree
 *  - Recurse
 *
 * Example 1:
 *  preorder = [3,9,20,15,7]
 *  inorder  = [9,3,15,20,7]
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
        // TODO: implement
        return null;
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
