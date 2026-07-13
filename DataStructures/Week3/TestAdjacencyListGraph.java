package DataStructures.Week3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import DataStructures.Week3.AdjacencyListGraph.Edge;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAdjacencyListGraph {
    
    AdjacencyListGraph operationsGraph;
    AdjacencyListGraph emptyGraph;
    AdjacencyListGraph singleElementGraph;
    AdjacencyListGraph selfLoopGraph;
    AdjacencyListGraph disconnectedGraph;
    AdjacencyListGraph UAGraph;
    AdjacencyListGraph UCGraph;
    AdjacencyListGraph DAGraph;
    AdjacencyListGraph DCGraph;

    @BeforeEach
    void setupGraphs(){
        operationsGraph = new AdjacencyListGraph();
        operationsGraph.addVertex("A");
        operationsGraph.addVertex("B");
        operationsGraph.addVertex("C");
        assertTrue(operationsGraph.addEdge("A", "B", 1));

        //empty
        emptyGraph = new AdjacencyListGraph();
        //single element
        singleElementGraph = new AdjacencyListGraph();
        singleElementGraph.addVertex("A");
        //undirected, acyclic,unweighted
        UAGraph =  new AdjacencyListGraph();
        UAGraph.addVertex("A");UAGraph.addVertex("B");
        UAGraph.addVertex("C");UAGraph.addVertex("D");
        UAGraph.addEdge("A", "B", 1);UAGraph.addEdge("B", "A", 1);
        UAGraph.addEdge("A", "C", 1);UAGraph.addEdge("C", "A", 1);
        UAGraph.addEdge("B", "D", 1);UAGraph.addEdge("D", "B", 1);
        UAGraph.addEdge("C", "D", 1);UAGraph.addEdge("D", "C", 1);
        //undirected, cyclic,unweighted
        UCGraph =  new AdjacencyListGraph();
        UCGraph.addVertex("A");UCGraph.addVertex("B");
        UCGraph.addVertex("C");UCGraph.addVertex("D");
        UCGraph.addEdge("A", "B", 1);UCGraph.addEdge("B", "A", 1);
        UCGraph.addEdge("A", "C", 1);UCGraph.addEdge("C", "A", 1);
        UCGraph.addEdge("B", "D", 1);UCGraph.addEdge("D", "B", 1);
        UCGraph.addEdge("C", "D", 1);UCGraph.addEdge("D", "C", 1);
        UCGraph.addEdge("C", "B", 1);UCGraph.addEdge("B", "C", 1);
        //directed, acyclic,unweighted
        DAGraph =  new AdjacencyListGraph();
        DAGraph.addVertex("A");DAGraph.addVertex("B");
        DAGraph.addVertex("C");DAGraph.addVertex("D");
        DAGraph.addEdge("A", "B", 1);
        DAGraph.addEdge("A","C", 1);
        DAGraph.addEdge("B", "D", 1);
        DAGraph.addEdge("C", "D", 1);
        //directed, cyclic,unweighted
        DCGraph =  new AdjacencyListGraph();
        DCGraph.addVertex("A");DCGraph.addVertex("B");
        DCGraph.addVertex("C");DCGraph.addVertex("D");
        DCGraph.addEdge("A", "B", 1);
        DCGraph.addEdge("A","C", 1);
        DCGraph.addEdge("B", "D", 1);
        DCGraph.addEdge("C", "D", 1);
        DCGraph.addEdge("C", "B", 1);
        //disconnected
        disconnectedGraph = new AdjacencyListGraph();
        disconnectedGraph.addVertex("A");disconnectedGraph.addVertex("B");
        disconnectedGraph.addVertex("C");disconnectedGraph.addVertex("D");
        disconnectedGraph.addEdge("A", "B", 1);
        disconnectedGraph.addEdge("C", "D", 1);
        //self-loop
        selfLoopGraph = new AdjacencyListGraph();
        selfLoopGraph.addVertex("A");
        selfLoopGraph.addEdge("A","A", 1);
    }

    //#region addVertex
    @Test
    void addVertex_P_returnsTrue(){
        assertTrue(operationsGraph.addVertex("P"));
    }

    @Test
    void addVertex_existingVertex_returnFalse(){
        assertFalse(operationsGraph.addVertex("A"));
    }

    @Test
    void addVertex_null_throwsIllegalArgumentException(){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addVertex(null));
    }

    @Test
    void addVertex_empty_throwsIllegalArgumentException(){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addVertex(""));
    }

    //#endregion

    //#region addEdge
   @Test
    void addEdge_existingVertexes_returnTrue(){
        operationsGraph.addVertex("P");
        operationsGraph.addVertex("Z");
        assertTrue(operationsGraph.addEdge("P", "Z", 1));
    }

    @Test
    void addEdge_existingEdge_returnTrueAndUpdatedValue(){
        operationsGraph.addVertex("P");
        operationsGraph.addVertex("Z");
        operationsGraph.addEdge("P", "Z", 1);
        assertAll(()->{
            assertTrue(operationsGraph.addEdge("P", "Z", 2));        
            assertEquals(2, operationsGraph.getEdge("P", "Z"));
        });
    }
    
    @Test
    void addEdge_weightIsZero_throwsIllegalArgumentException(){
        operationsGraph.addVertex("P");
        operationsGraph.addVertex("Z");
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addEdge("P", "Z", 0));
    }

    @Test
    void addEdge_negativeWeight_throwsIllegalArgumentException(){
        operationsGraph.addVertex("P");
        operationsGraph.addVertex("Z");
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addEdge("P", "Z", -2));
    }

    @ParameterizedTest
    @CsvSource({", B","A, ",", "})
    void addEdge_null_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addEdge(src, dest, 1));
    }

    @ParameterizedTest
    @CsvSource({"'', B", "A, ''","'', ''"})
    void addEdge_empty_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.addEdge(src, dest, 1));
    }

    @ParameterizedTest
    @CsvSource({"A, P","P, B","P, P"})
    void addEdge_nonExisting_returnFalse(String src, String dest){
        assertFalse(operationsGraph.addEdge(src, dest, 1));
    }
    //#endregion

    //#region getEdge

    @Test
    void getEdge_existing_returnOne(){
        assertEquals(1, operationsGraph.getEdge("A", "B"));
    }

    @ParameterizedTest
    @CsvSource({"A, P","P, B","P, P"})
    void getEdge_nonExisting_returnZero(String src, String dest){
        assertEquals(0, operationsGraph.getEdge(src, dest));
    }

    @ParameterizedTest
    @CsvSource({", P","P, ",", "})
    void getEdge_null_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.getEdge(src, dest));
    }

    @ParameterizedTest
    @CsvSource({"'', P","P, ''","'', ''"})
    void getEdge_empty_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.getEdge(src, dest));
    }
    //#endregion

    //#region dijkstra

    record DijkstraCase(
        String label,
        Supplier<AdjacencyListGraph> graph,
        String src,
        String dest,
        List<String> expected
    ){}

    Stream<DijkstraCase> dijkstraCases(){
        AdjacencyListGraph nonOptimalBFS = new AdjacencyListGraph();
        nonOptimalBFS.addVertex("A");nonOptimalBFS.addVertex("B");
        nonOptimalBFS.addVertex("C");nonOptimalBFS.addVertex("D");
        nonOptimalBFS.addEdge("A", "B", 10);
        nonOptimalBFS.addEdge("A", "C", 2);
        nonOptimalBFS.addEdge("B", "D", 1);
        nonOptimalBFS.addEdge("C", "B", 2);
        return Stream.of(
            new DijkstraCase("empty_A_to_A", ()->emptyGraph, "A", "A", List.of()),
            new DijkstraCase("singleElement_A_to_A", ()->singleElementGraph, "A", "A", List.of("A")),
            new DijkstraCase("UAG_A_to_D", ()->UAGraph, "A", "D", List.of("A","B","D")),
            new DijkstraCase("UCG_A_to_D", ()->UCGraph, "A", "D", List.of("A","B","D")),
            new DijkstraCase("DAG_A_to_D", ()->DAGraph, "A","D", List.of("A","B","D")),
            new DijkstraCase("DCG_A_to_D", ()->DCGraph, "A", "D", List.of("A","B","D")),
            new DijkstraCase("disconnected_A_to_C", ()->disconnectedGraph, "A", "C",List.of()),
            new DijkstraCase("selfLoop_A_to_A", ()->selfLoopGraph, "A", "A", List.of("A")),
            new DijkstraCase("weighted_BFS_not_optimal_A_to_D", ()->nonOptimalBFS, "A", "D", List.of("A","C","B","D"))
        );
    }

    @ParameterizedTest
    @CsvSource({", P","P, ",", "})
    void dijkstra_null_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.dijkstra(src, dest));
    }

    @ParameterizedTest
    @CsvSource({"'', P","P, ''","'', ''"})
    void dijkstra_empty_throwsIllegalArgumentException(String src, String dest){
        assertThrowsExactly(IllegalArgumentException.class, ()->operationsGraph.dijkstra(src, dest));
    }

    @Test
    void dijkstra_nonExistingDest_returnEmptyList(){
        assertTrue(operationsGraph.dijkstra("A", "P").isEmpty());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("dijkstraCases")
    void dijkstra_variousGraphs_returnsExpectedPath(DijkstraCase tc){
        assertEquals(tc.expected(), tc.graph().get().dijkstra(tc.src(), tc.dest()));
    }

    //#endregion

    //#region topologicalSortKahn

    record topologicalSortKahnCase(
        String label,
        Supplier<AdjacencyListGraph> graph
    ){}

    Stream<topologicalSortKahnCase> topologicalSortKahnCases(){
        return Stream.of(
            new topologicalSortKahnCase("empty", ()->emptyGraph),
            new topologicalSortKahnCase("singleElement", ()->singleElementGraph),
            new topologicalSortKahnCase("UAG", ()->UAGraph),
            new topologicalSortKahnCase("UCG", ()->UCGraph),
            new topologicalSortKahnCase("DAG", ()->DAGraph),
            new topologicalSortKahnCase("DCG", ()->DCGraph),
            new topologicalSortKahnCase("disconnected", ()->disconnectedGraph),
            new topologicalSortKahnCase("selfLoop", ()->selfLoopGraph)
        );
    }

    @ParameterizedTest(name="{0}")
    @MethodSource("topologicalSortKahnCases")
    void topologicalSortKahn_variousInputs_validOutputs(topologicalSortKahnCase tc){
        AdjacencyListGraph graph = tc.graph().get();
        List<String> result = graph.topologicalSortKahn();

        boolean allExistOnlyOnce = true;
        boolean edgeOrderRespected = true;
        HashSet<String> seen = new HashSet<>();
        for (String v : result) {
            List<Edge> edges = graph.getEdges(v);
            for(Edge edge : edges){
                if(seen.contains(edge.dest)) edgeOrderRespected = false;
            }
            if(seen.contains(v)){
                allExistOnlyOnce = false;
            }   
            seen.add(v);
        }
        int expectedSize = graph.isDCyclicDFS() ? 0 : graph.getNumberOfVertices();
        // all nodes appear in output max once
        assertTrue(allExistOnlyOnce, "each vertex appears only once");
        // each node v appears before node t if v has an edge to t (v -> t)
        // e.g: A->B, A->C, B->D, B->C, C->D gives: A,C,B,D, because C->B
        assertTrue(edgeOrderRespected, "edge order respected (src before dest)");
        // cyclic -> empty list; acyclic -> all vertices present
        assertEquals(expectedSize, result.size(), "result size");
    }
    //#endregion
}
