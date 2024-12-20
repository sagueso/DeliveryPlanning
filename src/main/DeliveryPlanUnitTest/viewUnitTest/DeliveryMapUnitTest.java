package com._1.hex.DeliveryPlanning.view;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryMapUnitTest {

    private DeliveryMap deliveryMap;
    private StreetMap streetMapMock;

    @BeforeEach
    public void setup() {
        deliveryMap = new DeliveryMap();
        streetMapMock = mock(StreetMap.class);
    }

    @Test
    public void testSetStreetMap() {
        Map<Integer, Intersection> intersectionsMock = mock(Map.class);
        when(streetMapMock.getIntersections()).thenReturn(intersectionsMock);

        deliveryMap.setStreetMap(streetMapMock);

        assertEquals(streetMapMock, deliveryMap.streetMap);
        verify(streetMapMock, times(1)).getIntersections();
    }

    @Test
    public void testSetRoute() {
        List<Intersection> route = new ArrayList<>();
        deliveryMap.setRoute(route);

        assertEquals(route, deliveryMap.route);
    }

    @Test
    public void testSetSelectedIntersections() {
        List<Intersection> selectedIntersections = new ArrayList<>();
        deliveryMap.setSelectedIntersections(selectedIntersections);

        assertEquals(selectedIntersections, deliveryMap.selectedIntersections);
    }

    @Test
    public void testNormalizeLatitude() {
        deliveryMap.minLatitudeValue = 0.0;
        deliveryMap.maxLatitudeValue = 10.0;

        Double normalized = deliveryMap.normalizeLatitude(5.0);

        assertEquals(500.0, normalized);
    }

    @Test
    public void testNormalizeLongitude() {
        deliveryMap.minLongitudeValue = 0.0;
        deliveryMap.maxLongitudeValue = 10.0;

        Double normalized = deliveryMap.normalizeLongitude(5.0);

        assertEquals(500.0, normalized);
    }

    @Test
    public void testFindClickedIntersection() {
        Intersection intersectionMock = mock(Intersection.class);
        when(intersectionMock.getLatitude()).thenReturn(5.0);
        when(intersectionMock.getLongitude()).thenReturn(5.0);

        Map<Integer, Intersection> intersectionsMock = mock(Map.class);
        when(intersectionsMock.values()).thenReturn(List.of(intersectionMock));
        when(streetMapMock.getIntersections()).thenReturn(intersectionsMock);

        deliveryMap.setStreetMap(streetMapMock);

        Point point = new Point(500, 500);
        Intersection foundIntersection = deliveryMap.findClickedIntersection(point);

        assertEquals(intersectionMock, foundIntersection);
    }
}
