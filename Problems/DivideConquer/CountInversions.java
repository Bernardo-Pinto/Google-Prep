package Problems.DivideConquer;

import java.util.HashSet;

public class CountInversions {
    /*
    Given an array, count the number of "inversions" — pairs (i, j)
        where i < j but arr[i] > arr[j].

    [2, 4, 1, 3, 5] → 3 inversions: (2,1), (4,1), (4,3)
    [5, 4, 3, 2, 1] → 10 inversions (fully reversed)
    [1, 2, 3, 4, 5] → 0 inversions (already sorted)
    [2,1,3] -> 1 inversion
    [1,2,3] -> 0 inversions
    [3,2,1] -> 3 inversions 
    */

    //#region O(n^2) time and O(log n) stack + O(n)hashSet = O(n) solution
    public static int countInversions(int[] arr){
        return split(arr,0,arr.length-1, new HashSet<>());
    }

    private static int split(int[] arr, int min, int max, HashSet<Integer> seen){
        if(min>=max){
            return (seen.contains(min)) ? 0 : countLeft(arr, min);
        }
        int mid = min + (max-min)/2;
        seen.add(mid);
        return countLeft(arr,mid) 
        + split(arr, min, mid-1, seen)
        + split(arr, mid+1, max, seen);
    }

    private static int countLeft(int[] arr, int mid){
        int inversions = 0;
        int midValue = arr[mid]; //mid = j
        for(int i=mid-1;i>=0;i--){
            // all i < j
            if(arr[i]>midValue){
                inversions++;
            }
        }
        return inversions;
    }
    //#endregion

    //#region MergeSort Approach O(nlogn) time, O(n) space
    public static int countInversionsMergeSort(int[] arr){
        int r = splitMergeSort(arr, 0, arr.length, new int[arr.length]);
        return r;
    }

    private static int splitMergeSort(int[] arr, int min,int max, int[] aux){
        if(max-min<=1){
            return 0;
        }
        int mid = min + (max - min) / 2;
        int leftInversions = splitMergeSort(arr, min, mid, aux);
        int rightInversions =  splitMergeSort(arr, mid, max, aux);
        int myInversions = merge(arr, min, mid, max, aux);
        return myInversions+leftInversions+rightInversions;
    }

    private static int merge(int[] arr, int min, int mid, int max, int[] aux){
        for(int i = min;i<max;i++){
            aux[i] = arr[i];
        }

        int i = min;
        int j = mid;
        int k = min;
        int inversions = 0;
        while(i < mid || j < max){
            if(i >= mid){
                arr[k] = aux[j];
                j++;
            } else if(j>=max){
                arr[k] = aux[i];
                i++;
            } else if(aux[i]<= aux[j]){
                arr[k] = aux[i];
                i++;
            } else {
                arr[k] = aux[j];
                j++;
                inversions += mid-i;
            }
            k++;
        }
        return inversions;
    }


    //#endregion

    public static void main(String[] args){
        int[] test1 = new int[]{3,2,1}; //3
        int[] test2 = new int[]{1,2,3}; //0
        int[] test3 = new int[]{2,1,3}; //1
        int[] test4 = new int[]{2,3,1}; //2
        int[] test5 = new int[]{2, 4, 1, 3, 5}; //3
        int[] test6 = new int[]{5, 4, 3, 2, 1}; //10
        int[] test7 = new int[]{1, 2, 3, 4, 5}; //0
        System.out.println("test1 (3): "  + countInversionsMergeSort(test1));
        System.out.println("test2 (0): "  + countInversionsMergeSort(test2));
        System.out.println("test3 (1): "  + countInversionsMergeSort(test3));
        System.out.println("test4 (2): "  + countInversionsMergeSort(test4));
        System.out.println("test5 (3): "  + countInversionsMergeSort(test5));
        System.out.println("test6 (10): " + countInversionsMergeSort(test6));
        System.out.println("test7 (0): "  + countInversionsMergeSort(test7));
    }
}
