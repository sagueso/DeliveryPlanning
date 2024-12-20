package com._1.hex.DeliveryPlanning.controller;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.utils.PersistenceFileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerUnitTest {

    @Mock
    private GraphService graphService;

    @Mock
    private TspService tspService;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller(graphService, tspService);
    }

    @Test
    public void testAddCourier() {
        Courrier courrier = new Courrier();
        controller.addCourier(courrier);
        assertEquals(1, controller.getCouriers().size());
    }

    @Test
    public void testGetCouriers() throws IOException {
        List<Courrier> couriers = new ArrayList<>();
        couriers.add(new Courrier());
        when(PersistenceFileUtils.readCouriersFromFile(anyString())).thenReturn(couriers);

        List<Courrier> result = controller.getCouriers();
        assertEquals(1, result.size());
    }

    @Test
    public void testAddStreetMap() {
        StreetMap streetMap = new StreetMap();
        controller.addStreetMap(streetMap);
        verify(graphService, times(1)).addMap(streetMap);
    }

    @Test
    public void testAddIntersection() {
        Intersection intersection = new Intersection();
        controller.addIntersection(intersection);
        assertEquals(1, controller.getSelectedIntersections().size());
    }

    @Test
    public void testComputeGraph() {
        StreetMap streetMap = new StreetMap();
        List<Long> solution = new ArrayList<>();
        solution.add(1L);
        when(tspService.searchSolution(anyInt(), any(Request.class), any(GraphService.class))).thenReturn(solution);
        when(streetMap.getIntersectionById(anyLong())).thenReturn(new Intersection());

        List<Intersection> result = controller.computeGraph(streetMap);
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveRouteToFile() {
        Courrier courrier = new Courrier();
        courrier.setId(1);
        controller.setPerson(courrier);
        controller.saveRouteToFile();
        verify(PersistenceFileUtils, times(1)).saveRouteToFile(any(Route.class), anyString());
    }

    @Test
    public void testLoadRouteFromFile() {
        Courrier courrier = new Courrier();
        courrier.setId(1);
        controller.setPerson(courrier);
        Route route = new Route();
        when(PersistenceFileUtils.readRouteFromFile(anyString(), anyInt())).thenReturn(route);

        controller.loadRouteFromFile();
        assertNotNull(controller.getListRoute());
    }

    @Test
    public void testSetPerson() {
        Courrier courrier = new Courrier();
        controller.setPerson(courrier);
        assertEquals(courrier, controller.getPerson());
    }

    @Test
    public void testGetNbPanel() {
        int nbPanel = controller.getNbPanel();
        assertEquals(0, nbPanel);
    }

    @Test
    public void testSetNbPanel() {
        controller.setNbPanel(1);
        assertEquals(1, controller.getNbPanel());
    }

    @Test
    public void testGetStreetMap() {
        StreetMap streetMap = new StreetMap();
        controller.addStreetMap(streetMap);
        assertEquals(streetMap, controller.getStreetMap());
    }

    @Test
    public void testReinitializeListIntersection() {
        Intersection intersection = new Intersection();
        controller.addIntersection(intersection);
        controller.reinitializeListIntersection();
        assertEquals(0, controller.getSelectedIntersections().size());
    }

    @Test
    public void testGetSelectedIntersections() {
        Intersection intersection = new Intersection();
        controller.addIntersection(intersection);
        List<Intersection> intersections = controller.getSelectedIntersections();
        assertEquals(1, intersections.size());
    }

    @Test
    public void testGetRouteInt() {
        List<Integer> nodes = new ArrayList<>();
        nodes.add(1);
        when(tspService.getNodes()).thenReturn(nodes);

        List<Integer> result = controller.getRouteInt();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetDistances() {
        List<Double> distances = new ArrayList<>();
        distances.add(1.0);
        when(tspService.getDistances()).thenReturn(distances);

        List<Double> result = controller.getDistances();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetPickUpTimes() {
        Intersection intersection = new Intersection();
        controller.addIntersection(intersection);
        List<Double> pickUpTimes = controller.getPickUpTimes();
        assertEquals(1, pickUpTimes.size());
    }
}
