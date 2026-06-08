package DataStructures;

@SuppressWarnings("unchecked")
public class HashMap<K,V> {
    
    class Node{
        final int hash;
        final K key;
        V value;
        Node next;
        
        public Node(int hash, K key, V value){
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o){
            
            if(o == this) return true;
            else if(o == null) return false;
            else if(o.getClass() == this.getClass()){
                Node n = (Node)o;
                return this.hash == n.hash && this.key.equals(n.key);
            }
            else return false;
        }
    }
    
    private int capacity;
    private Object[] arr;
    private int size;
    private double loadFactor = 0.75;
    
    public HashMap(){
        this.capacity = 16;
        this.arr = new Object[capacity];
    }

    public void put(K key, V value){
        // hash the key and get the index
        int hash = key.hashCode();
        int index = getIndexFromHash(hash);
        // now check if a a Node already exists at the index
        if(arr[index] == null){
            // does not exist, create and add at this index
            Node node = new Node(hash, key, value);
            arr[index] = node;
            size++;
        } else {
            // exists, check Node and entire chain for possible duplicate using the hashCode and equals()
            Node curr = (Node) arr[index];
            Node prev = null;
            while(curr != null){
                if(curr.key.equals(key)){
                    // duplicate found, replace value
                    curr.value = value;
                    return;
                }
                prev = curr;
                curr = curr.next;
            }

            // if no dup was found, curr will be null. Create node and append
            if(curr == null){
                Node node = new Node(hash, key, value);
                prev.next = node;
                size++;
            }
        }

        // if size reached capacity * loadFactor, we should increase the capacity and rehash all elements
        if(size >= capacity * loadFactor){
            this.resize();
        }
    }

    private void resize(){
        capacity = capacity * 2;
        this.size = 0;
        Object[] oldArr = this.arr;
        this.arr = new Object[capacity];

        for(Object entry : oldArr){
            Node curr = (Node) entry;
            //each node might have chained nodes
            while(curr != null){
                this.put(curr.key, curr.value);
                curr = curr.next;
            }
        }
    }

    public V get(K key){
        int hash = key.hashCode();
        int index = getIndexFromHash(hash);
        Node curr = (Node) this.arr[index];
        while(curr != null){
            if(curr.key.equals(key)){
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    private int getIndexFromHash(int hash){
        return hash & (capacity - 1); //TODO understand why its like this
    }

    public V remove(K key){
        int hash = key.hashCode();
        int index = getIndexFromHash(hash);
        Node curr = (Node) this.arr[index];
        Node prev = null;
        boolean found = false;
        while(curr != null){
            if(curr.key.equals(key)){
                found = true;
                break;
            }
            prev = curr;
            curr = curr.next;
        }

        //
        if(found){
            if(prev == null){
                this.arr[index] = curr.next;
            } else if(curr.next == null){ 
                //prev is not null, but curr.next is
                // this means its not the firstNode for that index, but it is the last
                prev.next = null;
            } else {
                //prev is not null, curr.next is not null, must link prev to curr.next
                prev.next = curr.next;
            }

            size--;
            return curr.value;
        }else {
            return null;
        }
    }

    public boolean contains(K key){
        int hash = key.hashCode();
        int index = getIndexFromHash(hash);
        Node curr = (Node) this.arr[index];
        while(curr != null){
            if(curr.key.equals(key)){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    //Best: O(1), Worst: O(n + m), where n is capacity and m is chain length
    public boolean containsValue(V value){
        //since there is no key, I must look through all the possiblities?
        for(Object entry : this.arr){
            Node curr = (Node) entry;
            while(curr != null){
                if(curr.value.equals(value)) return true;
                curr = curr.next;
            }
        }

        return false;
    }

    public int size(){
        return this.size;
    }
}
