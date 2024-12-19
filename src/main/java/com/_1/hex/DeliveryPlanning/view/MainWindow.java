package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import com._1.hex.DeliveryPlanning.model.Courrier;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.controller.Controller;
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
    private final JSplitPane splitPane;
    private final Controller controller;
    private final DeliveryMap mapPanel;
    private final DeliverersListPanel deliverersListPanel;

    @Autowired
    public MainWindow(Controller controller) {
        super("Map");
        this.controller = controller;
        this.currentState = -1;
        setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(1000);
        splitPane.setResizeWeight(0.7);

        this.mapPanel = new DeliveryMap();
        this.mapPanel.setBackground(Color.WHITE);
        this.mapPanel.setPreferredSize(new Dimension(1000, 1000));


        this.controlPanel = new ControlPanel();
        addGenerateRouteButtonCallback();
        addSaveRoutePathButtonCallback();
        addLoadRoutePathButtonCallback();
        addReturnMainButtonCallback();
        drawPointsWhenIntersectionsIsClicked();

        this.deliverersListPanel = new DeliverersListPanel();
        this.deliverersListPanel.setPreferredSize(new Dimension(600, 1000));
        addCourrierButtonCallback();

        splitPane.setLeftComponent(this.mapPanel);
        add(splitPane, BorderLayout.CENTER);

        if(controller.getNbPanel()==0){
            splitPane.setRightComponent(this.deliverersListPanel);

            for(Courrier c : controller.getCouriers()){
                JButton personButton = new JButton(c.getName());
                this.deliverersListPanel.add(personButton);

                personButton.addActionListener(f -> {

                    controller.setPerson(c);
                    controller.setNbPanel(1);
                    this.controlPanel.setNameLabel(c.getName());
                    changePanel01();
                    validate();
                    repaint();

                });

            }
        }
        else if(controller.getNbPanel()==1){
            splitPane.setRightComponent(this.controlPanel);
        }

    }

    void changePanel01(){
        splitPane.setRightComponent(this.controlPanel);
    }

    void changePanel10() {
        splitPane.setRightComponent(this.deliverersListPanel);
        this.mapPanel.setRoute(null);
        List <Intersection> inter = new ArrayList<>();
        this.mapPanel.setSelectedIntersections(inter);
        this.currentState = -1;
        controller.reinitializeListIntersection();
        this.controlPanel.reinitializeControlPanel();
    }

    void addReturnMainButtonCallback(){
        this.controlPanel.getReturnMainButton().addActionListener(e -> {
            changePanel10();
            validate();
            repaint();
        });
    }

    void addCourrierButtonCallback(){
        this.deliverersListPanel.getAddPersonButton().addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Add Deliverer");
            dialog.setLayout(new FlowLayout());
            String getMessage = JOptionPane.showInputDialog(this.deliverersListPanel, "Cliquez pour rajouter un courrier");

            if(getMessage!=null){
                Courrier courrier = new Courrier(getMessage);
                controller.addCourier(courrier);
                JButton personButton = new JButton(getMessage);
                this.deliverersListPanel.add(personButton);

                personButton.addActionListener(f -> {

                    controller.setPerson(courrier);
                    controller.setNbPanel(1);
                    this.controlPanel.setNameLabel(courrier.getName());
                    changePanel01();
                    validate();
                    repaint();

                });

                validate();
                repaint();
            }


        });
    }


    void setStreetMap() {
        this.mapPanel.setStreetMap(controller.getStreetMap());
    }

    void drawPointsWhenIntersectionsIsClicked() {
        this.mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Intersection intersection = mapPanel.findClickedIntersection(e.getPoint());
                if(intersection != null){
                    System.out.println("Intersection clicked: " + intersection.getId());
                    controller.addIntersection(intersection);
                    mapPanel.setSelectedIntersections(controller.getSelectedIntersections());
                    currentState = controlPanel.updateControlText(currentState);
                }
            }
        });
    }

    void addGenerateRouteButtonCallback(){
        this.controlPanel.getGeneratePathButton().addActionListener(e -> {
            generateRoute();
            repaint();
        });
    }

    void addSaveRoutePathButtonCallback(){
        this.controlPanel.getSaveRoutePathButton().addActionListener(e -> controller.saveRouteToFile());
    }

    void addLoadRoutePathButtonCallback(){
        this.controlPanel.getLoadRoutePathButton().addActionListener(e -> {
            loadRouteFromFile();
            repaint();
        });
    }

    void loadRouteFromFile(){
        controller.loadRouteFromFile();
        mapPanel.setRoute(controller.getListRoute());
        List<Intersection>  l = controller.getSelectedIntersections();
        controller.reinitializeListIntersection();
        for (Intersection intersection : l) {
            controller.addIntersection(intersection);
        }
        mapPanel.setSelectedIntersections(l);
    }

     void generateRoute(){
        List<Intersection> listRoute = controller.computeGraph(controller.getStreetMap());


        if (listRoute.isEmpty()){
            System.out.println("route is empty");
            JDialog dialog2 = new JDialog();
            dialog2.setTitle("Route pas trouve");
            //dialog2.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("Impossible de trouver une route. Veuillez choisir autres points.", SwingConstants.CENTER);
            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                dialog2.dispose(); // Close the dialog
                List <Intersection> inter = new ArrayList<>();
                mapPanel.setSelectedIntersections(inter);
                currentState = -1;
                controller.reinitializeListIntersection();
                controlPanel.reinitializeControlPanel();
            });
            JPanel panelPopUp = new JPanel();
            panelPopUp.add(messageLabel);
            panelPopUp.add(Box.createVerticalStrut(10));
            panelPopUp.add(okButton);
            dialog2.add(panelPopUp);
            dialog2.setSize(500,100);
            dialog2.setLocationRelativeTo(mapPanel);
            dialog2.setVisible(true);

        }
        else{
            List<Double> pickUpTimes = controller.getPickUpTimes();
            controlPanel.populateScrollContentPanel(controller.getRouteInt(), controller.getDistances(), pickUpTimes);
        }
        mapPanel.setRoute(listRoute);
        mapPanel.repaint();
     }

    public void paint(Graphics g) {
        super.paint(g);
    }
}

