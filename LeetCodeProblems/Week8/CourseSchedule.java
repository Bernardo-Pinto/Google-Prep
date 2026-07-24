package LeetCodeProblems.Week8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * LeetCode #207 - Course Schedule
 *
 * There are numCourses courses labeled 0..numCourses-1.
 * You are given prerequisites[i] = [a, b] meaning you must take course b before course a.
 * Return true if you can finish all courses, false if there is a cycle.
 *
 * Example 1:
 *  numCourses=2, prerequisites=[[1,0]]
 *  Output: true  (take 0 then 1)
 *
 * Example 2:
 *  numCourses=2, prerequisites=[[1,0],[0,1]]
 *  Output: false  (0 requires 1 and 1 requires 0 — cycle)
 *
 * Example 3:
 *  numCourses=4, prerequisites=[[1,0],[2,0],[3,1],[3,2]]
 *  Output: true
 *
 * Constraints:
 *  - 1 <= numCourses <= 2000
 *  - 0 <= prerequisites.length <= 5000
 *  - prerequisites[i].length == 2
 *  - 0 <= a, b < numCourses
 *  - No duplicate prerequisites
 *
 * Hint: This is cycle detection on a directed graph.
 *  - DFS with 3-color marking (white/gray/black): gray = in current DFS stack
 *  - OR Kahn's algorithm (topological sort): if processed nodes < numCourses → cycle
 */
public class CourseSchedule {

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer,List<Integer>> depen = new HashMap<>();
        for(int i=0;i<prerequisites.length;i++){
            if(!depen.containsKey(prerequisites[i][0])){
                List<Integer> list =  new ArrayList<>();
                list.add(prerequisites[i][1]);
                depen.put(prerequisites[i][0],list);
            }else {
                List<Integer> list = depen.get(prerequisites[i][0]);
                list.add(prerequisites[i][1]);
            }
        }
        HashSet<Integer> visited =  new HashSet<>();
        for(int i=0;i<prerequisites.length;i++){
            HashSet<Integer> path =  new HashSet<>();
            if(isCyclic(prerequisites[i][0], depen, path, visited)) return false;
        }

        return true;
    }

    private static boolean isCyclic(int course, HashMap<Integer,List<Integer>> depen, 
        HashSet<Integer> path, HashSet<Integer> visited){
            if(path.contains(course)) return true;
            if(visited.contains(course)) return false;
            path.add(course);

            if(!depen.containsKey(course)) return false;
            List<Integer> dependencies = depen.get(course);
            for(Integer i : dependencies){
                if(isCyclic(i, depen, path, visited)) return true;
            }
        path.remove(course);
        visited.add(course);
        return false;
    }

    public static void main(String[] args) {
        // Expected: true
        System.out.println(canFinish(2, new int[][]{{1, 0}}));

        // Expected: false
        System.out.println(canFinish(2, new int[][]{{1, 0}, {0, 1}}));

        // Expected: true  (0→1→3, 0→2→3)
        System.out.println(canFinish(4, new int[][]{{1,0},{2,0},{3,1},{3,2}}));

        // Expected: false  (0→1→2→0)
        System.out.println(canFinish(3, new int[][]{{1,0},{2,1},{0,2}}));

        // Expected: true  (no prerequisites)
        System.out.println(canFinish(1, new int[][]{}));

        // Expected: true  (5 courses, no cycle)
        System.out.println(canFinish(5, new int[][]{{1,4},{2,4},{3,1},{3,2}}));
    }
}
