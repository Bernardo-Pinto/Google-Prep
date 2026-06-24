package DataStructures.Week4;

import java.util.Arrays;

public class SortingAlgorithms {

    //#region HeapSort

    public static int[] heapSort(int[] arr){
        // create a max heap, then take max(root), put it at the end, decrease heapsize by 1 
        heapify(arr,arr.length);
        return arr;
    } 

    private static void heapify(int[] arr, int heapSize){
        // start from botton nodes, and sift them down
        // skip if they have no children
        //note that arr.length/2-1 is the first non child node
        for(int i = heapSize/2-1;i>=0;i--){
            moveDown(arr, i,heapSize);
        }
        for(int i = 0;i<arr.length-1;i++){
            int last = arr[heapSize-1];
            int max = arr[0];
            arr[heapSize-1] = max;
            arr[0] = last;
            heapSize -= 1;
            moveDown(arr, 0,heapSize);
        }
    }
    private static void moveDown(int[] arr, int index, int heapSize){
        int curr = arr[index];
        int maxChildIndex = getMaxChildIndex(arr, index, heapSize);
        while(maxChildIndex != -1){
            int maxChild = arr[maxChildIndex];
            if (maxChild<=curr) break;
            arr[index] = maxChild;
            arr[maxChildIndex] = curr;
            index = maxChildIndex;
            maxChildIndex = getMaxChildIndex(arr, index, heapSize);
        }
    }

    private static int getMaxChildIndex(int[] arr, int index, int heapSize){
        int leftChildIndex = getLeftChildIndex(index);
        if(leftChildIndex >= heapSize) return -1;

        int rightChildIndex = getRightChildIndex(index);
        if(rightChildIndex >= heapSize) return leftChildIndex;

        int leftChild = arr[leftChildIndex];
        int rightChild = arr[rightChildIndex];
        return leftChild >= rightChild ? leftChildIndex : rightChildIndex;
    }

    private static int getLeftChildIndex(int index){
        return (index*2) + 1;
    }

    private static int getRightChildIndex(int index){
        return (index*2) + 2;
    }


    //#endregion

    //#region MergeSort
    
    // Time: O(nlogn) all cases
    // Space: O(n) aux + O(log n) call stack
    // Stable: Yes
    public static void mergeSortInPlace(int[] arr){
        mergeSortHelperInplace(arr, new int[arr.length],0,arr.length);
    }
    
    private static void mergeSortHelperInplace(int[] arr, int[] aux, int min, int max){
        if(max-min <= 1){
            return;
        }else {
            int mid = min + (max-min) / 2;
            mergeSortHelperInplace(arr,aux,min,mid);
            mergeSortHelperInplace(arr,aux,mid,max);
            mergeInPlace(arr, aux, min,mid,max );
        }
    }
    private static void mergeInPlace(int[] arr, int[] aux, int min, int mid, int max){

        for(int i = min;i<max;i++){
            aux[i] = arr[i];
        }
        int i,j,k;
        i = k = min;
        j = mid;
        while(i < mid || j < max){

            if(i >= mid || (j < max && aux[j] < aux[i])){
                arr[k] = aux[j];
                j++;
            } else {
                arr[k] = aux[i];
                i++;
            }
            k++;
        }
    }

    public static int[] mergeSort(int[] arr){
        return mergeSortHelper(arr);
    }

    private static int[] mergeSortHelper(int[] arr){
        if(arr.length <= 1){
            return arr;
        }else {
            int mid = arr.length / 2;
            int[] leftHalf  = Arrays.copyOfRange(arr, 0, mid);
            int[] left = mergeSortHelper(leftHalf);
            int[] rightHalf = Arrays.copyOfRange(arr, mid,arr.length);
            int[] right = mergeSortHelper(rightHalf);
            return merge(left, right);
        }
    }

