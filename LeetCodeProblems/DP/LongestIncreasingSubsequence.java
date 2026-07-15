package LeetCodeProblems.DP;

import java.util.Arrays;

public class LongestIncreasingSubsequence {
    /*
    1. Define the subproblem:
        - What does dp[i][j] represent? : The length of the longest path at (i,j)
    2. What is the recurrence?
        -  how does dp[i][j] depend on smaller states? : at i=0 length is 0 for all j. at i = 1, length is either 0 or 1
    3. Identify base cases
        - What are the smallest inputs where the answer is trivially known? : a single number is length 1
    4. Determine the answer
        - Is it dp[n]? dp[m][n]? max(dp[i][j])? : Probably dp[n] or dp[m][n], we accumulate length
    5. Check the order
        - Fill from left-to-right, right-to-left, bottom-up, etc? : probably left to right, we accumulate length as long as next number is bigger
        - Make sure each cell depends on already computed cells: length of first number is length of 0 + 1
     */

    /*
    Given an integer array, return the length of the longest strictly increasing subsequence.
    [10, 9, 2, 5, 3, 7, 101, 18]  → 4  ([2,3,7,101] or [2,5,7,101])
    [0, 1, 0, 3, 2, 3]            → 4  ([0,1,2,3])
    [7, 7, 7, 7]                  → 1
    [1]                           → 1


    <0,1,0,3,2,3>
     1 2 1 2 2 2 
     - 2 1 3 3 3
     - - 1 3 3 3
     - - - 3 3 3
     - - - - 3 4

    */
    public static int lISeq(int[] arr){
        int[] dp = new int[arr.length];
        for(int i=0;i<dp.length;i++){
            dp[i] = 1;
        }
        for(int i=1;i<arr.length;i++){
            int N=arr[i-1];
            for(int j=i;j<arr.length;j++){
                if(N < arr[j]){
                    dp[j] = Math.max(dp[i-1] + 1, dp[j]);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    public static void main(String[] args) {
        int[] t1 = new int[]{10,9,2,5,3,7,101,18};
        int[] t2 = new int[]{0,1,0,3,2,3};
        int[] t3 = new int[]{7,7,7,7};
        int[] t4 = new int[]{1};
        System.out.println("(4) : " + lISeq(t1));
        System.out.println("(4) : " + lISeq(t2));
        System.out.println("(1) : " + lISeq(t3));
        System.out.println("(1) : " + lISeq(t4));
        int[] t5 = new int[]{0,1,2,0,1,2,3,0,1,2,3,4};
        System.out.println("(5) : " + lISeq(t5));
        int[] t6 = new int[]{0,1,2,0,1,2,3,4};
        System.out.println("(5) : " + lISeq(t6));
        int[] t7 = new int[]{0,1,2,0,0,0,3,4};
        System.out.println("(5) : " + lISeq(t7));
    }
}
