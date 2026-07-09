# Dynamic Programming Quiz — Week 5

Fill in your answers, then ask for a review.

---

## 1. DP Fundamentals

**Q1. What are the two properties a problem must have for DP to apply? Define each in your own words — don't just name them.**

A:
1- it must be possible to breakdown the problem into subproblems, with base case(s) having trivial solutions
2- it must be possible to combine the solutions of the subproblems to achieve the solution to the original problem

Correction: 
- Overlapping subproblems: the same subproblems recur multiple times in the recursion tree (not just "can be broken down")
- Optimal substructure: the optimal solution to the whole problem contains optimal solutions to its subproblems
---

**Q2. What is the difference between top-down (memoization) and bottom-up (tabulation) DP? When would you prefer one over the other?**

A:The top down approach starts with the original(big) problem, and recursively finds the solutions for the subproblems of each problem until a base case is reached, at which point it starts to combine the solutions until the base problem is solved. It can use memoization to save computed solutions so when a recursive call asks for a solution of the problem, it first consults the memoized solutions, and if the solution is there, it returns that instead.
Bottom up is the same idea, but you start from the base cases into the smaller problems, building up to the solution of the original problem, combining the solutions along the way. It also saves computed solutions into memory, so no 2 same computations are done if the result already exists. I would argue that bottom up is always preffered as it removes the call stack memory overhead, and it doesnt branch, resulting in less accesses to the memory containing the solutions to the subproblems. 

---

**Q3. Fibonacci is the canonical example of overlapping subproblems. What does "overlapping subproblems" mean here specifically — what gets recomputed without memoization?**

A:Fibonacci solution for n depends on the solutions of the n-1 and n-2.Solved using recursion, you would end up with branches solving the same n-i problem. for example, fib(4) would result int fib(3) and fib(2), and then fib(3) would also solve fib(2). In this case, fib(2) would get computed 2 times, which is an overlapping subproblem. 

---

**Q4. Describe the general technique for deriving a DP subproblem definition. What question do you ask yourself first?**

A:
1- What is the simplest, trivial subproblem i have the answer to?
2- Will it need a 1D array or a 2D?
3- If 1D, what does dp[i] represent? If 2D what does dp[i][j] represent?

Correction: First question is "What does this state represent, and what choices can produce it?”". So in the end,a mix of what i had and this.
---

## 2. Classic Problems — Recurrences

**Q5. Write the recurrence for Coin Change (minimum coins). What is the base case? What does `dp[i]` represent?**

A:
1- Simplest subproblem with a trivial answer: 
    1.1: If amount is 0, its 0. 
    1.2: If amount is the exact value of one of the coins, its 1
2- Its one array with another numeric input, 1D should work
3- dp[i] represents the minumum number of coins needed to reach amount i

Recurrence: Set all positions of dp except the first to amount +1. Leave dp[0] at 0. For a position dp[i], for every coin, if the coin value is <= i, set dp[i] to the minimum of dp[i-coinValue] + 1 or dp[i], else skip the coin

---

**Q6. Write the recurrence for 0/1 Knapsack. What are the base cases? What does `dp[i][w]` represent?**

A:
1- Simplest subproblem with a trivial answer: 
    1.1: If capacity is 0, its 0. 
2- Its one array with another numeric input, 1D should work
3- dp[i] represents the maximum value at capacity i

Recurrence: For a position dp[i], for every item, if i+weight of item <=capacity:
    - set dp[i+weight] to max of dp[i+weight] and dp[i]+value of item
---

**Q7. Write the recurrence for LCS. What does `dp[i][j]` equal when `s1[i] == s2[j]`? When they don't match?**

A:
1- Simplest subproblem with a trivial answer: 
    1.1: empty string is a subsequence of empty string, with length is 0
2- Its 2 arrays, so 2D (they are strings, but i will consider them as arrays of characters)
3- Lets say w1 uses i, w2 uses j. dp[i][j] represents the legnth of the longest subsequence of w2.substring(0,j) in w1.substring(0,i).

