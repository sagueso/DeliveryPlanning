package com._1.hex.DeliveryPlanning.utils;


import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;

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
import java.util.List;

public class XmlParser {
    public static void parse(String xmlPath) throws FileNotFoundException, XMLStreamException {
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

                        Integer id = idAtt == null? null : Integer.parseInt(idAtt.getValue());
                        Double latitude = lat == null? null : Double.parseDouble(lat.getValue());
                        Double longitude = lng == null? null : Double.parseDouble(lng.getValue());

                        Intersection intersection = new Intersection(id, latitude, longitude);
                        intersections.add(intersection);
                        break;
                    case "trocon":
                        Attribute dest = startElement.getAttributeByName(new QName("destination"));
                        Attribute len = startElement.getAttributeByName(new QName("longueur"));
                        Attribute nam = startElement.getAttributeByName(new QName("nomRue"));
                        Attribute org = startElement.getAttributeByName(new QName("origine"));

                        Integer destination = dest == null? null : Integer.parseInt(dest.getValue());
                        Double length = len == null? null : Double.parseDouble(len.getValue());
                        String name = nam == null? null : nam.getValue();
                        Integer origin  = org == null? null : Integer.parseInt(org.getValue());

                        Street street = new Street(origin, destination, name, length);
                        streets.add(street);
                        break;
                }
            }
        }
        return;
    }
}

