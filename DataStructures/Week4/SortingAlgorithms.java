package DataStructures.Week4;

import java.util.Arrays;

public class SortingAlgorithms {
    
    //#region heapSort

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
    public static void main(String[] args) {
        //quickSortTest();
        //mergeSortTest();
        heapSortTest();
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

    private static void printArr(String method, int[] arr){
        String result = "";
        for(int i = 0;i<arr.length;i++){
            result += ", " + arr[i];
        }
        System.out.println(method + ": " + result.substring(1));
    }
}
