package Problems.DP;

public class ClimbingStairs {
    int[] memo; 
    public int climbStairs(int n) {
        if(n == 1) return 1;
        if(n == 0) return -1;
        memo = new int[n];
        memo[0] = 1;
        memo[1] = 2;
        return climb(n);
    }

    private int climb(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        int i = n-1;
        if(memo[i] != 0) return memo[i];
        else memo[i] = climb(i) + climb(i-1);
        return memo[i];
    }
}
