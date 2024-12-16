package com._1.hex.DeliveryPlanning.view;

import com._1.hex.DeliveryPlanning.model.Intersection;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControlPanel extends JPanel {

    private final JScrollPane scrollPane;
    private final JPanel scrollContentPanel;
    private final JLabel controlText;
    private final JButton generatePathButton;
    private final JButton saveRoutePathButton;
    private final JButton loadRoutePathButton;
    private final String[] states;

    public ControlPanel() {
        super();

        this.states = new String[] {
            "Click on an intersection to set it as a start point",
            "Click on an intersection to set it as an end point"
        };

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 1000));
        setBackground(Color.LIGHT_GRAY);

        JLabel nameLabel = new JLabel("Nom du livreur", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(nameLabel);

        this.controlText = new JLabel("Click on an intersection to set it as a start point", SwingConstants.CENTER);
        this.controlText.setFont(new Font("Arial", Font.PLAIN, 14));
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        // Content panel for scroll pane
        this.scrollContentPanel = new JPanel();
        this.scrollContentPanel.setLayout(new BoxLayout(scrollContentPanel, BoxLayout.Y_AXIS));
        this.scrollContentPanel.setBackground(Color.LIGHT_GRAY);
        this.scrollContentPanel.add(this.controlText);

        // Scroll pane setup
        this.scrollPane = new JScrollPane(scrollContentPanel);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border
        this.scrollPane.getViewport().setBackground(Color.LIGHT_GRAY); // Match background
        this.scrollPane.setPreferredSize(new Dimension(580, 400));
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(scrollPane);

        this.generatePathButton = new JButton("Generate Path");
        this.saveRoutePathButton = new JButton("SaveRoute");
        this.loadRoutePathButton = new JButton("LoadRoute");

        this.add(Box.createVerticalGlue());
        this.add(generatePathButton);
        this.add(saveRoutePathButton);
        this.add(loadRoutePathButton);
    }

    public JButton getGeneratePathButton() {
        return generatePathButton;
    }

    public JButton getSaveRoutePathButton() {
        return saveRoutePathButton;
    }

    public JButton getLoadRoutePathButton() {
        return loadRoutePathButton;
    }

    public int updateControlText(int currentState) {
        currentState = (currentState + 1) % 2;
        this.controlText.setText(this.states[currentState+1]);

        return currentState;
    }

    public void populateScrollContentPanel(List<Intersection> intersections, List<Double> hour) {
        for (Intersection intersection : intersections) {
            JPanel linePanel = new JPanel();
            linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS));
            linePanel.setBackground(Color.LIGHT_GRAY);

            // Add the shape (circle or square)
            //ShapeIcon shapeIcon = new ShapeIcon(item.getShape(), item.getColor(), 20, 20);
            JLabel shapeLabel = new JLabel(/*shapeIcon*/);
            linePanel.add(shapeLabel);
            linePanel.add(Box.createRigidArea(new Dimension(10, 0))); // Spacer

            // Add the text
            JLabel textLabel = new JLabel(/*hour*/);
            textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            linePanel.add(textLabel);

            // Add the line to the scroll content panel
            linePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            scrollContentPanel.add(linePanel);
            scrollContentPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer between lines
        }
    }
}
