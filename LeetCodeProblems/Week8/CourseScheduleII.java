package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #210 - Course Schedule II
 *
 * There are numCourses courses labeled 0 to numCourses-1.
 * You are given prerequisites[i] = [a, b] meaning you must take course b before a.
 *
 * Return the ordering in which you should take all courses to finish them.
 * If it is impossible (cycle exists), return an empty array.
 *
 * This is topological sort — extension of Course Schedule I (#207).
 *
 * Example 1:
 *  numCourses = 2, prerequisites = [[1,0]]
 *  Output: [0,1]   (take 0 first, then 1)
 *
 * Example 2:
 *  numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 *  Output: [0,2,1,3] or [0,1,2,3]  (multiple valid orderings exist)
 *
 * Example 3:
 *  numCourses = 1, prerequisites = []
 *  Output: [0]
 *
 * Example 4 (impossible):
 *  numCourses = 2, prerequisites = [[1,0],[0,1]]
 *  Output: []
 *
 * Constraints:
 *  - 1 <= numCourses <= 2000
 *  - 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 *  - prerequisites[i].length == 2
 *  - 0 <= ai, bi < numCourses
 *  - ai != bi
 *  - No duplicate prerequisites
 */
public class CourseScheduleII {

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        // TODO: implement
        return new int[]{};
    }

    public static void main(String[] args) {
        // [0,1]
        System.out.println(Arrays.toString(findOrder(2, new int[][]{{1,0}})));

        // [0,1,2,3] or [0,2,1,3]
        System.out.println(Arrays.toString(findOrder(4, new int[][]{{1,0},{2,0},{3,1},{3,2}})));

        // [0]
        System.out.println(Arrays.toString(findOrder(1, new int[][]{})));

        // [] (cycle)
        System.out.println(Arrays.toString(findOrder(2, new int[][]{{1,0},{0,1}})));

        // [] (self-loop would be invalid input but test a 3-cycle)
        System.out.println(Arrays.toString(findOrder(3, new int[][]{{0,1},{1,2},{2,0}})));
    }
}
