package com._1.hex.DeliveryPlanningUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GraphServiceUnitTest {

    private GraphService graphService = default!;
    private StreetMap streetMap = default!;
    private Intersection source = default!;
    private Intersection target = default!;

    @BeforeEach
    public void setup() {
        graphService = new GraphService();
        streetMap = Mockito.mock(StreetMap.class);
        source = Mockito.mock(Intersection.class);
        target = Mockito.mock(Intersection.class);
    }

    @Test
    public void testAddMap() {
        // Arrange
        Mockito.when(streetMap.getIntersectionsIds()).thenReturn(Arrays.asList(1, 2, 3));
        Street street = new Street();
        street.setOrigin(new Intersection(1, 1L));
        street.setDestination(new Intersection(2, 2L));
        street.setLength(10.0);
        Mockito.when(streetMap.getStreets()).thenReturn(Arrays.asList(street));
        Map<Integer, Intersection> intersections = new HashMap<>();
        intersections.put(1, new Intersection(1, 1L));
        intersections.put(2, new Intersection(2, 2L));
        Mockito.when(streetMap.getIntersections()).thenReturn(intersections);

        // Act
        graphService.addMap(streetMap);

        // Assert
        assertNotNull(graphService);
    }

    @Test
    public void testDoesPathExists() {
        // Arrange
        graphService.addMap(streetMap);
        Mockito.when(source.getInternalId()).thenReturn(1);
        Mockito.when(target.getInternalId()).thenReturn(2);

        // Act
        boolean result = graphService.doesPathExists(source, target);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testComputeTheShortestPath() {
        // Arrange
        graphService.addMap(streetMap);
        Mockito.when(source.getInternalId()).thenReturn(1);
        Mockito.when(target.getInternalId()).thenReturn(2);

        // Act
        Pair<List<Long>, Double> result = graphService.computeTheShortestPath(source, target);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getFirst().size());
        assertEquals(10.0, result.getSecond());
    }
}
