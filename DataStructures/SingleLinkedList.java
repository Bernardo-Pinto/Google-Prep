package DataStructures;

import java.util.Collection;

public class SingleLinkedList<T> {

    public class Node{
        public T element;
        public Node next;

        public Node(T element){
            this.element = element;
            this.next = null;
        }
    }

    private Node firstNode;
    private Node lastNode;
    private int amountOfNodes;

    public SingleLinkedList(){
        this.firstNode = null;
        this.lastNode = null;
        this.amountOfNodes = 0;
    }
    
    void add(int index, T element){

        //if I add at index 0 i should just call addFirst
        if(index == 0){
            this.addFirst(element);
            return;
        }

        //otherwise, find the correct node and insert in the middle
        int i = 1; //start at 1 because we know 0 will call addFirst
        Node curr = firstNode.next;
        Node prev = firstNode;
        while(i != index){
            prev = curr;
            curr = curr.next;
            i++;
        }
        Node node = new Node(element);
        node.next = curr;
        prev.next = node;
        amountOfNodes++;
    }

    void addFirst(T element){

        Node node = new Node(element);

        if(this.firstNode == null){
            this.firstNode = this.lastNode = node;
        } else { // would changing the order make this operation faster? e.g. change the if to != null
            node.next = this.firstNode;
            this.firstNode = node;
        }
        amountOfNodes++;
    }
    
    boolean addLast(T element){
        return this.add(element);
    }

    boolean add(T element){

        Node node = new Node(element);

        if(this.firstNode == null){
            this.firstNode = this.lastNode = node;
        } else { // would changing the order make this operation faster? e.g. change the if to != null
            this.lastNode.next = node;
            this.lastNode = node;
        }
        amountOfNodes++;
        return true;
    }

    boolean addAll(Collection<? extends T> col){
        for(T v : col){
            this.add(v);
        }
        return true;
    }

    boolean contains(T element){
        Node curr = firstNode;

        if(curr == null) return false;

        do{
            if(curr.element.equals(element)) return true;
            curr = curr.next;
        }while(curr.next != null);

        return curr.element.equals(element);
    }

    Node get(int index){
        int i = 0;
        Node curr = firstNode;
        while(i != index){
            curr = curr.next;
            i++;
        }
        return curr;
    }

    int indexOf(T element){
        int i = 0;
        Node curr = firstNode;
        while(!curr.element.equals(element)){
            curr = curr.next;
            i++;
        }
        return i;
    }

    Node removeByIndex(int index){
        
        if(index == 0){
            Node first = this.firstNode;
            this.removeFirst();
            return first;
        }

        if(index == this.size()-1){
            Node last = this.lastNode;
            this.removeLast();
            return last;
        }
        
        int i = 1;
        Node curr = this.firstNode.next;
        Node prev = this.firstNode;
        while(i != index){
            prev = curr;
            curr = curr.next;
            i++;
        }
        prev.next = curr.next;
        amountOfNodes--;
        return curr;
    }

    boolean remove(T element){

        Node curr = this.firstNode;
        Node prev = null;

        while(!curr.element.equals(element) && curr.next != null){
            prev = curr;
            curr = curr.next;
        }

        if (curr.element.equals(element)){
            // if there is only 1 node, and is equal,then prev and curr.next are null
            if(prev == null && curr.next == null){
                this.firstNode = this.lastNode = null;
            }
            // if there is more than 1 node, and the first one is equal, then previous is null
            else if(prev == null){
                this.firstNode = curr.next;
            }
            // if there is more than 1 node, and the last one is equal, then previous is not null
            // but the curr.next is null
            else if(curr.next == null){ 
                // we know prev is not null because if it was, then we would be in the previous case
                prev.next = null;
                this.lastNode = prev;
            }
            // if there are more than 2 nodes, and a middle one is equal, then previous is not null
            // and the curr.next is not null
            else{
                prev.next = curr.next;
            }
            amountOfNodes--;
            return true;
        }
        return false;
    }

    Node removeFirst(){
        Node first = this.firstNode;
        this.firstNode = first.next;
        if(this.firstNode == null) this.lastNode = null;
        amountOfNodes--;
        return first;
    }
    Node removeLast(){

        Node curr = firstNode;
        Node prev = null;

        if(curr == null) return null;

        amountOfNodes--;
        if(curr.next == null){
            this.firstNode = null;
            return curr;
        }

        while(curr.next != null){
            prev = curr;
            curr = curr.next;
        }
        prev.next = null;
        this.lastNode = prev;
        return curr;
    }

    int size(){
        return amountOfNodes;
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();
        Node curr = this.firstNode;
        if(curr != null){
            sb.append(curr.element + ", ");

            while(curr.next != null){
                curr = curr.next;
                sb.append(curr.element + ", ");
            }

            return sb.substring(0, sb.length()-2);

        }
        return "";
    }
}
