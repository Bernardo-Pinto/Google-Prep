package DataStructures.Week1;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class MinHeap<T extends Comparable<? super T>> {
    
    private int size = 0;
    private Object[] arr;
    private int capacity = 11;

    public MinHeap(){
        this.size = 0;
        this.arr = new Object[capacity];
    }

    public MinHeap(Object[] arr, int size){
        this.size = size;
        this.capacity = arr.length;
        this.arr = arr;
    }

    public void insert(T value){

        // value starts at index size-1 (last position)
        this.arr[size] = value;
        //now we must check if this is added correctly by asking its parent "am I smaller then you?"
        // if yes, we must swap their places: this new value becomes the parent and the old parent becomes the child
        // and repeat the process until the answer is no
        int prevIndex = size;
        int parentIndex = this.parentIndex(size); 
        T parent = (T) this.arr[parentIndex];
        while(parent != null && value.compareTo(parent) < 0){
            this.arr[parentIndex] = value;
            this.arr[prevIndex] = parent; 
            prevIndex = parentIndex;
            parentIndex = this.parentIndex(parentIndex);
            parent = (T) this.arr[parentIndex];
        }
        
        size++;
        if(size == capacity){
            this.resize();
        }
    }

    private int parentIndex(int i){
        return (i-1) / 2;
    }

    private int leftChildIndex(int i){
        return (i*2) + 1;
    }

    private int rightChildIndex(int i){
        return (i*2) + 2;
    }

    private void resize(){
        Object[] oldArr = this.arr;
        int oldCapacity = this.capacity;
        this.capacity = capacity * 2;
        this.arr =  new Object[this.capacity];
        for(int i=0;i<oldCapacity;i++){
            this.arr[i] = oldArr[i];
        }
    }

    //removes and return the minimum value
    public T poll() {

        if(size == 0) return null;

        //returning the minimum value is trivial, as it is always at index 0
        T min = (T)this.arr[0];
        // to keep the tree complete, we will remove the first value and place the last value at the root
        // We then move that value down the tree by swapping it with the lowest of its children, 
        // until all its children are bigger than it or there are no more children
        T last = (T) this.arr[size-1];
        this.arr[size-1] = null;
        this.arr[0] = last;
        moveDown(0);

        size--;
        return min;
    }

    private void moveDown(int baseIndex){

        T base = (T)this.arr[baseIndex];
        int minChildIndex = minChildIndex(baseIndex);

        while(minChildIndex != -1) {
            T minChild = (T)this.arr[minChildIndex];
            if(base.compareTo(minChild) <= 0) break;
            this.arr[baseIndex] = minChild;
            this.arr[minChildIndex] = base;
            baseIndex = minChildIndex;
            minChildIndex = minChildIndex(baseIndex);
        }
    }

    private int minChildIndex(int parentIndex){
        
        int leftChildIndex = this.leftChildIndex(parentIndex);
        T leftChild = (leftChildIndex < size) ? (T)this.arr[leftChildIndex] : null;

        int rightChildIndex = this.rightChildIndex(parentIndex);
        T rightChild = (rightChildIndex < size) ? (T)this.arr[rightChildIndex] : null;

        if(leftChild != null && rightChild != null){
            return leftChild.compareTo(rightChild) <= 0 ? leftChildIndex : rightChildIndex;
        } else if (leftChild != null) {
            return leftChildIndex;
        } else if(rightChild != null){
            //possibly never happens due to tree being complete
            return rightChildIndex;
        } else {
            // no child found
            return -1;
        }
    }

    //returns the min value
    T peek(){
        return isEmpty() ? null : (T)arr[0];
    }

    int size(){
        return size;
    }

    boolean isEmpty(){
        return (size == 0);
    }
    
    public static <T extends Comparable<? super T>> MinHeap<T> heapify(T[] input){

        // dont modify original array, and add more slots for future inserts
        T[] copiedArr = Arrays.copyOf(input,input.length*2);

        //TODO what if input has nulls in the middle?
        // 1- we can either assume that the input has no null throughout the array
        // 2- for every null value, we find the next non null value, until the end of the array

        MinHeap<T> heap = new MinHeap<>(copiedArr,input.length);
        //if we start from index 0, we might get an invalid tree.
        // take [2,6,4,7,1,3] as an example. It should return [1,2,3,7,6,4]
        for(int i = (input.length/2 - 1);i >= 0;i--){
            heap.moveDown(i);
        }

        return heap;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(); 
        for(int i=0;i<size;i++){
            T value = (T)this.arr[i];
            sb.append("index:" + i + " with value: " + value + " and parent: " + (T)this.arr[this.parentIndex(i)] + "\n");
        }
        return sb.toString();
    }
}
