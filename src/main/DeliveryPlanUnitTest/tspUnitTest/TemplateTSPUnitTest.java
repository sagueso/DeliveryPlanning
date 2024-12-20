package com._1.hex.DeliveryPlanning.tsp;

import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TemplateTSPUnitTest {

    private TemplateTSP templateTSP;
    private Graph mockGraph;

    @BeforeEach
    public void setup() {
        mockGraph = new MockGraph();
        templateTSP = new TemplateTSPImpl();
    }

    @Test
    public void testSearchSolution() {
        // Arrange
        int timeLimit = 1000;

        // Act
        templateTSP.searchSolution(timeLimit, mockGraph);

        // Assert
        assertNotNull(templateTSP.getSolution(0));
        assertTrue(templateTSP.getSolutionCost() >= 0);
    }

    @Test
    public void testGetSolution() {
        // Arrange
        int timeLimit = 1000;
        templateTSP.searchSolution(timeLimit, mockGraph);

        // Act
        Integer solution = templateTSP.getSolution(0);

        // Assert
        assertNotNull(solution);
    }

    @Test
    public void testGetSolutionCost() {
        // Arrange
        int timeLimit = 1000;
        templateTSP.searchSolution(timeLimit, mockGraph);

        // Act
        double solutionCost = templateTSP.getSolutionCost();

        // Assert
        assertTrue(solutionCost >= 0);
    }

}
