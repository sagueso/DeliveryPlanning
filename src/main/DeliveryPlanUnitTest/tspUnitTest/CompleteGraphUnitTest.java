package com._1.hex.DeliveryPlanning.tsp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class CompleteGraphUnitTest {

    private CompleteGraph completeGraph;

    @BeforeEach
    public void setup() {
        completeGraph = new CompleteGraph(5);
    }

    @Test
    public void testGetNbVertices() {
        // Arrange
        int expectedNbVertices = 5;

        // Act
        int actualNbVertices = completeGraph.getNbVertices();

        // Assert
        assertEquals(expectedNbVertices, actualNbVertices);
    }

    @Test
    public void testGetCost() {
        // Arrange
        int i = 0;
        int j = 1;

        // Act
        Double cost = completeGraph.getCost(i, j);

        // Assert
        assertNotNull(cost);
        assertTrue(cost >= 10 && cost <= 40);
    }

    @Test
    public void testIsArc() {
        // Arrange
        int i = 0;
        int j = 1;

        // Act
        boolean isArc = completeGraph.isArc(i, j);

        // Assert
        assertTrue(isArc);
    }

    @Test
    public void testGetPredecessor() {
        // Arrange
        int i = 0;

        // Act
        Integer predecessor = completeGraph.getPredecessor(i);

        // Assert
        assertEquals(-1, predecessor);
    }

}
