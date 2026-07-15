package LeetCodeProblems.DP;

public class DistinctSubSequences {

    /*

    base cases:
    1- not equal: take value from  
    2- equal: take value from 

      "" a a
    "" 0 0 0
     a 0 1 1 
     a 0 2 2 
     a 0 3 3

      "" r a b b i t
    "" 0 0 0 0 0 0 0 
    r  0 1 0 0 0 0 0
    a  0 1 1 0 0 0 0
    b  0 1 1 1 1 0 0
    b  0 1 1 2 2 0 0
    b  0 1 1 3 3 0 0
    i  0 1 1 3 3 1 0
    t  0 1 1 3 3 1 1

      "" b a g
    "" 0 0 0 0
    b  0 1 0 0
    a  0 1 1 0
    b  0 1 1 0
    g  0 1 1 0
    b  0 1 1 0
    a  0 1 2 0
    g  0 1 2 1 

      "" b
    "" 0 0
     b 0 1
     b 0 2

      "" b a
    "" 0 0 0
     b 0 1 0
     b 0 2 0

      "" b a
    "" 0 0 0
     a 0 0 1
     b 0 1 0
     b 0 2 0

      "" b a
    "" 0 0 0
     b 0 1 0
     a 0 1 1
     b 0 1 1

      "" b a
    "" 0 0 0
     b 0 1 0
     a 0 1 1
     a 0 1 2
     b 0 1 2

      "" b a g = 4 times
    "" 0 0 0 0 
    g  0 0 0 1
    b  0 1 0 1
    b  0 1 0 1
    a  0 1 1 1
    a  0 1 2 1
    g  0 1 2 2
    
      "" b a g = 4 times
    "" 0 0 0 0 
    g  0 0 0 1
    b  0 1 1 1
    b  0 2 2 2
    a  0 2 3 3
    a  0 2 4 4
    g  0 2 4 5
    
    */

    public static int distinctSubSeq(String s, String t){
        int[][] dp = new int[t.length()+1][s.length()+1];
        for(int i = 0;i<s.length();i++) dp[0][i] = 1;
        for(int i=1;i<=t.length();i++){
            for(int j=1;j<=s.length();j++){
                dp[i][j] = dp[i][j-1];
                if(t.charAt(i-1) == s.charAt(j-1)){
                    if(dp[i-1][j-1] > 0){
                        dp[i][j] += dp[i-1][j-1];
                    }
                }
            }
        }
        return dp[t.length()][s.length()];
    }
    public static void main(String[] args) {
        String s1 = "aaa";
        String t1 = "aa";
        System.out.println("Test 1 (3) : " + distinctSubSeq(s1,t1));

        String s2 = "rabbbit";
        String t2 = "rabbit";
        System.out.println("Test 2 (3) : " + distinctSubSeq(s2,t2));

        String s3 = "abc";
        String t3 = "abc";
        System.out.println("Test 3 (1) : " + distinctSubSeq(s3,t3));

        String s4 = "abc";
        String t4 = "abcd";
        System.out.println("Test 4 (0) : " + distinctSubSeq(s4,t4));

        String s5 = "babgbag";
        String t5 = "bag";
        System.out.println("Test 5 (5) : " + distinctSubSeq(s5,t5));
    }
}
