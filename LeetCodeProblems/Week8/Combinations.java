package LeetCodeProblems.Week8;

import java.util.*;

/**
 * LeetCode #77 - Combinations
 *
 * Given two integers n and k, return all possible combinations of k numbers
 * chosen from the range [1, n] in any order.
 *
 * Example 1:
 *  n=4, k=2
 *  Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *
 * Example 2:
 *  n=1, k=1
 *  Output: [[1]]
 *
 * example 3:
 * n=3, k=2
 * (1,2,3) : (1,2),(1,3),(2,3)
 * ()
 * 
 *  * example 4:
 * n=4, k=3
 * (1,2,3,4) : (1,2,3),(1,2,4),(1,3,4),(2,3,4)
 * 
 *  * n=5, k=4
 * (1,2,3,4,5) : (1,2,3,4),(1,2,3,5),(1,2,4,5),(1,3,4,5),(2,3,4,5)
 * 

 * Constraints:
 *  - 1 <= n <= 20
 *  - 1 <= k <= n
 *
 * */
public class Combinations {

    static List<List<Integer>> result;
    public static List<List<Integer>> combine(int n, int k) {
        result = new ArrayList<>();
        List<Integer> level;
        for(int i = 1;i<=n-(k-1);i++){
            level =  new ArrayList<>();
            level.add(i);
            matcher(n, k, i+1,1, level);
        }
        return result;
    }
    private static void matcher(int n, int k,int start, int it, List<Integer> list){
        if(it == k) {
            result.add(new ArrayList<>(list));
            return;
        }
        
        for(int i=start;i<=n-(k-list.size())+1;i++){
            list.add(i);
            matcher(n, k, i+1,it+1, list);
            list.removeLast();
        }
    }

    // ---- reference solution ----

    public static List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int n, int k, int start, List<Integer> path, List<List<Integer>> res) {
        // base case: path is complete
        if (path.size() == k) {
            res.add(new ArrayList<>(path)); // snapshot — path is mutated, so copy it
            return;
        }

        // pruning: no point trying i if fewer than (k - path.size()) numbers remain
        int limit = n - (k - path.size()) + 1;
        for (int i = start; i <= limit; i++) {
            path.add(i);               // choose
            backtrack(n, k, i + 1, path, res);
            path.remove(path.size()-1); // un-choose (backtrack)
        }
    }

    public static void main(String[] args) {
        // Expected: 6 combinations
        List<List<Integer>> r1 = combine(4, 2);
        System.out.println("Count: " + r1.size() + " → " + r1);

        // Expected: [[1]]
        List<List<Integer>> r2 = combine(1, 1);
        System.out.println("Count: " + r2.size() + " → " + r2);

        // // Expected: C(5,3)=10
        List<List<Integer>> r3 = combine(5, 3);
        System.out.println("Count: " + r3.size() + " → " + r3);

        // // Expected: C(4,4)=1  → [[1,2,3,4]]
        List<List<Integer>> r4 = combine(4, 4);
        System.out.println("Count: " + r4.size() + " → " + r4);
    }
}
/*

Hint: Backtracking — start from a number `start`, try each number from
 *  start..n, recurse with start+1, backtrack.
 *  Pruning: if remaining candidates < still-needed slots, stop early.
 *    → only loop while i <= n - (k - path.size()) + 1
 
*/
