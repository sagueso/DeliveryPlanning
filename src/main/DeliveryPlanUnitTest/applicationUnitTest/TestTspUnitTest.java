package com._1.hex.DeliveryPlanning.application;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.springframework.stereotype.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTspUnitTest {

    private XmlParser xmlParser = default!;
    private GraphService graphService = default!;
    private TspService tspService = default!;
    private StreetMap map = default!;
    private Request request = default!;

    @BeforeEach
    public void setup() throws IOException, XMLStreamException {
        MockitoAnnotations.openMocks(this);
        xmlParser = new XmlParser();
        graphService = new GraphService();
        tspService = new TspService(new TSP1());
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        map = xmlParser.parse(xmlPath);
        graphService.addMap(map);
        Warehouse warehouse = new Warehouse(map.getIntersectionById(2835339774L), "entrepot");
        request = new Request(warehouse);
    }

    @Test
    public void testSearchSolution() throws IOException, XMLStreamException {
        Intersection intersection1 = map.getIntersectionById(1679901320L);
        Intersection intersection2 = map.getIntersectionById(208769457L);
        Delivery delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        intersection1 = map.getIntersectionById(208769120L);
        intersection2 = map.getIntersectionById(25336179L);
        delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        List<Long> route = tspService.searchSolution(10000, request, graphService);
        assertNotNull(route, "Route should not be null");
        assertFalse(route.isEmpty(), "Route should not be empty");
    }

    @Test
    public void testNoSolutionFound() throws IOException, XMLStreamException {
        List<Long> route = tspService.searchSolution(1, request, graphService);
        assertNull(route, "Route should be null when no solution is found");
    }
}
