package LeetCodeProblems.Week8;

import java.util.ArrayDeque;
import java.util.Queue;

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
        Queue<int[]> queue =  new ArrayDeque<>();
        int numIslands = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                if(grid[i][j] == '0') continue;
                numIslands++; // found land, increase island count
                queue.add(new int[]{i,j});
                while(!queue.isEmpty()){
                    int[] pos = queue.poll();
                    int row = pos[0];
                    int col = pos[1];
                    boolean inBounds = row < grid.length && row >=0;
                    inBounds = inBounds && col >=0 && col < grid[row].length; 
                    if(!inBounds || grid[row][col] == '0') continue;
                    
                    //this grid posision is land ('1'). explore down,right directions while they are land, 
                    // and set them to water so we dont process them again.
                    grid[row][col] = '0';
                    queue.add(new int[]{row+1,col}); // down
                    queue.add(new int[]{row,col+1}); // right
                    //don't add up and left, since that is where we came from, and they are marked as water now
                }
            }
        }
        return numIslands;
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

        // Expected: 5  (diagonal cells are NOT connected — each '1' is isolated)
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
