# Data Structures Quiz — Week 2

Fill in your answers, then ask for a review.

---

## 1. Binary Trees — Traversals

**Q1. Name the four main tree traversal strategies and describe the visit order of each.**

A:
BFS: By tree level, from left to right.
DFS PreOrder: Node value, left child values, right child values
DFS InOrder: left child values, Node value, right child values
DFS PostOrder: left child values, right child values, Node value

---

**Q2. You have this tree:**
```
        4
       / \
      2   6
     / \ / \
    1  3 5  7
```
Write out the result of: inorder, preorder, postorder, and BFS (level-order).

A:
BFS: 4,2,6,1,3,5,7
DFS PreOrder: 4,2,1,3,6,5,7
DFS InOrder: 1,2,3,4,5,6,7
DFS PostOrder: 1,3,2,5,7,6,4
---

**Q3. When would you choose BFS over DFS for a binary tree problem? Give a concrete example.**

A:Well, DFS is good to find paths in a video game for example, so when you want to find a node that is deep into the tree, like a leaf node.
For BFS, I guess the opposite is true, we suspect the node we want is not deep into the tree. I can't think of an example

AI Correction: Two things to refine. **On DFS**: your video game example is actually a case where DFS is *wrong* — it finds *a* path but not the *shortest* one, and games typically need shortest path (BFS or Dijkstra). DFS is better for problems like "does any path exist?", "find all paths from root to leaf", or "check if a path with a given sum exists" — problems where you need to explore deep possibilities fully, not find the nearest solution. **On BFS**: minimum depth is a contrived example. More meaningful ones: **printing nodes level by level** (you literally need BFS to group by depth), or **finding the nearest node satisfying a condition** (e.g. nearest leaf, nearest node with value X) — BFS guarantees the first match is the closest one. The core rule: use BFS when proximity to root/distance matters, DFS when you need to explore a full path or all possibilities.

---

## 2. Binary Search Trees

**Q4. What is the BST invariant? Does it apply to the whole subtree or just immediate children?**

A: All left nodes are smaller than all their parents. All right nodes are bigger than their parents. Applies to the whole tree.

---

**Q5. What is the time complexity of search, insert, and delete in a BST? What is the worst case and what causes it?**

A: They are the same for all 3 operations: average O(log(n)). Worst is O(N), if the tree is a linkedList structure, by adding only elements to one side

---

**Q6. How do you delete a node with two children from a BST? Walk through the steps.**

A: Let deleted value's node = D. Find the node with the smallest value that is bigger than the D's value (Minimum Next Value MNV), and change the value of the D with MNV. Delete the node with the MNV.
1- From D, go right. Then go left until left is null. That node has the MNV.
2- Change D's value to MNV.
3- Let P be MNV's node parent. Set P.left to MNV's node.right

---

**Q7. How do you validate that a binary tree is a valid BST? What is wrong with just checking that each node is greater than its left child and less than its right child?**

A: Because a tree:
        10
       / \
      6   20
     / \ / \
    1  14 5  7
Would be considered valid.
So to validate, I would recursivly ask the node if its value is smaller than max bound and bigger than min bound. 
Max bound is set to parent's value when going left. e.g: 
1- is 10 valid ? Max and min are null right now, so we consider that yes it is.
2- going left, we ask if 6 is valid, with max = 10, min = null, 6 < max, and since min is null, 6 is valid.
3- going right, we ask if 14 is valid. With max = 10, min = 6. 14 < max (10) is not true, so the tree is not valid 
---

## 3. Balanced BSTs (Conceptual)

**Q8. Why do we need balanced BSTs? What problem do AVL and Red-Black trees solve?**

A: Because deleting from BST might leave the tree unblanced and degrade performance.
AVL and Red-Black trees solve this by keeping the tree balanced.
AVL keeps the tree strictly balanced (max height difference between left and right tree = 1)
Red-Black keeps the tree loosely balanced, where the length between the root to any leaf is at most twice the length of the shortest path.
    This is true because of the rules that every path must have the same amount of black nodes, and you cannot have 2 reds in a row. 
    So the shortest path is black only, and the biggest an alternating Black-Red path, which can at most be double of the black only path.
---

**Q9. In an AVL tree, what is the balance factor and when does a rotation get triggered?**

A: Balance factor of AVL tree is the amount of Nodes inserted left of it vs right. So if root = 10, and we insert 6 and 8, we will have:
root balance: -2
6 balance: +1
8 balance: 0

A rotation gets triggered when the balance reaches -2 or +2

AI Correction: The balance factor is the **height difference** between the left and right subtrees (`height(left) - height(right)` or vice versa), not the count of nodes. Your numeric example happens to produce the same numbers because of the specific tree shape, but with a different tree (e.g. a subtree with 10 nodes on the left and height 4, vs 3 nodes on the right with height 3) the node count and height give different values. The height-based definition is what determines when rotations are needed.

---

**Q10. Name the four rotation cases in an AVL tree and describe what tree shape triggers each one.**