Recurrence: 
    If w1.charAt(i-1) == w2.charAt(j-1):
        - dp[i][j] = dp[i-1][j-1]+1;
         (dp[i-1][j-1] because that is the max length of the lcs until that point: we do not want the value on dp[i-1][j] because that would cause probems if the previous character was the same, and we don't want dp[i][j-1] because the character is not going to increase the lcs twice)
    Else
        - dp[i][j] = max of dp[i-1][j] and dp[i][j-1]
        (take the max of the previous character at this position or the max of current character, which might have increased the lcs)
---

**Q8. Write the recurrence for Edit Distance. What are the 3 operations, and which table cell does each come from?**
A:
  " h o r s e
" 0 1 2 3 4 5
r 1 1 2 2 3 4
o 2 2 1 2 3 4
s 3 3 2 2 2 3

1- Simplest subproblem with a trivial answer, for two characters, c1 and c2: 
    1.1: c1==c2: 0 ops
    1.2: c1 != c2: 1 op
2- Its 2 arrays, so 2D (they are strings, but i will consider them as arrays of characters)
3- Lets say w1 uses i, w2 uses j. dp[i][j] represents the minimum amount of edits needed to tranform one word into the other, with words being word.substring(0,i) and 0 to j.

Recurrence:
    1: c1==c2, dp[i][j] = dp[i-1][j-1]
    2: c1 != c2: min of (dp[i-1][j] and dp[i][j-1] and dp[i-1][j-1]) + 1
---

**Q9. For Distinct Subsequences, what does `dp[i][j]` represent? Write the recurrence for the case when `s[j] == t[i]` and when they don't match.**

A:
  " b a d g b a g
" 1 1 1 1 1 1 1 1
b 0 1 1 1 1 2 2 2
a 0 0 1 1 1 1 3 3
g 0 0 0 1 2 2 2 5
1- Simplest subproblem with a trivial answer, for two words, w1 and w2: 
    1.1: "" is subseq of ""
    1.2: w1==w1: 1 subseq
2- Its 2 arrays, so 2D (they are strings, but i will consider them as arrays of characters)
3- dp[i][j] reprensts the the amount of distinct subseq up until that point
Recurrence:
    1: c1==c2, dp[i][j] = dp[i-1][j-1] + dp[i][j-1]
    2: c1 != c2: dp[i][j-1]
---

## 3. Complexity

**Q10. What is the time and space complexity of your bottom-up Word Break solution? What does each dimension of the DP table represent?**

A:
TIME: O(N*M*L), N = length of target word. M number of elements in list. L average length of the all words in word list 
SPACE: O(N)
dp[i]. i represents from what index of the word we are trying to start from. i+wordLength represents the indexes we can reach for any index i.

---

**Q11. LIS has two common solutions. What are their time complexities and what is the key insight that gets you from O(n²) to O(n log n)?**

010323
0
01
01
013
012 -> (maxLength = max(maxLength, currentLength), insert 2 before 3, drop everything after 2)
0123 -> last number, maxLength = max(maxLength, currentLength)

A:
O(n^2) and O(nlogn)
The insight is that we can save the current longest sequence in an array, adding to the end if the next number is bigger than all elements (bigger than tail, O(1)), and if not, insert it at the correct index by doing a binary serach which is log(n), and saving the max length everytime this is done.

---

**Q12. You have `s = "aab"`, `t = "ab"`. How many distinct subsequences of `s` equal `t`? Trace through the table manually (3 rows × 4 columns).**

A:
  " a a b
" 1 1 1 1
a 0 1 2 2
b 0 0 1 2

Correction:
 - match: dp[i][j] = dp[i-1][j-1] + dp[i][j-1]
 - no match: dp[i][j] = dp[i][j-1]
---

## 4. NP-Complete

**Q13. Define P and NP in plain terms (no Turing machines). What is the open P vs NP question?**

A:
P: problems that can be solved in polinomial time
NP: problems whose solutions can be verified in polinomial time
P vs NP: If a problem can be verified in polinomial time, can it be solved in polinomial time as well?
---

**Q14. What is the difference between NP-complete and NP-hard? Give one example of a problem that is NP-hard but not NP-complete.**

A:
NP hard means that it is at least as hard as every problem in NP, which means an NP hard problem might not have a solution that can be verified in polinomial time. NP complete does. 
Also, if a problem in NP-complete could be solved in polinomial time, all the other NP-complete problems could as well.
An example of a problem that is NP hard but not NP complete is the Traveling Salesman problem, trying to find the shortest path that visits all cities exactly once and returns to the initial city. 

---

**Q15. Why is 0/1 Knapsack DP considered pseudo-polynomial, not truly polynomial? Be precise — what does "input size" mean here?**

A: Because if the capacity is large, capacity could be rewritten as 2^(something), and that "something" can be 20 for example, making it n*2^20 which is exponential
Correction: Input size of W is logbase2(W) bits, and since W = 2^(logbase2(W)), complexity will be O(n * 2^b), where b is number of bits (logbase2(W)).

---

**Q16. TSP with 15 cities: how many routes does brute force check? What does Held-Karp DP improve it to, and why is it still exponential?**

A: Brute force checks 14! paths. I don't know what Held-Karp DP does, but it seems to improve it O(n^2*2^n), i dont think they will ask about that.

---

**Q17. Greedy by value/weight ratio is optimal for fractional knapsack but fails for 0/1. Give a concrete counterexample (specific numbers) showing it fails.**

A:
val:(4,5,8)
wt:(3,4,5), capacity 7.
If not fractionional, greedy will take item 3 and will not be able to put anything else, giving a value of 8, when highest value is 9 by taking items 1 and 2 
---

**Q18. An interviewer gives you an NP-complete problem with n = 10⁶. What do you say? Walk through your reasoning out loud.**

A:
So since n is quite large, an exact solution is not computanionally feasable, so I would try a greedy algorithm that would give a good enough answer. If n were to be increased even further, possibly enchance the greedy algorithm with heuristics

---

**Q19. What is the signal that a problem is likely NP-complete? What two things do you check?**

A:
1- if it envolves processing n inputs n or m amount of times, aka an exponential solution
2- it is easy to verify if a solution is valid

Correction: 
(1) verifying a proposed solution is easy (polynomial)
(2) finding the solution requires searching through exponentially many possibilities (subsets, permutations, assignments).
---
