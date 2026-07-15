package LeetCodeProblems;

import java.util.HashMap;
import java.util.HashSet;

public class LongestSubStringWithoutRepeatingChar {
    public int lengthOfLongestSubstring(String s) {
        //bad solution
        // if(s.length() < 2) return s.length();
        // int window = 1;
        // HashSet<Character> seen = new HashSet<>();
        // while(window<=s.length()){
        //     boolean fits = false;
        //     for(int i=0;i<=s.length()-window;i++){
        //         char[] curr = s.substring(i,i+window).toCharArray();
        //         seen.clear();
        //         for(char c : curr){
        //             if(seen.contains(c)){
        //                 i+= s.substring(i,i+window).indexOf(c);
        //                 break;
        //             } else seen.add(c);
        //         }
        //         if(seen.size() == window) {
        //             fits = true;
        //             break;
        //         }
        //     }
        //     if(!fits) return window-1;
        //     else window++;
        // }
        // return window-1;

        //good solution
        int left = 0, max = 0;
        HashMap<Character, Integer> lastSeen = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;  // jump left past the duplicate
            }
            lastSeen.put(c, right);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
