package LeetCodeProblems.Week8;

/**
 * LeetCode #200 - Number of Islands
 *
 * Given an m x n 2D binary grid where '1' = land and '0' = water,
 * return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting
 * adjacent land cells horizontally or vertically.
 *
 * Example 1:
 *  [["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]]
 *  Output: 1
 *
 * Example 2:
 *  [["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]]
 *  Output: 3
 *
 * Constraints:
 *  - m == grid.length, n == grid[i].length
 *  - 1 <= m, n <= 300
 *  - grid[i][j] is '0' or '1'
 *
 * Hint: DFS/BFS from each unvisited '1', marking visited cells.
 *  Modifying the grid in-place (marking '1'→'0') avoids a visited array.
 */
public class NumberOfIslands {

    public static int numIslands(char[][] grid) {
        // TODO: implement
        return 0;
    }

    public static void main(String[] args) {
        // Expected: 1
        System.out.println(numIslands(new char[][]{
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        }));

        // Expected: 3
        System.out.println(numIslands(new char[][]{
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        }));

        // Expected: 1  (entire grid is one island)
        System.out.println(numIslands(new char[][]{
            {'1','1'},
            {'1','1'}
        }));

        // Expected: 4  (diagonal cells are NOT connected)
        System.out.println(numIslands(new char[][]{
            {'1','0','1'},
            {'0','1','0'},
            {'1','0','1'}
        }));

        // Expected: 0
        System.out.println(numIslands(new char[][]{
            {'0','0'},
            {'0','0'}
        }));
    }
}
