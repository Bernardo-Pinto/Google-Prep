package DataStructures.Week1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import DataStructures.Week1.SingleLinkedList.Node;

public class Test {
    public static void main(String args[]){
        testSingleLinkedList();
    }

    static void testPriorityQueue(){

        // --- min-heap via comparator (natural order) ---
        System.out.println("=== PQ: min-heap (natural order) ===");
        PriorityQueue<Integer> minPQ = new PriorityQueue<>((a, b) -> b - a);
        minPQ.insert(5);
        minPQ.insert(1);
        minPQ.insert(3);
        minPQ.insert(2);
        minPQ.insert(4);
        System.out.println("peek (expect 1): " + minPQ.peek());
        System.out.println("poll (expect 1): " + minPQ.poll());
        System.out.println("poll (expect 2): " + minPQ.poll());
        System.out.println("poll (expect 3): " + minPQ.poll());
        System.out.println("size (expect 2): " + minPQ.size());

        // --- max-heap via comparator (reverse order) ---
        System.out.println("\n=== PQ: max-heap (reverse order) ===");
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a, b) -> a - b);
        maxPQ.insert(5);
        maxPQ.insert(1);
        maxPQ.insert(3);
        maxPQ.insert(2);
        maxPQ.insert(4);
        System.out.println("peek (expect 5): " + maxPQ.peek());
        System.out.println("poll (expect 5): " + maxPQ.poll());
        System.out.println("poll (expect 4): " + maxPQ.poll());
        System.out.println("poll (expect 3): " + maxPQ.poll());
        System.out.println("size (expect 2): " + maxPQ.size());

        // --- isEmpty ---
        System.out.println("\n=== PQ: isEmpty ===");
        PriorityQueue<Integer> emptyPQ = new PriorityQueue<>((a, b) -> a - b);
        System.out.println("isEmpty on new PQ (expect true): " + emptyPQ.isEmpty());
        emptyPQ.insert(1);
        System.out.println("isEmpty after insert (expect false): " + emptyPQ.isEmpty());
        emptyPQ.poll();
        System.out.println("isEmpty after polling last (expect true): " + emptyPQ.isEmpty());

        // --- poll on empty ---
        System.out.println("\n=== PQ: poll on empty ===");
        System.out.println("poll on empty (expect null): " + emptyPQ.poll());

        // --- custom object: task scheduling by priority number ---
        System.out.println("\n=== PQ: custom objects (highest int priority first) ===");
        PriorityQueue<int[]> taskPQ = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        taskPQ.insert(new int[]{1, 3}); // task id=1, priority=3
        taskPQ.insert(new int[]{2, 5}); // task id=2, priority=5
        taskPQ.insert(new int[]{3, 1}); // task id=3, priority=1
        taskPQ.insert(new int[]{4, 4}); // task id=4, priority=4
        System.out.println("poll task id (expect 2, prio 5): id=" + taskPQ.poll()[0]);
        System.out.println("poll task id (expect 4, prio 4): id=" + taskPQ.poll()[0]);
        System.out.println("poll task id (expect 1, prio 3): id=" + taskPQ.poll()[0]);
    }

    static void testMaxHeap(){
        // --- Creation ---
        System.out.println("=== Creation ===");
        MaxHeap<Integer> heap = new MaxHeap<>();
        System.out.println("=== Add ===");
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        System.out.println("ToString(): \n" + heap.toString());
        heap.insert(2);
        heap.insert(1);
        System.out.println("ToString(): \n" + heap.toString());
        
        System.out.println("=== Poll once ===");
        int v = heap.poll();
        System.out.println("Removed value: " + v);
        System.out.println("ToString(): \n" + heap.toString());


        System.out.println("=== Heapify ===");
        Integer[] arr = new Integer[]{2,6,4,7,1,3};
        MaxHeap<Integer> heapified = MaxHeap.heapify(arr);
        System.out.println("heapified to String: \n" + heapified.toString());
    }

    static void testMinHeap(){
        // --- Creation ---
        System.out.println("=== Creation ===");
        MinHeap<Integer> heap = new MinHeap<>();
        System.out.println("=== Add ===");
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        System.out.println("ToString(): \n" + heap.toString());
        heap.insert(2);
        heap.insert(1);
        System.out.println("ToString(): \n" + heap.toString());
        
        System.out.println("=== Poll once ===");
        int v = heap.poll();
        System.out.println("Removed value: " + v);
        System.out.println("ToString(): \n" + heap.toString());


        System.out.println("=== Heapify ===");
        Integer[] arr = new Integer[]{2,6,4,7,1,3};
        MinHeap<Integer> heapified = MinHeap.heapify(arr);
        System.out.println("heapified to String: \n" + heapified.toString());
    }

    static void testHashMap(){

        // --- Creation ---
        System.out.println("=== Creation ===");
        HashMap<String, Integer> map = new HashMap<>();
        System.out.println("Size (expect 0): " + map.size());

        // --- put + get ---
        System.out.println("\n=== put + get ===");
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println("get(a) (expect 1): " + map.get("a"));
        System.out.println("get(b) (expect 2): " + map.get("b"));
        System.out.println("get(c) (expect 3): " + map.get("c"));
        System.out.println("Size (expect 3): " + map.size());

        // --- update existing key ---
        System.out.println("\n=== update ===");
        map.put("a", 99);
        System.out.println("get(a) after update (expect 99): " + map.get("a"));
        System.out.println("Size after update (expect 3, not 4): " + map.size());

        // --- get missing key ---
        System.out.println("\n=== get missing key ===");
        System.out.println("get(z) (expect null): " + map.get("z"));

        // --- containsKey ---
        System.out.println("\n=== containsKey ===");
        System.out.println("containsKey(b) (expect true): " + map.contains("b"));
        System.out.println("containsKey(z) (expect false): " + map.contains("z"));

        // --- containsValue ---
        System.out.println("\n=== containsValue ===");
        System.out.println("containsValue(2) (expect true): " + map.containsValue(2));
        System.out.println("containsValue(42) (expect false): " + map.containsValue(42));

        // --- remove ---
        System.out.println("\n=== remove ===");
        Integer removed = map.remove("b");
        System.out.println("remove(b) (expect 2): " + removed);
        System.out.println("get(b) after remove (expect null): " + map.get("b"));
        System.out.println("Size after remove (expect 2): " + map.size());
        System.out.println("remove(z) missing key (expect null): " + map.remove("z"));

        // --- collision handling: force same bucket ---
        System.out.println("\n=== collision (same bucket) ===");
        // "Aa" and "BB" have the same hashCode in Java
        HashMap<String, String> collisionMap = new HashMap<>();
        collisionMap.put("Aa", "first");
        collisionMap.put("BB", "second");
        System.out.println("get(Aa) (expect first): " + collisionMap.get("Aa"));
        System.out.println("get(BB) (expect second): " + collisionMap.get("BB"));
        collisionMap.remove("Aa");
        System.out.println("get(Aa) after remove (expect null): " + collisionMap.get("Aa"));
        System.out.println("get(BB) after removing Aa (expect second): " + collisionMap.get("BB"));

        // --- resize: insert enough entries to trigger resize (default load 0.75 * 16 = 12) ---
        System.out.println("\n=== resize ===");
        HashMap<Integer, Integer> bigMap = new HashMap<>();
        for(int i = 0; i < 20; i++) bigMap.put(i, i * 10);
        System.out.println("Size after 20 inserts (expect 20): " + bigMap.size());
        System.out.println("get(0) after resize (expect 0): " + bigMap.get(0));
        System.out.println("get(19) after resize (expect 190): " + bigMap.get(19));
        System.out.println("get(10) after resize (expect 100): " + bigMap.get(10));
    }

    static void testStack(){
        Stack<Integer> stack = new Stack<>();
        System.out.println("Is empty? Should be true: " + stack.isEmpty());
        System.out.println("Testing push");
        for(int i = 0;i<10;i++){stack.push(i+100);}
        System.out.println(stack.toString());
        System.out.println("Is empty? Should be false: " + stack.isEmpty());
        System.out.println("Testing peek");
        System.out.println(stack.peek());
        System.out.println("Testing pop");
        int v = stack.pop();
        System.out.println(v);
        System.out.println(stack.toString());
    }

    static void testDoubleLinkedList(){
        
        // --- Creation ---
        System.out.println("=== Creation ===");
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        System.out.println("Size (expect 0): " + list.size());
        System.out.println("ToString empty (expect ''): '" + list.toString() + "'");

        // --- add(T) / addLast ---
        System.out.println("\n=== add(T) ===");
        list.addAll(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("After addAll 1-5 (expect 1, 2, 3, 4, 5): " + list.toString());
        System.out.println("Size (expect 5): " + list.size());

        // --- addFirst ---
        System.out.println("\n=== addFirst ===");
        list.addFirst(0);
        System.out.println("After addFirst 0 (expect 0, 1, 2, 3, 4, 5): " + list.toString());

        // --- add(int index, T) ---
        System.out.println("\n=== add(index, T) ===");
        list.add(3, 99);
        System.out.println("After add(3, 99) (expect 0, 1, 2, 99, 3, 4, 5): " + list.toString());
        list.add(0, 88);
        System.out.println("After add(0, 88) (expect 88, 0, 1, 2, 99, 3, 4, 5): " + list.toString());

        // --- get ---
        System.out.println("\n=== get ===");
        System.out.println("get(0) (expect 88): " + list.get(0).element);
        System.out.println("get(4) (expect 99): " + list.get(4).element);
        System.out.println("get(last=" + (list.size()-1) + ") (expect 5): " + list.get(list.size()-1).element);

        // --- contains ---
        System.out.println("\n=== contains ===");
        System.out.println("contains(99) (expect true): " + list.contains(99));
        System.out.println("contains(5) last node (expect true): " + list.contains(5));
        System.out.println("contains(42) (expect false): " + list.contains(42));

        // --- indexOf ---
        System.out.println("\n=== indexOf ===");
        System.out.println("indexOf(88) (expect 0): " + list.indexOf(88));
        System.out.println("indexOf(99) (expect 4): " + list.indexOf(99));

        // --- removeFirst ---
        System.out.println("\n=== removeFirst ===");
        list.removeFirst();
        System.out.println("After removeFirst (expect 0, 1, 2, 99, 3, 4, 5): " + list.toString());

        // --- removeLast ---
        System.out.println("\n=== removeLast ===");
        list.removeLast();
        System.out.println("After removeLast (expect 0, 1, 2, 99, 3, 4): " + list.toString());

        // --- removeByIndex ---
        System.out.println("\n=== removeByIndex ===");
        list.removeByIndex(3); // remove 99
        System.out.println("After removeByIndex(3) remove 99 (expect 0, 1, 2, 3, 4): " + list.toString());
        list.removeByIndex(0); // remove first
        System.out.println("After removeByIndex(0) (expect 1, 2, 3, 4): " + list.toString());
        list.removeByIndex(list.size()-1); // remove last
        System.out.println("After removeByIndex(last) (expect 1, 2, 3): " + list.toString());

        // --- remove(T) ---
        System.out.println("\n=== remove(T) ===");
        list.add(9);
        System.out.println("After add 9 (expect 1, 2, 3, 9): " + list.toString());
        list.remove(2); // middle
        System.out.println("After remove(2) middle (expect 1, 3, 9): " + list.toString());
        list.remove(9); // last node
        System.out.println("After remove(9) last (expect 1, 3): " + list.toString());
        list.remove(1); // first node
        System.out.println("After remove(1) first (expect 3): " + list.toString());

        // --- single element edge cases ---
        System.out.println("\n=== Single element edge cases ===");
        SingleLinkedList<String> single = new SingleLinkedList<>();
        single.add("only");
        System.out.println("Single element toString (expect only): " + single.toString());
        System.out.println("contains 'only' (expect true): " + single.contains("only"));
        single.removeLast();
        System.out.println("After removeLast on single element, size (expect 0): " + single.size());
        System.out.println("ToString after empty (expect ''): '" + single.toString() + "'");
    }

    static void testSingleLinkedList(){

        // // --- Creation ---
        System.out.println("=== Creation ===");
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        Iterator<Integer> it = list.iterator();
        try{
            Integer v = it.next();
            System.out.println("Is v null?: (true) " + (v==null));
        } catch(NoSuchElementException nse){
            System.out.println("Exception: Iterator does not have next: " + nse.toString());
        }
        System.out.println("Does it have a next? (false) " + it.hasNext());
        System.out.println("Size (expect 0): " + list.size());
        System.out.println("ToString empty (expect ''): '" + list.toString() + "'");

        // --- add(T) / addLast ---
        System.out.println("\n=== add(T) ===");
        list.addAll(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("After addAll 1-5 (expect 1, 2, 3, 4, 5): " + list.toString());
        System.out.println("Size (expect 5): " + list.size());

        // --- addFirst ---
        System.out.println("\n=== addFirst ===");
        list.addFirst(0);
        System.out.println("After addFirst 0 (expect 0, 1, 2, 3, 4, 5): " + list.toString());

        // --- add(int index, T) ---
        System.out.println("\n=== add(index, T) ===");
        list.add(3, 99);
        System.out.println("After add(3, 99) (expect 0, 1, 2, 99, 3, 4, 5): " + list.toString());
        list.add(0, 88);
        System.out.println("After add(0, 88) (expect 88, 0, 1, 2, 99, 3, 4, 5): " + list.toString());

        System.out.println("-------Comparator test------");
        Comparator<SingleLinkedList<Integer>.Node> nodeComparator = list.nodeComparator((e1,e2) -> e2.compareTo(e1));
        SingleLinkedList<Integer>.Node nodeA = list.new Node(3);
        SingleLinkedList<Integer>.Node nodeB = list.new Node(7);
        System.out.println("compare(3, 7) (expect negative): " + nodeComparator.compare(nodeA, nodeB));
        System.out.println("compare(7, 3) (expect positive): " + nodeComparator.compare(nodeB, nodeA));
        System.out.println("compare(3, 3) (expect 0): " + nodeComparator.compare(nodeA, nodeA));
        // // --- get ---
        // System.out.println("\n=== get ===");
        // System.out.println("get(0) (expect 88): " + list.get(0).element);
        // System.out.println("get(4) (expect 99): " + list.get(4).element);
        // System.out.println("get(last=" + (list.size()-1) + ") (expect 5): " + list.get(list.size()-1).element);

        // // --- contains ---
        // System.out.println("\n=== contains ===");
        // System.out.println("contains(99) (expect true): " + list.contains(99));
        // System.out.println("contains(5) last node (expect true): " + list.contains(5));
        // System.out.println("contains(42) (expect false): " + list.contains(42));

        // // --- indexOf ---
        // System.out.println("\n=== indexOf ===");
        // System.out.println("indexOf(88) (expect 0): " + list.indexOf(88));
        // System.out.println("indexOf(99) (expect 4): " + list.indexOf(99));

        // // --- removeFirst ---
        // System.out.println("\n=== removeFirst ===");
        // list.removeFirst();
        // System.out.println("After removeFirst (expect 0, 1, 2, 99, 3, 4, 5): " + list.toString());

        // // --- removeLast ---
        // System.out.println("\n=== removeLast ===");
        // list.removeLast();
        // System.out.println("After removeLast (expect 0, 1, 2, 99, 3, 4): " + list.toString());

        // // --- removeByIndex ---
        // System.out.println("\n=== removeByIndex ===");
        // list.removeByIndex(3); // remove 99
        // System.out.println("After removeByIndex(3) remove 99 (expect 0, 1, 2, 3, 4): " + list.toString());
        // list.removeByIndex(0); // remove first
        // System.out.println("After removeByIndex(0) (expect 1, 2, 3, 4): " + list.toString());
        // list.removeByIndex(list.size()-1); // remove last
        // System.out.println("After removeByIndex(last) (expect 1, 2, 3): " + list.toString());

        // // --- remove(T) ---
        // System.out.println("\n=== remove(T) ===");
        // list.add(9);
        // System.out.println("After add 9 (expect 1, 2, 3, 9): " + list.toString());
        // list.remove(2); // middle
        // System.out.println("After remove(2) middle (expect 1, 3, 9): " + list.toString());
        // list.remove(9); // last node
        // System.out.println("After remove(9) last (expect 1, 3): " + list.toString());
        // list.remove(1); // first node
        // System.out.println("After remove(1) first (expect 3): " + list.toString());

        // // --- single element edge cases ---
        // System.out.println("\n=== Single element edge cases ===");
        // SingleLinkedList<String> single = new SingleLinkedList<>();
        // single.add("only");
        // System.out.println("Single element toString (expect only): " + single.toString());
        // System.out.println("contains 'only' (expect true): " + single.contains("only"));
        // single.removeLast();
        // System.out.println("After removeLast on single element, size (expect 0): " + single.size());
        // System.out.println("ToString after empty (expect ''): '" + single.toString() + "'");

        System.out.println("Test iterator: ");
        while(it.hasNext()){
            Integer next = it.next();
            if(it.hasNext())System.out.print(next + ", ");
            else System.out.print(next);
        }
    }

    static void testArrayList(){
        System.out.println("Testing creation");
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println(list.size());
        System.out.println("Testing add");
        for(int i = 0;i<12;i++){list.add(i+100);}
        System.out.println(list.toString());
        System.out.println(list.size());
        System.out.println("Testing remove by index");
        list.removeByIndex(3);
        System.out.println(list.toString());
        System.out.println(list.size());
        System.out.println("Testing remove by value");
        list.removeByValue(109);
        System.out.println(list.toString());
        System.out.println(list.size());
    }
}
