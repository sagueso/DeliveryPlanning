package com._1.hex.DeliveryPlanning.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

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
        this.add(controlText);

        this.generatePathButton = new JButton("Generate Path");
        this.saveRoutePathButton = new JButton("SaveRoute");
        this.loadRoutePathButton = new JButton("LoadRoute");

        this.add(Box.createVerticalGlue());
        this.add(generatePathButton);
        this.add(saveRoutePathButton);
        this.add(loadRoutePathButton);
    }

    public void setControlText(String text) {
        this.controlText.setText(text);
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
}
