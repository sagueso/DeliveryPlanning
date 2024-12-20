package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import com._1.hex.DeliveryPlanning.tsp.TestTsp;
import com._1.hex.DeliveryPlanning.utils.XmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTspUnitTest {

    private StreetMap map;
    private GraphService graphService;
    private Request request;
    private TspService tspService;

    @BeforeEach
    public void setUp() throws IOException, XMLStreamException {
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        XmlParser xmlParser = new XmlParser();
        map = xmlParser.parse(xmlPath);
        graphService = new GraphService();
        graphService.addMap(map);

        Warehouse warehouse = new Warehouse(map.getIntersectionById(2835339774L), "entrepot");
        request = new Request(warehouse);

        Intersection intersection1 = map.getIntersectionById(1679901320L);
        Intersection intersection2 = map.getIntersectionById(208769457L);
        Delivery delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        intersection1 = map.getIntersectionById(208769120L);
        intersection2 = map.getIntersectionById(25336179L);
        delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        tspService = new TspService(new TSP1());
    }

    @Test
    public void testSearchSolution() {
        List<Long> route = tspService.searchSolution(10000, request, graphService);
        assertNotNull(route, "Route should not be null");
        assertFalse(route.isEmpty(), "Route should not be empty");
    }

    @Test
    public void testNoSolutionFound() {
        List<Long> route = tspService.searchSolution(1, request, graphService);
        assertNull(route, "Route should be null when no solution is found");
    }
}
