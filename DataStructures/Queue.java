package DataStructures;

public class Queue<T> {
    
    // a queue is FIFO
    SingleLinkedList<T> list;
    public Queue(){
        this.list = new SingleLinkedList<>();
    }

    public void enqueue(T value){
        list.addLast(value);
    }

    public T dequeue(){
        return isEmpty() ? null : list.removeFirst().element;
    }
    
    public T peek(){
        return isEmpty() ? null : list.get(0).element;
    }

    public boolean isEmpty(){
        return list.size() == 0;
    }
    
    public int size(){
        return list.size();
    }
}
