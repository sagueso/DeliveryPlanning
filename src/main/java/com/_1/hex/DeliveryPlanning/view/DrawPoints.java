// src/main/java/com/_1/hex/DeliveryPlanning/view/AnotherClass.java
package com._1.hex.DeliveryPlanning.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.view.DrawPoints;


public class DrawPoints {

    DrawMap drawMap = new DrawMap();

    void drawPoints(Graphics g,Intersection point){
        Graphics2D graph = (Graphics2D) g;

        Double latitude = point.getLatitude();
        Double longitude = point.getLongitude();

        Double latTrasf = drawMap.transfLatitude(latitude);
        Double lonTrasf = drawMap.transfLongitude(longitude);

        System.out.println("Latitude: " + latitude + " Longitude: " + longitude);

        System.out.println("Latitude: " + latTrasf + " Longitude: " + lonTrasf);

        graph.setColor(Color.RED);
        //graph.draw(new Ellipse2D.Double(latTrasf, lonTrasf, 5, 5));
        graph.drawOval(latTrasf.intValue(), lonTrasf.intValue(), 5, 5);
        graph.fillOval(latitude.intValue(), longitude.intValue(), 5, 5);    
    }
}