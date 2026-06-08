package DataStructures;

import java.util.Arrays;
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class PriorityQueue<T> {

    private int size = 0;
    private int capacity = 11;
    private Object[] arr;
    private Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparator){
        this.comparator = comparator;
        this.arr = new Object[this.capacity];
    }

    public void insert(T value){
        //insert at size
        int newValueIndex = size;
        this.arr[newValueIndex] = value;
        size++;
        //move it up as long as comparator.compare(value,parent) > 0
        // because we want the higher priority closer to the root
        // e.g: prio of value is higher than prio of parent? then:
        // comparator.compare(value,parent) > 0
        int parentIndex = this.parentIndex(newValueIndex);
        T parent = (T)this.arr[parentIndex];
        while(parent != null && this.comparator.compare(value, parent)>0){
            this.swap(newValueIndex, parentIndex);
            newValueIndex = parentIndex;
            parentIndex = this.parentIndex(newValueIndex);
            parent = (T)this.arr[parentIndex];
        }

        if(this.size == this.capacity){
            this.resize();
        }
    }

    private void resize(){
        this.capacity *= 2;
        this.arr = Arrays.copyOf(this.arr, capacity);
    }

    public T poll(){

        if(size == 0) return null;

        T maxPrioValue = (T)this.arr[0];
        size--;
        T lastValue = (T)this.arr[size];
        this.arr[size] = null;

        int lastValueIndex = 0;
        this.arr[lastValueIndex] = lastValue;
        
        //move the value down until there is no child that has more priority
        int highestPrioChildIndex = this.findHighestPrioChildIndex(0);
    
        while(highestPrioChildIndex != -1 
            && this.comparator.compare(lastValue, (T)this.arr[highestPrioChildIndex] ) < 0){

            this.swap(lastValueIndex, highestPrioChildIndex);
            lastValueIndex = highestPrioChildIndex;
            highestPrioChildIndex = this.findHighestPrioChildIndex(lastValueIndex);
        }

        return maxPrioValue;
    }

    private void swap(int index1, int index2){
        T value1 = (T)this.arr[index1];
        T value2 = (T)this.arr[index2];
        this.arr[index1] = value2;
        this.arr[index2] = value1;
    }

    private int findHighestPrioChildIndex(int parentIndex){
        int leftChildIndex = this.leftChildIndex(parentIndex);
        int rightChildIndex = this.rightChildIndex(parentIndex);

        T leftChild  = leftChildIndex  >= size ? null : (T)this.arr[leftChildIndex];
        T rightChild = rightChildIndex >= size ? null : (T)this.arr[rightChildIndex];

        if(leftChild != null && rightChild != null){
            return this.comparator.compare(leftChild,rightChild) >= 0 ? leftChildIndex : rightChildIndex;
        } else if(leftChild != null){
            return leftChildIndex;
        } else if(rightChild != null){
            return rightChildIndex;
        } else {
            return -1;
        }
    }


    private int parentIndex(int index){
        return ((index - 1) / 2);
    }

    private int leftChildIndex(int index){
        return (index * 2 + 1);
    }

    private int rightChildIndex(int index){
        return (index * 2 + 2);
    }

    public T peek(){
        return isEmpty() ? null : (T)this.arr[0];
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
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
