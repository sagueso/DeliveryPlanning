package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class StreetGraphUnitTest {

    private Request request;
    private GraphService graphService;
    private StreetGraph streetGraph;

    @BeforeEach
    public void setUp() {
        request = Mockito.mock(Request.class);
        graphService = Mockito.mock(GraphService.class);
        streetGraph = new StreetGraph(request, graphService);
    }

    @Test
    public void testGetNbVertices() {
        when(request.getNbOfIntersections()).thenReturn(5);
        assertEquals(5, streetGraph.getNbVertices());
    }

    @Test
    public void testGetCostValid() {
        Intersection intersection1 = Mockito.mock(Intersection.class);
        Intersection intersection2 = Mockito.mock(Intersection.class);
        when(intersection1.getId()).thenReturn(1L);
        when(intersection2.getId()).thenReturn(2L);
        when(request.getWarehouse()).thenReturn(intersection1);
        when(request.getTrip()).thenReturn(Collections.singletonList(new Request.Trip(intersection1, intersection2)));
        when(request.getDistance(1L, 2L)).thenReturn(10.0);

        assertEquals(10.0, streetGraph.getCost(0, 1));
    }

    @Test
    public void testGetCostInvalid() {
        when(request.getTrip()).thenReturn(Collections.singletonList(new Request.Trip(null, null)));
        assertNull(streetGraph.getCost(-1, 1));
        assertNull(streetGraph.getCost(1, -1));
        assertNull(streetGraph.getCost(3, 1));
        assertNull(streetGraph.getCost(1, 3));
    }

    @Test
    public void testGetCostComputeShortestPath() {
        Intersection intersection1 = Mockito.mock(Intersection.class);
        Intersection intersection2 = Mockito.mock(Intersection.class);
        when(intersection1.getId()).thenReturn(1L);
        when(intersection2.getId()).thenReturn(2L);
        when(request.getWarehouse()).thenReturn(intersection1);
        when(request.getTrip()).thenReturn(Collections.singletonList(new Request.Trip(intersection1, intersection2)));
        when(request.getDistance(1L, 2L)).thenReturn(null);
        when(graphService.computeTheShortestPath(any(Intersection.class), any(Intersection.class)))
                .thenReturn(new Pair<>(Arrays.asList(1L, 2L), 15.0));

        assertEquals(15.0, streetGraph.getCost(0, 1));
    }

    @Test
    public void testIsArcValid() {
        Intersection intersection1 = Mockito.mock(Intersection.class);
        Intersection intersection2 = Mockito.mock(Intersection.class);
        when(request.getWarehouse()).thenReturn(intersection1);
        when(request.getTrip()).thenReturn(Collections.singletonList(new Request.Trip(intersection1, intersection2)));
        when(graphService.doesPathExists(any(Intersection.class), any(Intersection.class))).thenReturn(true);

        assertTrue(streetGraph.isArc(0, 1));
    }

    @Test
    public void testIsArcInvalid() {
        when(request.getTrip()).thenReturn(Collections.singletonList(new Request.Trip(null, null)));
        assertFalse(streetGraph.isArc(-1, 1));
        assertFalse(streetGraph.isArc(1, -1));
        assertFalse(streetGraph.isArc(3, 1));
        assertFalse(streetGraph.isArc(1, 3));
    }

    @Test
    public void testGetPredecessor() {
        when(request.getPredecessor(1)).thenReturn(0);
        assertEquals(0, streetGraph.getPredecessor(1));
    }
}
