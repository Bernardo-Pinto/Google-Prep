# Graphs Quiz — Week 3

Fill in your answers, then ask for a review.

---

## Section 1: Representations

**Q1. You need to frequently ask "does edge (A→B) exist?" and the graph is dense (many edges). Which representation is better and why?**

A:Matrix, since lookup is O(1) and there will be low wasted memory

---

**Q2. You need to iterate over all neighbours of a node efficiently and the graph is sparse (few edges). Which representation is better and why?**

A: List. It only saves existing edges in a list in a hashmap of <node,list of edges>. Will not waste much space.

---

**Q3. What is the space complexity of an adjacency matrix for V vertices? What about an adjacency list?**

A: Matrix: V*V = V^2. List: V*M where M is the amount of edges/vertices.

---

## Section 2: Traversal

**Q4. BFS uses a _______, DFS uses a _______ (or the call stack).**

A:BFS uses a queue, DFS uses a Stack

---

**Q5. Given this graph (undirected):**
```
A - B - D
|       |
C ------+
```
What order does BFS from A visit nodes? What order does DFS from A (always pick alphabetically first neighbour)?

A:
BFS from A: ABCD
DFS from A: ABDC

---

## Section 3: Cycle Detection

**Q6. In directed cycle detection via DFS, you maintain two sets: `visited` and `path`. What is the purpose of each? Why do you need both?**

A: Visited maintains the nodes that have been explored (processed all its neighbours). If its not on the path but its on the visited, it gets skipped.
path maintains the current path from source. If a a node that is being explored is already on the path, that means a cycle exists.

---

**Q7. Union-Find cycle detection: you process edge (A-B). Both A and B have the same root. What does that mean and what do you return?**

A:That means there is a cycle (A and B come from the same node, or A comes from B or vice versa) and I return true.

---

**Q8. Can Union-Find (as you implemented it) detect cycles in directed graphs? Why or why not?**

A:No it cannot. But I don't know or remember why

---

## Section 4: Shortest Path

**Q9. Why does BFS give the shortest path in an unweighted graph but NOT in a weighted graph?**

A: Assuming the weight is always the same, the shortest path is the one with the least nodes. BFS will find the path to a leaf with the shortest amount of nodes.
In a weighted graph, the that path might have a total sum of the weight higher than a path with more nodes, but lower overall cost.  

---

**Q10. Dijkstra fails with negative edge weights. Give a concrete example (a small graph) showing why.**

A:Because a negative edge might mean a better path exists to a node, but it that node was processed first(becuase its cost was lower), then this second node's value will not be udpated. Graph: Vertices A,B,C. Edges: A->B: 10, A->C:5, B->C -10
Dijkstra will process A, explore B and C. C will have lower cost, so it processes C. Since there is no edge from C, it then processes B. B has an edge to C with cost -10, but C is already processed, so it won't update its value.
---

**Q11. In Dijkstra, when you poll a node from the PQ you first check `if(visited.contains(curr)) continue`. Why? What are these duplicate entries and where do they come from?**

A:That is in case for example a node A has edges to node B and node C, and B also has an edge to C. If we don't have that condition, we would keep exploring forever (cycle). And it is also an optimization, since processed nodes will already have the minimal cost from source, so there is point in visiting those nodes.
AI Correction: The main reason is stale duplicate entries. If a cheaper path to a node is found, a new entry is is pushed to the PQ, and the cheaper path is explored. The stale/more expensive paths will not be explored because by then the node will have been explored.

---

**Q12. What does Dijkstra's `parents` map store, and how do you reconstruct the path from it?**

A: parents stores the path from source to destiny. To reconstruct, you start from destiny, get its parent and so on and so forth, then reverse it.

---

## Section 5: Topological Sort

**Q13. What two conditions must a graph satisfy for topological sort to be valid?**

A:Must not have a cycle, must be directed

---

