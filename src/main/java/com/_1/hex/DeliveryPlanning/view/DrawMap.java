package com._1.hex.DeliveryPlanning.view;


import java.awt.Graphics;
import java.awt.Graphics2D;
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

    public DrawMap() {
        super("Map");
        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void defineStreetMap (StreetMap streetMap) {
        this.streetMap = streetMap;
    }


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

    void drawRoutes (Graphics g, List<Integer> route) {
        Graphics2D g2d = (Graphics2D) g;

    }
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Intersection p1 = new Intersection(8,123L, 45.75, 105.26);
        Intersection p2 = new Intersection(9,124L, 409.75, 105.56);

        Double latitude1 = p1.getLatitude();
        Double latitude2 = p2.getLatitude();
        Double longitude1 = p1.getLongitude();
        Double longitude2 = p2.getLongitude();

        g2d.draw(new Line2D.Double(latitude1,longitude1, latitude2, longitude2));

        System.out.printf("%.2f", latitude2);
        System.out.printf("%.2f", longitude2);
        System.out.printf("%.2f", 45.75f);
        //g2d.drawLine(120, 50, 360, 50);

        //g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));

        g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
        //g2d.draw(new Line2D.Float(45.75f, 105.26f, 409.75f, 105.26f));


    }

    public void paint(Graphics g) {
        super.paint(g);
        //drawLines(g);
        Intersection p1 = new Intersection(1,123L, 55.75, 105.26);
        Intersection p2 = new Intersection(2,124L, 49.75, 105.56);
        Intersection p3 = new Intersection(3,125L, 41.85, 106.26);
        Intersection p4 = new Intersection(4,126L, 43.15, 103.26);
        Intersection p5 = new Intersection(5,127L, 46.75, 153.26);
        Intersection p6 = new Intersection(6,128L, 45.25, 158.26);
        Street l1 = new Street(p1, p2, "lala", 155.0);
        Street l2 = new Street(p3, p2, "lala", 155.0);
        Street l3 = new Street(p5, p6, "lala", 155.0);
        Street l4 = new Street(p4, p5, "lala", 155.0);
        Street l5 = new Street(p1, p6, "lala", 155.0);
        List<Street> list=new ArrayList<Street>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);
        list.add(l5);
        //List<Street> list2= streetMap.getStreets();

        readLines (g);
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