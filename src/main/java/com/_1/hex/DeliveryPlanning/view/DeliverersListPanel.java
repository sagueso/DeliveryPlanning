package com._1.hex.DeliveryPlanning.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DeliverersListPanel extends javax.swing.JPanel {
    List<String> listPerson = new ArrayList<>();

    //Panel for picking up a person to do the delivery
    public DeliverersListPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 1000));
        setBackground(Color.LIGHT_GRAY);
        JButton addPersonButton = new JButton("Add Person");
        JLabel nameLabel = new JLabel("Nom du livreur", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(nameLabel);
        add(addPersonButton, BorderLayout.SOUTH);
        addPersonButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Add Deliverer");
            dialog.setLayout(new FlowLayout());
            String getMessage = JOptionPane.showInputDialog(this, "Cliquez pour rajouter un entrepot");
            addPerson(getMessage);
        });

    }

    public void setListPerson(List<String> listPerson) {
        this.listPerson = listPerson;
        for (String person : listPerson) {
            JLabel personLabel = new JLabel(person, SwingConstants.CENTER);
            personLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            personLabel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

    }

    public void addPerson(String person) {
        listPerson.add(person);
        JLabel personLabel = new JLabel(person, SwingConstants.CENTER);
        personLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        personLabel.add(Box.createRigidArea(new Dimension(0, 20)));
        add(personLabel);
        validate();
        repaint();
    }

}
