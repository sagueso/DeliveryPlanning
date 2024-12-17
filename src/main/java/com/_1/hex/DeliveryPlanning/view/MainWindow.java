package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.service.DelevaryService;
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
public class MainWindow extends JFrame {
    private final ControlPanel controlPanel;
    private int currentState;
    private final DelevaryService delevaryService;
    private final DeliveryMap mapPanel;
    //List<Intersection> route;

    //TODO no need for graphService
    @Autowired
    public MainWindow(DelevaryService delevaryService) {
        super("Map");
        this.delevaryService = delevaryService;
        this.currentState = -1;
        setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.mapPanel = new DeliveryMap();
        this.mapPanel.setBackground(Color.WHITE);
        this.mapPanel.setPreferredSize(new Dimension(1000, 1000));

        this.controlPanel = new ControlPanel();

        add(this.mapPanel, BorderLayout.CENTER);
        add(this.controlPanel, BorderLayout.EAST);

        addGenerateRouteButtonCallback();
        addSaveRoutePathButtonCallback();
        addLoadRoutePathButtonCallback();
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
                    currentState = controlPanel.updateControlText(currentState);
                }
            }
        });
    }

    void addGenerateRouteButtonCallback(){
        this.controlPanel.getGeneratePathButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRoute();
                repaint();
            }
        });
    }

    void addSaveRoutePathButtonCallback(){
        this.controlPanel.getSaveRoutePathButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //controlPanel.populateScrollContentPanel();
                delevaryService.saveRouteToFile();
            }
        });
    }

    void addLoadRoutePathButtonCallback(){
        this.controlPanel.getLoadRoutePathButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRouteFromFile();
                repaint();
            }
        });
    }

    void loadRouteFromFile(){
        mapPanel.setRoute(delevaryService.loadRouteFromFile());
    }

     void generateRoute(){
        List<Intersection> listRoute = delevaryService.computeGraph(delevaryService.getStreetMap());
        controlPanel.populateScrollContentPanel(delevaryService.getRouteInt(), delevaryService.getDistances());
        mapPanel.setRoute(listRoute);
        mapPanel.repaint();
     }

    public void paint(Graphics g) {
        super.paint(g);
    }
}

