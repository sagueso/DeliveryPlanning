package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.tsp.TemplateTSP;
import com._1.hex.DeliveryPlanning.tsp.Graph;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TemplateTSPUnitTest {

    private TemplateTSP tsp;
    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = mock(Graph.class);
        tsp = new TemplateTSP() {
            @Override
            protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
                return 0; // Mock implementation
            }

            @Override
            protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
                return unvisited.iterator(); // Mock implementation
            }
        };
    }

    @Test
    public void testSearchSolution() {
        when(graph.getNbVertices()).thenReturn(5);
        when(graph.getCost(anyInt(), anyInt())).thenReturn(1.0);
        when(graph.isArc(anyInt(), anyInt())).thenReturn(true);
        when(graph.getPredecessor(anyInt())).thenReturn(-1);

        tsp.searchSolution(1000, graph);

        assertNotEquals(-1, tsp.getSolution(0));
        assertNotEquals(-1, tsp.getSolution(1));
        assertNotEquals(-1, tsp.getSolution(2));
        assertNotEquals(-1, tsp.getSolution(3));
        assertNotEquals(-1, tsp.getSolution(4));
        assertTrue(tsp.getSolutionCost() > 0);
    }

    @Test
    public void testGetSolution() {
        when(graph.getNbVertices()).thenReturn(5);
        tsp.searchSolution(1000, graph);

        assertEquals(-1, tsp.getSolution(5));
        assertEquals(-1, tsp.getSolution(-1));
    }

    @Test
    public void testGetSolutionCost() {
        when(graph.getNbVertices()).thenReturn(5);
        tsp.searchSolution(1000, graph);

        assertTrue(tsp.getSolutionCost() > 0);
    }

    @Test
    public void testBranchAndBound() {
        when(graph.getNbVertices()).thenReturn(5);
        when(graph.getCost(anyInt(), anyInt())).thenReturn(1.0);
        when(graph.isArc(anyInt(), anyInt())).thenReturn(true);
        when(graph.getPredecessor(anyInt())).thenReturn(-1);

        Collection<Integer> unvisited = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Collection<Integer> visited = new ArrayList<>(Collections.singletonList(0));
        Map<Integer, Double> costBetweenTakeUpAndReturn = new HashMap<>();
        costBetweenTakeUpAndReturn.put(0, 0.0);

        boolean result = tsp.branchAndBound(0, unvisited, visited, 0, costBetweenTakeUpAndReturn, 300);

        assertTrue(result);
    }
}
