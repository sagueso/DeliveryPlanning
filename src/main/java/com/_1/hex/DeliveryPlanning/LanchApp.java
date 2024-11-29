package com._1.hex.DeliveryPlanning;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LanchApp {

    @Autowired
    GraphService graphService;

    @Autowired
    XmlParser xmlParser;

    public void lanch() {
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        try {
            StreetMap map = xmlParser.parse(xmlPath);
            graphService.addMap(map);
            Intersection source = map.getIntersectionByLongId(208769039L);
            Intersection destination = map.getIntersectionByLongId(25173820L);
            List<Integer> shortestPath = graphService.computeTheShortestPath(source,destination);
            List<Long> shortestPathLong = new ArrayList<>();

            for(Integer i : shortestPath) {
                shortestPathLong.add( map.getIntersectionById(i).getId() ) ;
            }
            System.out.println("Shortest Path : "+shortestPathLong);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }
}
