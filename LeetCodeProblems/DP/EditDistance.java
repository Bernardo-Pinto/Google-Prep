package LeetCodeProblems.DP;

public class EditDistance {
    /*
    horse -> ros  = 3

      "" h o r s e
    "" 0 1 2 3 4 5
    r  1 1 2 2 3 4
    o  2 2 1 2 3 4
    s  3 3 2 2 2 3

    reverse path = es,(ss),ro,(oo),hr
    path = hr -> replace with r,ro -> delete, es-> delete
    
    */
    public static int editDistance(String word, String target){
        int[][] dp = new int[target.length()+1][word.length()+1];
        for(int i=1;i<=target.length();i++){
            dp[i][0] = dp[i-1][0] + 1;
        }
        for(int i=1;i<=word.length();i++){
            dp[0][i] = dp[0][i-1] + 1;
        }

        for(int i=1;i<=word.length();i++){
            for(int j=1;j<=target.length();j++){
                if(word.charAt(i-1) == target.charAt(j-1)){
                    dp[j][i] = dp[j-1][i-1];
                } else {
                    int distance = Math.min(dp[j-1][i-1], dp[j-1][i]);
                    distance = Math.min(distance, dp[j][i-1]);
                    dp[j][i] = distance + 1;
                }
                
            }
        }
        return dp[target.length()][word.length()];
    }

    public static void main(String[] args) {
        String w1 = "horse";
        String t1 = "ros";
        System.out.println("Test 1 (3) : " + editDistance(w1, t1));
        String w2 = "intention";
        String t2 = "execution";
        System.out.println("Test 2 (5) : " + editDistance(w2, t2));
        String w3 = "";
        String t3 = "abc";
        System.out.println("Test 3 (3) : " + editDistance(w3, t3));
        String w4 = "abc";
        String t4 = "abc";
        System.out.println("Test 4 (0) : " + editDistance(w4, t4));
    }
}
