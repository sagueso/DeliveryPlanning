package com._1.hex.DeliveryPlanning.service;


import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class XmlParser {

    @Autowired
    GraphService graphService;

    public Map<Long, Integer> getMapIds() {
        return mapIds;
    }

    Map<Long,Integer> mapIds ;
    int globalId;

    public XmlParser() {
        this.mapIds = new HashMap<>();
        this.globalId=1;
    }


    public void parse(String xmlPath) throws FileNotFoundException, XMLStreamException {
        List<Intersection> intersections = new ArrayList<Intersection>();
        List<Street> streets = new ArrayList<Street>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlPath));
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "noeud":
                        Attribute idAtt = startElement.getAttributeByName(new QName("id"));
                        Attribute lat = startElement.getAttributeByName(new QName("latitude"));
                        Attribute lng = startElement.getAttributeByName(new QName("longitude"));

                        Long id = idAtt == null? null : Long.parseLong(idAtt.getValue());
                        Double latitude = lat == null? null : Double.parseDouble(lat.getValue());
                        Double longitude = lng == null? null : Double.parseDouble(lng.getValue());

                        Intersection intersection = new Intersection(id, latitude, longitude);
                        intersections.add(intersection);
                        mapIds.put(id,globalId);
                        graphService.addNodes(globalId);
                        globalId++;
                        break;
                    case "troncon":
                        Attribute dest = startElement.getAttributeByName(new QName("destination"));
                        Attribute len = startElement.getAttributeByName(new QName("longueur"));
                        Attribute nam = startElement.getAttributeByName(new QName("nomRue"));
                        Attribute org = startElement.getAttributeByName(new QName("origine"));

                        Long destination = dest == null? null : Long.parseLong(dest.getValue());
                        Double length = len == null? null : Double.parseDouble(len.getValue());
                        String name = nam == null? null : nam.getValue();
                        Long origin  = org == null? null : Long.parseLong(org.getValue());

                        Street street = new Street(origin, destination, name, length);
                        streets.add(street);

                        Integer idOrigin  = mapIds.get(origin);
                        Integer idDest  = mapIds.get(destination);
                        if (idOrigin!=null && idDest!=null && length!=null && length>0) {
                            try{
                                graphService.addEdge(idOrigin,idDest,length);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                        else {
                            System.out.println("is there such a case ?");
                        }
                        break;
                }
            }
        }
        return;
    }
}

