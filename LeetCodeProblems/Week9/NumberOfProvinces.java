package LeetCodeProblems.Week9;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

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
 * 
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
        int provinces = 0;
        Queue<Integer> queue =  new ArrayDeque<>();
        HashSet<Integer> explored = new HashSet<>();
        for(int i=0;i<isConnected[0].length;i++){
            if(explored.contains(i)) continue;
            provinces++;

            queue.add(i);
            while(!queue.isEmpty()){
                int city = queue.poll();
                if(explored.contains(city)) continue;
                explored.add(city);
                int[] connections = isConnected[city];
                for(int j=0;j<connections.length;j++){
                    if(explored.contains(j)) continue;
                    if(connections[j] == 1) {
                        queue.add(j);
                    }
                }
            }
        }
        return provinces;
    }

    public static void main(String[] args) {
        // Expected: 2
        System.out.println(findCircleNum(new int[][]{{1,1,0},{1,1,0},{0,0,1}}));

        // Expected: 3
        System.out.println(findCircleNum(new int[][]{{1,0,0},{0,1,0},{0,0,1}}));

        // All connected — Expected: 1
        System.out.println(findCircleNum(new int[][]{{1,1,1},{1,1,1},{1,1,1}}));
        
        //Expected 1
        System.out.println(findCircleNum(new int[][]{{1,1,0},{1,1,1},{0,1,1}}));

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
