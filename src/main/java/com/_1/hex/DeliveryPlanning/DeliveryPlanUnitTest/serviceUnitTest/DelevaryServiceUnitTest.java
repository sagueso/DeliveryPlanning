package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;


import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.DelevaryService;
import com._1.hex.DeliveryPlanning.utils.PersistenceFileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DelevaryServiceUnitTest {

    private StreetMap streetMap = default!;
    private List<Intersection> selectedIntersections = default!;
    private List<Intersection> listRoute = default!;
    private List<Double> distances = default!;
    private Warehouse warehouse = default!;
    private int nbPanel = default!;
    private Courrier person = default!;
    private List<Courrier> courriers = default!;
    private Intersection startPoint = default!;
    private Intersection endPoint = default!;
    private Request request = default!;
    private int index = default!;
    private GraphService graphService = default!;
    @Mock
    private TspService tspService = default!;
    @InjectMocks
    private DelevaryService delevaryService = default!;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        delevaryService = new DelevaryService(graphService, tspService);
    }

    @Test
    public void testSetPerson() {
        // Arrange
        Courrier courrier = new Courrier();

        // Act
        delevaryService.setPerson(courrier);

        // Assert
        assertEquals(courrier, delevaryService.getPerson());
    }

    @Test
    public void testGetNbPanel() {
        // Arrange
        int expectedNbPanel = 5;
        delevaryService.setNbPanel(expectedNbPanel);

        // Act
        int actualNbPanel = delevaryService.getNbPanel();

        // Assert
        assertEquals(expectedNbPanel, actualNbPanel);
    }

    @Test
    public void testSetNbPanel() {
        // Arrange
        int expectedNbPanel = 5;

        // Act
        delevaryService.setNbPanel(expectedNbPanel);

        // Assert
        assertEquals(expectedNbPanel, delevaryService.getNbPanel());
    }

        @Test
        public void testGetStreetMap() {
            // Arrange
            StreetMap expectedStreetMap = new StreetMap();
            delevaryService.addStreetMap(expectedStreetMap);

            // Act
            StreetMap actualStreetMap = delevaryService.getStreetMap();

            // Assert
            assertEquals(expectedStreetMap, actualStreetMap);
        }

        @Test
        public void testReinitializeListIntersection() {
            // Arrange
            delevaryService.addInergection(new Intersection());
            delevaryService.addInergection(new Intersection());

            // Act
            delevaryService.reinitializeListIntersection();

            // Assert
            assertTrue(delevaryService.getSelectedIntersections().isEmpty());
            assertEquals(0, delevaryService.index);
        }

        @Test
        public void testAddInergection() {
            // Arrange
            Intersection intersection1 = new Intersection();
            Intersection intersection2 = new Intersection();
            Intersection intersection3 = new Intersection();

            // Act
            int index1 = delevaryService.addInergection(intersection1);
            int index2 = delevaryService.addInergection(intersection2);
            int index3 = delevaryService.addInergection(intersection3);

            // Assert
            assertEquals(1, index1);
            assertEquals(2, index2);
            assertEquals(3, index3);
            assertEquals(3, delevaryService.getSelectedIntersections().size());
        }

        @Test
        public void testGetSelectedIntersections() {
            // Arrange
            Intersection intersection1 = new Intersection();
            Intersection intersection2 = new Intersection();
            delevaryService.addInergection(intersection1);
            delevaryService.addInergection(intersection2);

            // Act
            List<Intersection> selectedIntersections = delevaryService.getSelectedIntersections();

            // Assert
            assertEquals(2, selectedIntersections.size());
            assertTrue(selectedIntersections.contains(intersection1));
            assertTrue(selectedIntersections.contains(intersection2));
        }
    
    
        @Test
        public void testGetRouteInt() {
            // Arrange
            List<Integer> expectedRouteInt = new ArrayList<>();
            when(tspService.getNodes()).thenReturn(expectedRouteInt);

            // Act
            List<Integer> actualRouteInt = delevaryService.getRouteInt();

            // Assert
            assertEquals(expectedRouteInt, actualRouteInt);
        }

        @Test
        public void testGetDistances() {
            // Arrange
            List<Double> expectedDistances = new ArrayList<>();
            when(tspService.getDistances()).thenReturn(expectedDistances);

            // Act
            List<Double> actualDistances = delevaryService.getDistances();

            // Assert
            assertEquals(expectedDistances, actualDistances);
        }

        @Test
        public void testGetDeliveryDuration() {
            // Arrange
            Intersection endPoint = new Intersection();
            Delivery delivery = new Delivery(new Intersection(), endPoint);
            delivery.setPickupDuration(10.0);
            request = new Request(new Warehouse(new Intersection(), "Lyon"));
            request.addDelivery(delivery);
            delevaryService = new DelevaryService(graphService, tspService);
            delevaryService.request = request;

            // Act
            Double duration = delevaryService.getDeliveryDuration(endPoint);

            // Assert
            assertEquals(10.0, duration);
        }

        @Test
        public void testGetPickUpTimes() {
            // Arrange
            Intersection intersection1 = new Intersection();
            Intersection intersection2 = new Intersection();
            selectedIntersections = new ArrayList<>();
            selectedIntersections.add(intersection1);
            selectedIntersections.add(intersection2);
            delevaryService = new DelevaryService(graphService, tspService);
            delevaryService.selectedIntersections = selectedIntersections;
            Delivery delivery = new Delivery(intersection1, intersection2);
            delivery.setPickupDuration(10.0);
            request = new Request(new Warehouse(new Intersection(), "Lyon"));
            request.addDelivery(delivery);
            delevaryService.request = request;

            // Act
            List<Double> pickUpTimes = delevaryService.getPickUpTimes();

            // Assert
            assertEquals(2, pickUpTimes.size());
            assertEquals(10.0, pickUpTimes.get(0));
            assertNull(pickUpTimes.get(1));
        }
    
    @Test
    public void testAddCourrier() {
        // Arrange
        Courrier courrier = new Courrier();

        // Act
        delevaryService.addCourrier(courrier);

        // Assert
        assertTrue(delevaryService.getCourriers().contains(courrier));
    }

    @Test
    public void testGetCourriers() throws IOException {
        // Arrange
        List<Courrier> courriers = new ArrayList<>();
        when(PersistenceFileUtils.readCouriersFromFile(anyString())).thenReturn(courriers);

        // Act
        List<Courrier> result = delevaryService.getCourriers();

        // Assert
        assertEquals(courriers, result);
    }

    @Test
    public void testAddStreetMap() {
        // Arrange
        StreetMap streetMap = new StreetMap();

        // Act
        delevaryService.addStreetMap(streetMap);

        // Assert
        assertEquals(streetMap, delevaryService.getStreetMap());
    }

    @Test
    public void testComputeGraph() {
        // Arrange
        StreetMap streetMap = new StreetMap();
        when(tspService.searchSolution(anyInt(), any(Request.class), any(GraphService.class))).thenReturn(new ArrayList<>());

        // Act
        List<Intersection> result = delevaryService.computeGraph(streetMap);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testSaveRouteToFile() {
        // Arrange
        doNothing().when(PersistenceFileUtils.class);
        delevaryService.setPerson(new Courrier());

        // Act
        delevaryService.saveRouteToFile();

        // Assert
        verify(PersistenceFileUtils.class, times(1)).saveRouteToFile(any(Route.class), anyString());
    }

    @Test
    public void testLoadRouteFromFile() {
        // Arrange
        Route route = new Route();
        when(PersistenceFileUtils.readRouteFromFile(anyString(), anyInt())).thenReturn(route);
        delevaryService.setPerson(new Courrier());

        // Act
        delevaryService.loadRouteFromFile();

        // Assert
        assertNotNull(delevaryService.getListRoute());
    }
}
