package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.Map;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.DelevaryService;
import com._1.hex.DeliveryPlanning.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * 
 * @author www.codejava.net
 *
 */
@Component
public class DrawMap extends JFrame {
    private final DelevaryService delevaryService;
    private final GraphService graphService;
    StreetMap streetMap;
    Double minLatitudeValue = Double.MAX_VALUE;
    Double maxLatitudeValue = Double.MIN_VALUE;
    Double minLongitudeValue = Double.MAX_VALUE;
    Double maxLongitudeValue = Double.MIN_VALUE;
    List<Long> route;

    @Autowired
    public DrawMap(DelevaryService delevaryService, GraphService graphService) {
        super("Map");
        this.delevaryService = delevaryService;
        this.graphService = graphService;
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void defineStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
    }


    void defineRoute (List<Long> route) {this.route = route;}

    Double normalizeLatitude(Double latitude) {
        return (latitude - minLatitudeValue) * 1000 / (maxLatitudeValue - minLatitudeValue);
    }

    Double normalizeLongitude(Double longitude) {
        return (longitude - minLongitudeValue) * 1000 / (maxLongitudeValue - minLongitudeValue);
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

    void drawStreetsOnJFrame_FromStreetMap(Graphics g) {
        Graphics2D g3d = (Graphics2D) g;
        g3d.setColor(Color.black);

        List<Street> l = streetMap.getStreets();
        Double latitudeOrigin, longitudeOrigin, latitudeDestination, longitudeDestination;

        for (Street line : l) {
            latitudeOrigin = normalizeLatitude(line.getOrigin().getLatitude());
            longitudeOrigin = normalizeLongitude(line.getOrigin().getLongitude());
            latitudeDestination = normalizeLatitude(line.getDestination().getLatitude());
            longitudeDestination = normalizeLongitude(line.getDestination().getLongitude());
            g3d.draw(new Line2D.Double(latitudeOrigin, longitudeOrigin, latitudeDestination, longitudeDestination));
        }
    }

    void drawPointsWhenIntersectionsIsClicked() {
        List<Integer> clicksCounter = new ArrayList<>();
        clicksCounter.add(0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer iterator = 1;
                Integer pointsGenerated = 0;

                

                    for (Intersection intersection : streetMap.getIntersections().values()) {
                        Ellipse2D.Double circle = new Ellipse2D.Double(
                                normalizeLatitude(intersection.getLatitude()) - 5,
                                normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);

                        if (circle.contains(e.getPoint())) {
                            if (clicksCounter.get(0) == 0) {
                                Rectangle.Double rectangle = new Rectangle.Double(circle.x, circle.y, circle.width,
                                        circle.height);
                                Graphics g = getGraphics();
                                Graphics2D graph = (Graphics2D) g;
                                graph.setColor(Color.DARK_GRAY);
                                graph.fill(rectangle);
                                clicksCounter.set(0, -1);
                                clicksCounter.add(0);
                                pointsGenerated = delevaryService.addInergection(intersection);
                            }

                            else {
                                for (int i = iterator; i < clicksCounter.size(); i++) {
                                    if (clicksCounter.get(i) == 1) {
                                        Graphics g = getGraphics();
                                        Graphics2D graph = (Graphics2D) g;
                                        graph.setColor(new Color((i * 50) % 256, (i * 80) % 256, (i * 110) % 256));
                                        graph.fill(circle);
                                        clicksCounter.set(i, 2);
                                        clicksCounter.add(0);
                                        iterator++;
                                        pointsGenerated = delevaryService.addInergection(intersection);
                                        if (pointsGenerated>=5){
                                            List<Long> l = delevaryService.computeGraph();
                                            List<Intersection> listRoute = new ArrayList<>();
                                            listRoute.add(streetMap.getIntersectionById(l.get(0)));
                                            for(int i =1;i<l.size();i++){
                                                Intersection inter = streetMap.getIntersectionById(l.get(i));
                                                if( listRoute.get(listRoute.size()-1) != inter){listRoute.add(inter);}
                                             }
                                        }
                                        break;
                                    } else if (clicksCounter.get(i) == 0) {
                                        Rectangle.Double rectangle = new Rectangle.Double(circle.x, circle.y,
                                                circle.width,
                                                circle.height);
                                        Graphics g = getGraphics();
                                        Graphics2D graph = (Graphics2D) g;
                                        graph.setColor(new Color((i * 50) % 256, (i * 80) % 256, (i * 110) % 256));
                                        graph.fill(rectangle);
                                        clicksCounter.set(i, 1);
                                        pointsGenerated = delevaryService.addInergection(intersection);
                                        if (pointsGenerated>=5){
                                            List<Long> l = delevaryService.computeGraph();
                                            List<Intersection> listRoute = new ArrayList<>();
                                            listRoute.add(streetMap.getIntersectionById(l.get(0)));
                                            for(int i =1;i<l.size();i++){
                                                Intersection inter = streetMap.getIntersectionById(l.get(i));
                                                if( listRoute.get(listRoute.size()-1) != inter){listRoute.add(inter);}
                                             }
                                            
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                
            }
        });
    }

    void drawRoutes_FromTheOriginAndDestination(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2f));
        Double x1, y1, x2, y2;

        for (int i=0; i<route.size()-1; i++){
            Long id1 = route.get(i);
            Long id2 = route.get(i+1);
          
            Intersection source = streetMap.getIntersectionById(id1);
            Intersection destination = streetMap.getIntersectionById(id2);
            x1 = normalizeLatitude(source.getLatitude());
            y1 = normalizeLongitude(source.getLongitude());
            x2 = normalizeLatitude(destination.getLatitude());
            y2 = normalizeLongitude(destination.getLongitude());
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

/*
    void listenToClicksOnIntersections (Graphics g) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Collection<Intersection> intersectionList = streetMap.getIntersections().values();
                for (Intersection intersection : intersectionList) {
                    Double latTrasf = normalizeLatitude(intersection.getLatitude());
                    Double lonTrasf = normalizeLongitude(intersection.getLongitude());
                    Ellipse2D.Double circle = new Ellipse2D.Double(latTrasf - 5.0, lonTrasf - 5.0, 10, 10);
                    if (circle.contains(e.getPoint())) {
                    int index = delevaryService.addInergection(intersection);
                    System.out.println("intersection id = " + intersection.getId());
                    System.out.println("Intersection clicked: Latitude: " + intersection.getLatitude() + ", Longitude: " + intersection.getLongitude());
                    if (index>=5){
                        List<Long> l = delevaryService.computeGraph();
                        List<Intersection> listRoute = new ArrayList<>();
                        listRoute.add(streetMap.getIntersectionById(l.get(0)));
                        for(int i =1;i<l.size();i++){
                            Intersection inter = streetMap.getIntersectionById(l.get(i));
                            if( listRoute.get(listRoute.size()-1) != inter){listRoute.add(inter);}
                        }
                        System.out.println(l);
                    }
                    break;
                    }
                }
            }
        });
    }
*/
  
    public void paint(Graphics g) {
        super.paint(g);

        intialise_MinAndMaxValues_For_LatitudeAndLongitude(streetMap.getIntersections());

        drawStreetsOnJFrame_FromStreetMap(g);
        if (route != null) {
            drawRoutes_FromTheOriginAndDestination(g);
        }

        drawPointsWhenIntersectionsIsClicked();
    }

}