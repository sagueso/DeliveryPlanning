package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import java.util.HashMap;
import java.util.Map;

import com._1.hex.DeliveryPlanning.tsp.CompleteGraph;
import com._1.hex.DeliveryPlanning.tsp.TSP;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunTSPUnitTest {

    private TSP tsp;
    private TSP tsp1;

    @BeforeEach
    public void setup() {
        tsp = new TSP1();
        tsp1 = new TSP1();
    }

    @Test
    public void testSearchSolutionNonPred() {
        // Arrange
        int nbVertices = 8;
        CompleteGraph graphNonPred = new CompleteGraph(nbVertices);

        // Act
        tsp.searchSolution(20000, graphNonPred);
        double solutionCost = tsp.getSolutionCost();

        // Assert
        assertTrue(solutionCost > 0);
    }

    @Test
    public void testSearchSolutionPred() {
        // Arrange
        int nbVertices = 8;
        Map<Integer, Integer> predecessors = new HashMap<>();
        predecessors.put(1, 2);
        predecessors.put(5, 4);
        CompleteGraph graphNonPred = new CompleteGraph(nbVertices);
        CompleteGraph graphPred = new CompleteGraph(graphNonPred, predecessors);

        // Act
        tsp1.searchSolution(20000, graphPred);
        double solutionCost = tsp1.getSolutionCost();

        // Assert
        assertTrue(solutionCost > 0);
    }

}
