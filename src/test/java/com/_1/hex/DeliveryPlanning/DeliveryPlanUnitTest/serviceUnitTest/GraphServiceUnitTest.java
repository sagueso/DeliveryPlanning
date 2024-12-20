package com._1.hex.DeliveryPlanning.service;

import java.util.*;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphServiceUnitTest {

    private GraphService graphService;
    private StreetMap streetMap;
    private Intersection intersection1;
    private Intersection intersection2;
    private Intersection intersection3;
    private Street street1;
    private Street street2;

    @BeforeEach
    public void setUp() {
        graphService = new GraphService();
        intersection1 = new Intersection(1, 1L);
        intersection2 = new Intersection(2, 2L);
        intersection3 = new Intersection(3, 3L);
        street1 = new Street(intersection1, intersection2, 10.0);
        street2 = new Street(intersection2, intersection3, 15.0);
        streetMap = new StreetMap(Arrays.asList(intersection1, intersection2, intersection3), Arrays.asList(street1, street2));
        graphService.addMap(streetMap);
    }

    @Test
    public void testAddMap() {
        assertTrue(graphService.doesPathExists(intersection1, intersection2));
        assertTrue(graphService.doesPathExists(intersection2, intersection3));
        assertFalse(graphService.doesPathExists(intersection1, intersection3));
    }

    @Test
    public void testDoesPathExists() {
        assertTrue(graphService.doesPathExists(intersection1, intersection2));
        assertTrue(graphService.doesPathExists(intersection2, intersection3));
        assertFalse(graphService.doesPathExists(intersection1, intersection3));
    }

    @Test
    public void testComputeTheShortestPath() {
        Pair<List<Long>, Double> result = graphService.computeTheShortestPath(intersection1, intersection2);
        assertNotNull(result);
        assertEquals(Arrays.asList(1L, 2L), result.getFirst());
        assertEquals(10.0, result.getSecond());

        result = graphService.computeTheShortestPath(intersection2, intersection3);
        assertNotNull(result);
        assertEquals(Arrays.asList(2L, 3L), result.getFirst());
        assertEquals(15.0, result.getSecond());

        result = graphService.computeTheShortestPath(intersection1, intersection3);
        assertNull(result);
    }
}
