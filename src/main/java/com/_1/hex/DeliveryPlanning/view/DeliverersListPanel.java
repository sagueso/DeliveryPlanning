package com._1.hex.DeliveryPlanning.view;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class DeliverersListPanel extends javax.swing.JPanel {

    JButton addPersonButton;

    //Panel for picking up a person to do the delivery
    public DeliverersListPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 1000));
        setBackground(Color.LIGHT_GRAY);
        JLabel welcomeLabel = new JLabel("Bienvenu dans Delivery Planning!");
        JLabel pickCourrierLabel = new JLabel("Choisissez un livreur pour effectuer la livraison");


        JLabel listLabel = new JLabel("Liste des courriers:");

        addPersonButton = new JButton("Add Person");


        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pickCourrierLabel.setFont(new Font("Arial", Font.BOLD, 18));
        listLabel.setFont(new Font("Arial", Font.BOLD, 18));

        add(Box.createRigidArea(new Dimension(10, 20)));

        add(Box.createRigidArea(new Dimension(10, 20)));
        add(welcomeLabel);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(pickCourrierLabel);
        add(Box.createRigidArea(new Dimension(10, 20)));
        add(listLabel);


        add(addPersonButton, BorderLayout.SOUTH);

    }

    public JButton getAddPersonButton() { return this.addPersonButton; }



}
