# Data Structures Quiz — Week 1

Fill in your answers, then ask for a review.

---

## 1. Arrays & ArrayList

**Q1. What is the time complexity of `get(i)`, `insert at end`, and `insert at index i` in a dynamic array? Justify each.**

A:
get(i): O1, its by index, arraylist just does array[i] underneath is which is a direct memory lookup
insertatend: O1 as well, arraylist keeps the size as a variable, just needs to add at[size]. At worst its On, when it needs to make a new array and copy everything.
insert at index i: On (or On-i, which is On i guess). Moves all the elemnts after "i" to the right after inserting the new element
---

**Q2. When a dynamic array resizes, why do we double the capacity instead of adding a fixed amount (e.g. +10)?**

A: resizing is expensive, we don't want to do it often

---

**Q3. What is amortized O(1) insertion? Why does doubling give you that but +10 does not?**

A: on average you insert at the last position which is O1. Since resizing does not happen often if we double the size, its amortized O1. If we increase by 10 instead, a On resizing will happen frequently

---

## 2. Linked Lists

**Q4. What are the trade-offs between a singly linked list and a doubly linked list? When would you choose one over the other?**

A:Single linked list uses less memory, but double linked list has a more efficient removeLast() method. I would use a doubly linked list over a single linked when i need to remove from tail

---

**Q5. What is the time complexity of `get(i)` in a linked list vs. an array? Why?**

A: On, compared to O1 for array, because it needs to go from 1st node until it gets at the correct index.

---

**Q6. How do you detect a cycle in a linked list? What is the time and space complexity of your approach?**

A:Add nodes to a hashset and see if current node exists. If not, add and continue. On
AI correction: 
Q6 — HashSet approach is correct and O(n) time, but you didn't mention O(n) space. The interviewer will ask. The optimal solution is Floyd's cycle detection (tortoise and hare): two pointers, slow moves 1 step, fast moves 2 — if they meet, there's a cycle. O(n) time, O(1) space.
---

**Q7. How would you reverse a singly linked list in-place? Walk through the steps.**

A: 
curr = head.next // curr = 1
prev = null
// 1 -> 2 -> 3
while(curr != null)
    next = curr.next
    curr.next = prev 
    prev = curr
    curr = next
prevHead = head // prevHead = 1
head = tail // head = 3
tail = prevHead // tail = 1

it 1: null <- 1 2 -> 3
it 2: null <- 1 <- 2 3
it 3: null <- 1 <- 2 <- 3
after while : 3->2->1->null, 3 is head, 1 is tail

---

## 3. Stack & Queue

**Q8. What is the difference between a stack and a queue? Give a real-world example of each.**

A:stack is LIFO and queue is FIFO. Queue is self explainatory, a queue to pay groceries, first to get in line is first to pay.Stack is like a pile of napkins that the staff keeps stocked, the last napkins to be added are the first to be used

---

**Q9. How would you implement a queue using two stacks? What is the amortized time complexity of each operation?**

A: since stack is LIFO, i could add always to the same stack. When a dequeue operation is requested, I would check the other stack first. if not empty, pop from that stack and return that. If it is empty, pop from the first stack and add to the second stack until the first stack is empty, effectivly reversing the order.
enqueue: O1, its just a push on first stack
pop:amortized O1 since the On operation would only happen on empty stack two
peek: same as pop
all else (for the basic operations) is O1

---

**Q10. What does LIFO and FIFO mean? Which applies to a stack and which to a queue?**

A: Already answered before

---

## 4. HashMap

**Q11. How does a HashMap work internally? Walk through what happens on `put(key, value)`.**

A: I'm going to assume that the answer you want is only of the second question:
1- An index is calculated using the hash from the, using the hascode and performing the modulus operation with the capacity of the array underneath -1
2- A Node with the hash from the key, the key, the value, and a null next is inserted at that index
2.1- an object already exists at that index -> it checks if its not the same key and value using equals, and continues to do so until:
    2.1.1: no match is found, so this new node is appended to the last node in this index
    2.1.2: a match is found, the value of the already present node is updated
2.2- its empty, so the node is inserted at that index
3- check if the size is equal or higher than capacity * 0.75 (we want there to be enough spaces, or there will be too many collisions). If it is, we double the capacity of the array and create a new array with new capacity and values from old array

