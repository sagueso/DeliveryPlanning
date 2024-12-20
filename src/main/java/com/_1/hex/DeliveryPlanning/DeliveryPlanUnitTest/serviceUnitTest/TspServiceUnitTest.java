package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.Delivery;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.tsp.Graph;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import com._1.hex.DeliveryPlanning.tsp.TSP;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TspServiceUnitTest {

    private TspService tspService = default!;
    @Mock
    private TSP1 tspMock = default!;
    @Mock
    private Request requestMock = default!;
    @Mock
    private GraphService graphServiceMock = default!;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tspService = new TspService(tspMock);
    }

    @Test
    public void testSearchSolution() {
        // Arrange
        when(requestMock.getWarehouse()).thenReturn(new Intersection(1L));
        when(requestMock.getTrip()).thenReturn(new ArrayList<>());
        when(tspMock.getSolution(anyInt())).thenReturn(0);

        // Act
        List<Long> result = tspService.searchSolution(100, requestMock, graphServiceMock);

        // Assert
        assertNotNull(result);
        verify(tspMock, times(1)).searchSolution(anyInt(), any(Graph.class));
    }

    @Test
    public void testGetNodes() {
        // Arrange
        List<Integer> expectedNodes = new ArrayList<>();
        expectedNodes.add(1);
        tspService.nodes = expectedNodes;

        // Act
        List<Integer> result = tspService.getNodes();

        // Assert
        assertEquals(expectedNodes, result);
    }

    @Test
    public void testGetDistances() {
        // Arrange
        List<Double> expectedDistances = new ArrayList<>();
        expectedDistances.add(10.0);
        tspService.distances = expectedDistances;

        // Act
        List<Double> result = tspService.getDistances();

        // Assert
        assertEquals(expectedDistances, result);
    }
}
