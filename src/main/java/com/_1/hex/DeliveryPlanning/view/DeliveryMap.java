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

    Double minLatitudeValue;
    Double maxLatitudeValue;
    Double minLongitudeValue;
    Double maxLongitudeValue;
    List<Integer> clicksCounter;
    Integer iterator;
    Integer pointsGenerated;
    List<Intersection> route;
    List<Intersection> selectedIntersections;
    StreetMap streetMap;

    DeliveryMap() {
        super();
        this.minLatitudeValue = Double.MAX_VALUE;
        this.maxLatitudeValue = Double.MIN_VALUE;
        this.minLongitudeValue = Double.MAX_VALUE;
        this.maxLongitudeValue = Double.MIN_VALUE;
        this.clicksCounter = new ArrayList<>();
        this.clicksCounter.add(0);
        this.iterator = 1;
        this.pointsGenerated = 0;
        this.route = null;
        this.selectedIntersections = new ArrayList<>();

    }

    void setStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
        intialise_MinAndMaxValues_For_LatitudeAndLongitude(this.streetMap.getIntersections());
    }

    void setRoute(List<Intersection> route) {
        this.route = route;
        repaint();
    }

    void setSelectedIntersections(List<Intersection> selectedIntersections) {
        this.selectedIntersections = selectedIntersections;
        repaint();
    }

    void intialise_MinAndMaxValues_For_LatitudeAndLongitude(Map<Integer, Intersection> intersections) {
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

    void drawPoint(Graphics2D g2d, Ellipse2D.Double circle) {
        if (this.clicksCounter.get(0) == 0) {
            Rectangle.Double rectangle = new Rectangle.Double(circle.x, circle.y, circle.width,
                    circle.height);
            g2d.setColor(Color.DARK_GRAY);
            g2d.fill(rectangle);
            this.clicksCounter.set(0, -1);
            clicksCounter.add(0);
        }
        else {
            for (int i = iterator; i < clicksCounter.size(); i++) {
                if (clicksCounter.get(i) == 1) {
                    g2d.setColor(new Color((i * 50) % 256, (i * 80) % 256, (i * 110) % 256));
                    g2d.fill(circle);
                    clicksCounter.set(i, 2);
                    clicksCounter.add(0);
                    iterator++;
                    break;
                } else if (clicksCounter.get(i) == 0) {
                    Rectangle.Double rectangle = new Rectangle.Double(circle.x, circle.y,
                            circle.width,
                            circle.height);
                    g2d.setColor(new Color((i * 50) % 256, (i * 80) % 256, (i * 110) % 256));
                    g2d.fill(rectangle);
                    clicksCounter.set(i, 1);
                    break;
                }
            }
        }
    }

    public void drawSelectedIntersections(Graphics2D g2d) {
        this.clicksCounter = new ArrayList<>();
        this.clicksCounter.add(0);
        this.iterator = 1;
        this.pointsGenerated = 0;
        if(this.selectedIntersections != null) {
            List<Intersection> selectedIntersections = this.selectedIntersections;
            for (Intersection intersection : selectedIntersections) {
                Ellipse2D.Double circle = new Ellipse2D.Double(
                        normalizeLatitude(intersection.getLatitude()) - 5,
                        normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);
                drawPoint(g2d, circle);
            }
        }
    }

    Intersection findClickedIntersection(Point e) {
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
