package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #417 - Pacific Atlantic Water Flow
 *
 * Given an m x n matrix of non-negative integers representing the height of each
 * cell, water can flow to a neighboring cell (up/down/left/right) if the neighbor's
 * height is less than or equal to the current cell's height.
 *
 * Water flows off the top and left edges into the Pacific Ocean.
 * Water flows off the bottom and right edges into the Atlantic Ocean.
 *
 * Return a list of coordinates [r, c] where water can flow to BOTH oceans.
 *
 * Key insight: instead of simulating water flowing down from every cell,
 * do multi-source BFS *uphill* from each ocean's border simultaneously.
 * Cells reachable from both BFS runs are the answer.
 *
 * Example 1:
 *  heights = [[1,2,2,3,5],
 *             [3,2,3,4,4],
 *             [2,4,5,3,1],
 *             [6,7,1,4,5],
 *             [5,1,1,2,4]]
 *  Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
 *
 * Example 2:
 *  heights = [[1]]
 *  Output: [[0,0]]
 *
 * Constraints:
 *  - m == heights.length
 *  - n == heights[r].length
 *  - 1 <= m, n <= 200
 *  - 0 <= heights[r][c] <= 10^5
 */
public class PacificAtlantic {

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        // TODO: implement
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        int[][] h1 = {
            {1,2,2,3,5},
            {3,2,3,4,4},
            {2,4,5,3,1},
            {6,7,1,4,5},
            {5,1,1,2,4}
        };
        // Expected: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
        System.out.println(pacificAtlantic(h1));

        // Expected: [[0,0]]
        System.out.println(pacificAtlantic(new int[][]{{1}}));

        // 1x4: all cells flow to both
        // Pacific: left edge (col 0); Atlantic: right edge (col 3)
        // All heights equal → water can flow either direction
        System.out.println(pacificAtlantic(new int[][]{{1,1,1,1}}));
    }
}