A:
RR: The node with balance +2 has a right child with balance +1. Rotate the parent to the left. 
RL: The node with balance +2 has a right child with balance -1. Rotate the child to the right. Rotate the parent to left
LL: The node with balance -2 has a left child with balance -1.Rotate the parent to the right. 
LR: The node with balance -2 has a right child with balance +1.Rotate the child to the left. Rotate the parent to right

---

**Q11. What is the Red-Black tree invariant? List all the rules.**

A: The invariant of the Red-Black tree is the Color assigned to each Node, which can be Black or Red.
Rules:
1- no 2 reds in a row. E.g: Red Parent cannot have red child
2- the path from any node to any leaf nodes in its left and right subtrees must have the same amount of black nodes
3- All nodes must be either black or red
4- the root is always black 

AI Correction: Missing one rule: **5 — All null leaves (NIL nodes) are considered black.** This is what makes rule 2 well-defined — the "leaf" endpoints of every path are the null sentinels, and they count as black.

---

**Q12. What is the main practical difference between AVL and Red-Black trees? When would you prefer one over the other?**

A:Already explained the main practical difference above: AVL keeps the tree strictly balanced (max height difference between left and right tree = 1)
Red-Black keeps the tree loosely balanced, where the length between the root to any leaf is at most twice the length of the shortest path.
    This is true because of the rules that every path must have the same amount of black nodes, and you cannot have 2 reds in a row. 
    So the shortest path is black only, and the biggest an alternating Black-Red path, which can at most be double of the black only path.
I would prefer an AVL tree when reads are way more often than rights, because AVL keeps the tree strictly balanced so it triggers more rotations which can be expensive in the long run, whereas the RB-Tree costs less to write or read because it triggers less rotations, but reads are less efficient because the tree is not as balanced.

---

## 4. Tries

**Q13. What is a Trie and what problem does it solve better than a HashMap?**

A: A trie is a node with a value and an array of nodes, equal to the length of the alphabet it represents (can be other things, but alphabet is a good example)
It solves the problem of representing words bettet than a HashMap.

AI Correction: You described the structure well but didn't explain *why* it beats a HashMap. The key answer is **prefix operations**. A HashMap can only do exact-key lookup efficiently — to find all words with prefix "app" you'd have to scan every key: O(n). A Trie does it in O(m) where m = prefix length, regardless of how many words are stored. Tries also share storage for common prefixes, which can be more space-efficient than storing every full word separately in a HashMap.

---

**Q14. What is the time complexity of insert, search, and startsWith in a Trie? What does it depend on?**

A: Let m = length of argument
insert: O(m)
search: O(m)
startsWith:O(m)

---

**Q15. How does delete work in a Trie? What are the two cases you need to handle?**

A:
If the element to delete is a prefix of another word, simply mark the end of the word as not a word (isEndOfWord = false)
If not, delete all the letters from either the root or the last letter that marks the end of a word or has more than 1 non-null child.
e.g: insert apple and application. Delete apple. only e must be deleted, since l has 2 non children. Or insert app and apple. Delete apple. Oly le must be deleted, since the second p marks the end of a word. 

---

**Q16. You have a Trie with the words: `app`, `apple`, `application`, `bat`, `ball`. Draw (or describe) what the trie looks like in memory — what nodes exist, which are marked as end-of-word.**

A:root has 2 non null children:
a -> p -> p(end of word) -> l. l has 2 children
    e(end of word)
    i -> c -> a -> t -> i -> o -> n(end of word)
b -> a. a has 2 children:
    t(end of word)
    l -> l(end of word)

---

**Q17. How does wildcard search (using `.` to match any character) differ from regular search in a Trie? Why must it be recursive?**

A: when '.' is found, it searches all of the node's non-null children. 
It must be recursive because a new search must be triggered for each subword after the wildcard

---

## 5. General

**Q18. You need to store a dictionary of 100,000 words and support: (a) exact lookup, (b) prefix search, (c) count of words with a given prefix. Which data structure would you use and why?**

A:I would use a trie, because it handles all operations more efficiently than other structures (possibly not as well as other advanced ones such as B-trees, but I have not learned them).
m = value length, k = number of child nodes
a: O(m)
b: O(m)
c: O(k+m)

AI Correction: Two issues. First, (c) is labelled "a" again — typo. Second, the complexity for (c) is not O(k+m). Navigating to the prefix endpoint is O(m), but then counting requires visiting every node in the prefix subtree. The correct complexity is **O(m + S)** where S = number of nodes in the subtree rooted at the end of the prefix (proportional to how many words/characters exist under that prefix). In the worst case this is O(total nodes in trie).

---

**Q19. What is an n-ary tree? How does its node structure differ from a binary tree node?**

A: Its a tree with any n number of children. Any node can have any amount of children nodes.

AI Correction: Correct, but missing the structural difference of the node. A binary tree node has two explicit pointers: `left` and `right`. An n-ary tree node has a **list or array of children** (variable length), e.g. `List<Node> children` or `Node[] children`. This is exactly what your Trie node uses — `Object[] nodes` of size `alphabetSize` is a fixed-size n-ary node where `n = alphabetSize`.
