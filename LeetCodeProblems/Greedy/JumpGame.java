package LeetCodeProblems.Greedy;

public class JumpGame {
    /*
    Jump Game — LeetCode 55 (Medium)

    Given an array nums where nums[i] is the maximum jump length from index i,
     return true if you can reach the last index from index 0, otherwise false.
    [2, 3, 1, 1, 4]  → true   (0→1→4 or 0→2→3→4)
    [3, 2, 1, 0, 4]  → false  (always land on index 3 which has 0)
    [1]              → true
    [0]              → true   (already at last index)
    [0, 1]           → false
    */

    public static boolean jumpGame(int[] arr){
        if(arr.length == 0) return false;
        int maxReach = 0;
        for(int i = 0;i < arr.length;i++){
            if(i>maxReach) return false;
            maxReach = Math.max(maxReach,i + arr[i]);
            if(maxReach >= arr.length-1) return true;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] t1 = new int[]{2, 3, 1, 1, 4};
        int[] t2 = new int[]{3, 2, 1, 0, 4};
        int[] t3 = new int[]{1};
        int[] t4 = new int[]{0};
        int[] t5 = new int[]{0,1};
        int[] t6 = new int[]{2,2,0,1};
        int[] t7 = new int[]{1, 1, 1, 1};
        int[] t8 = new int[]{3, 1, 1, 1, 1};
        int[] t9 = new int[]{3, 3, 1, 0, 4};
        // System.out.println("t1: (true): " + jumpGame(t1));
        // System.out.println("t2: (false): " + jumpGame(t2));
        // System.out.println("t3: (true): " + jumpGame(t3));
        // System.out.println("t4: (true): " + jumpGame(t4));
        // System.out.println("t5: (false): " + jumpGame(t5));
        // System.out.println("t6: (true): " + jumpGame(t6));

        System.out.println("-----Jump Game 2-----");
        System.out.println("t1: (2): " + jumpGame2(t1));
        System.out.println("t2: (-1): " + jumpGame2(t2));
        System.out.println("t3: (0): " + jumpGame2(t3));
        System.out.println("t4: (0): " + jumpGame2(t4));
        System.out.println("t5: (-1): " + jumpGame2(t5));
        System.out.println("t6: (2): " + jumpGame2(t6));
        System.out.println("t7: (3): " + jumpGame2(t7));
        System.out.println("t8: (2): " + jumpGame2(t8));
        System.out.println("t9: (2): " + jumpGame2(t9));
    }


    /*
    Jump Game 2
    Same setup, but return the minimum number of jumps to reach the last index.
    You can always assume it's reachable.
        int[] t1 = new int[]{2, 3, 1, 1, 4};
        int[] t2 = new int[]{3, 2, 1, 0, 4};
        int[] t3 = new int[]{1};
        int[] t4 = new int[]{0};
        int[] t5 = new int[]{0,1};
        int[] t6 = new int[]{2,2,0,1};
        int[] t7 = new int[]{1, 1, 1, 1};
        int[] t8 = new int[]{3, 1, 1, 1, 1};
        int[] t9 = new int[]{3, 3, 1, 0, 4};
    */
    public static int jumpGame2(int[] arr){
        if(arr.length <= 1) return 0;
        int maxReach=0;
        int endReachIndex = 0;
        int jumps = 0;
        for(int i = 0;i < arr.length;i++){
            maxReach = Math.max(maxReach, (arr[i]+i));
            if(endReachIndex == i) {
                endReachIndex = maxReach;
                jumps++;
                if(maxReach >= arr.length-1) return jumps;
            }
        }
        return -1;
    }
}
