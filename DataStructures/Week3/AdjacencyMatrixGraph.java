package DataStructures.Week3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        matrix.get(originIndex).add(destIndex, weight);
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
        if(size-1 < originIndex || size-1 < destIndex) return false;
        return true;
    }

    public String printGraphBFS(){
        return "";
    }

    public String printGraphDFS(String vertex){
        return graphDFSToString(vertex, new HashSet<>());
    }

    private String graphDFSToString(String vertex, HashSet<String> visited){
        int originIndex = vertices.indexOf(vertex);
        if(originIndex == -1) return "";
        String result = "";
        List<Integer> edges = matrix.get(originIndex);
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

    public static void main(String[] args){
        AdjacencyMatrixGraph adjmgraph = new AdjacencyMatrixGraph();
        adjmgraph.addVertex("A");
        adjmgraph.addVertex("B");
        adjmgraph.addEdge("A", "B", 2);
        int edgeAtoB = adjmgraph.getEdge("A", "B");
        System.out.println("A to B: " + edgeAtoB);
        
        adjmgraph.addVertex("C");
        int edgeAtoC = adjmgraph.getEdge("A", "C");
        System.out.println("A to C: " + edgeAtoC);

        adjmgraph.addEdge("B", "C", 1);
        int edgeBtoC = adjmgraph.getEdge("B", "C");
        System.out.println("B to C: " + edgeBtoC);

        System.out.println(adjmgraph.printGraphDFS("A"));
    }
}
