package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.tsp.Graph;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class TSP1UnitTest {

    private TSP1 tsp1;

    @BeforeEach
    public void setup() {
        tsp1 = new TSP1();
    }

    @Test
    public void testBound() {
        // Arrange
        Integer currentVertex = 1;
        Collection<Integer> unvisited = Collections.emptyList();

        // Act
        int result = tsp1.bound(currentVertex, unvisited);

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void testIterator() {
        // Arrange
        Integer currentVertex = 1;
        Collection<Integer> unvisited = Collections.emptyList();
        Graph graph = new Graph();

        // Act
        Iterator<Integer> result = tsp1.iterator(currentVertex, unvisited, graph);

        // Assert
        assertNotNull(result);
    }
}
