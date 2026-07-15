package LeetCodeProblems;

import java.util.HashSet;

public class FindPairForTarget {
    public static void main(String[] args) {
        int[] arr = new int[100_000];
        for (int i = 0; i < arr.length; i++) arr[i] = i + 100; // no two elements sum to target
        arr = new int[]{1,2,3,4,5,5};
        int target = 10;
        boolean result; 
        long startTime;
        long stopTime;
        findPair(arr,target);

        startTime = System.nanoTime();
        result = findPair(arr,target);
        stopTime = System.nanoTime();
        System.out.println("Mine: " + result);
        System.out.println("Mine: " + (stopTime - startTime));
    }

    public static boolean findPair(int[] arr, int target){

        HashSet<Integer> seen = new HashSet<>();
        for (int num : arr) {
            if(seen.contains(target-num)) return true;
            else seen.add(num);
        }
        return false;
    }
}