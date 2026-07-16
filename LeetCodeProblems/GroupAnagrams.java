package LeetCodeProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<String> sortedStrings = new ArrayList<>();
        for(String s : strs){
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            sortedStrings.add(String.copyValueOf(arr));
        }
        //even faster than sorting the strings, build a freq arr of size 26 for each string
        // convert each arr to a string and use that as the key
        
        //we now have a list of the strings ordered, that share the same index with the orignal words
        HashMap<String,Integer> map = new HashMap<>();
        List<List<String>> result =  new ArrayList<>();
        for(int i=0;i<strs.length;i++){
            String word = strs[i];
            String sortedWord = sortedStrings.get(i);
            Integer index = map.get(sortedWord);
            if(index == null){
                map.put(sortedWord,result.size());
                List<String> list = new ArrayList<>();
                list.add(word);
                result.add(list);
            } else {
                result.get(index).add(word);
            }
        }

        return result;
    }
    public static void main(String[] args) {
        String[] strs1 = new String[]{"a"};
        String[] strs2 = new String[]{"eat","tea","tan","ate","nat","bat"};
        System.out.println(groupAnagrams(strs2));
    }
}
