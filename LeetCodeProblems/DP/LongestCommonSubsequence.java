package LeetCodeProblems.DP;

public class LongestCommonSubsequence {

    /*
        s1 = abcde
        s2 = ace

          "" a b c d e
        "" 0 0 0 0 0 0 
        a  0 1 1 1 1 1
        c  0 1 1 2 2 2
        e  0 1 1 2 2 3
     */

    // N = length of word1, M = length of word2 
    // This function is O(M + N*2M) = O(NM)
    public static int lcs(String word1, String word2){
        String s1 = word1.length()>= word2.length() ? word1 : word2;
        String s2 = s1.equals(word1) ? word2 : word1;
        int[][] dp = new int[s1.length()+1][s2.length()+1];

        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public static void main(String[] args) {
        String s11 = "abc";
        String s12 = "abc";
        System.out.println("(3) " + lcs(s11, s12));
        String s21 = "abc";
        String s22 = "def";
        System.out.println("(0) " + lcs(s21, s22));
        String s31 = "abcba";
        String s32 = "abcbcba";
        System.out.println("(5) " + lcs(s31, s32));
        String s41 = "abcde";
        String s42 = "ace";
        System.out.println("(3) " + lcs(s41, s42));
        String s51 = "abjlm";
        String s52 = "almbj";
        System.out.println("(3) " + lcs(s51, s52));
    }
}