---

**Q12. What is a hash collision? How can it be handled? Name two strategies.**

A: When the calculated index from a key is the same as another key (or even the same key).
I know of 2 strategies:
1- Nodes are used, and in case of collision and different key value pair, it is chained to the last node. An optimization can be done by transforming the Nodes into a tree structure if number of nodes is superior to a treshhold (8 for some reason)
2- The value is put at the next available index
I already explained how it is handled in the previous question.
---

**Q13. What is the load factor and why does it matter? What happens when it is exceeded?**

A: Load factor is how full the array the hashmap uses is. It matters because the fuller it is, the more collisions happen which degrades performance. I already explained in a previous question what happens.

---

**Q14. What is the average and worst-case time complexity of `get` and `put` in a HashMap? What causes the worst case?**

A:average: O1
worst: On, due to a resize

AI Correction:
Q14 — "Worst case due to resize" is not the right answer. Resize is O(n) but happens rarely. The true worst case for get/put is when all keys hash to the same bucket (e.g. all have the same hashCode()), making every operation O(n) to traverse the chain. Java mitigates this with tree-ification at 8 nodes → O(log n) worst case.

---

## 5. Heaps & Priority Queue

**Q15. What is the heap property? State it for both a min-heap and a max-heap.**

A: Not sure I understand the question, but for a min-heap, it states all children are higher or equal than their parent. 
For max-heap, the opposite: all children are smaller or equal than their parent. 
For both, the tree must be a binary complete tree
---

**Q16. Why is a heap stored as an array instead of as a tree with node objects? What are the index formulas for a node at index `i`?**

A: Less memory usage, faster traversal by acessing indexes.
parent = (i-1) / 2
left child = i * 2 + 1
right child = i * 2 + 2

---

**Q17. Walk through inserting a value into a min-heap step by step. What is the time complexity?**

A:Its Ologn. Inserts at first available position in the array, which means its the last element because the tree is complete.
Then it checks if its parent is bigger than him. If it is, they swap places. This keeps going until the condition is not met once.

---

**Q18. Walk through `poll()` on a min-heap step by step. Why do we place the last element at the root instead of just shifting everything up?**

A: Because:
1- shifting everythging up is Onlogn, and if we place the last element at the root and shift down,its Ologn
2- The heap property is assured (binary and complete)

---

**Q19. Why is `heapify` O(n) and not O(n log n)? Explain the intuition.**

A: This is a mathematical conclusion from an approximation using taylor series, because:
1- heapify shifts down elements starting from the bottom, and those who don't have children are not considered
2- most of the nodes are lower in the tree (think 1-2-4-8-16), so out of n nodes, we do 0 operations for 2^4 nodes, 1 op for 2^3 nodes, etc, which converges to On 

---

**Q20. What is the difference between a heap and a PriorityQueue? When would you use a PriorityQueue over a MinHeap/MaxHeap?**

A: A priority queue is the same as a heap, but with a custom Comparator.
I would use a PQ if I needed a custom Comparator

---

## 6. Complexity & Trade-offs

**Q21. Fill in the average-case time complexities:**

| Operation        | ArrayList | LinkedList | HashMap | MinHeap  |
|------------------|-----------|------------|---------|----------|
| Access by index  |     1      |      n      |   N/A   |   N/A    |
| Search by value  |     n      |      n      |     1    |   N/A    |
| Insert at end    |     1     |       1     |    N/A?     |  logn        |
| Insert at index  |     n      |      n      |   N/A   |   N/A    |
| Delete by value  |     n      |     n       |    1     |    n      |
| Get min/max      |   N/A     |    N/A     |   N/A   |     1     |

A: filled above.

---

**Q22. You need to process tasks in order of priority and new tasks can arrive at any time. Which data structure do you use and why? Would an array or linked list work? What would their trade-offs be?**

A: A PQ because it maintains getting highest priority O1. Array would need constant looking thorugh the array.Linkedlist would be On always
---

**Q23. You are given an unsorted array of 1 million integers and need to find the 10 largest values. What is the most efficient approach using a heap, and what is its time and space complexity?**

A: Keep a min-heap of 10 elements. poll when it excedes 10, eg reaches 11. After going through all integers, only the 10 largest remain. Time complexity is Onlogk, space is Ok
