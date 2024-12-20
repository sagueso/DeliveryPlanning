package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.controllerUnitTest;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.utils.PersistenceFileUtils;
import com._1.hex.DeliveryPlanning.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @Mock
    private PersistenceFileUtils persistenceFileUtils;

    @Mock
    private Courrier courrier;

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
    public void testAddStreetMap() {
        StreetMap streetMap = new StreetMap(null); // Replace "someParameter" with actual parameter if needed
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
        StreetMap streetMap = new StreetMap(null);
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