    private static int[] merge(int[] left, int[] right){

        int[] result = new int[left.length + right.length];

        int i,j,k;
        i = j = k = 0; //left, right, result pointers
        while(i < left.length || j < right.length){

            if(i >= left.length || (j < right.length && right[j] < left[i])){
                result[k] = right[j];
                j++;
            } else {
                result[k] = left[i];
                i++;
            }
            k++;
        }
        return result;
    }
    //#endregion

    //#region QuickSort

    // Time: Best and Average -> O(nlogn); Worst -> O(n^2)
    // Call stack Space: With TCE (Tail Call Elemination + Recurse on smaller) -> O(logn)
    // Heap Space: In place (none)
    // Stability: Not stable, does not keep relative order for equal elements
    public static void quicksort(int[] arr, int min, int max){
        quickSortInplaceRT(arr, min,max);
    }

    private static void quickSortInplace(int[] arr, int min, int max){
        if(max-min < 2) return;
        int p = hoarePartition(arr,min,max);
        quickSortInplace(arr, min, p+1);
        quickSortInplace(arr, p+1, max);
    }

    private static void quickSortInplaceRT(int[] arr, int min, int max){
        while(max - min > 1){
            int p = hoarePartition(arr,min,max);
            if(p - min < max - p){
                //left is smaller, recurse on this side
                quickSortInplaceRT(arr, min, p+1);
                min = p+1;
            } else {
                //right side is smaller
                quickSortInplaceRT(arr, p+1, max);
                max = p+1;
            }
        }
    }

