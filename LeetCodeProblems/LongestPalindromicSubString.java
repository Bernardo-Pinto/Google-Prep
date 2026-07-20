package LeetCodeProblems;

public class LongestPalindromicSubString {
    public static String longestPalindrome(String s) {
        char[] arr = s.toCharArray();
        String longest = ""; 
        // int left = 0;
        // int right = arr.length-1;
        // int i = left;
        // int j = right;
        // while(right>=0){
        //     while(left<right){
        //         while(i<j){
        //             if(arr[i] == arr[j]){
        //                 i++;
        //                 j--;
        //             } else {
        //                 break;
        //             }
        //         }
        //         if(i>=j && arr[i] == arr[j]) longest = longest.length() > (right-left) ? longest : s.substring(left,right+1);
        //         left++;
        //         i=left;
        //         j=right;
        //     }
        //     right--;
        //     left = 0;
        //     i=left;
        //     j=right;
        // }

        // for(int c=1;c<arr.length-2;c++){
        //     String s1 = ""+arr[c];
        //     s1 = finder(arr,c-1,c+1,s1);
        //     longest = longest.length() > s1.length() ? longest : s1;
        //     if(arr[c] == arr[c+1]){
        //         String s2 = "" + arr[c] + arr[c+1];
        //         s2 = finder(arr, c-1, c+2, s2);
        //         longest = longest.length() > s2.length() ? longest : s2;
        //     }
        // }
        String pal = ""+arr[0];
        char prev = arr[0];
        int left = -1;
        int right = 1;
        for(int i=0;i<arr.length;i++){
            if(i+1<arr.length && prev == arr[i+1]){
                pal += prev;
                right++;
            }
            else {
                pal = finder(arr, left, right, pal);
                longest = longest.length() > pal.length() ? longest : pal;
                if(i+1<arr.length){
                    pal = ""+arr[i+1];
                    prev = arr[i+1];
                    left = i;
                    right = i+2;
                }
            }
        }
        return longest == "" ? s.charAt(0)+"" : longest;
    }

    public static String finder(char[] arr, int left, int right, String word){
        String palindrome = word;
        while(left>=0 && right<arr.length){
            if(arr[left] == arr[right]){
                palindrome = arr[left] + palindrome;
                palindrome += arr[right];
            } else break;
            left--;
            right++;
        }
        return palindrome;
    }

    public static void main(String[] args) {
        String s1 = "babad";
        String s2 = "aacabckacaa";
        String s3 = "abb";
        System.out.println(longestPalindrome(s1));
        System.out.println(longestPalindrome(s2));
        System.out.println(longestPalindrome(s3));
    }
}
