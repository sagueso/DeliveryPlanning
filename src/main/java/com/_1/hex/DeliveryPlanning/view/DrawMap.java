package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.*;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 *
 * @author www.codejava.net
 *
 */
@Component
public class DrawMap extends JFrame {
    private static JPanel controlPanel;
    private static int currentState;
    private static JPanel mapPanel;
    private static JLabel controlText;
    private final DelevaryService delevaryService;
    private final GraphService graphService;
    StreetMap streetMap;
    Double minLatitudeValue = Double.MAX_VALUE;
    Double maxLatitudeValue = Double.MIN_VALUE;
    Double minLongitudeValue = Double.MAX_VALUE;
    Double maxLongitudeValue = Double.MIN_VALUE;
    List<Intersection> route;
    List<Integer> clicksCounter = new ArrayList<>();
    Integer iterator = 1;
    Integer pointsGenerated = 0;

    @Autowired
    public DrawMap(DelevaryService delevaryService, GraphService graphService) {
        super("Map");
        this.delevaryService = delevaryService;
        this.graphService = graphService;
        currentState = -1;
        setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.WHITE);
        mapPanel.setPreferredSize(new Dimension(1000, 1000));

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(600, 1000));
        controlPanel.setBackground(Color.LIGHT_GRAY);

        JLabel nameLabel = new JLabel("Nom du livreur", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(nameLabel);

        controlText = new JLabel("Click on an intersection to set it as a start point", SwingConstants.CENTER);
        controlText.setFont(new Font("Arial", Font.PLAIN, 14));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(controlText);

        JButton generatePathButton = new JButton("Generate Path");
        JButton saveRoutePathButton = new JButton("SaveRoute");
        JButton loadRoutePathButton = new JButton("LoadRoute");

        controlPanel.add(Box.createVerticalGlue());
        controlPanel.add(generatePathButton);
        controlPanel.add(saveRoutePathButton);
        controlPanel.add(loadRoutePathButton);

        add(mapPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        drawPointsWhenIntersectionsIsClicked();


        generatePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRoute();
                repaint();
            }
        });
        saveRoutePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delevaryService.saveRouteToFile();
            }
        });
        loadRoutePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRouteFromFile();
                repaint();
            }
        });

    }

    void defineStreetMap(StreetMap streetMap) {
        this.streetMap = streetMap;
        intialise_MinAndMaxValues_For_LatitudeAndLongitude(streetMap.getIntersections());
    }


    void defineRoute (List<Intersection> route) {this.route = route;}

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

    void drawPoint(Ellipse2D.Double circle) {

        updateControlText();
        if (clicksCounter.get(0) == 0) {
            Rectangle.Double rectangle = new Rectangle.Double(circle.x, circle.y, circle.width,
                    circle.height);
            Graphics g = getGraphics();
            Graphics2D graph = (Graphics2D) g;
            graph.setColor(Color.DARK_GRAY);
            graph.fill(rectangle);
            clicksCounter.set(0, -1);
            clicksCounter.add(0);
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
                    break;
                }
            }
        }
    }

    void generateRoute(){
        List<Intersection> listRoute = delevaryService.computeGraph(streetMap);
        route = listRoute;
    }

    void loadRouteFromFile(){
        List<Intersection> listRoute = delevaryService.loadRouteFromFile();
        route = listRoute;
    }

    void drawPointsWhenIntersectionsIsClicked() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                for (Intersection intersection : streetMap.getIntersections().values()) {
                    Ellipse2D.Double circle = new Ellipse2D.Double(
                            normalizeLatitude(intersection.getLatitude()) - 5,
                            normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);

                    if (circle.contains(e.getPoint())) {
                        drawPoint(circle);
                        pointsGenerated = delevaryService.addInergection(intersection);
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
            Intersection source = route.get(i);
            Intersection destination = route.get(i+1);


            x1 = normalizeLatitude(source.getLatitude());
            y1 = normalizeLongitude(source.getLongitude());
            x2 = normalizeLatitude(destination.getLatitude());
            y2 = normalizeLongitude(destination.getLongitude());
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        drawStreetsOnJFrame_FromStreetMap(g);
        if (route != null) {
            drawRoutes_FromTheOriginAndDestination(g);
        }

        this.clicksCounter = new ArrayList<>();
        this.clicksCounter.add(0);
        this.iterator = 1;
        this.pointsGenerated = 0;
        if(delevaryService.getSelectedIntersections() != null) {
            List<Intersection> selectedIntersections = delevaryService.getSelectedIntersections();
            for (Intersection intersection : selectedIntersections) {
                Ellipse2D.Double circle = new Ellipse2D.Double(
                        normalizeLatitude(intersection.getLatitude()) - 5,
                        normalizeLongitude(intersection.getLongitude()) - 5, 10, 10);
                drawPoint(circle);
            }
        }
    }


    private static void updateControlText() {
        String[] states = {
                "Click on an intersection to set it as a start point",
                "Click on an intersection to set a pick-up point",
                "Click on an intersection to set a delivery point"
        };
        currentState = (currentState + 1) % 2;
        controlText.setText(states[currentState+1]);

    }
}

