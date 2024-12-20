package com._1.hex.DeliveryPlanningUnitTest;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StreetGraphUnitTest {

    private Request request;
    private GraphService graphService;
    private StreetGraph streetGraph;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        graphService = mock(GraphService.class);
        streetGraph = new StreetGraph(request, graphService);
    }

    @Test
    public void testGetNbVertices() {
        when(request.getNbOfIntersections()).thenReturn(5);

        int nbVertices = streetGraph.getNbVertices();

        assertEquals(5, nbVertices);
    }

    @Test
    public void testGetCost() {
        Intersection intersection1 = mock(Intersection.class);
        Intersection intersection2 = mock(Intersection.class);
        when(request.getWarehouse()).thenReturn(intersection1);
        when(request.getTrip()).thenReturn(List.of());
        when(intersection1.getId()).thenReturn(1L);
        when(intersection2.getId()).thenReturn(2L);
        when(request.getDistance(1L, 2L)).thenReturn(10.0);

        Double cost = streetGraph.getCost(0, 1);

        assertEquals(10.0, cost);
    }

    @Test
    public void testIsArc() {
        Intersection intersection1 = mock(Intersection.class);
        Intersection intersection2 = mock(Intersection.class);
        when(request.getWarehouse()).thenReturn(intersection1);
        when(request.getTrip()).thenReturn(List.of());
        when(graphService.doesPathExists(intersection1, intersection2)).thenReturn(true);

        boolean isArc = streetGraph.isArc(0, 1);

        assertTrue(isArc);
    }

    @Test
    public void testGetPredecessor() {
        when(request.getPredecessor(1)).thenReturn(0);

        Integer predecessor = streetGraph.getPredecessor(1);

        assertEquals(0, predecessor);
    }
}
