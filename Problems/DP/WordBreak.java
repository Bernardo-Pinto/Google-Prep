package Problems.DP;

public class WordBreak {
    /*

    state i need to save: 
    how to combine states: 

    s = "leetcode",  wordDict = ["leet", "code"]         → true
    s = "applepenapple", wordDict = ["apple", "pen"]     → true
    s = "catsandog",  wordDict = ["cats", "dog", "sand", "and", "cat"] → false
    s = "cars",       wordDict = ["car", "ca", "rs"]     → true
    s = "abbc", ["a","b","c"]    -> true
    [null,null,null,null]
    */
    public static boolean wordBreak(String word, String[] dict){
        Boolean[] memo = new Boolean[word.length()+1];
        //return chooseNextWord(word, dict, 0, memo);
        return wordBreakBottomUp(word, dict);
    }

    private static boolean wordBreakBottomUp(String word, String[] dict){
        boolean[] dp = new boolean[word.length()+1];
        dp[0] = true;
        for(int i=0;i<word.length();i++){
            if(!dp[i]) continue;
            for(int j=0;j<dict.length;j++){
                if(i + dict[j].length() <= word.length()){
                    if(dict[j].equals(word.substring(i,i+dict[j].length()))){
                        dp[i+dict[j].length()] = true;
                    }
                }
            }
        }
        return dp[word.length()];
    }   
   // save index length to indicate that it is possible to reach that index with the previous characters fully filled up
    private static boolean chooseNextWord(String word, String[] dict, int length, Boolean[] memo){

        if(length == word.length()) return true;
        if(memo[length] != null) return memo[length]; 

        for(int i=0; i<dict.length; i++){
            if(dict[i].length() <= word.length()-length) {
                String sub = word.substring(length,length + dict[i].length());
                if(sub.equals(dict[i])){
                    if(chooseNextWord(word, dict,length+dict[i].length(), memo)) 
                        return true;
                }
            }
        }
        memo[length] = false;
        return false;
    }

   public static void main(String[] args) {
    String s1 = "leetcode";
    String[] d1 = new String[]{"leet", "code"};
    System.out.println("Test 1: leetcode (true) : " + wordBreak(s1, d1));
    String s2 = "applepenapple";
    String[] d2 = new String[]{"apple", "pen"};
    System.out.println("Test 2: applepenapple (true) : " + wordBreak(s2, d2));
    String s3 = "catsandog";
    String[] d3 = new String[]{"cats", "dog", "sand", "and", "cat"};
    System.out.println("Test 3: catsandog (false) : " + wordBreak(s3, d3));
    String s4 = "cars";
    String[] d4 = new String[]{"car", "ca", "rs"};
    System.out.println("Test 4: cars (true) : " + wordBreak(s4, d4));
    String s5 = "abcb";
    String[] d5 = new String[]{"a", "bb", "c"};
    System.out.println("Test 5: abcb (false) : " + wordBreak(s5, d5));
    String s6 = "abbc";
    String[] d6 = new String[]{"a", "b", "c"};
    System.out.println("Test 6: abbc (true) : " + wordBreak(s6, d6));
   }
}
