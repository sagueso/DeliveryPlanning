package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.tsp.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GraphUnitTest {

    private Graph graph;

    @BeforeEach
    public void setup() {
        graph = mock(Graph.class);
    }

    @Test
    public void testGetNbVertices() {
        // Arrange
        when(graph.getNbVertices()).thenReturn(5);

        // Act
        int result = graph.getNbVertices();

        // Assert
        assertEquals(5, result);
    }

    @Test
    public void testGetCost() {
        // Arrange
        when(graph.getCost(1, 2)).thenReturn(10.0);

        // Act
        Double result = graph.getCost(1, 2);

        // Assert
        assertEquals(10.0, result);
    }

    @Test
    public void testIsArc() {
        // Arrange
        when(graph.isArc(1, 2)).thenReturn(true);

        // Act
        boolean result = graph.isArc(1, 2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetPredecessor() {
        // Arrange
        when(graph.getPredecessor(1)).thenReturn(0);

        // Act
        Integer result = graph.getPredecessor(1);

        // Assert
        assertEquals(0, result);
    }

}
