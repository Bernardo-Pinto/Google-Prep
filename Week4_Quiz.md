# Algorithms Quiz — Week 4

Fill in your answers, then ask for a review.

---

## 1. Sorting Algorithms

**Q1. Fill in the table: time complexity (best/average/worst), space complexity, and stable/unstable for quicksort, mergesort, and heapsort.**

A:
Quicksort
    Time
        Best:O(NlogN)
        Avg: O(NlogN)
        Worst: O(NlogN) assuming a random or median pivot is chosen, O(N^2) otherwise
    Space: Inplace, O(1) -> CORRECTION: O(logN) with optimizations (Tail call elimination(TCE) + recurse smaller).
    Stable?: No. If 2 equal elements exist and one of them is chosen, they can be ordered differently, depending on chosen partition
MergeSort
    Time
        Best: O(NlogN)
        Avg: O(NlogN)
        Worst: O(NlogN)
    Space: O(N) heap, O(logN) stack
    Stable? Yes
HeapSort
    Time
        Best: O(NlogN) 
        Avg: O(NlogN) N insertions, NlogN siftdowns 
        Worst: O(NlogN)
    Space: O(N) -> CORRECTION: O(1). The array is treated as the heap, and if heapified.
    Stable? No. An insertion puts the element at the end of the array, which is first in line to replace the removed element. That means an equal element put first will come out later.

---

**Q2. When would you prefer mergesort over quicksort, and when would you prefer quicksort over mergesort?**

A:Mergesort over quicksort when relative ordering matters, quicksort over mergesort when space is limited.
Addition: Also good for linked lists and external sort (it naturally works on chunks).

---

**Q3. Quicksort is O(n²) worst case — when does this happen, and how do you mitigate it?**

A: When a pivot is picked by picking first or last and input is sorted descending 

---

**Q4. What is counting sort and when can it be used? What is its time and space complexity?**

A:Its a sorting algorithm, and can (should) be used when the input's sorting field(s) are non decimal,the range of values is not large  (I don't know what the factor n/rangeOfValuesInN  should be), we dont care about using extra space and want the relative order to be preserved
Time
    Best: O(N+M), where N is numbers of elements and M is number of different elements in N 
    Avg:  same as best
    Worst: same as best
Space: O(N+M)
Stable? Yes

---

**Q5. What is radix sort? How does it achieve O(n) on integers, and what is its limitation?**

A: Another sorting algorithm. Its actually O(d * (n+b)). For example, (15,5): d = 2, n = 2, b = 10. First find largest = 2 cycles. Then for d times, order n numbers by digit of order d, from 0 to d using counting sort for example, which is O(N+M). That makes it O(D * (N+M)). I don0t know the limitation
LIMITATION: Only works on integers or fixed-alphabet strings. A large d makes time complexity much worse.

Time
    Best: O(D * (N+M))
    Avg:  same as best
    Worst: same as best
Space: O(N+M)
Stable? Yes

---

## 2. Binary Search

**Q6. What is the time complexity of binary search? What precondition must hold?**

A: 
BestO(logN)
Avg:O(logn)
Worst:O(N)
Elements to the left of node must be smaller, elements to the right of node must be bigger.

CORRECTION: Nevermind, its binary search not binary tree search. Its O(LogN) on a sorted input.

---

**Q7. How does `firstOccurrence` differ from standard binary search? What change in logic makes it O(log n) instead of O(n)?**

A: Well, if the input is sorted, we can find it in logN by always splitting the array in 2, until the element is found.

---

**Q8. In rotated binary search (e.g. `[4,5,6,7,0,1,2]`), how do you determine which half is sorted and where the target might be?**

A:When splitting the array in 2, we compare the element in the middle to the target element.
If the first element (arr[min]) is smaller than middle, we say the left half is sorted. We will call this leftSorted
If the first element is larger than mididle, we say the right side is sorted. We will call this rightSorted
Knowing this, we compare the target with the first element and middle.
If leftSorted, and the target is
    :smaller than first element, then target is on the right side
    :bigger  than first element, then target is on the left side
If rightSorted, its the same but inverted logic.

---

## 3. Divide & Conquer

**Q9. What are the three steps of divide and conquer? Give one example of a problem that is best solved with D&C.**

A:
    1 - Split
    2 - Solve
    3 - Merge
    Mergesort

---

**Q10. What is the Master Theorem? State the three cases and give an example recurrence for each.**

A: T(n) = aT(n/b) + f(n), where:
    -> a is the number of subproblems and a>=1
    -> n/b is the size of each subproblem and b>=1
    -> n is the size of the problem
    -> f(n) is the cost of work done outside the recursive calls (merge subproblem solutions)

