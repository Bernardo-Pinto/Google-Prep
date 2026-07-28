package LeetCodeProblems.Week9;

/**
 * LeetCode #547 - Number of Provinces  [MEDIUM]
 *
 * There are n cities. isConnected[i][j] = 1 means city i and city j are directly connected.
 * A province is a group of directly or indirectly connected cities (i.e., a connected component).
 * Return the total number of provinces.
 *
 * This is the canonical Union-Find / DSU problem.
 *
 * Example 1:
 *  isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 *  Output: 2  (cities 0&1 are one province; city 2 is alone)
 *
 * Example 2:
 *  isConnected = [[1,0,0],[0,1,0],[0,0,1]]
 *  Output: 3  (each city is its own province)
 *
 * Constraints:
 *  - 1 <= n <= 200
 *  - isConnected[i][j] == 1 or 0
 *  - isConnected[i][i] == 1  (self-connected)
 *  - isConnected[i][j] == isConnected[j][i]  (undirected)
 */
public class NumberOfProvinces {

    public static int findCircleNum(int[][] isConnected) {
        // TODO: implement with Union-Find
        return 0;
    }

    public static void main(String[] args) {
        // Expected: 2
        System.out.println(findCircleNum(new int[][]{{1,1,0},{1,1,0},{0,0,1}}));

        // Expected: 3
        System.out.println(findCircleNum(new int[][]{{1,0,0},{0,1,0},{0,0,1}}));

        // All connected — Expected: 1
        System.out.println(findCircleNum(new int[][]{{1,1,1},{1,1,1},{1,1,1}}));

        // Single city — Expected: 1
        System.out.println(findCircleNum(new int[][]{{1}}));
    }
}

/*
 * Union-Find pattern:
 *  - find(x): returns the root of x's component (with path compression)
 *  - union(x, y): merges the two components (by rank)
 *  - Count distinct roots at the end = number of provinces
 */
