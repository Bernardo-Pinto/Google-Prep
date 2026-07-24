package LeetCodeProblems.Week8;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;


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
        Queue<int[]> queue = new ArrayDeque<>();
        int totalNormalOranges = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                if(grid[i][j] == 1) totalNormalOranges++;
                else if(grid[i][j] == 2) queue.add(new int[]{i,j});
            }
        }
        if(totalNormalOranges == 0) return 0;
        if(queue.size() == 0) return -1;

        //BFS from all rotten "at once"
        boolean[][] seen =  new boolean[grid.length][grid[0].length];
        //minutes -1 because we consider the very first iteration with the seeded rotten oranges
        // to be the "start" of the state: minute 0, with all rotten oranges explored
        int minutes = -1;
        while (!queue.isEmpty()) {

            minutes++;
            int orangesInThisMinute = queue.size();
            for(int k=0;k<orangesInThisMinute;k++){
                int[] pos = queue.poll();
                int i = pos[0];
                int j = pos[1];
    
                if(seen[i][j]) continue; //might have duplicates in queue and been updated since then 
                seen[i][j] = true;

                if(grid[i][j] == 1) totalNormalOranges--;

                if(inBounds(grid, i, j+1) && shouldExplore(grid, seen, i, j+1)) 
                    queue.add(new int[]{i,j+1}); //right
                if(inBounds(grid, i+1, j) && shouldExplore(grid, seen, i+1, j)) 
                    queue.add(new int[]{i+1,j}); //down
                if(inBounds(grid, i, j-1) && shouldExplore(grid, seen, i, j-1)) 
                    queue.add(new int[]{i,j-1}); // left
                if(inBounds(grid, i-1, j) && shouldExplore(grid, seen, i-1, j)) 
                    queue.add(new int[]{i-1,j}); // up
            }
        }
        
        return totalNormalOranges == 0 ? minutes : -1;
    }

    private static boolean inBounds(int[][] grid, int i, int j){
        return i < grid.length && i >= 0 && j < grid[i].length && j >= 0;
    }

    private static boolean shouldExplore(int[][] grid, boolean[][] seen, int i, int j){
        return !seen[i][j] && grid[i][j] == 1;
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
