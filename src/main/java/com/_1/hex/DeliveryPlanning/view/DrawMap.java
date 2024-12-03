package com._1.hex.DeliveryPlanning.view;


import java.awt.*;
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
        setSize(480, 200);
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

        List<Street> l = streetMap.getStreets();
        Map<Integer, Intersection> m = streetMap.getIntersections();
        findMinMax(m);
        Integer zoom = 1000;

        for (Street line : l) {
            //System.out.println((line.getOrigin().getLatitude()-45)*100);
            g3d.draw(new Line2D.Double((line.getOrigin().getLatitude()-minlat)*zoom/(maxlat-minlat), (line.getOrigin().getLongitude()-minlon)*zoom/(maxlon-minlon), (line.getDestination().getLatitude()-minlat)*zoom/(maxlat-minlat), (line.getDestination().getLongitude()-minlon)*zoom/(maxlon-minlon)));
        }

    }

    void drawRoutes (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Integer zoom = 1000;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2f));
        for (int i=0; i<route.size()-1; i++){
            Integer id1 = route.get(i);
            Integer id2 = route.get(i+1);
            Intersection source = streetMap.getIntersectionById(id1);
            Intersection destination = streetMap.getIntersectionById(id2);
            g2d.draw(new Line2D.Double((source.getLatitude()-minlat)*zoom/(maxlat-minlat), (source.getLongitude()-minlon)*zoom/(maxlon-minlon), (destination.getLatitude()-minlat)*zoom/(maxlat-minlat), (destination.getLongitude()-minlon)*zoom/(maxlon-minlon)));
        }
    }


    public void paint(Graphics g) {
        super.paint(g);

        readLines (g);
        if(route != null) {
            drawRoutes(g);
        }
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