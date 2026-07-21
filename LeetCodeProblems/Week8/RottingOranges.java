package LeetCodeProblems.Week8;

/**
 * LeetCode #994 - Rotting Oranges
 *
 * You are given an m x n grid where:
 *   0 = empty cell
 *   1 = fresh orange
 *   2 = rotten orange
 *
 * Every minute, a rotten orange spreads to adjacent (4-directional) fresh oranges.
 * Return the minimum number of minutes until no fresh orange remains.
 * Return -1 if it is impossible.
 *
 * Example 1:
 *  [[2,1,1],
 *   [1,1,0],
 *   [0,1,1]]
 *  Output: 4
 *
 * Example 2:
 *  [[2,1,1],
 *   [0,1,1],
 *   [1,0,1]]
 *  Output: -1  (bottom-left fresh orange is isolated)
 *
 * Example 3:
 *  [[0,2]]
 *  Output: 0  (no fresh oranges)
 *
 * Constraints:
 *  - m == grid.length, n == grid[i].length
 *  - 1 <= m, n <= 10
 *  - grid[i][j] is 0, 1, or 2
 *
 * Hint: Multi-source BFS — seed the queue with ALL rotten oranges at minute 0,
 *  then expand layer by layer. Each BFS level = 1 minute.
 */
public class RottingOranges {

    public static int orangesRotting(int[][] grid) {
        // TODO: implement
        return 0;
    }

    public static void main(String[] args) {
        // Expected: 4
        System.out.println(orangesRotting(new int[][]{
            {2,1,1},
            {1,1,0},
            {0,1,1}
        }));

        // Expected: -1  (isolated fresh orange)
        System.out.println(orangesRotting(new int[][]{
            {2,1,1},
            {0,1,1},
            {1,0,1}
        }));

        // Expected: 0  (no fresh oranges)
        System.out.println(orangesRotting(new int[][]{{0,2}}));

        // Expected: 0  (no oranges at all)
        System.out.println(orangesRotting(new int[][]{{0,0},{0,0}}));

        // Expected: 2
        System.out.println(orangesRotting(new int[][]{
            {2,1,1},
            {1,1,1},
            {0,1,2}
        }));
    }
}
