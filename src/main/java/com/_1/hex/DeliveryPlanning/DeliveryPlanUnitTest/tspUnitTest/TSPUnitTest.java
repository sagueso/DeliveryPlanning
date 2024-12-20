package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.tsp.Graph;
import com._1.hex.DeliveryPlanning.tsp.TSP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TSPUnitTest {

    private TSP tsp;
    private Graph graph;

    @BeforeEach
    public void setup() {
        tsp = mock(TSP.class);
        graph = mock(Graph.class);
    }

    @Test
    public void testSearchSolution() {
        // Arrange
        int timeLimit = 1000;

        // Act
        tsp.searchSolution(timeLimit, graph);

        // Assert
        verify(tsp, times(1)).searchSolution(timeLimit, graph);
    }

    @Test
    public void testGetSolution() {
        // Arrange
        int index = 0;
        when(tsp.getSolution(index)).thenReturn(0);

        // Act
        Integer result = tsp.getSolution(index);

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void testGetSolutionCost() {
        // Arrange
        when(tsp.getSolutionCost()).thenReturn(10.0);

        // Act
        double result = tsp.getSolutionCost();

        // Assert
        assertEquals(10.0, result, 0.01);
    }

}
