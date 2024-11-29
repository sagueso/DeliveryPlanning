package com._1.hex.DeliveryPlanning;

import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
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
            xmlParser.parse(xmlPath);
            System.out.println(xmlParser.getMapIds().isEmpty());
            Map<Long,Integer> dict = xmlParser.getMapIds();
            Integer source = dict.get(208769039L);
            Integer destination = dict.get(25173820L);
            System.out.println(source);
            System.out.println(destination);
            boolean test1 = graphService.checkIfNodeExists(source);
            boolean test2 = graphService.checkIfNodeExists(destination);
            boolean test3 = graphService.checkIfEdgeExists(source, destination);
            graphService.computeTheShortestPath(source,destination);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }
}
