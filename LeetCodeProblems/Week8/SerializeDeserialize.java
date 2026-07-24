package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #297 - Serialize and Deserialize Binary Tree
 *
 * Design an algorithm to serialize a binary tree to a string and
 * deserialize that string back to the original tree structure.
 *
 * There is no restriction on your serialization format.
 * The tree must round-trip correctly: deserialize(serialize(root)) reproduces root.
 *
 * Example 1:
 *      1
 *     / \
 *    2   3
 *       / \
 *      4   5
 *  serialize → "1,2,null,null,3,4,null,null,5,null,null" (or your format)
 *  deserialize → original tree
 * 
 * BFS preorder: "1,2,3,null,null,4,5,null,null,null,null"
 *
 * Example 2:
 *  Input: root = []  →  serialize = "null", deserialize = null
 *
 * Constraints:
 *  - Number of nodes: [0, 10^4]
 *  - -1000 <= Node.val <= 1000
 *

 */
public class SerializeDeserialize {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static String serialize(TreeNode root) {

        Queue<Optional<TreeNode>> queue =  new ArrayDeque<>();
        queue.offer(Optional.ofNullable(root));

        StringBuilder sb =  new StringBuilder();
        while(!queue.isEmpty()){
            TreeNode node = queue.poll().orElse(null);
            sb.append(',');
            if(node == null) {
                sb.append("null");
                continue;
            }
            else{
                sb.append(node.val);
            }

            queue.add(Optional.ofNullable(node.left));
            queue.add(Optional.ofNullable(node.right));
        }

        sb.delete(0, 1);
        return sb.toString();
    }

    public static TreeNode deserialize(String data) {

        if(data.equals("null")) return null;

        Queue<String> tokens =  new LinkedList<>(Arrays.asList(data.split(",")));
        TreeNode root = new TreeNode(Integer.parseInt(tokens.poll()));
        Queue<TreeNode> nodes =  new LinkedList<>();
        nodes.offer(root);
        while(!nodes.isEmpty()){
            TreeNode node =  nodes.poll();
            String left = tokens.poll();
            String right =  tokens.poll();
            if(!left.equals("null")){
                node.left = new TreeNode(Integer.valueOf(left));
                nodes.offer(node.left);
            } 
            if(!right.equals("null")){
                node.right = new TreeNode(Integer.valueOf(right));
                nodes.offer(node.right);
            }
        }
        return root;
    }

    // ---------- helpers ----------

    static String inorder(TreeNode root) {
        if (root == null) return "null";
        return "(" + inorder(root.left) + " " + root.val + " " + inorder(root.right) + ")";
    }

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

    static void roundTrip(TreeNode original) {
        String serialized = serialize(original);
        TreeNode restored  = deserialize(serialized);
        System.out.println("Original:   " + inorder(original));
        System.out.println("Serialized: " + serialized);
        System.out.println("Restored:   " + inorder(restored));
        System.out.println("Match: " + inorder(original).equals(inorder(restored)));
        System.out.println();
    }

    public static void main(String[] args) {
        //      1
        //     / \
        //    2   3
        //       / \
        //      4   5
        roundTrip(build(1, 2, 3, null, null, 4, 5));

        // Single node
        roundTrip(build(1));

        // Null tree
        roundTrip(null);

        // Left-skewed
        //  1
        //   \
        //    2
        //     \
        //      3
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.right = new TreeNode(2);
        leftSkewed.right.right = new TreeNode(3);
        roundTrip(leftSkewed);
    }
}

/*
 * Hint: BFS (level-order) or preorder DFS both work.
 *  With DFS preorder: serialize writes val or "null", deserialize uses a queue of tokens.
 *  */
