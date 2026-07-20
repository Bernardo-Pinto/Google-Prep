package LeetCodeProblems;

import java.util.Arrays;

public class NonOverlappingIntervals {
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[1],b[1]));
        int collisions = 0;
        int n = intervals.length;
        int prevEnd = intervals[0][1];
        for(int j=1;j<n;j++){
            if(prevEnd > intervals[j][0]){
                collisions++;
            }else{
                prevEnd = intervals[j][1];
            }
        }
        return collisions;
    }

    public static void main(String[] args) {
        //output = 1
        int[][] t1 = new int[][]{new int[]{1,2}, new int[]{2,3}, new int[]{3,4}, new int[]{1,3}};

        //output = 0
        int[][] t2 = new int[][]{new int[]{3,5}, new int[]{0,3}};

        //output = 1
        int[][] t3 = new int[][]{new int[]{3,5}, new int[]{0,4}};

        //output = 1
        int[][] t4 = new int[][]{new int[]{3,5}, new int[]{0,4},new int[]{1,3}};

        //output = 2
        int[][] t5 = new int[][]{new int[]{2,5}, new int[]{0,4},new int[]{1,3}};

        //output = 2
        int[][] t6 = new int[][]{new int[]{1,100}, new int[]{11,22},new int[]{1,11}, new int[]{2,12}};

        System.out.println(eraseOverlapIntervals(t1));
        System.out.println(eraseOverlapIntervals(t2));
        System.out.println(eraseOverlapIntervals(t3));
        System.out.println(eraseOverlapIntervals(t4));
        System.out.println(eraseOverlapIntervals(t5));
        System.out.println(eraseOverlapIntervals(t6));
    }
}
