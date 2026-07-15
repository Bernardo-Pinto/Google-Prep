package LeetCodeProblems;

public class MinWindowSubstring {
    public static String minWindow(String s, String t) {
        // substring must include every char of t exactly once, in any order
        // we want the minimum substring
        // a substring is continuous

        // if it doesnt exist, return ""
        if(t.length() > s.length()) return "";

        //initialize an array with size of the english alphabet
        //  with the count of the character in t
        int[] freq = new int[128];
        for (char c : t.toCharArray()) freq[c]++;
        
        int left  = 0;
        int right = 0;
        int minLength = s.length()+1;
        int startIndex = 0;
        int count = t.length(); //number of characters needed to complete t

        while(right<s.length()){
            char c = s.charAt(right);
            //if amount of c needed is higher than 0, decrease count
            if(freq[c] > 0) count--;
            // decrease the amount needed in the array, even if it goes negative (we have extra of this letter)
            freq[c]--;
            
            // if count is 0, that means we have t in our substring
            while(count == 0){
                // since count is 0, we might have a new minLength
                if(right - left < minLength){
                    startIndex = left;
                    minLength = right - left;
                }
                char charAtLeft = s.charAt(left);
                // increase the amount needed; if it goes above 0, we lost a required char
                freq[charAtLeft]++;
                if(freq[charAtLeft] > 0) count++;
                //move left: this is needed, because we are looking for the minimum window
                // and we already have a window that has t.
                // No window can be shorter than the one we found if we dont move left
                left++;
            }
            right++;
        }
        if(minLength == s.length()+1) return ""; //no minLength was found inside the loop, meaning it does not exist
        return s.substring(startIndex,startIndex + minLength+1);
    }
    public static void main(String[] args) {
        String s = "ADOBECODEBANC"; 
        String t = "ABC";
        String s1 = "a"; 
        String t1 = "a";
        String s2 = "ACCCBAA"; 
        String t2 = "AC";
        System.out.println(minWindow(s, t));
        System.out.println(minWindow(s1, t1));
        System.out.println(minWindow(s2, t2));
    }
}
