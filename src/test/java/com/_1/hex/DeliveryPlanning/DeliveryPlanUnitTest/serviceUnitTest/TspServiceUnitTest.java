package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.Delivery;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TspServiceUnitTest {

    @Mock
    private TSP1 tsp;

    @Mock
    private GraphService graphService;

    @InjectMocks
    private TspService tspService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tspService = new TspService(tsp);
    }

    @Test
    public void testSearchSolution() {
        Request request = new Request();
        // Set up the request object with necessary data

        when(tsp.getSolution(0)).thenReturn(0);
        when(tsp.getSolution(1)).thenReturn(1);
        when(tsp.getSolution(2)).thenReturn(2);
        // Add more mock returns as needed

        List<Long> result = tspService.searchSolution(100, request, graphService);

        // Add assertions to verify the result
        assertEquals(Arrays.asList(/* expected list of node ids */), result);
    }

    @Test
    public void testGetNodes() {
        List<Integer> nodes = Arrays.asList(0, 1, 2);
        tspService.nodes = nodes;

        List<Integer> result = tspService.getNodes();

        assertEquals(nodes, result);
    }

    @Test
    public void testGetDistances() {
        List<Double> distances = Arrays.asList(10.0, 20.0, 30.0);
        tspService.distances = distances;

        List<Double> result = tspService.getDistances();

        assertEquals(distances, result);
    }
}
