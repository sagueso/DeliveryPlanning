package com._1.hex.DeliveryPlanning.utils;


import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
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


@Service
public class XmlParser {

    @Autowired
    GraphService graphService;

    public XmlParser() {
    }


    public StreetMap parse(String xmlPath) throws FileNotFoundException, XMLStreamException {

        StreetMap map = new StreetMap(xmlPath);
        Integer internalId = 0;

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


                        Intersection intersection = new Intersection(internalId, id, latitude, longitude);
                        internalId++;
                        map.addIntersection(intersection);
                        //graphService.addNodes(globalId);
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

                        map.addStreet(origin, destination, name, length);
                        //Street street = new Street(origin, destination, name, length);
                        //streets.add(street);

                        /*
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
                        */
                }
            }
        }
        return map;
    }
}

