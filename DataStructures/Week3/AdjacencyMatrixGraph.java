package DataStructures.Week3;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Function;

public class AdjacencyMatrixGraph {
 
    private int size;
    private List<String> vertices;
    private List<List<Integer>> matrix; 

    public AdjacencyMatrixGraph(){
        this.size = 0;
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    public boolean addVertex(String vertex){
        if(vertices.contains(vertex)) return false;
        this.vertices.add(vertex);
        this.matrix.add(new ArrayList<>());
        size++;
        for(List<Integer> li : matrix){
            while(li.size() < this.size){
                li.add(0);
            }
        }
        return true;
    }

    public boolean addEdge(String origin, String dest, int weight){
        if(weight < 0) return false;
        int originIndex = vertices.indexOf(origin);
        int destIndex = vertices.indexOf(dest);
        if(!checkBounds(originIndex,destIndex)) return false;
        matrix.get(originIndex).set(destIndex, weight);
        return true;
    }

    public void deleteEdge(){

    }

    public int getEdge(String origin, String dest){
        int originIndex = vertices.indexOf(origin);
        int destIndex = vertices.indexOf(dest);
        if(!checkBounds(originIndex, destIndex)) return 0;
        return matrix.get(originIndex).get(destIndex);
    }

    boolean checkBounds(int originIndex, int destIndex){
        if(originIndex < 0 || originIndex > size-1 || 
            destIndex < 0 || destIndex > size-1) return false;
        return true;
    }

    public String printGraphBFS(String vertex){
        String result = "";
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(vertex);
        visited.add(vertex);
        while(!queue.isEmpty()){

            vertex = queue.poll();
            result += vertex;
            
            for(int i=0; i < size;i++){
                int value = getEdge(vertex, vertices.get(i));
                if(value > 0 && !visited.contains(vertices.get(i))){
                    queue.offer(vertices.get(i));
                    visited.add(vertices.get(i));
                }
            }
        }
        return result;
    }

    public String printGraphDFS(String vertex){
        HashSet<String> set = new HashSet<>();
        set.add(vertex);
        return graphDFSToString(vertex,set);
    }

    private String graphDFSToString(String vertex, HashSet<String> visited){
        int originIndex = vertices.indexOf(vertex);
        if(originIndex == -1) return "";
        String result = "";
        // in case i want to return weights
        // List<Integer> edges = matrix.get(originIndex);
        for(int i = 0; i < size; i++){
            String nextVertex = vertices.get(i);
            boolean hasEdge = this.getEdge(vertex, nextVertex) > 0;
            if(!vertex.equals(nextVertex) && !visited.contains(nextVertex) && hasEdge){
                visited.add(nextVertex);
                result += graphDFSToString(nextVertex, visited);
            }
        }
        return vertex + result;
    }

    public boolean isCyclic(boolean directed){
        if(directed) return isDCyclicDFS();
        else return isUDCyclicDFS();
    }

    private boolean isUDCyclicDFS(){
        boolean isCyclic = false;
        for(int i=0;i<size;i++){
            String currVertex = vertices.get(i);
            for(int j = 0;j<size;j++){

                String nextNode = vertices.get(j);
                if(getEdge(currVertex, nextNode)>0){
                    HashSet<String> visited = new HashSet<>();
                    visited.add(currVertex);
                    isCyclic = isCyclic || isUDCyclicDFSHelper(nextNode,currVertex, visited);
                }
            }
        }
        return isCyclic;
    }

    private boolean isUDCyclicDFSHelper(String vertex,String parent, HashSet<String> visited){
        if(visited.contains(vertex)) return true;
        for(int j = 0;j<size;j++){
            String nextNode = vertices.get(j);
            if(nextNode.equals(parent)) continue;
            if(getEdge(vertex, nextNode)>0){
                visited.add(vertex);
                if(isUDCyclicDFSHelper(nextNode, vertex, visited)) return true;
            }
        }
        return false;
    }

    private boolean isDCyclicDFS(){
        for(int i=0;i<size;i++){
            String currVertex = vertices.get(i);
            for(int j = 0;j<size;j++){
                String nextNode = vertices.get(j);
                if(getEdge(currVertex, nextNode)>0){
                    HashSet<String> visited = new HashSet<>();
                    HashSet<String> path = new HashSet<>();
                    visited.add(currVertex);
                    path.add(currVertex);
                    if(isDCyclicDFSHelper(nextNode, visited, path)) return true;
                }
            }
        }
        return false;
    }

    private boolean isDCyclicDFSHelper(String vertex, HashSet<String> visited, HashSet<String> path){
        if(path.contains(vertex))return true; //if its on path, we can do the path again, cycle
        if(visited.contains(vertex)) return false; //already went from this node, no cycle
        path.add(vertex);
        visited.add(vertex);

        for(int j = 0;j<size;j++){
            String nextNode = vertices.get(j);
            if(getEdge(vertex, nextNode)>0){
                if(isDCyclicDFSHelper(nextNode, visited, path)) return true;
            }
        }
        path.remove(vertex);
        return false;
    }

    public boolean isUDCyclicUnion(){

        List<String> parents = new ArrayList<>();
        for(int i=0;i<size;i++){
            parents.add(vertices.get(i));
        }

        for(int i=0;i<size;i++){
            String currVertex = vertices.get(i);
            for(int j=i+1;j<size;j++){
                String nextVertex = vertices.get(j);
                int rootI = findRoot(i, parents);
                int rootJ = findRoot(j, parents);
                if(getEdge(currVertex, nextVertex)>0){
                    if(rootI == rootJ) return true;
                    else parents.set(rootJ, parents.get(rootI));
                }
            }
        }

        return false;
    }

    private int findRoot(int index, List<String> parents){
        while(!parents.get(index).equals(vertices.get(index))){
            index = vertices.indexOf(parents.get(index));
        }
        return index;
    }

    // ---- TOPOLOGICAL SORT (Kahn's Algorithm — BFS-based) ----
    // Only valid on Directed Acyclic Graphs (DAGs).
    // Returns an empty list if the graph has a cycle.
    //
    // Idea: a node with no incoming edges has nothing it depends on,
    // so it can always come first. We repeatedly pick such nodes.
    //
    // Steps:
    //   1. Count in-degrees (how many edges point INTO each node)
    //   2. Enqueue all nodes with in-degree 0 (no dependencies)
    //   3. Each time we process a node, we "remove" it from the graph:
    //      reduce the in-degree of all its neighbors
    //   4. Any neighbor whose in-degree drops to 0 is now free — enqueue it
    //   5. If we processed every node, it's a valid DAG → return order
    //      If not, a cycle exists → return []
    public List<String> topologicalSortKahn(){
        // Step 1: compute in-degree for every vertex
        // In-degree of vertex j = number of edges pointing to j
        // In the matrix, column j holds all incoming edges for vertex j
        int[] inDegree = new int[size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(matrix.get(i).get(j) > 0) inDegree[j]++;
            }
        }

        // Step 2: seed the queue with every node that has no incoming edges
        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < size; i++){
            if(inDegree[i] == 0) queue.offer(vertices.get(i));
        }

