package DataStructures.Week3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
                int weight = e.weight;
                if(weight > 0 && !visited.contains(e.dest)){
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
        for(Map.Entry<String,List<Edge>> entry : edges.entrySet()){
            for(Edge e : entry.getValue()){
                if(e.weight>0){
                    HashSet<String> visited = new HashSet<>();
                    HashSet<String> path = new HashSet<>();
                    visited.add(entry.getKey());
                    path.add(entry.getKey());
                    if(isDCyclicDFSHelper(e.dest, visited, path)) return true;
                }
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
        return null;
    }

    public static void main(String[] args){
        testSubGraphs();
        testTraversal();
        testCycleDFS();
        testCycleUnion();
        testShortestPath();
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
    }
}
