package com._1.hex.DeliveryPlanning.view;


import com._1.hex.DeliveryPlanning.DeliveryPlanningApplication;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.controller.Controller;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.utils.XmlParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
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

                ApplicationContext context = SpringApplication.run(DeliveryPlanningApplication.class, args);
                Controller controller = context.getBean(Controller.class);
                GraphService graphService =  context.getBean(GraphService.class);
                MainWindow mainWindow = context.getBean(MainWindow.class);
                XmlParser xmlParser = new XmlParser();


                StreetMap map;
                String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
                try {
                    map = xmlParser.parse(xmlPath);

                    controller.addStreetMap(map);
                    mainWindow.setStreetMap();
                    mainWindow.setVisible(true);
                    /*Intersection source =
                            map.getIntersectionById(208769039L);
                    Intersection destination = map.getIntersectionById(25173820L);

                    Pair<List<Long>, Double> path = graphService.computeTheShortestPath(source,destination);
                    drawMap.defineRoute(path.getFirst());*/


                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
