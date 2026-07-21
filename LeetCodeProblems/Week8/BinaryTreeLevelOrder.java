package LeetCodeProblems.Week8;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * LeetCode #102 - Binary Tree Level Order Traversal
 *
 * Given the root of a binary tree, return the level order traversal
 * of its nodes' values (i.e., from left to right, level by level).
 *
 * Example 1:
 *       3
 *      / \
 *     9  20
 *        / \
 *       15   7
 *  Input: root = [3,9,20,null,null,15,7]
 *  Output: [[3],[9,20],[15,7]]
 *
 * Example 2:
 *  Input: root = [1]
 *  Output: [[1]]
 *
 * Example 3:
 *  Input: root = []
 *  Output: []
 *
 * Constraints:
 *  - Number of nodes: [0, 2000]
 *  - -1000 <= Node.val <= 1000
 */
public class BinaryTreeLevelOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if(root == null) return levels;

        Deque<TreeNode> queue =  new ArrayDeque<>(); 
        queue.addFirst(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level =  new ArrayList<>();
            for(int i=0; i < levelSize;i++){
                TreeNode curr = queue.removeLast(); 
                level.add(curr.val);
                if(curr.left != null) queue.addFirst(curr.left);
                if(curr.right != null) queue.addFirst(curr.right);                
            }
            levels.add(level);
        }
        return levels;
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
        // Expected: [[3],[9,20],[15,7]]
        System.out.println(levelOrder(build(3, 9, 20, null, null, 15, 7)));

        // Expected: [[1]]
        System.out.println(levelOrder(build(1)));

        // Expected: []
        System.out.println(levelOrder(null));

        // Expected: [[1],[2,3],[4,5]]
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        System.out.println(levelOrder(build(1, 2, 3, 4, 5)));

        System.out.println(levelOrder(build(1, 2, 3, 4, 5, 6, 7)));

        // expected: [[1], [2], [3]]
        System.out.println(levelOrder(build(1, 2, null, 3)));

        //expected [[1],[2],[3],[4,5]]
        System.out.println(levelOrder(build(1, null, 2, null, 3, 4, 5)));
    }
}
