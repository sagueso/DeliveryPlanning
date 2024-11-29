package com._1.hex.DeliveryPlanning.view;


import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

@Component
public class MainDrawMap {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()  {




            @Override
            public void run() {
                DrawMap drawMap = new DrawMap();
                XmlParser xmlParser = new XmlParser();
                GraphService graphService = new GraphService();

                StreetMap map;
                String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
                try {
                    map = xmlParser.parse(xmlPath);
                    graphService.addMap(map);
                    Intersection source = map.getIntersectionByLongId(208769039L);
                    Intersection destination = map.getIntersectionByLongId(25173820L);
                    //Integer source = Intersection(208769039L);
                    //Integer destination = dict.get(25173820L);
                    //System.out.println(source);
                    //System.out.println(destination);
                    //boolean test1 = graphService.checkIfNodeExists(source);
                    //boolean test2 = graphService.checkIfNodeExists(destination);
                    //boolean test3 = graphService.checkIfEdgeExists(source, destination);
                    graphService.computeTheShortestPath(source,destination);
                    int a = 1;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }

                drawMap.defineStreetMap(map);

                drawMap.setVisible(true);
            }
        });
    }
}
