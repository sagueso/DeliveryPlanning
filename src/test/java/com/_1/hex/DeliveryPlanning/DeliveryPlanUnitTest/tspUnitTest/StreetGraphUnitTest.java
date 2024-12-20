package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.model.Warehouse;
import com._1.hex.DeliveryPlanning.model.Request.trip;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import com.github.tomakehurst.wiremock.common.Gzip;

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
    public void testGetPredecessor() {
        when(request.getPredecessor(1)).thenReturn(0);
        assertEquals(0, streetGraph.getPredecessor(1));
    }
}