        List<String> result = new ArrayList<>();

        while(!queue.isEmpty()){
            String curr = queue.poll();
            result.add(curr);           // this node is "done" — add to order
            int currIndex = vertices.indexOf(curr);

            // Step 3+4: for every neighbor of curr, remove the edge by
            // decrementing its in-degree; if it reaches 0, enqueue it
            for(int j = 0; j < size; j++){
                if(matrix.get(currIndex).get(j) > 0){
                    inDegree[j]--;
                    if(inDegree[j] == 0) queue.offer(vertices.get(j));
                }
            }
        }

        // Step 5: if we didn't process every node, a cycle exists
        if(result.size() != size) return new ArrayList<>();
        return result;
    }

    // ---- TOPOLOGICAL SORT (DFS-based) ----
    // Same requirement: DAG only.
    //
    // Idea: in DFS, a node is "finished" only after all nodes reachable
    // from it are finished. So the finishing order is the reverse of
    // topological order — push to a stack as you finish, then pop the stack.
    //
    // Steps:
    //   1. DFS from every unvisited node
    //   2. After recursing into all neighbors of a node, push it onto a stack
    //      (this is called "post-order")
    //   3. Pop the stack → topological order
    //
    // Cycle detection: track nodes currently on the DFS path ("path" set).
    // If we revisit a node on the current path, there's a back edge → cycle.
    public List<String> topologicalSortDFS(){
        HashSet<String> visited = new HashSet<>();
        HashSet<String> path    = new HashSet<>();  // current DFS path (for cycle detection)
        Deque<String>   stack   = new ArrayDeque<>();

        for(String vertex : vertices){
            if(visited.contains(vertex)) continue;
            // returns false if a cycle is detected
            if(!topologicalSortDFSHelper(vertex, visited, path, stack))
                return new ArrayList<>();  // cycle found — no valid order
        }

        // Pop the stack into a list — this is the topological order
        List<String> result = new ArrayList<>();
        while(!stack.isEmpty()) result.add(stack.pop());
        return result;
    }

    // Returns false if a cycle is detected, true otherwise.
    private boolean topologicalSortDFSHelper(String vertex,
                                              HashSet<String> visited,
                                              HashSet<String> path,
                                              Deque<String> stack){
        path.add(vertex);     // mark as on the current DFS path
        visited.add(vertex);  // mark as visited so we don't restart DFS from here

        for(int j = 0; j < size; j++){
            String neighbor = vertices.get(j);
            if(matrix.get(vertices.indexOf(vertex)).get(j) > 0){
                if(path.contains(neighbor)) return false;     // back edge → cycle
                if(!visited.contains(neighbor)){
                    if(!topologicalSortDFSHelper(neighbor, visited, path, stack))
                        return false;
                }
            }
        }

        path.remove(vertex);  // leaving this node — remove from current path
        stack.push(vertex);   // post-order: all neighbors done, now push self
        return true;
    }

    // ---- A* SEARCH ----
    // A* finds the shortest weighted path from src to dest, just like Dijkstra.
    // The difference: A* uses a *heuristic* to prioritise nodes that appear
    // to be closer to the destination, so it explores fewer nodes.
    //
    // Every node n has two values:
    //   g(n) = actual cost paid so far to reach n from src  (same as Dijkstra's distances map)
    //   h(n) = heuristic estimate of remaining cost from n to dest  (caller provides this)
    //   f(n) = g(n) + h(n)  ← the PQ sorts by this
    //
    // The heuristic MUST be admissible (never overestimate) to guarantee the
    // optimal path. Passing h = v -> 0 makes A* behave exactly like Dijkstra.
    //
    // Parameters:
    //   heuristic — a function from vertex name to estimated remaining cost
    //               e.g.  v -> 0          (admissible, = Dijkstra)
    //                     v -> myMap.get(v)  (domain-specific estimate)
    public List<String> aStar(String src, String dest, Function<String, Integer> heuristic){
        if(!vertices.contains(src) || !vertices.contains(dest)) return new ArrayList<>();

        // g(n): cheapest known actual cost to reach each node from src
        Map<String, Integer> g = new HashMap<>();

        // parents: which node did we arrive from on the best known path
        Map<String, String> parents = new HashMap<>();

        // visited: nodes that have been finalised (polled and processed)
        HashSet<String> visited = new HashSet<>();

        // PQ stores pairs of (f-cost, vertex), ordered by f-cost ascending.
        // f is baked in at insertion time — same pattern as Dijkstra's (cost, vertex) edges.
        // Stale entries (more expensive entries) are skipped via visited.
        PriorityQueue<Map.Entry<Integer, String>> pq = new PriorityQueue<>(
            (a, b) -> a.getKey() - b.getKey()
        );

        // Initialise: src has g=0, f=0+h(src)
        g.put(src, 0);
        parents.put(src, null);
        pq.offer(Map.entry(heuristic.apply(src), src));

        while(!pq.isEmpty()){
            Map.Entry<Integer, String> polled = pq.poll();
            String curr = polled.getValue();

            // Skip stale entries — a cheaper path to curr was already finalised
            if(visited.contains(curr)) continue;
            visited.add(curr);

            if(curr.equals(dest)) break;

            int currIndex = vertices.indexOf(curr);

            for(int j = 0; j < size; j++){
                int edgeWeight = matrix.get(currIndex).get(j);
                if(edgeWeight <= 0) continue;
                String neighbour = vertices.get(j);
                if(visited.contains(neighbour)) continue;

                int newG = g.get(curr) + edgeWeight;

                if(newG < g.getOrDefault(neighbour, Integer.MAX_VALUE)){
                    g.put(neighbour, newG);
                    parents.put(neighbour, curr);
                    // f = g(neighbour) + h(neighbour), baked in at insertion
                    pq.offer(Map.entry(newG + heuristic.apply(neighbour), neighbour));
                }
            }
        }

        if(!parents.containsKey(dest)) return new ArrayList<>();
        List<String> path = new ArrayList<>();
        String node = dest;
        while(node != null){ path.add(node); node = parents.get(node); }
        java.util.Collections.reverse(path);
        return path;
    }

    public static void main(String[] args){
        testTraversal();
        testCycleDFS();
        testCycleUnion();
        testTopologicalSort();
        testAStar();
    }

    private static void testTraversal(){
        System.out.println("-----Traversal-----");
        AdjacencyMatrixGraph undirectedGraph = new AdjacencyMatrixGraph();
        undirectedGraph.addVertex("A"); undirectedGraph.addVertex("B");
        undirectedGraph.addVertex("C"); undirectedGraph.addVertex("D");
        undirectedGraph.addVertex("E"); undirectedGraph.addVertex("F");
        undirectedGraph.addVertex("G");
        undirectedGraph.addEdge("D", "A", 1); undirectedGraph.addEdge("A", "D", 1);
        undirectedGraph.addEdge("E", "A", 1); undirectedGraph.addEdge("A", "E", 1);
        undirectedGraph.addEdge("A", "C", 1); undirectedGraph.addEdge("C", "A", 1);
        undirectedGraph.addEdge("E", "C", 1); undirectedGraph.addEdge("C", "E", 1);
        undirectedGraph.addEdge("C", "B", 1); undirectedGraph.addEdge("B", "C", 1);
        undirectedGraph.addEdge("C", "F", 1); undirectedGraph.addEdge("F", "C", 1);
        undirectedGraph.addEdge("B", "F", 1); undirectedGraph.addEdge("F", "B", 1);
        undirectedGraph.addEdge("C", "G", 1); undirectedGraph.addEdge("G", "C", 1);
        System.out.println("Undirected DFS from D: " + undirectedGraph.printGraphDFS("D"));
        System.out.println("Undirected BFS from D: " + undirectedGraph.printGraphBFS("D"));

        AdjacencyMatrixGraph directedGraph = new AdjacencyMatrixGraph();
        directedGraph.addVertex("A"); directedGraph.addVertex("B");
        directedGraph.addVertex("C"); directedGraph.addVertex("D");
        directedGraph.addVertex("E"); directedGraph.addVertex("F");
        directedGraph.addVertex("G");
        directedGraph.addEdge("D", "A", 1); directedGraph.addEdge("D", "E", 1);
        directedGraph.addEdge("A", "C", 1); directedGraph.addEdge("E", "A", 1);
        directedGraph.addEdge("C", "E", 1); directedGraph.addEdge("C", "G", 1);
        directedGraph.addEdge("C", "F", 1); directedGraph.addEdge("F", "B", 1);
        directedGraph.addEdge("B", "C", 1);
        System.out.println("Directed DFS from D: " + directedGraph.printGraphDFS("D"));
        System.out.println("Directed BFS from D: " + directedGraph.printGraphBFS("D"));
    }

    private static void testCycleDFS(){
        System.out.println("-----Is Cyclic DFS-----");
        AdjacencyMatrixGraph udCyclicGraph = new AdjacencyMatrixGraph();
        udCyclicGraph.addVertex("A"); udCyclicGraph.addVertex("B");
        udCyclicGraph.addVertex("C"); udCyclicGraph.addVertex("D"); udCyclicGraph.addVertex("E");
        udCyclicGraph.addEdge("A", "B", 1); udCyclicGraph.addEdge("B", "A", 1);
        udCyclicGraph.addEdge("B", "C", 1); udCyclicGraph.addEdge("C", "B", 1);
        udCyclicGraph.addEdge("B", "D", 1); udCyclicGraph.addEdge("D", "B", 1);
        udCyclicGraph.addEdge("C", "D", 1); udCyclicGraph.addEdge("D", "C", 1);
        udCyclicGraph.addEdge("D", "E", 1); udCyclicGraph.addEdge("E", "D", 1);
        System.out.println("Undirected IsCyclicDFS? (true) : " + udCyclicGraph.isUDCyclicDFS());

        AdjacencyMatrixGraph dCyclicGraph = new AdjacencyMatrixGraph();
        dCyclicGraph.addVertex("A"); dCyclicGraph.addVertex("B");
        dCyclicGraph.addVertex("C"); dCyclicGraph.addVertex("D"); dCyclicGraph.addVertex("E");
        dCyclicGraph.addEdge("A", "B", 1); dCyclicGraph.addEdge("A", "D", 1);
        dCyclicGraph.addEdge("B", "E", 1); dCyclicGraph.addEdge("C", "B", 1);
        dCyclicGraph.addEdge("C", "D", 1); dCyclicGraph.addEdge("E", "C", 1);
        System.out.println("Directed IsCyclicDFS? (true) : " + dCyclicGraph.isDCyclicDFS());

        AdjacencyMatrixGraph dLongCycle = new AdjacencyMatrixGraph();
        dLongCycle.addVertex("A"); dLongCycle.addVertex("B");
        dLongCycle.addVertex("C"); dLongCycle.addVertex("D");
        dLongCycle.addEdge("A", "B", 1); dLongCycle.addEdge("B", "C", 1);
        dLongCycle.addEdge("C", "D", 1); dLongCycle.addEdge("D", "A", 1);
        System.out.println("Directed long cycle A->B->C->D->A (true) : " + dLongCycle.isDCyclicDFS());

        AdjacencyMatrixGraph dag = new AdjacencyMatrixGraph();
        dag.addVertex("A"); dag.addVertex("B"); dag.addVertex("C"); dag.addVertex("D");
        dag.addEdge("A", "B", 1); dag.addEdge("A", "C", 1);
        dag.addEdge("B", "D", 1); dag.addEdge("C", "D", 1);
        System.out.println("Directed DAG diamond (false): " + dag.isDCyclicDFS());

        AdjacencyMatrixGraph dagShared = new AdjacencyMatrixGraph();
        dagShared.addVertex("Z"); dagShared.addVertex("A"); dagShared.addVertex("B");
        dagShared.addVertex("C"); dagShared.addVertex("D");
        dagShared.addEdge("Z", "A", 1); dagShared.addEdge("A", "B", 1);
        dagShared.addEdge("A", "C", 1); dagShared.addEdge("B", "D", 1); dagShared.addEdge("C", "D", 1);
        System.out.println("Directed DAG shared sink (false): " + dagShared.isCyclic(true));

        AdjacencyMatrixGraph tree = new AdjacencyMatrixGraph();
        tree.addVertex("A"); tree.addVertex("B"); tree.addVertex("C"); tree.addVertex("D");
        tree.addEdge("A", "B", 1); tree.addEdge("B", "A", 1);
        tree.addEdge("B", "C", 1); tree.addEdge("C", "B", 1);
        tree.addEdge("C", "D", 1); tree.addEdge("D", "C", 1);
        System.out.println("Undirected tree/path (false): " + tree.isUDCyclicDFS());
    }

    private static void testCycleUnion(){
        System.out.println("-----Is Cyclic Union-----");
        AdjacencyMatrixGraph uCyclic = new AdjacencyMatrixGraph();
        uCyclic.addVertex("A"); uCyclic.addVertex("B");
        uCyclic.addVertex("C"); uCyclic.addVertex("D");
        uCyclic.addEdge("A", "B", 1); uCyclic.addEdge("B", "A", 1);
        uCyclic.addEdge("B", "C", 1); uCyclic.addEdge("C", "B", 1);
        uCyclic.addEdge("B", "D", 1); uCyclic.addEdge("D", "B", 1);
        uCyclic.addEdge("C", "D", 1); uCyclic.addEdge("D", "C", 1);
        System.out.println("Undirected with cycle     (true) : " + uCyclic.isUDCyclicUnion());

        AdjacencyMatrixGraph uTree = new AdjacencyMatrixGraph();
        uTree.addVertex("A"); uTree.addVertex("B");
        uTree.addVertex("C"); uTree.addVertex("D");
        uTree.addEdge("A", "B", 1); uTree.addEdge("B", "A", 1);
        uTree.addEdge("B", "C", 1); uTree.addEdge("C", "B", 1);
        uTree.addEdge("C", "D", 1); uTree.addEdge("D", "C", 1);
        System.out.println("Undirected tree no cycle  (false): " + uTree.isUDCyclicUnion());

        AdjacencyMatrixGraph single = new AdjacencyMatrixGraph();
        single.addVertex("A");
        System.out.println("Single node               (false): " + single.isUDCyclicUnion());

        AdjacencyMatrixGraph dagSharedUnion = new AdjacencyMatrixGraph();
        dagSharedUnion.addVertex("Z"); dagSharedUnion.addVertex("A");
        dagSharedUnion.addVertex("B"); dagSharedUnion.addVertex("C"); dagSharedUnion.addVertex("D");
        dagSharedUnion.addEdge("Z", "A", 1); dagSharedUnion.addEdge("A", "Z", 1);
        dagSharedUnion.addEdge("A", "B", 1); dagSharedUnion.addEdge("B", "A", 1);
        dagSharedUnion.addEdge("A", "C", 1); dagSharedUnion.addEdge("C", "A", 1);
        dagSharedUnion.addEdge("B", "D", 1); dagSharedUnion.addEdge("D", "B", 1);
        dagSharedUnion.addEdge("C", "D", 1); dagSharedUnion.addEdge("D", "C", 1);
        System.out.println("Undirected shared sink    (true) : " + dagSharedUnion.isUDCyclicUnion());

        AdjacencyMatrixGraph flatUnionBug = new AdjacencyMatrixGraph();
        flatUnionBug.addVertex("A"); flatUnionBug.addVertex("B"); flatUnionBug.addVertex("C");
        flatUnionBug.addVertex("D"); flatUnionBug.addVertex("E");
        flatUnionBug.addEdge("A", "C", 1); flatUnionBug.addEdge("C", "A", 1);
        flatUnionBug.addEdge("B", "C", 1); flatUnionBug.addEdge("C", "B", 1);
        flatUnionBug.addEdge("B", "D", 1); flatUnionBug.addEdge("D", "B", 1);
        flatUnionBug.addEdge("D", "E", 1); flatUnionBug.addEdge("E", "D", 1);
        flatUnionBug.addEdge("A", "E", 1); flatUnionBug.addEdge("E", "A", 1);
        System.out.println("Flat union bug A-C-B-D-E-A cycle (true) : " + flatUnionBug.isUDCyclicUnion());
    }

    private static void testTopologicalSort(){
        System.out.println("-----Topological Sort-----");

        // Classic DAG: course prerequisites
        // A→C, B→C, B→D, C→E, D→F, E→F
        // Valid orders: [A,B,C,D,E,F] or [B,A,C,D,E,F] etc.
        AdjacencyMatrixGraph dag = new AdjacencyMatrixGraph();
        dag.addVertex("A"); dag.addVertex("B"); dag.addVertex("C");
        dag.addVertex("D"); dag.addVertex("E"); dag.addVertex("F");
        dag.addEdge("A", "C", 1);
        dag.addEdge("B", "C", 1); dag.addEdge("B", "D", 1);
        dag.addEdge("C", "E", 1);
        dag.addEdge("D", "F", 1);
        dag.addEdge("E", "F", 1);
        System.out.println("DAG Kahn : " + dag.topologicalSortKahn());
        System.out.println("DAG DFS  : " + dag.topologicalSortDFS());

        // Graph WITH a cycle — both should return []
        AdjacencyMatrixGraph cyclic = new AdjacencyMatrixGraph();
        cyclic.addVertex("A"); cyclic.addVertex("B"); cyclic.addVertex("C");
        cyclic.addEdge("A", "B", 1);
        cyclic.addEdge("B", "C", 1);
        cyclic.addEdge("C", "A", 1); // back edge creates cycle
        System.out.println("Cyclic Kahn ([]): " + cyclic.topologicalSortKahn());
        System.out.println("Cyclic DFS  ([]): " + cyclic.topologicalSortDFS());

        // Single node — trivial valid order
        AdjacencyMatrixGraph single = new AdjacencyMatrixGraph();
        single.addVertex("A");
        System.out.println("Single node Kahn ([A]): " + single.topologicalSortKahn());
        System.out.println("Single node DFS  ([A]): " + single.topologicalSortDFS());
    }

    private static void testAStar(){
        System.out.println("-----A* Search-----");

        // Graph: A-B (w=10), A-C (w=1), C-B (w=1)
        // Optimal path is [A,C,B] with cost=2. Direct A-B costs 10.
        AdjacencyMatrixGraph g = new AdjacencyMatrixGraph();
        g.addVertex("A"); g.addVertex("B"); g.addVertex("C");
        g.addEdge("A", "B", 10); g.addEdge("B", "A", 10);
        g.addEdge("A", "C", 1);  g.addEdge("C", "A", 1);
        g.addEdge("C", "B", 1);  g.addEdge("B", "C", 1);

        // --- CASE 1: h=0 (pure Dijkstra) ---
        // No heuristic guidance. Explores all nodes by cost. Finds optimal path.
        System.out.println("Dijkstra (h=0)                      ([A,C,B]): "
            + g.aStar("A", "B", v -> 0));

        // --- CASE 2: A* with admissible heuristic ---
        // h(C)=1, h(A)=2, h(B)=0 — never overestimates real remaining cost.
        // f(C) = g(C)+h(C) = 1+1 = 2   →  C is preferred first
        // f(B) = g(B)+h(B) = 10+0 = 10 →  direct B is deprioritised
        // Result: same optimal [A,C,B]. A* just gets there with less exploration.
        System.out.println("A* admissible h (same result)       ([A,C,B]): "
            + g.aStar("A", "B", v -> v.equals("C") ? 1 : v.equals("A") ? 2 : 0));

        // --- CASE 3: A* with INADMISSIBLE heuristic (overestimates) ---
        // h(C)=999 wildly overestimates cost from C to B.
        // f(C) = g(C)+h(C) = 1+999 = 1000  →  C looks terrible, A* avoids it
        // f(B) = g(B)+h(B) = 10+0   = 10   →  direct B looks cheap by comparison
        // A* finalises B immediately via the direct edge → returns WRONG path [A,B]
        // This is why admissibility matters: break it, lose the optimality guarantee.
        System.out.println("A* INADMISSIBLE h (WRONG — not opt) ([A,B])  : "
            + g.aStar("A", "B", v -> v.equals("C") ? 999 : 0));

        // --- CASE 4: disconnected ---
        AdjacencyMatrixGraph g2 = new AdjacencyMatrixGraph();
        g2.addVertex("A"); g2.addVertex("B"); g2.addVertex("C");
        g2.addEdge("A", "B", 1); g2.addEdge("B", "A", 1);
        System.out.println("Disconnected                        ([]): "
            + g2.aStar("A", "C", v -> 0));

        // --- CASE 5: same node ---
        System.out.println("Same node                           ([A]): "
            + g.aStar("A", "A", v -> 0));
    }
}
