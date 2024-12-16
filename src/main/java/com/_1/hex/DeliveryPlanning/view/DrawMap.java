package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.util.List;

import com._1.hex.DeliveryPlanning.model.Intersection;
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
    private static StreetMap streetMap;
    private static JLabel controlText;
    private final DelevaryService delevaryService;
    private final GraphService graphService;
    private final DeliveryMap mapPanel;
    //List<Intersection> route;

    //TODO no need for graphService
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

        this.mapPanel = new DeliveryMap();
        this.mapPanel.setBackground(Color.WHITE);
        this.mapPanel.setPreferredSize(new Dimension(1000, 1000));

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

        add(this.mapPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        generatePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRoute();
                repaint(); //TODO
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

        drawPointsWhenIntersectionsIsClicked();

    }

    void setStreetMap() {
        this.mapPanel.setStreetMap(delevaryService.getStreetMap());
    }

    void drawPointsWhenIntersectionsIsClicked() {
        this.mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Intersection intersection = mapPanel.findClickedIntersection(e.getPoint());
                if(intersection != null){
                    delevaryService.addInergection(intersection);
                    mapPanel.setSelectedIntersections(delevaryService.getSelectedIntersections());
                    updateControlText();
                }
            }
        });
    }


    void loadRouteFromFile(){
        mapPanel.setRoute(delevaryService.loadRouteFromFile());
    }

     void generateRoute(){
        List<Intersection> listRoute = delevaryService.computeGraph(delevaryService.getStreetMap());
        mapPanel.setRoute(listRoute);
        mapPanel.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
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

