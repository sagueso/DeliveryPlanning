package com._1.hex.DeliveryPlanning.view;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com._1.hex.DeliveryPlanning.LanchApp;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import com._1.hex.DeliveryPlanning.view.DrawPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.stream.XMLStreamException;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */

public class DrawMap extends JFrame {

    StreetMap streetMap;
    Double minlat = Double.MAX_VALUE;
    Double maxlat = Double.MIN_VALUE;
    Double minlon = Double.MAX_VALUE;
    Double maxlon = Double.MIN_VALUE;
    List<Integer> route;

    public DrawMap() {
        super("Map");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void defineStreetMap (StreetMap streetMap) {
        this.streetMap = streetMap;
    }

    void defineRoute (List<Integer> route) {this.route = route;}

    void findMinMax(Map<Integer, Intersection> intersections) {
        for (Integer key : intersections.keySet()) {
            Intersection intersection = intersections.get(key);
            if(intersection.getLatitude()<minlat){
                minlat = intersection.getLatitude();
            }
            if(intersection.getLongitude()<minlon){
                minlon = intersection.getLongitude();
            }
            if(intersection.getLatitude()>maxlat){
                maxlat = intersection.getLatitude();
            }
            if(intersection.getLongitude()>maxlon){
                maxlon = intersection.getLongitude();
            }
        }
    }

    void readLines (Graphics g) {
        Graphics2D g3d = (Graphics2D) g;
        g3d.setColor(Color.black);

        List<Street> l = streetMap.getStreets();
        Map<Integer, Intersection> m = streetMap.getIntersections();
        //findMinMax(m);
        Double x1, y1, x2, y2;

        for (Street line : l) {
            //System.out.println((line.getOrigin().getLatitude()-45)*100);
            x1 = transfLatitude(line.getOrigin().getLatitude());
            y1 = transfLongitude(line.getOrigin().getLongitude());
            x2 = transfLatitude(line.getDestination().getLatitude());
            y2 = transfLongitude(line.getDestination().getLongitude());
            g3d.draw(new Line2D.Double(x1, y1, x2, y2));
        }

    }

    Double transfLatitude(Double latitude){
        return (latitude - minlat) * 1000 /(maxlat-minlat);
    }
    Double transfLongitude(Double longitude){
        return (longitude - minlon) * 1000 /(maxlon-minlon);
    }
/*
    void drawPoints(Graphics g, Intersection point){
        Graphics2D g2d = (Graphics2D) g;
        DrawPoints drawPoints = new DrawPoints();
        drawPoints.drawPoints(g2d, point);
    }
*/

void drawPoints(Graphics g,Intersection point){
    Graphics2D graph = (Graphics2D) g;

    Double latitude = point.getLatitude();
    Double longitude = point.getLongitude();

    Double latTrasf = transfLatitude(latitude);
    Double lonTrasf = transfLongitude(longitude);

    System.out.println("Latitude: " + latitude + " Longitude: " + longitude);

    System.out.println("Latitude: " + latTrasf + " Longitude: " + lonTrasf);

    graph.setColor(Color.RED);
    Ellipse2D.Double circle = new Ellipse2D.Double(latTrasf - 5.0, lonTrasf - 5.0, 10, 10);
    graph.draw(circle);
    graph.fill(circle);    
}

    void drawRoutes (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Integer zoom = 1000;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2f));
        Double x1, y1, x2, y2;
        for (int i=0; i<route.size()-1; i++){
            Integer id1 = route.get(i);
            Integer id2 = route.get(i+1);
            Intersection source = streetMap.getIntersectionById(id1);
            Intersection destination = streetMap.getIntersectionById(id2);
            x1 = transfLatitude(source.getLatitude());
            y1 = transfLongitude(source.getLongitude());
            x2 = transfLatitude(destination.getLatitude());
            y2 = transfLongitude(destination.getLongitude());
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }


    public void paint(Graphics g) {
        super.paint(g);

        findMinMax(streetMap.getIntersections());

        readLines (g);
        if(route != null) {
            drawRoutes(g);
        }

        drawPoints(g, streetMap.getIntersectionById(route.get(2)));
    }

/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                    new DrawMap().setVisible(true);
            }
        });
    }*/
}