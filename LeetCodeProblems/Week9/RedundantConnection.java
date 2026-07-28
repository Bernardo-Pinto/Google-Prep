package LeetCodeProblems.Week9;

/**
 * LeetCode #684 - Redundant Connection  [MEDIUM]
 *
 * An undirected graph of n nodes (labeled 1..n) was formed from a tree by adding one extra edge.
 * Given edges in the order they were added, return the extra edge that, if removed,
 * would make the graph a tree again.
 * If multiple answers exist, return the one that appears last in the input.
 *
 * Example 1:
 *  edges = [[1,2],[1,3],[2,3]]
 *  Output: [2,3]   (1-2-3 is a tree; [2,3] creates a cycle)
 *
 * Example 2:
 *  edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]
 *  Output: [1,4]   (adding [1,4] creates a cycle 1-2-3-4-1)
 *
 * Constraints:
 *  - n == edges.length
 *  - 3 <= n <= 1000
 *  - edges[i].length == 2
 *  - 1 <= ai < bi <= edges.length
 *  - ai != bi
 *  - No repeated edges
 *  - The graph is connected
 */
public class RedundantConnection {

    public static int[] findRedundantConnection(int[][] edges) {
        // TODO: implement with Union-Find
        return new int[]{};
    }

    public static void main(String[] args) {
        // Expected: [2,3]
        System.out.println(java.util.Arrays.toString(
            findRedundantConnection(new int[][]{{1,2},{1,3},{2,3}})));

        // Expected: [1,4]
        System.out.println(java.util.Arrays.toString(
            findRedundantConnection(new int[][]{{1,2},{2,3},{3,4},{1,4},{1,5}})));
    }
}

/*
 * Key insight: use Union-Find. Process edges one by one.
 * If an edge connects two nodes that are ALREADY in the same component → it's the redundant edge.
 */
