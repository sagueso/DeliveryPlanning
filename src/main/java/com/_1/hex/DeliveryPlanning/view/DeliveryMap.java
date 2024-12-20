package com._1.hex.DeliveryPlanning.view;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliveryMap extends JPanel {

    private Double minLatitudeValue;
    private Double maxLatitudeValue;
    private Double minLongitudeValue;
    private Double maxLongitudeValue;
    private List<Intersection> route;
    private List<Intersection> selectedIntersections;
    private StreetMap streetMap;

    DeliveryMap() {
        super();
        this.minLatitudeValue = Double.MAX_VALUE;
        this.maxLatitudeValue = Double.MIN_VALUE;
        this.minLongitudeValue = Double.MAX_VALUE;
        this.maxLongitudeValue = Double.MIN_VALUE;
        this.route = null;
        this.selectedIntersections = new ArrayList<>();

    }

    void setStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
        intialiseMinAndMaxLatitudeAndLongitude(this.streetMap.getIntersections());
    }

    void setRoute(List<Intersection> route) {
        this.route = route;
        repaint();
    }

    void setSelectedIntersections(List<Intersection> selectedIntersections) {
        this.selectedIntersections = selectedIntersections;
        repaint();
    }

    /**
     * This method is used to find the minimum and maximum latitude and longitude values of the intersections
     * @param intersections List of the intersections of the map
     */
    void intialiseMinAndMaxLatitudeAndLongitude(Map<Integer, Intersection> intersections) {
        for (Integer key : intersections.keySet()) {
            Intersection intersection = intersections.get(key);
            if (intersection.getLatitude() < minLatitudeValue) {
                minLatitudeValue = intersection.getLatitude();
            }
            if (intersection.getLongitude() < minLongitudeValue) {
                minLongitudeValue = intersection.getLongitude();
            }
            if (intersection.getLatitude() > maxLatitudeValue) {
                maxLatitudeValue = intersection.getLatitude();
            }
            if (intersection.getLongitude() > maxLongitudeValue) {
                maxLongitudeValue = intersection.getLongitude();
            }
        }
    }

    Double normalizeLatitude(Double latitude) {
        return (latitude - minLatitudeValue) * 1000 / (maxLatitudeValue - minLatitudeValue);
    }

    Double normalizeLongitude(Double longitude) {
        return (longitude - minLongitudeValue) * 1000 / (maxLongitudeValue - minLongitudeValue);
    }

    void drawStreets(Graphics2D g2d) {
        g2d.setColor(Color.black);

        List<Street> l = this.streetMap.getStreets();
        Double latitudeOrigin, longitudeOrigin, latitudeDestination, longitudeDestination;

        for (Street line : l) {
            latitudeOrigin = normalizeLatitude(line.getOrigin().getLatitude());
            longitudeOrigin = normalizeLongitude(line.getOrigin().getLongitude());
            latitudeDestination = normalizeLatitude(line.getDestination().getLatitude());
            longitudeDestination = normalizeLongitude(line.getDestination().getLongitude());
            g2d.draw(new Line2D.Double(latitudeOrigin, longitudeOrigin, latitudeDestination, longitudeDestination));
        }
    }

    void drawRoutes(Graphics2D g2d) {
        if (this.route == null || this.route.size() < 2) {
            return;
        }
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2f));

        for (int i=0; i<this.route.size()-1; i++){
            Intersection source = this.route.get(i);
            Intersection destination = this.route.get(i+1);

            Double x1 = normalizeLatitude(source.getLatitude());
            Double y1 = normalizeLongitude(source.getLongitude());
            Double x2 = normalizeLatitude(destination.getLatitude());
            Double y2 = normalizeLongitude(destination.getLongitude());

            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    /**
     * This method is used to draw the selected intersections on the map with different shapes and colors.
     * The first intersection is drawn as a black rectangle, the following intersections are drawn paired by two circles
     * and rectangles with the same color, but different colors for each pair.
     * @param g2d the 2d graphics object
     */
    public void drawSelectedIntersections(Graphics2D g2d) {
        if(this.selectedIntersections != null) {
            List<Intersection> selectedIntersections = this.selectedIntersections;
            for (int i=0; i< selectedIntersections.size(); i++) {
                Intersection intersection = selectedIntersections.get(i);
                if(i == 0) {
                    Rectangle.Double rectangle = new Rectangle.Double(
                            normalizeLatitude(intersection.getLatitude()) - 5,
                            normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fill(rectangle);
                }
                else if(i%2 == 1){
                    int pair = i/2 + 1;
                    Color dynamicColor = new Color((pair * 50) % 256, (pair * 80) % 256, (pair * 110) % 256);
                    Rectangle.Double rectangle = new Rectangle.Double(
                            normalizeLatitude(intersection.getLatitude()) - 5,
                            normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);
                    g2d.setColor(dynamicColor);
                    g2d.fill(rectangle);
                }
                else{
                    int pair = i/2;
                    Color dynamicColor = new Color((pair * 50) % 256, (pair * 80) % 256, (pair * 110) % 256);
                    Ellipse2D.Double circle = new Ellipse2D.Double(
                            normalizeLatitude(intersection.getLatitude()) - 5,
                            normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);
                    g2d.setColor(dynamicColor);
                    g2d.fill(circle);
                }
            }
        }
    }

    /**
     * This method is used to find the intersection that is clicked by the user
     * @param e the point where the user clicked
     * @return the intersection that is clicked
     */
    public Intersection findClickedIntersection(Point e) {
        for (Intersection intersection : this.streetMap.getIntersections().values()) {
            Ellipse2D.Double circle = new Ellipse2D.Double(
                    normalizeLatitude(intersection.getLatitude()) - 5,
                    normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);

            if (circle.contains(e)){
                return intersection;
            }
        }
        return null;
    }

    /**
     * Paint the streets and the selected intersections and route on the map
     * @param g the graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawStreets(g2d);
        drawSelectedIntersections(g2d);
        if (this.route != null) {
            drawRoutes(g2d);
        }
    }

}