I dont remember the three cases.

CORRECTION:
Case 1: a>b^d -> O(n^(logb(a))) -> T(n)=8T(n/2) + O(n) -> O(n^2)
---

**Q11. Max subarray via D&C is O(n log n), but Kadane's algorithm is O(n). Why would you ever teach the D&C version?**

A: This question is bad, its directly reffering to the CountInversions problem that you suggested me to practice D&C and where I arrived at Kadene's algorithm instead, it doesnt really test anything relevant.

---

## 4. Greedy Algorithms

**Q12. What two properties must hold for a greedy algorithm to be provably correct?**

A:
(1) Greedy choice property — the globally optimal solution can always be constructed by making locally optimal choices.
(2) Optimal substructure — the optimal solution to the whole problem contains optimal solutions to its subproblems.

---

**Q13. Coin change with denominations {1, 3, 4} and target 6: does greedy work? What does greedy return? What is optimal?**

A: It does. It selects the highest coin for each i from 1 to target.Greedy will return 2 (3+3), which is the same as optimal. 
WRONG: What i used as an answer is DP. Greedy will fail because it will pick largest coin each time, not arrving to the 3+3 optimal solution
---

**Q14. What is the exchange argument? Sketch how you'd use it to prove greedy is correct for activity selection (pick earliest-ending job).**

A: The optimal solution's each step can be replaced with the greedy choice and the answer must remain optimal. So for each step, starting at s0 up to sk, if any step is replaced by the greedy choice and the optimal solution is no longer possible then it fails. If not, it means greedy choice is correct.

---

## 5. Hashing as an Algorithmic Technique

**Q15. What is a rolling hash? How does Rabin-Karp use it to find a pattern in a string, and what is its time complexity?**

A: A rolling hash is a technique to improve the time complexity of finding where a substring exists in a string. To do this, they are represented in a numeric form (by hashing) and to get the next substring, instead of selecting a new substring, we get the next character from the string, and perform a formula on the numeric value of the substring, more precisely:
h(Si+1) = (b * (hS(i) - b^(L-1)*S[i]) + S[i+L]) mod m where:
    -> L is length of substring
    -> b is the base being used
    -> hS(i) is the current hash
    -> b^(L) is the base to the power of the length of the substring. I don't know why but theory says its b^(L-1), which seems wrong?
    -> S[i] is the element we are going to drop, the first digit of the hash
    -> S[i+L] is the new element we are going to sum to the hash
    -> mod m is to make it conform to a bucket of size m. m should be a large enough number to avoid collisions and preferrably prime.

Time complexity is O(L*N) where L is length of substring and N is the calculation of the new h(P), P being the substring.
If there are close to N collisions, it is O(NL).
Realistically, we aim for a very low amount of collisions, making is O(N) 
---

**Q16. What is a hash collision in the context of Rabin-Karp, and how do you handle it?**

A: When h(Si) is the same as h(Sk). We can use large prime m values, and even 2 hashing values, comparing both hashes to declare a collisions occurs.

---

## 6. Large Data

**Q17. Your dataset is 1TB and you need to sort it. RAM is 8GB. What is your approach? Name the two phases.**

A: We stream the data and sort it using heapsort with size k, where k <= 8GB. When an element is polled, write it memory ordered.
I don't know the name of the phases.
CORRECTION:
Phase 1 - Chunk Sort: Load 8GB chunk and sort it. Write the sorted chunk to disk as a file. Repeat until all data is sorted into files
Phase 2- k-way merge: Merge all files using a min-heap with a limit of number of files

---

**Q18. What is reservoir sampling? You have a stream of unknown length and want a uniform random sample of k items. How does it work?**

A:We take the first k items, and then for every new item, it has a probability of 1/k of replacing a random item we took.
CORRECTION: k/i, where i>k

---

**Q19. You need to count how many distinct user IDs appeared in a stream of 10 billion events. Exact precision is not required. What data structure do you use, and roughly how does it work?**

A: We create a binary 2D array of size 1million (i don't know what factor to use to determine the table size), where each value is either 0 or 1. We use 4 hashes to get 4 values within the boundaries of the array, and with the 4 values we can get 2 coordinates. For each event, we set 2 coords to 1. If they are already set, we mark it as duplicate. In the end, count the number of 1's in the 2D array 

CORRECTION: You described something close to a Bloom filter, not the right tool. For distinct counting the answer is HyperLogLog — it hashes each element, tracks the maximum number of leading zeros seen in any hash, and estimates 2^maxLeadingZeros distinct elements. Uses kilobytes of memory regardless of stream size, ~2% error. Bloom filter is for membership testing (is this ID in the set?), not counting.
---