    private static int hoarePartition(int[] arr, int min, int max){
        int pivotIndex = min + (max-min)/2;
        swap(arr, pivotIndex, min);
        int pivot = arr[min];//needed because pivot was moved to min
        
        int left = min-1;
        int right = max;
        while(true){
            do{
                left++;
            }while(arr[left]<pivot);
    
            do{
                right--;
            }while(arr[right]>pivot);

            if(left>=right) break;
            swap(arr, left, right);
        }
        return right;
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //#endregion

    //#region BinarySearch
    //we assume a sorted array, will not check if true

    public static int binarySearch(int[] arr, int target){
        int midIndex = arr.length / 2;
        int min=0;
        int max=arr.length;
        while(min < max){
            int midValue = arr[midIndex];
            if(midValue == target) return midIndex;
            if(midValue < target){
                min = midIndex+1;
            } else {
                max = midIndex;
            }
            midIndex = min + (max-min) / 2;
        }
        return -1;
    }

    public static int firstOccurrence(int[] arr, int target){
        int midIndex = arr.length / 2;
        int min=0;
        int max=arr.length;
        int foundIndex = -1;
        while(min<max){
            int midValue = arr[midIndex];
            if(midValue == target){
                foundIndex = midIndex;
                max = midIndex;
            }
            else if(midValue < target){
                min = midIndex+1;
            } else {
                max = midIndex;
            }
            midIndex = min + (max-min) / 2;
        }
        return foundIndex;
    }

    public static int lastOccurrence(int[] arr, int target){
        int midIndex = arr.length / 2;
        int min=0;
        int max=arr.length;
        int foundIndex = -1;
        while(min<max){
            int midValue = arr[midIndex];
            if(midValue == target){
                foundIndex = midIndex;
                min = midIndex+1;
            }
            else if(midValue < target){
                min = midIndex+1;
            } else {
                max = midIndex;
            }
            midIndex = min + (max-min) / 2;
        }
        return foundIndex;
    }

    public static int rotatedBinarySearch(int[] arr, int target){
        int min = 0;
        int max = arr.length;
        int mid = arr.length/2;
        boolean searchRight = true;
        while(min<max){
            mid = min + (max-min) / 2;
            if (arr[mid] == target) return mid;
            if(arr[min] < arr[mid]){ //left part sorted
                if(arr[min] <= target && target<arr[mid]){
                    //target is in left part
                    searchRight = false;
                } else {
                    // target on right part
                    searchRight = true;
                }
            } else {
                //right part sorted
                if(arr[min]>target && target > arr[mid]){
                    //target on right part
                    searchRight = true;
                } else {
                    // target on left part
                    searchRight = false;
                }
            }
            if(searchRight) min = mid+1;
            else max = mid;
        }
        return -1;
    }

    //#endregion

    public static void main(String[] args) {
        //quickSortTest();
        //mergeSortTest();
        //heapSortTest();
        binarySearchTest();
    }

    private static void heapSortTest(){
        int[] arr =  new int[]{16,42,22,7,15,3};
        printArr("Unsorted", arr);
        arr = heapSort(arr);
        printArr("heapSort", arr);
    }

    private static void mergeSortTest(){
        int[] arr =  new int[]{16,42,22,7,15,3};
        printArr("Unsorted", arr);
        arr = mergeSort(arr);
        printArr("mergeSort", arr);
        
         int[] arr2 =  new int[]{16,42,22,7,15,3};
        printArr("Unsorted", arr2);
        mergeSortInPlace(arr2);
        printArr("mergeSortInPlace", arr2);
    }

    private static void quickSortTest(){
        int[] arr =  new int[]{16,42,22,7,15,3};
        printArr("Unsorted", arr);
        quicksort(arr,0,arr.length);
        printArr("quickSort", arr);
    }

    private static void binarySearchTest(){
        System.out.println("--- standard ---");
        check("found middle",         binarySearch(new int[]{1,3,5,7,9}, 5), 2);
        check("found first",          binarySearch(new int[]{1,3,5,7,9}, 1), 0);
        check("found last",           binarySearch(new int[]{1,3,5,7,9}, 9), 4);
        check("not found",            binarySearch(new int[]{1,3,5,7,9}, 4), -1);
        check("single found",         binarySearch(new int[]{7}, 7), 0);
        check("single not found",     binarySearch(new int[]{7}, 5), -1);

        System.out.println("--- firstOccurrence ---");
        check("no duplicates",        firstOccurrence(new int[]{1,2,3,4,5}, 3), 2);
        check("duplicates middle",    firstOccurrence(new int[]{1,2,2,2,3}, 2), 1);
        check("all same",             firstOccurrence(new int[]{2,2,2,2,2}, 2), 0);
        check("duplicates at start",  firstOccurrence(new int[]{2,2,3,4,5}, 2), 0);
        check("not found",            firstOccurrence(new int[]{1,2,3,4,5}, 9), -1);

        System.out.println("--- lastOccurrence ---");
        check("no duplicates",        lastOccurrence(new int[]{1,2,3,4,5}, 3), 2);
        check("duplicates middle",    lastOccurrence(new int[]{1,2,2,2,3}, 2), 3);
        check("all same",             lastOccurrence(new int[]{2,2,2,2,2}, 2), 4);
        check("duplicates at end",    lastOccurrence(new int[]{1,2,3,5,5}, 5), 4);
        check("not found",            lastOccurrence(new int[]{1,2,3,4,5}, 9), -1);

        System.out.println("--- rotatedBinarySearch ---");
        check("found left half",      rotatedBinarySearch(new int[]{4,5,6,7,0,1,2}, 6), 2);
        check("found right half",     rotatedBinarySearch(new int[]{4,5,6,7,0,1,2}, 0), 4);
        check("not found",            rotatedBinarySearch(new int[]{4,5,6,7,0,1,2}, 3), -1);
        check("not rotated",          rotatedBinarySearch(new int[]{1,2,3,4,5}, 3), 2);
        check("single found",         rotatedBinarySearch(new int[]{1}, 1), 0);
        check("single not found",     rotatedBinarySearch(new int[]{1}, 0), -1);
        check("pivot at end",         rotatedBinarySearch(new int[]{2,3,4,5,1}, 1), 4);
    }

    private static void check(String label, int actual, int expected){
        System.out.println((actual == expected ? "PASS" : "FAIL") + " [" + label + "] expected=" + expected + " got=" + actual);
    }

    private static void printArr(String method, int[] arr){
        String result = "";
        for(int i = 0;i<arr.length;i++){
            result += ", " + arr[i];
        }
        System.out.println(method + ": " + result.substring(1));
    }
}
