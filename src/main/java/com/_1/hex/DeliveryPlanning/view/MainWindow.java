package com._1.hex.DeliveryPlanning.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

import com._1.hex.DeliveryPlanning.model.Courrier;
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
    private final DeliverersListPanel deliverersListPanel;
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

        this.deliverersListPanel = new DeliverersListPanel();


        add(this.mapPanel, BorderLayout.CENTER);

        if(delevaryService.getNbPanel()==0){
            add(this.deliverersListPanel, BorderLayout.SOUTH);
            addCourrierButtonCallback();

            for(Courrier c : delevaryService.getCourriers()){
                JButton personButton = new JButton(c.getName());
                this.deliverersListPanel.add(personButton);

                personButton.addActionListener(f -> {

                    delevaryService.setPerson(c);
                    delevaryService.setNbPanel(1);
                    this.controlPanel.setNameLabel(c.getName());
                    changePanel01();
                    validate();
                    repaint();

                });

            }
        }
        else if(delevaryService.getNbPanel()==1){
            add(this.controlPanel, BorderLayout.EAST);

            addGenerateRouteButtonCallback();
            addSaveRoutePathButtonCallback();
            addLoadRoutePathButtonCallback();
            drawPointsWhenIntersectionsIsClicked();
        }

    }

    void changePanel01(){

        add(this.controlPanel, BorderLayout.EAST);

        addGenerateRouteButtonCallback();
        addSaveRoutePathButtonCallback();
        addLoadRoutePathButtonCallback();
        drawPointsWhenIntersectionsIsClicked();

        remove(this.deliverersListPanel);
    }

    void addCourrierButtonCallback(){
        this.deliverersListPanel.getAddPersonButton().addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Add Deliverer");
            dialog.setLayout(new FlowLayout());
            String getMessage = JOptionPane.showInputDialog(this.deliverersListPanel, "Cliquez pour rajouter un courrier");

            if(getMessage!=null){
                Courrier courrier = new Courrier(getMessage);
                delevaryService.addCourrier(courrier);
                JButton personButton = new JButton(getMessage);
                this.deliverersListPanel.add(personButton);

                personButton.addActionListener(f -> {

                    delevaryService.setPerson(courrier);
                    delevaryService.setNbPanel(1);
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

        List<Double> pickUpTimes = delevaryService.getPickUpTimes();
        controlPanel.populateScrollContentPanel(delevaryService.getRouteInt(), delevaryService.getDistances(), pickUpTimes);
        mapPanel.setRoute(listRoute);
        mapPanel.repaint();
     }

    public void paint(Graphics g) {
        super.paint(g);
    }
}

