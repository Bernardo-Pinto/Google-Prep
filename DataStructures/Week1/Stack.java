package DataStructures.Week1;

public class Stack<T> {
    
    SingleLinkedList<T> list;

    public Stack(){
        this.list = new SingleLinkedList<>();
    }

    public void push(T value){
        // push a value, this value is now the first one
        list.addFirst(value);
    }

    public T pop(){

        // remove and return the first element
        return isEmpty() ? null : list.removeFirst().element;
    }
    
    public T peek(){
        //return the first element
        return isEmpty() ? null : list.get(0).element;
    }
    public int size(){
        //return the amount of elements in this stack
        return list.size();
    }

    public boolean isEmpty(){
        return list.size() == 0;
    }

    public String toString(){
        return list.toString();
    }
}