**Q14. Kahn's algorithm: what does "in-degree" mean, and why do you start with in-degree-0 nodes?**

A: in-degree means how many nodes have a connection to that node. You start with the 0 in-degrees because they don't depend on other nodes.
For example, if the nodes reprent building materials for a house, cement and steel would be 0 nodes, and foundation and base would be 1 in-degree.

---

**Q15. Kahn's cycle detection: after the BFS loop finishes, `result.size() = 4` but the graph has 5 nodes. What does this tell you?**

A:That there is a cycle, and its a node with a self loop.

---

**Q16. DFS topological sort: a node is added to the result *after* recursing into all its neighbours. Why does this give the correct order when reversed?**

A:Because we want to add the nodes with the most dependencies and nothing depends on. After processing that, we go back to the previous node, and since now the nodes that depended on this node are already added to the list, we can add this one, and so on and so forth
AI correction: post-order means a node is only added after all nodes reachable from it are already in the list. So reversing puts a node before everything it points to — which is exactly the topological requirement.

---

**Q17. Trace Kahn's on this DAG:**
```
A → C
B → C → E → F
B → D → F
```
What is a valid output order?

A:A,B,C,D,E,F

---

## Section 6: A*

**Q18. What are `g(n)`, `h(n)`, and `f(n)` in A\*?**

A:
g(n):actual cost of source to node
h(n):heuristic cost from this node to destiny node
f(n):g+h

---

**Q19. What does "admissible heuristic" mean? What happens if the heuristic is inadmissible?**

A: it means it does not overestimate the actual cost. If it does, a sub optimal path may be returned.

---

**Q20. What does A\* reduce to when `h(n) = 0` for all nodes?**

A: to Dijkstra

---

**Q21. In your A\* implementation you store `Map.Entry<Integer, String>` in the PQ instead of just `String`. Why? What was wrong with using a `PriorityQueue<String>` with a comparator that reads from the `g` map?**

A: Weird way to phrase it, but basically the PQ needs to use the f cost instead of the g cost to select the next node, otherwise its just dijkstra .(forgot the last question)

AI Correction: The missing part: a PriorityQueue<String> with a comparator reading from the g map is broken because the PQ doesn't re-sort when you update g externally. The heap structure is fixed at insertion time. So a node inserted with g=10 and later updated to g=2 stays in the wrong heap position. Map.Entry<Integer, String> bakes the cost in at insertion — stale entries just get skipped via visited.

---

## Section 7: Complexity Quickfire

**Q22. Fill in the table:**

| Algorithm | Time | Space |
|---|---|---|
| BFS / DFS | | |
| Dijkstra (binary heap PQ) | | |
| Kahn's topological sort | | |
| DFS topological sort | | |
| A\* (worst case) | | |

A:
BFS/DFS: Time = O(n) , Space = O(1)
Dijkstra (binary heap PQ): Time = O(vlog(e)) where v=total vertices,e=total edges , Space = O(path+log(e)+visited) = O(log(e))
Kahn's topological sort:Time = O(max-in-degree * v * log(e)) , Space = O(v)
DFS topological sort:Time = O(v) , Space = O(path + visited) = O(1)
A\* (worst case):Time = O(log(e)) , Space = O(path+log(e)+visited) = O(log(e))

AI Correction: 
BFS/DFS                  : Time = O(V+E)       , Space = O(1)
Dijkstra (binary heap PQ): Time = O((V+E)logV) , Space = O(V)
Kahn's topological sort  : Time = O(V+E)       , Space = O(V)
DFS topological sort     : Time = O(V+E)       , Space = O(V)
A\* (worst case)         : Time = O((V+E)logV) , Space = O(V)

Key:
- All graph algorithms are O(V+E) minimum — you must visit every node and every edge at least once
- Space is O(V) not O(1) — you have visited, parents, result, all sized by number of nodes
- Dijkstra and A* add a log factor from the PQ (each insertion/poll is O(log V))
---
