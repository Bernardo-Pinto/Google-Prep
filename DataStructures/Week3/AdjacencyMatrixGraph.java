package DataStructures.Week3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        List<Integer> temp = matrix.get(originIndex);
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

    public static void main(String[] args){

        System.out.println("-----Is Cyclic-----");
        AdjacencyMatrixGraph udCyclicGraph = new AdjacencyMatrixGraph();
        udCyclicGraph.addVertex("A");
        udCyclicGraph.addVertex("B");
        udCyclicGraph.addVertex("C");
        udCyclicGraph.addVertex("D");
        udCyclicGraph.addVertex("E");

        udCyclicGraph.addEdge("A", "B", 1);
        udCyclicGraph.addEdge("B", "A", 1);

        udCyclicGraph.addEdge("B", "C", 1);
        udCyclicGraph.addEdge("C", "B", 1);

        udCyclicGraph.addEdge("B", "D", 1);
        udCyclicGraph.addEdge("D", "B", 1);

        udCyclicGraph.addEdge("C", "D", 1);
        udCyclicGraph.addEdge("D", "C", 1);

        udCyclicGraph.addEdge("D", "E", 1);
        udCyclicGraph.addEdge("E", "D", 1);

        System.out.println("Undirected IsCyclicDFS? (true) : " + udCyclicGraph.isUDCyclicDFS());

        AdjacencyMatrixGraph dCyclicGraph = new AdjacencyMatrixGraph();
        dCyclicGraph.addVertex("A");
        dCyclicGraph.addVertex("B");
        dCyclicGraph.addVertex("C");
        dCyclicGraph.addVertex("D");
        dCyclicGraph.addVertex("E");

        dCyclicGraph.addEdge("A", "B", 1);
        dCyclicGraph.addEdge("A", "D", 1);
        dCyclicGraph.addEdge("B", "E", 1);
        dCyclicGraph.addEdge("C", "B", 1);
        dCyclicGraph.addEdge("C", "D", 1);
        dCyclicGraph.addEdge("E", "C", 1);
        System.out.println("Directed IsCyclicDFS? (true) : " + dCyclicGraph.isDCyclicDFS());

        // directed: long cycle — back edge does NOT point to immediate parent (A→B→C→D→A)
        AdjacencyMatrixGraph dLongCycle = new AdjacencyMatrixGraph();
        dLongCycle.addVertex("A");
        dLongCycle.addVertex("B");
        dLongCycle.addVertex("C");
        dLongCycle.addVertex("D");
        dLongCycle.addEdge("A", "B", 1);
        dLongCycle.addEdge("B", "C", 1);
        dLongCycle.addEdge("C", "D", 1);
        dLongCycle.addEdge("D", "A", 1); // back edge skips all parents
        System.out.println("Directed long cycle A->B->C->D->A (true) : " + dLongCycle.isDCyclicDFS());

        // directed: DAG (diamond) — no cycle
        AdjacencyMatrixGraph dag = new AdjacencyMatrixGraph();
        dag.addVertex("A");
        dag.addVertex("B");
        dag.addVertex("C");
        dag.addVertex("D");
        dag.addEdge("A", "B", 1);
        dag.addEdge("A", "C", 1);
        dag.addEdge("B", "D", 1);
        dag.addEdge("C", "D", 1); // diamond: two paths to D, but no cycle
        System.out.println("Directed DAG diamond (false): " + dag.isDCyclicDFS());

        // DAG with shared sink: Z→A, A→B, A→C, B→D, C→D — no cycle
        AdjacencyMatrixGraph dagShared = new AdjacencyMatrixGraph();
        dagShared.addVertex("Z"); dagShared.addVertex("A");
        dagShared.addVertex("B"); dagShared.addVertex("C"); dagShared.addVertex("D");
        dagShared.addEdge("Z", "A", 1);
        dagShared.addEdge("A", "B", 1); dagShared.addEdge("A", "C", 1);
        dagShared.addEdge("B", "D", 1); dagShared.addEdge("C", "D", 1);
        System.out.println("Directed DAG shared sink (false): " + dagShared.isCyclic(true));

        // undirected: tree/path — no cycle
        AdjacencyMatrixGraph tree = new AdjacencyMatrixGraph();
        tree.addVertex("A");
        tree.addVertex("B");
        tree.addVertex("C");
        tree.addVertex("D");
        tree.addEdge("A", "B", 1); tree.addEdge("B", "A", 1);
        tree.addEdge("B", "C", 1); tree.addEdge("C", "B", 1);
        tree.addEdge("C", "D", 1); tree.addEdge("D", "C", 1);
        System.out.println("Undirected tree/path (false): " + tree.isUDCyclicDFS());

        System.out.println("--------New undirectedGraph-------");
        AdjacencyMatrixGraph undirectedGraph = new AdjacencyMatrixGraph();
        undirectedGraph.addVertex("A");
        undirectedGraph.addVertex("B");
        undirectedGraph.addVertex("C");
        undirectedGraph.addVertex("D");
        undirectedGraph.addVertex("E");
        undirectedGraph.addVertex("F");
        undirectedGraph.addVertex("G");
        undirectedGraph.addEdge("D", "A", 1);
        undirectedGraph.addEdge("A", "D", 1);
        undirectedGraph.addEdge("E", "A", 1);
        undirectedGraph.addEdge("A", "E", 1);
        undirectedGraph.addEdge("A", "C", 1);
        undirectedGraph.addEdge("C", "A", 1);
        undirectedGraph.addEdge("E", "C", 1);
        undirectedGraph.addEdge("C", "E", 1);
        undirectedGraph.addEdge("C", "B", 1);
        undirectedGraph.addEdge("B", "C", 1);
        undirectedGraph.addEdge("C", "F", 1);
        undirectedGraph.addEdge("F", "C", 1);
        undirectedGraph.addEdge("B", "F", 1);
        undirectedGraph.addEdge("F", "B", 1);
        undirectedGraph.addEdge("C", "G", 1);
        undirectedGraph.addEdge("G", "C", 1);
        System.out.println("DFS: " + undirectedGraph.printGraphDFS("D"));
        System.out.println("BFS: " + undirectedGraph.printGraphBFS("D"));

         System.out.println("--------New directedGraph-------");
        AdjacencyMatrixGraph directedGraph = new AdjacencyMatrixGraph();
        directedGraph.addVertex("A");
        directedGraph.addVertex("B");
        directedGraph.addVertex("C");
        directedGraph.addVertex("D");
        directedGraph.addVertex("E");
        directedGraph.addVertex("F");
        directedGraph.addVertex("G");
        directedGraph.addEdge("D", "A", 1);
        directedGraph.addEdge("D", "E", 1);
        directedGraph.addEdge("A", "C", 1);
        directedGraph.addEdge("E", "A", 1);
        directedGraph.addEdge("C", "E", 1);
        directedGraph.addEdge("C", "G", 1);
        directedGraph.addEdge("C", "F", 1);
        directedGraph.addEdge("F", "B", 1);
        directedGraph.addEdge("B", "C", 1);

        System.out.println("DFS: " + directedGraph.printGraphDFS("D"));
        System.out.println("BFS: " + directedGraph.printGraphBFS("D"));

        System.out.println("-----Is Cyclic Union-----");
        // undirected with cycle: A-B, B-C, B-D, C-D
        AdjacencyMatrixGraph uCyclic = new AdjacencyMatrixGraph();
        uCyclic.addVertex("A"); uCyclic.addVertex("B");
        uCyclic.addVertex("C"); uCyclic.addVertex("D");
        uCyclic.addEdge("A", "B", 1); uCyclic.addEdge("B", "A", 1);
        uCyclic.addEdge("B", "C", 1); uCyclic.addEdge("C", "B", 1);
        uCyclic.addEdge("B", "D", 1); uCyclic.addEdge("D", "B", 1);
        uCyclic.addEdge("C", "D", 1); uCyclic.addEdge("D", "C", 1);
        System.out.println("Undirected with cycle     (true) : " + uCyclic.isUDCyclicUnion());

        // undirected tree — no cycle: A-B-C-D
        AdjacencyMatrixGraph uTree = new AdjacencyMatrixGraph();
        uTree.addVertex("A"); uTree.addVertex("B");
        uTree.addVertex("C"); uTree.addVertex("D");
        uTree.addEdge("A", "B", 1); uTree.addEdge("B", "A", 1);
        uTree.addEdge("B", "C", 1); uTree.addEdge("C", "B", 1);
        uTree.addEdge("C", "D", 1); uTree.addEdge("D", "C", 1);
        System.out.println("Undirected tree no cycle  (false): " + uTree.isUDCyclicUnion());

        // single node, no edges
        AdjacencyMatrixGraph single = new AdjacencyMatrixGraph();
        single.addVertex("A");
        System.out.println("Single node               (false): " + single.isUDCyclicUnion());

        // DAG shared sink: Z→A, A→B, A→C, B→D, C→D — undirected version has no cycle
        AdjacencyMatrixGraph dagSharedUnion = new AdjacencyMatrixGraph();
        dagSharedUnion.addVertex("Z"); dagSharedUnion.addVertex("A");
        dagSharedUnion.addVertex("B"); dagSharedUnion.addVertex("C"); dagSharedUnion.addVertex("D");
        dagSharedUnion.addEdge("Z", "A", 1); dagSharedUnion.addEdge("A", "Z", 1);
        dagSharedUnion.addEdge("A", "B", 1); dagSharedUnion.addEdge("B", "A", 1);
        dagSharedUnion.addEdge("A", "C", 1); dagSharedUnion.addEdge("C", "A", 1);
        dagSharedUnion.addEdge("B", "D", 1); dagSharedUnion.addEdge("D", "B", 1);
        dagSharedUnion.addEdge("C", "D", 1); dagSharedUnion.addEdge("D", "C", 1);
        System.out.println("Undirected shared sink    (true) : " + dagSharedUnion.isUDCyclicUnion());

        // flat union bug: A-C, B-C, B-D, D-E, A-E forms a cycle A-C-B-D-E-A
        // flat union misses it because it doesn't find the component root
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
}
