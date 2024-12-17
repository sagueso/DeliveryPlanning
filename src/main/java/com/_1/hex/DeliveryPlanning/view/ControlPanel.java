package com._1.hex.DeliveryPlanning.view;

import com._1.hex.DeliveryPlanning.model.Intersection;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
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
        this.controlText.setText(this.states[currentState]);

        return currentState;
    }

    public void populateScrollContentPanel(List<Integer> orderOfIntersections, List<Double> hour) {
        scrollContentPanel.removeAll();
        scrollContentPanel.revalidate();
        scrollContentPanel.repaint();
        for (int i = 0; i < orderOfIntersections.size(); i++) {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Left aligned with small spacing
            //linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS));
            linePanel.setBackground(Color.LIGHT_GRAY);
            linePanel.setPreferredSize(new Dimension(500, 20));

            JPanel shapePanel = getJPanel(orderOfIntersections.get(i));
            shapePanel.setBackground(Color.LIGHT_GRAY);
            linePanel.add(shapePanel);

            // Add the text

            String text = i == 0? getHourString(0.0, orderOfIntersections.get(i)) : getHourString(hour.get(i-1), orderOfIntersections.get(i));
            JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
            textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            textLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            linePanel.add(textLabel);
            // Add the line to the scroll content panel
            //linePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            scrollContentPanel.add(linePanel);
            scrollContentPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer between lines
        }
        // Refresh the scroll content panel
        scrollContentPanel.revalidate();
        scrollContentPanel.repaint();
    }

    private String getHourString(double hour, int order) {
        if (order == 0) {
            return "Départ à 00h00";
        }
        int time = (int) Math.floor(hour/4.167);
        int h = time / 60;
        int m = time % 60;
        String s = "Arrivée à " + h + "h" + m;
        if(order % 2 == 1) {
            s += " (Pickup)";
        }
        else {
            s += " (Delivery)";
        }
        return s;
    }

    private JPanel getJPanel(int i) {
        // Draw the shape
        // Set preferred size for each line
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the shape
                if(i == 0) {
                    Rectangle.Double rectangle = new Rectangle.Double(5,5,10,10);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fill(rectangle);
                }
                else if(i %2 == 1){
                    int pair = i /2 + 1;
                    Color dynamicColor = new Color((pair * 50) % 256, (pair * 80) % 256, (pair * 110) % 256);
                    Rectangle.Double rectangle = new Rectangle.Double(5,5,10,10);
                    g2d.setColor(dynamicColor);
                    g2d.fill(rectangle);
                }
                else{
                    int pair = i /2;
                    Color dynamicColor = new Color((pair * 50) % 256, (pair * 80) % 256, (pair * 110) % 256);
                    Ellipse2D.Double circle = new Ellipse2D.Double(5,5,10,10);
                    g2d.setColor(dynamicColor);
                    g2d.fill(circle);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(15, 15); // Set preferred size for each line
            }
        };
    }
}
