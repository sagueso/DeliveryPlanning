package com._1.hex.DeliveryPlanning.application;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;
import com._1.hex.DeliveryPlanning.utils.XmlParser;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@Component
public class TestTsp {
    public static void main(String[] args) throws IOException, XMLStreamException {
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        XmlParser xmlParser = new XmlParser();
        StreetMap map = xmlParser.parse(xmlPath);
        GraphService graphService = new GraphService();
        graphService.addMap(map);


        Warehouse warehouse = new Warehouse(map.getIntersectionById(2835339774L), "entrepot");
        Request request = new Request(warehouse);
        Intersection intersection1 = map.getIntersectionById(1679901320L);
        Intersection intersection2 = map.getIntersectionById(208769457L);

        Delivery delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        intersection1 = map.getIntersectionById(208769120L);
        intersection2 = map.getIntersectionById(25336179L);

        delivery1 = new Delivery(intersection1, intersection2);
        request.addDelivery(delivery1);

        TspService tspService = new TspService(new TSP1());
        List<Long> route = tspService.searchSolution(10000, request, graphService);
        if(route == null){
            System.out.println("No solution found");
            return;
        }
        for (Long id : route) {
            System.out.println(id);
        }
    }

}
