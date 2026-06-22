package DataStructures.Week3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Queue;

public class AdjacencyListGraph {


    private class Edge{
        public int weight;
        public String dest;
        public Edge(int w, String dest){
            this.dest = dest;
            this.weight = w;
        }
    }

    private Map<String, List<Edge>> edges; 

    public AdjacencyListGraph(){
        this.edges = new HashMap<>();
    }

    public boolean addVertex(String vertex){
        if(this.edges.containsKey(vertex)) return false;
        this.edges.put(vertex, new ArrayList<>());
        return true;
    }

    public boolean addEdge(String origin, String dest, int weight){
        if(weight < 0) return false;
        List<Edge> vertexEdges = edges.get(origin);
        if(vertexEdges == null) return false;
        Edge originToDestEdge = vertexEdges.stream()
            .filter(e -> e.dest.equals(dest))
            .findFirst()
            .orElse(null);
        if(originToDestEdge != null){
            originToDestEdge.weight = weight;
        } else {
            vertexEdges.add(new Edge(weight, dest));
        }
        return true;
    }

    public void deleteEdge(){

    }

    public int getEdge(String origin, String dest){
        if(!edges.containsKey(origin)) return 0;
        List<Edge> originEdges = edges.get(origin);
        Edge originDestEdge = originEdges.stream()
            .filter( e -> e.dest.equals(dest))
            .findFirst()
            .orElse(null);
        if(originDestEdge == null) return 0;
        return originDestEdge.weight;
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
            List<Edge> vertexEdges = this.edges.get(vertex);
            for(Edge e : vertexEdges){
                if(e.weight > 0 && !visited.contains(e.dest)){
                    queue.offer(e.dest);
                    visited.add(e.dest);
                }
            }
        }
        return result;
    }

    public String printGraphDFS(String vertex){
        HashSet<String> visited = new HashSet<>();
        visited.add(vertex);
        return graphDFSToString(vertex,visited);
    }

    private String graphDFSToString(String vertex, HashSet<String> visited){
        if(!edges.containsKey(vertex)) return "";
        String result = "";
        
        List<Edge> vertexEdges = edges.get(vertex);
        for(Edge e : vertexEdges){
            if(!e.dest.equals(vertex) && e.weight > 0 && !visited.contains(e.dest)){
                visited.add(e.dest);
                result += graphDFSToString(e.dest, visited);
            }
        }
        return vertex + result;
    }

    public boolean isCyclic(boolean directed){
        if(directed) return isDCyclicDFS();
        else return isUDCyclicDFS();
    }

    private boolean isUDCyclicDFS(){
        for(Map.Entry<String,List<Edge>> entry  : edges.entrySet()){
            for(Edge e : entry.getValue()){
                if(e.weight > 0){
                    HashSet<String> visited =  new HashSet<>();
                    visited.add(entry.getKey());
                    if(isUDCyclicDFSHelper(e.dest, entry.getKey(), visited)) return true;
                }
            }
        }
        return false;
    }

    private boolean isUDCyclicDFSHelper(String vertex,String parent, HashSet<String> visited){
        if(visited.contains(vertex)) return true;
        for(Edge e : edges.get(vertex)){
            if(parent.equals(e.dest)) continue;
            if(e.weight > 0) {
                visited.add(vertex);
                if(isUDCyclicDFSHelper(e.dest, vertex, visited)) return true;
            }
        }
        return false;
    }

    private boolean isDCyclicDFS(){
        HashSet<String> visited = new HashSet<>();
        HashSet<String> path = new HashSet<>();
        for(String vertex : edges.keySet()){
            if(!visited.contains(vertex)){
                if(isDCyclicDFSHelper(vertex, visited, path)) return true;
            }
        }
        return false;
    }

    private boolean isDCyclicDFSHelper(String vertex, HashSet<String> visited, HashSet<String> path){
        if(path.contains(vertex))return true; //if its on path, we can do the path again, cycle
        if(visited.contains(vertex)) return false; //all nodes explored, no cycle
        path.add(vertex);
        visited.add(vertex);

        for(Edge e : edges.get(vertex)){
            if(e.weight>0){
                if(isDCyclicDFSHelper(e.dest, visited, path)) return true;;
            }
        }
        path.remove(vertex);
        return false;
    }

    public boolean isUDCyclicUnion(){

        List<String> vertices = new ArrayList<>(edges.keySet());
        List<String> parents = new ArrayList<>(vertices);

        for(String vertex : edges.keySet()){
            for(Edge e : edges.get(vertex)){
                if(e.dest.compareTo(vertex)<0) continue; //do not process already processed nodes
                int rootI = findRoot(vertices.indexOf(vertex),vertices, parents);
                int rootJ = findRoot(vertices.indexOf(e.dest), vertices, parents);
                if(e.weight>0){
                    if(rootI == rootJ) return true;
                    else parents.set(rootJ, parents.get(rootI));
                }

            }
        }
        return false;
    }

    private int findRoot(int index, List<String> vertices, List<String> parents){
        while(!parents.get(index).equals(vertices.get(index))){
            index = vertices.indexOf(parents.get(index));
        }
        return index;
    }

    public List<String> findAllGraphsToStringBFS(){
        List<String> result =  new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        for(String vertex : edges.keySet()){
            String graph = "";
            Queue<String> queue = new LinkedList<>();
            queue.offer(vertex);
            while(!queue.isEmpty()){

                String curr = queue.poll();
                if(visited.contains(curr)) continue;
                graph += curr;
                visited.add(curr);
                for(Edge e : edges.get(curr)){
                    if(e.weight>0 && !visited.contains(e.dest)){
                        queue.offer(e.dest);
                    }
                }
            }
            if(!graph.isEmpty()) result.add(graph);
        }
        return result;
    }

    public List<String> findAllGraphsToStringDFS(){
        HashSet<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();

        for(String vertex : edges.keySet()){
            if(visited.contains(vertex)) continue;
            visited.add(vertex);
            String graph = "";
            graph += findAllGraphsToStringDFSHelper(vertex,visited);
            if(!graph.isEmpty()) result.add(graph);
        }
        return result;
    }

    private String findAllGraphsToStringDFSHelper(String vertex, HashSet<String> visited){
        String result = "";
        for(Edge e : edges.get(vertex)){
            if(visited.contains(e.dest)) continue;
            visited.add(e.dest);
            if(e.weight>0){
                result += findAllGraphsToStringDFSHelper(e.dest, visited);
            }
        }
        return vertex + result;
    }

    public List<String> shortestPath(String src, String dest){
        if(!edges.containsKey(src) || !edges.containsKey(dest)) return new ArrayList<>();
        Map<String, String> parents = new HashMap<>();
        Queue<String> queue = new LinkedList<>(); // using BFS to find shortest path
        queue.offer(src);
        parents.put(src, null);

        while(!queue.isEmpty()){
            String curr = queue.poll();
            if(curr.equals(dest)) break;
            for(Edge e : edges.get(curr)){
                if(e.weight>0 && !parents.containsKey(e.dest)){
                    queue.offer(e.dest);
                    parents.put(e.dest, curr);
                }
            }
        }
        List<String> path = new ArrayList<>();
        if(!parents.containsKey(dest)) return path;

        String next = dest;
        while(next != null){
            path.add(next);
            next = parents.get(next);
        }
        return path.reversed();
    }


    public List<String> dijkstra(String src, String dest){
        //need: 
        // parents (where did I come from): HashMap<String,String>
        // edges: Map<List<Edge>>
        // distances (total cost of shortest path to get to me): HashMap<String, int>
        // priority queue with minheap to get lowest cost easily from queue and O(1)
        HashMap<String,String> parents = new HashMap<>();
        HashMap<String,Integer> nodeValues = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        PriorityQueue<Edge> pQueue = new PriorityQueue<>((e1,e2)->e1.weight-e2.weight);
        nodeValues.put(src, 0);
        pQueue.offer(new Edge(0,src));
        parents.put(src, null);

        //pQ has pairs: weight is value of vertex, and vertex
        while(!pQueue.isEmpty()){
            Edge curr = pQueue.poll();
            if(visited.contains(curr.dest)) continue; //we already processed this node
            visited.add(curr.dest);
            if(curr.dest.equals(dest)) break;

            for(Edge e : edges.get(curr.dest)){
                if(e.weight >= 0){ //dijkstra only processes non negative values
                    Integer newCost = e.weight + nodeValues.get(curr.dest);
                    Integer destValue = nodeValues.get(e.dest);
                    if(destValue == null || newCost < destValue){
                        parents.put(e.dest, curr.dest);
                        nodeValues.put(e.dest, newCost);
                        pQueue.offer(new Edge(newCost,e.dest));
                    }
                }
            }
        }
        List<String> path = new ArrayList<>();
        if(!parents.containsKey(dest)) return path;

        String next = dest;
        while(next != null){
            path.add(next);
            next = parents.get(next);
        }
        return path.reversed();
    }

    //BFS
    public List<String> topologicalSortKahn(){
        List<String> result =  new ArrayList<>();
        HashMap<String, Integer> inDegree = new HashMap<>();
        for(String node : edges.keySet()){
            inDegree.put(node, 0);
        }
        for(String node : edges.keySet()){
            for(Edge e : edges.get(node)){
                inDegree.merge(e.dest, 1, (a,b) -> a+b);
            }
        }
        Queue<String> queue =  new LinkedList<>();
        inDegree.forEach((k,v) -> {
            if(v == 0) queue.offer(k);
        });

        while (!queue.isEmpty()) {
            String node = queue.poll();
            result.add(node);
            for(Edge e : edges.get(node)){
                inDegree.merge(e.dest, -1, (a,b) -> a+b);
                if(inDegree.get(e.dest) == 0) queue.offer(e.dest);
            }
        }
        if(result.size() != edges.size()) return new ArrayList<>();
        return result;
    }

    public List<String> topologicalSortDFS(){
        List<String> result = new ArrayList<>();
        HashSet<String> path = new HashSet<>();
        HashSet<String> visited = new HashSet<>();
        for(String vertex : edges.keySet()){
            if(!visited.contains(vertex)){
                if(!topologicalSortDFSHelper(vertex, result, visited, path)){
                    return new ArrayList<>();
                }
            }
        }
        return result.reversed();
    }

    private boolean topologicalSortDFSHelper(String curr,List<String> result, HashSet<String> visited, HashSet<String> path){
        path.add(curr);
        visited.add(curr);

        for(Edge e : edges.get(curr)){
            if(path.contains(e.dest)) return false;
            if(!visited.contains(e.dest)) 
                if(!topologicalSortDFSHelper(e.dest, result, visited, path))
                    return false;
        }
        path.remove(curr);
        result.add(curr);
        return true;
    }

    public static void main(String[] args){
        // testSubGraphs();
        // testTraversal();
        // testCycleDFS();
        // testCycleUnion();
        //testDijkstra();
        testTopologicalSort();
    }

    private static void testSubGraphs(){
        System.out.println("-----Find SubGraphs-----");
        AdjacencyListGraph subGraphs = new AdjacencyListGraph();
        subGraphs.addVertex("A");
        subGraphs.addVertex("B");
        subGraphs.addVertex("C");
        List<String> graphs = subGraphs.findAllGraphsToStringDFS();
        System.out.println("Size is 3? : (true) " + (graphs.size()==3));
        System.out.println("3 subgraphs: (A; B; C) " + graphs.get(0) + "; " 
            + graphs.get(1) + "; " 
            + graphs.get(2));
        subGraphs.addEdge("A", "B", 1);
        graphs = subGraphs.findAllGraphsToStringDFS();
        System.out.println("Size is 2? : (true) " + (graphs.size()==2));
        System.out.println("2 subgraphs: (AB; C) " + graphs.get(0) + "; " 
            + graphs.get(1));
    }

    private static void testTraversal(){
        System.out.println("-----Traversal-----");
        AdjacencyListGraph undirectedGraph = new AdjacencyListGraph();
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

        AdjacencyListGraph directedGraph = new AdjacencyListGraph();
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
        AdjacencyListGraph udCyclicGraph = new AdjacencyListGraph();
        udCyclicGraph.addVertex("A"); udCyclicGraph.addVertex("B");
        udCyclicGraph.addVertex("C"); udCyclicGraph.addVertex("D");
        udCyclicGraph.addVertex("E");
        udCyclicGraph.addEdge("A", "B", 1); udCyclicGraph.addEdge("B", "A", 1);
        udCyclicGraph.addEdge("B", "C", 1); udCyclicGraph.addEdge("C", "B", 1);
        udCyclicGraph.addEdge("B", "D", 1); udCyclicGraph.addEdge("D", "B", 1);
        udCyclicGraph.addEdge("C", "D", 1); udCyclicGraph.addEdge("D", "C", 1);
        udCyclicGraph.addEdge("D", "E", 1); udCyclicGraph.addEdge("E", "D", 1);
        System.out.println("Undirected IsCyclicDFS? (true) : " + udCyclicGraph.isUDCyclicDFS());

        AdjacencyListGraph dCyclicGraph = new AdjacencyListGraph();
        dCyclicGraph.addVertex("A"); dCyclicGraph.addVertex("B");
        dCyclicGraph.addVertex("C"); dCyclicGraph.addVertex("D"); dCyclicGraph.addVertex("E");
        dCyclicGraph.addEdge("A", "B", 1); dCyclicGraph.addEdge("A", "D", 1);
        dCyclicGraph.addEdge("B", "E", 1); dCyclicGraph.addEdge("C", "B", 1);
        dCyclicGraph.addEdge("C", "D", 1); dCyclicGraph.addEdge("E", "C", 1);
        System.out.println("Directed IsCyclicDFS? (true) : " + dCyclicGraph.isDCyclicDFS());

        AdjacencyListGraph dLongCycle = new AdjacencyListGraph();
        dLongCycle.addVertex("A"); dLongCycle.addVertex("B");
        dLongCycle.addVertex("C"); dLongCycle.addVertex("D");
        dLongCycle.addEdge("A", "B", 1); dLongCycle.addEdge("B", "C", 1);
        dLongCycle.addEdge("C", "D", 1); dLongCycle.addEdge("D", "A", 1);
        System.out.println("Directed long cycle A->B->C->D->A (true) : " + dLongCycle.isDCyclicDFS());

        AdjacencyListGraph dag = new AdjacencyListGraph();
        dag.addVertex("A"); dag.addVertex("B"); dag.addVertex("C"); dag.addVertex("D");
        dag.addEdge("A", "B", 1); dag.addEdge("A", "C", 1);
        dag.addEdge("B", "D", 1); dag.addEdge("C", "D", 1);
        System.out.println("Directed DAG diamond (false): " + dag.isDCyclicDFS());

        AdjacencyListGraph dagShared = new AdjacencyListGraph();
        dagShared.addVertex("Z"); dagShared.addVertex("A"); dagShared.addVertex("B");
        dagShared.addVertex("C"); dagShared.addVertex("D");
        dagShared.addEdge("Z", "A", 1); dagShared.addEdge("A", "B", 1);
        dagShared.addEdge("A", "C", 1); dagShared.addEdge("B", "D", 1); dagShared.addEdge("C", "D", 1);
        System.out.println("Directed DAG shared sink (false): " + dagShared.isCyclic(true));

        AdjacencyListGraph tree = new AdjacencyListGraph();
        tree.addVertex("A"); tree.addVertex("B"); tree.addVertex("C"); tree.addVertex("D");
        tree.addEdge("A", "B", 1); tree.addEdge("B", "A", 1);
        tree.addEdge("B", "C", 1); tree.addEdge("C", "B", 1);
        tree.addEdge("C", "D", 1); tree.addEdge("D", "C", 1);
        System.out.println("Undirected tree/path (false): " + tree.isUDCyclicDFS());
    }

    private static void testCycleUnion(){
        System.out.println("-----Is Cyclic Union-----");
        AdjacencyListGraph uCyclic = new AdjacencyListGraph();
        uCyclic.addVertex("A"); uCyclic.addVertex("B");
        uCyclic.addVertex("C"); uCyclic.addVertex("D");
        uCyclic.addEdge("A", "B", 1); uCyclic.addEdge("B", "A", 1);
        uCyclic.addEdge("B", "C", 1); uCyclic.addEdge("C", "B", 1);
        uCyclic.addEdge("B", "D", 1); uCyclic.addEdge("D", "B", 1);
        uCyclic.addEdge("C", "D", 1); uCyclic.addEdge("D", "C", 1);
        System.out.println("Undirected with cycle     (true) : " + uCyclic.isUDCyclicUnion());

        AdjacencyListGraph uTree = new AdjacencyListGraph();
        uTree.addVertex("A"); uTree.addVertex("B");
        uTree.addVertex("C"); uTree.addVertex("D");
        uTree.addEdge("A", "B", 1); uTree.addEdge("B", "A", 1);
        uTree.addEdge("B", "C", 1); uTree.addEdge("C", "B", 1);
        uTree.addEdge("C", "D", 1); uTree.addEdge("D", "C", 1);
        System.out.println("Undirected tree no cycle  (false): " + uTree.isUDCyclicUnion());

        AdjacencyListGraph single = new AdjacencyListGraph();
        single.addVertex("A");
        System.out.println("Single node               (false): " + single.isUDCyclicUnion());

        AdjacencyListGraph dagSharedUnion = new AdjacencyListGraph();
        dagSharedUnion.addVertex("Z"); dagSharedUnion.addVertex("A");
        dagSharedUnion.addVertex("B"); dagSharedUnion.addVertex("C"); dagSharedUnion.addVertex("D");
        dagSharedUnion.addEdge("Z", "A", 1); dagSharedUnion.addEdge("A", "Z", 1);
        dagSharedUnion.addEdge("A", "B", 1); dagSharedUnion.addEdge("B", "A", 1);
        dagSharedUnion.addEdge("A", "C", 1); dagSharedUnion.addEdge("C", "A", 1);
        dagSharedUnion.addEdge("B", "D", 1); dagSharedUnion.addEdge("D", "B", 1);
        dagSharedUnion.addEdge("C", "D", 1); dagSharedUnion.addEdge("D", "C", 1);
        System.out.println("Undirected shared sink    (true) : " + dagSharedUnion.isUDCyclicUnion());

        AdjacencyListGraph flatUnionBug = new AdjacencyListGraph();
        flatUnionBug.addVertex("A"); flatUnionBug.addVertex("B"); flatUnionBug.addVertex("C");
        flatUnionBug.addVertex("D"); flatUnionBug.addVertex("E");
        flatUnionBug.addEdge("A", "C", 1); flatUnionBug.addEdge("C", "A", 1);
        flatUnionBug.addEdge("B", "C", 1); flatUnionBug.addEdge("C", "B", 1);
        flatUnionBug.addEdge("B", "D", 1); flatUnionBug.addEdge("D", "B", 1);
        flatUnionBug.addEdge("D", "E", 1); flatUnionBug.addEdge("E", "D", 1);
        flatUnionBug.addEdge("A", "E", 1); flatUnionBug.addEdge("E", "A", 1);
        System.out.println("Flat union bug A-C-B-D-E-A cycle (true) : " + flatUnionBug.isUDCyclicUnion());
    }

    private static void testShortestPath(){
        System.out.println("-----Shortest Path (BFS)-----");
        // Linear path: A-B-C-D-E
        AdjacencyListGraph g = new AdjacencyListGraph();
        g.addVertex("A"); g.addVertex("B"); g.addVertex("C");
        g.addVertex("D"); g.addVertex("E");
        g.addEdge("A", "B", 1); g.addEdge("B", "A", 1);
        g.addEdge("B", "C", 1); g.addEdge("C", "B", 1);
        g.addEdge("C", "D", 1); g.addEdge("D", "C", 1);
        g.addEdge("D", "E", 1); g.addEdge("E", "D", 1);
        System.out.println("A to E (A-B-C-D-E): " + g.shortestPath("A", "E"));

        // Shortcut exists: A-B-C and A-C directly
        AdjacencyListGraph g2 = new AdjacencyListGraph();
        g2.addVertex("A"); g2.addVertex("B"); g2.addVertex("C"); g2.addVertex("D");
        g2.addEdge("A", "B", 1); g2.addEdge("B", "A", 1);
        g2.addEdge("B", "C", 1); g2.addEdge("C", "B", 1);
        g2.addEdge("A", "C", 1); g2.addEdge("C", "A", 1); // shortcut A→C
        g2.addEdge("C", "D", 1); g2.addEdge("D", "C", 1);
        System.out.println("A to D, shortcut A-C (A-C-D): " + g2.shortestPath("A", "D"));

        // No path (disconnected)
        AdjacencyListGraph g3 = new AdjacencyListGraph();
        g3.addVertex("A"); g3.addVertex("B"); g3.addVertex("C");
        g3.addEdge("A", "B", 1); g3.addEdge("B", "A", 1);
        System.out.println("A to C, disconnected ([]): " + g3.shortestPath("A", "C"));

        // Same node
        System.out.println("A to A ([A]): " + g.shortestPath("A", "A"));

        // Dest has onward neighbors: A-E direct, E-B; A to E should be [A,E] not via B
        AdjacencyListGraph g4 = new AdjacencyListGraph();
        g4.addVertex("A"); g4.addVertex("E"); g4.addVertex("B");
        g4.addEdge("A", "E", 1); g4.addEdge("E", "A", 1);
        g4.addEdge("E", "B", 1); g4.addEdge("B", "E", 1);
        System.out.println("A to E, E has neighbor B ([A,E]): " + g4.shortestPath("A", "E"));

        // Graph with a cycle: A-B-C-A, ask A to C; BFS should not loop
        AdjacencyListGraph g5 = new AdjacencyListGraph();
        g5.addVertex("A"); g5.addVertex("B"); g5.addVertex("C");
        g5.addEdge("A", "B", 1); g5.addEdge("B", "A", 1);
        g5.addEdge("B", "C", 1); g5.addEdge("C", "B", 1);
        g5.addEdge("C", "A", 1); g5.addEdge("A", "C", 1); // cycle
        System.out.println("A to C, cycle A-B-C-A ([A,C]): " + g5.shortestPath("A", "C"));
    }

    private static void testDijkstra(){
        System.out.println("-----Dijkstra (Weighted Shortest Path)-----");

        // Weights decide path: A-B direct (w=10), A-C (w=1), C-B (w=1)
        // BFS would return [A,B] (fewest hops), Dijkstra should return [A,C,B] (lowest cost=2)
        AdjacencyListGraph g = new AdjacencyListGraph();
        g.addVertex("A"); g.addVertex("B"); g.addVertex("C");
        g.addEdge("A", "B", 10); g.addEdge("B", "A", 10);
        g.addEdge("A", "C", 1);  g.addEdge("C", "A", 1);
        g.addEdge("C", "B", 1);  g.addEdge("B", "C", 1);
        System.out.println("A to B, cheaper via C ([A,C,B]): " + g.dijkstra("A", "B"));

        // Two paths, pick cheaper: A-B (w=1), B-D (w=1) vs A-C (w=1), C-D (w=5)
        // Dijkstra should pick A-B-D (cost=2) over A-C-D (cost=6)
        AdjacencyListGraph g2 = new AdjacencyListGraph();
        g2.addVertex("A"); g2.addVertex("B"); g2.addVertex("C"); g2.addVertex("D");
        g2.addEdge("A", "B", 1); g2.addEdge("B", "A", 1);
        g2.addEdge("B", "D", 1); g2.addEdge("D", "B", 1);
        g2.addEdge("A", "C", 1); g2.addEdge("C", "A", 1);
        g2.addEdge("C", "D", 5); g2.addEdge("D", "C", 5);
        System.out.println("A to D, cheaper via B ([A,B,D]): " + g2.dijkstra("A", "D"));

        // Disconnected
        AdjacencyListGraph g3 = new AdjacencyListGraph();
        g3.addVertex("A"); g3.addVertex("B"); g3.addVertex("C");
        g3.addEdge("A", "B", 1); g3.addEdge("B", "A", 1);
        System.out.println("A to C, disconnected ([]): " + g3.dijkstra("A", "C"));

        // Same node
        System.out.println("A to A ([A]): " + g.dijkstra("A", "A"));

        // Cheap first hop but expensive total: A-B (w=1), B-D (w=100), A-C (w=2), C-D (w=2)
        // Greedy first hop picks B (cheapest), but total via C is 4 vs 101
        // Dijkstra must pick [A,C,D]
        AdjacencyListGraph g4 = new AdjacencyListGraph();
        g4.addVertex("A"); g4.addVertex("B"); g4.addVertex("C"); g4.addVertex("D");
        g4.addEdge("A", "B", 1);   g4.addEdge("B", "A", 1);
        g4.addEdge("B", "D", 100); g4.addEdge("D", "B", 100);
        g4.addEdge("A", "C", 2);   g4.addEdge("C", "A", 2);
        g4.addEdge("C", "D", 2);   g4.addEdge("D", "C", 2);
        System.out.println("A to D, cheap first hop B but expensive total ([A,C,D]): " + g4.dijkstra("A", "D"));

        // Expensive direct shortcut vs cheap long path: A-E (w=100), A-B-C-D-E (w=1 each)
        // Dijkstra must pick [A,B,C,D,E] (cost=4) over [A,E] (cost=100)
        AdjacencyListGraph g5 = new AdjacencyListGraph();
        g5.addVertex("A"); g5.addVertex("B"); g5.addVertex("C"); g5.addVertex("D"); g5.addVertex("E");
        g5.addEdge("A", "E", 100); g5.addEdge("E", "A", 100);
        g5.addEdge("A", "B", 1);   g5.addEdge("B", "A", 1);
        g5.addEdge("B", "C", 1);   g5.addEdge("C", "B", 1);
        g5.addEdge("C", "D", 1);   g5.addEdge("D", "C", 1);
        g5.addEdge("D", "E", 1);   g5.addEdge("E", "D", 1);
        System.out.println("A to E, expensive direct vs cheap chain ([A,B,C,D,E]): " + g5.dijkstra("A", "E"));

        // Graph with a cycle: must not loop, must find [A,B,C]
        AdjacencyListGraph g6 = new AdjacencyListGraph();
        g6.addVertex("A"); g6.addVertex("B"); g6.addVertex("C");
        g6.addEdge("A", "B", 1); g6.addEdge("B", "A", 1);
        g6.addEdge("B", "C", 1); g6.addEdge("C", "B", 1);
        g6.addEdge("C", "A", 1); g6.addEdge("A", "C", 1);
        System.out.println("A to C with cycle, no loop ([A,C]): " + g6.dijkstra("A", "C"));
    }

    private static void testTopologicalSort(){
        System.out.println("-----Topological Sort-----");

        // Classic DAG: course prerequisites
        // A→C, B→C, B→D, C→E, D→F, E→F
        // Valid orders: [A,B,C,D,E,F] or [B,A,C,D,E,F] etc.
        AdjacencyListGraph dag = new AdjacencyListGraph();
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
        AdjacencyListGraph cyclic = new AdjacencyListGraph();
        cyclic.addVertex("A"); cyclic.addVertex("B"); cyclic.addVertex("C");
        cyclic.addEdge("A", "B", 1);
        cyclic.addEdge("B", "C", 1);
        cyclic.addEdge("C", "A", 1); // back edge creates cycle
        System.out.println("Cyclic Kahn ([]): " + cyclic.topologicalSortKahn());
        System.out.println("Cyclic DFS  ([]): " + cyclic.topologicalSortDFS());

        // Single node — trivial valid order
        AdjacencyListGraph single = new AdjacencyListGraph();
        single.addVertex("A");
        System.out.println("Single node Kahn ([A]): " + single.topologicalSortKahn());
        System.out.println("Single node DFS  ([A]): " + single.topologicalSortDFS());
    }
}
