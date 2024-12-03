// src/main/java/com/_1/hex/DeliveryPlanning/view/AnotherClass.java
package com._1.hex.DeliveryPlanning.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.view.DrawPoints;


public class DrawPoints {
    void drawPoints(Graphics g,Intersection point){
        Graphics2D graph = (Graphics2D) g;

        Double latitude = point.getLatitude();
        Double longitude = point.getLongitude();

        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);

        graph.setColor(Color.RED);
        graph.drawOval(latitude.intValue(), longitude.intValue(), 5, 5);
        graph.fillOval(latitude.intValue(), longitude.intValue()*100, 5, 5);    
    }
}