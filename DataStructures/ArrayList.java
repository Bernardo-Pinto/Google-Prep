package DataStructures;

@SuppressWarnings("unchecked")
public class ArrayList<T>{
    
    private T[] arr;
    private int size;
    private int capacity;
    
    public ArrayList(){
        this.size = 0;
        this.capacity = 10;
        this.arr = (T[])new Object[this.capacity];
    }

    // O(1)
    public T get(int index){
        if(checkIndex(index)){
            return arr[index];
        } else {
            return null;
        }
    }

    // O(1)
    public void set(int index, T value){
        if(checkIndex(index)){
            arr[index] = value;
        } else {
            //throw new Exception("Index out of bounds");
        }
    }

    // best:O(1), worst:O(n)
    public void add(T value){
        if(size >= capacity){
            int oldCapacity = capacity;
            capacity += (capacity/2) ;
            T[] newArr = (T[])new Object[capacity];
            for(int i = 0; i < oldCapacity;i++){
                newArr[i] = arr[i];
            }
            arr = newArr;
        } 
        arr[size] = value;
        size++;
    }

    public void addAtIndex(){
        //not implementing probably
    }

    // best:O(1), worst:O(n)
    public T removeByIndex(int index){
        if(checkIndex(index)){
            T removed = arr[index];
            for(int i=index; i < size; i++){
                arr[i] = arr[i+1];
            }
            arr[size] = null;
            size--;
            return removed;
        } else {
            return null;
        }
    }

  // best:O(1), worst:O(n)
    public boolean removeByValue(T value){
        for(int i = 0; i < size; i++){
            if(arr[i].equals(value)){
                return this.removeByIndex(i) != null;
            }
        }
        return false;
    }

    public int size(){
        return this.size;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i=0; i < size; i++){
            s.append(arr[i] + "\n");
        }
        
        if(s.length() > 0) s.deleteCharAt(s.length()-1);
        return s.toString();
    }

    private boolean checkIndex(int index){
        return (index >= 0 && index < size);
    }
} 