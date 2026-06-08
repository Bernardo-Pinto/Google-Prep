# Google SWE III — Learning Path (8–10 weeks)
---
## Week 1 — Data Structures Foundation

**How**: Study Theory then implement without AI and respecting OOP/OOD principles. Prioritize conceptual understanding over memorization — know *why* each structure works, not just *how* to write it.

- **OOP/OOD**: as you implement each structure, practice designing it properly — encapsulation, clean interfaces, meaningful naming
- Arrays, stacks, queues, linked lists (implement from scratch)
- Hash tables: how they work internally (hashing, collision handling — chaining vs open addressing, load factor)
- Priority queues / Heaps (min-heap, max-heap, heapify)
- For each implementation: decompose the problem first, then implement. Aim for elegance and efficiency — not just a working solution.
- Practice implementing system routines (e.g., a basic iterator, a custom comparator)

**Goal**: Implement LinkedList (singly + doubly), Stack, Queue, MinHeap, and HashMap from scratch in VSCode with clean OOP design.

---
## Week 2 — Trees + Tries

**How**: Study Theory then implement without AI and respecting OOP/OOD principles.

- Binary trees: construction, traversal (inorder/preorder/postorder, BFS level-order)
- BSTs: insert, delete, search, validation
- Balanced BSTs: understand conceptually how AVL or Red-Black maintains balance (know the invariant, when to use — no need to implement from scratch)
- Tries: insert, search, prefix search
- n-ary trees

**Goal**: Implement BinaryTree with all traversals (inorder, preorder, postorder, BFS level-order), BST with insert/delete/search/validation, and Trie with insert/search/prefix from scratch.

---
## Week 3 — Graphs

**How**: Study Theory then implement without AI and respecting OOP/OOD principles.

- Graph representations: adjacency list vs matrix vs object/pointers — pros, cons, when to use each
- BFS and DFS (iterative and recursive)
- Cycle detection (directed and undirected graphs)
- Connectivity
- Distance (shortest path, weighted vs unweighted)
- Dijkstra's algorithm
- A* conceptually (heuristic function, difference from Dijkstra)
- Runtime complexity of graph operations
- Trade-offs between representations for different operations (search, insert, traversal)

**Goal**: Implement a Graph class supporting adjacency list representation, with BFS, DFS (iterative and recursive), cycle detection, and Dijkstra from scratch.

---
## Week 4 — Algorithms: Sorting, Searching, Recursion, Divide & Conquer

**How**: Study Theory then implement without AI and respecting OOP/OOD principles.

- Sorting: quicksort, mergesort, heapsort, counting sort, radix sort — know time/space complexity of each and when to prefer one
- Hashing as an algorithmic technique: hash functions, rolling hash (Rabin-Karp), use in string matching and deduplication
- Binary search and its variants (first/last occurrence, rotated array)
- Divide and conquer pattern
- Recursion: call stack analysis, identifying base cases, time/space complexity of recursive solutions
- Greedy algorithms: when greedy is provably correct vs when it fails
- Handling obscenely large amounts of data: streaming, chunking, external sorting, approximate algorithms

**Goal**: Implement quicksort, mergesort, heapsort, and binary search variants (standard, first occurrence, last occurrence, rotated array) from scratch.

---
## Week 5 — Dynamic Programming

**How**: Study Theory then implement without AI.

- Top-down (memoization) vs bottom-up (tabulation) — know both
- How to identify a DP problem: overlapping subproblems + optimal substructure
- How to define the subproblem and the recurrence
- Classic problems: Fibonacci, coin change, 0/1 knapsack, longest common subsequence, longest increasing subsequence, edit distance, word break
- NP-Complete: read enough to understand what it means, recognize TSP and knapsack as canonical examples, explain why exact solutions are infeasible and when to use approximations

**Goal**: solve 5 classic DP problems without looking at solutions

---
## Week 6 — Testing, Code Quality + OOD/OOP Practice

**How**: Review testing theory and code quality principles (test case design, unit testing mindset, preventing bugs, clean code). Then apply them to your existing implementations from Weeks 1–5. For OOD/OOP, design at least one class hierarchy from scratch on paper before writing any code.

- **Test case design**: how to systematically find edge cases. Categories to always check: empty input, single element, all duplicates, negative numbers, overflow, very large input, null/None
- **Corner cases vs edge cases**: edge cases are extreme valid inputs (empty, max size); corner cases are intersections of multiple edge conditions. In practice the terms are used interchangeably — what matters is being systematic
- **Unit testing mindset**: for every function you write, ask "what are the minimal cases that fully verify this?"
- **Testing whiteboard code**: practice tracing through your code manually with a specific input after writing it — this is what interviewers expect before you say you are done
- **Preventing bugs**: immutability where appropriate, meaningful variable names, avoiding side effects in helper functions
- **Code maintainability/readability**: clean naming, single-responsibility functions, no magic numbers
- **Refactor/review sample code**: practice reading unfamiliar code, identifying improvements, and rewriting it more cleanly
- **Validating designs**: before coding, verify your approach handles all cases on paper — don't start implementing a design you haven't validated
- **Elegance and efficiency**: always ask "is there a cleaner way?" and "can I reduce the complexity?" after a working solution
- **Decomposing large problems**: practice breaking a complex problem into smaller, independently solvable subproblems before writing any code
- **OOD/OOP**: encapsulation, inheritance, polymorphism, composition over inheritance, when to use interfaces vs abstract classes. Practice designing a class hierarchy for a real object (e.g., a library system, a vending machine)
- **APIs**: what makes a clean API — clear method signatures, minimal surface area, good defaults. Be able to discuss it, no need to go deep

