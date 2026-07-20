package LeetCodeProblems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindAnagrams {
    public static List<Integer> findAnagrams(String s, String p) {
        HashMap<Character,Integer> freqM = new HashMap<>();
        int count = p.length();
        for(int i=0;i<p.length();i++){
            freqM.merge(p.charAt(i),1,(a,b) -> a+b);
        }

        List<Integer> indexes = new ArrayList<>();
        int pSize = p.length();
        char[] sChars =  s.toCharArray();
        for(int i=0;i<sChars.length;i++){
            int startIndex = i-(pSize-1);
            if(freqM.containsKey(sChars[i])){
                if(freqM.get(sChars[i])>0)count--;
                if(count == 0) indexes.add(startIndex);
                freqM.merge(sChars[i],-1,(a,b)->a+b);
            }
            if(i>=pSize-1){
                char c  = sChars[startIndex]; 
                if(freqM.containsKey(c)){
                    freqM.merge(c,1,(a,b)->a+b);
                    if(freqM.get(c) > 0) count++;
                }
            }
        }
        return indexes;
    }
    public static void main(String[] args) {
        String s1 = "abab";
        String p1 = "ab";
        String s2 = "cbaebabacd";
        String p2 = "abc";
        //System.out.println(findAnagrams(s1, p1));
        System.out.println(findAnagrams(s2, p2));
    }
}
