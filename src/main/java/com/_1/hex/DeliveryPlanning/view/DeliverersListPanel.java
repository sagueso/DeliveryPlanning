package com._1.hex.DeliveryPlanning.view;

import com._1.hex.DeliveryPlanning.service.DelevaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliverersListPanel extends javax.swing.JPanel {
    List<String> listPerson = new ArrayList<>();
    DelevaryService  delevaryService;
    DrawMap drawMap;

    //Panel for picking up a person to do the delivery
    public DeliverersListPanel(DelevaryService delevaryService, DrawMap drawmap) {
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
            String getMessage = JOptionPane.showInputDialog(this, "Enter the name of the deliverer");
            addPerson(getMessage);
        });
        this.delevaryService = delevaryService;
        this.drawMap = drawmap;



    }

    public void setListPerson(List<String> listPerson1) {

        for (String person : listPerson1) {
            addPerson(person);
        }

    }

    public void addPerson(String person) {
        listPerson.add(person);
        JButton personButton = new JButton(person);
        add(personButton);

        personButton.addActionListener(e -> {


            delevaryService.setPerson(person);
            String personName = delevaryService.getPerson();
            System.out.println(personName);
            delevaryService.setNbPanel(1);
            drawMap.repaint();

        });

        add(personButton);
        validate();
        repaint();
    }

}
