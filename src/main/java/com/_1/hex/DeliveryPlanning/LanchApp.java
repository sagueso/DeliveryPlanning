package com._1.hex.DeliveryPlanning;

import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

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
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