**Goal**: go back to previous implementations and write test cases. Trace through edge cases manually. Refactor at least one implementation from each prior week for elegance. Do this for at least 2 implementations of each section

---

## Week 7 — LeetCode: Arrays, Strings, Hash Maps, Intervals

**How**: Go to LeetCode, select Google problems, focus on Medium problems with some Hard. Solve it on LeetCode, then rewrite the solution manually on paper or a googleDoc/Obsidian without help, select the following topics:

At least:
- Two pointers and sliding window
- Hash map tricks: frequency counts, prefix sums, anagram detection
- String manipulation and substring problems
- Interval problems: merge intervals, meeting rooms
- "Distil large data sets to single values" and "transform one data set to another" map directly to these problem types

**Goal**: 3–4 problems per day.  

---
## Week 8 — LeetCode: Trees, Graphs, Backtracking

**How**: Go to LeetCode, select Google problems, focus on Medium problems with some Hard. Solve it on LeetCode, then rewrite the solution manually on paper or a googleDoc/Obsidian without help, select the following topics:

- Tree problems: LCA, path sums, level order, serialization
- Graph problems: BFS shortest path, DFS island counting, cycle detection, topological sort
- Backtracking: permutations, combinations, subsets, word search
- Loop problems: identify invariants, off-by-one errors, termination conditions

**Goal**: 3 to 4 problems a day

---
## Week 9 — LeetCode: DP Hard + Remaining Patterns + Refresh

**How**: Go to LeetCode, select Google problems. Solve it on LeetCode, then rewrite the solution manually on paper or a googleDoc/Obsidian without help, select the following topics:

- Hard DP problems
- Union-Find / DSU: read theory, practice on connected components problems
- Monotonic stack: next greater element, largest rectangle in histogram
- Bit manipulation: XOR tricks, bit masking (lower priority, if time allows)
- Concurrency refresh: mutex, deadlock conditions, race conditions — conceptual only
- Math: probability, combinatorics, n-choose-k problems — make sure you can actually solve these, not just recognize them

**Goal**: 2 to 3 problems a day

---
## Week 10 — Behavioral + Mock Interviews + Polish

**How**: No new algorithm topics. Dedicated to behavioral preparation and full-interview simulation. Practice out loud — written answers are not enough.

**Behavioral approach — areas to highlight in every answer:**
- Communication, Time management, Decision making, Flexibility, Initiative, Leadership, Organization, Problem solving
- Tailor answers to the specific role. Frame past experience positively — highlight growth and lessons learned, even in failures
- Whenever possible, quantify results to showcase impact. Connect past learnings to how they apply to this role
- Use the STAR approach to structure answers

**Hypothetical questions — a strong answer must include:**
1. Understanding of the problem: outline what experiences and factors are relevant, and what work still needs to be done
2. Thoughtful problem solving: show how you gather information, research, and get to the root cause
3. Potential solutions: answer the initial question, weigh pros and cons
4. Support for your solution: provide rationale for why it's best, and describe potential success metrics
5. Strong communication: structured, logical, balanced between brevity and detail

**Googleyness:**
- Be prepared to discuss how you used communication and decision-making skills to mobilize others
- Demonstrate stepping up to a leadership role, or helping a team succeed when you weren't officially the leader

**Leadership:**
- Share how you work individually and on a team, how you help others, how you navigate ambiguity, and how you push yourself to grow outside your comfort zone

**Preparation tasks:**
- Finalize your "Tell me about yourself" (2-minute version, engineer-to-engineer tone)
- Build your STAR story bank: 10–15 stories covering leadership, conflict, failure, ambiguity, cross-functional collaboration, pushing outside comfort zone, helping a team succeed without being the official leader
- Quantify every story (%, time saved, team size, revenue impact, etc.)
- Think about career accomplishments with supporting data
- Search "most common behavioral interview questions" → prepare 2 answers each for 10–20 of them
- Prepare 3–5 thoughtful questions to ask interviewers
- Full mock coding interviews under 45-min time pressure (Gemini Live, Pramp, or a peer)
- Re-read the Google Prep Guide

**Goal**: Complete STAR story bank (10–15 stories, all quantified), run at least 2 full mock interviews end-to-end (coding + behavioural), finalize questions to ask interviewers, re-read the Prep Guide.