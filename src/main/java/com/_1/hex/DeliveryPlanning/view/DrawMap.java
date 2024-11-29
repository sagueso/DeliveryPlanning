package com._1.hex.DeliveryPlanning.view;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class DrawMap extends JFrame {

    public DrawMap() {
        super("Lines Drawing Demo");

        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    void readLines (List<Street> l, Graphics g) {
        Graphics2D g3d = (Graphics2D) g;
        for (Street line : l) {

            g3d.draw(new Line2D.Double(line.getOrigin().getLatitude(), line.getOrigin().getLongitude(), line.getDestination().getLatitude(), line.getDestination().getLongitude()));
        }

    }

    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Intersection p1 = new Intersection(8,123L, 45.75, 105.26);
        Intersection p2 = new Intersection(9,124L, 409.75, 105.56);

        Double latitude1 = p1.getLatitude();
        Double latitude2 = p2.getLatitude();
        Double longitude1 = p1.getLongitude();
        Double longitude2 = p2.getLongitude();

        g2d.draw(new Line2D.Double(latitude1,longitude1, latitude2, longitude2));

        System.out.printf("%.2f", latitude2);
        System.out.printf("%.2f", longitude2);
        System.out.printf("%.2f", 45.75f);
        //g2d.drawLine(120, 50, 360, 50);

        //g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));

        g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
        //g2d.draw(new Line2D.Float(45.75f, 105.26f, 409.75f, 105.26f));


    }

    public void paint(Graphics g) {
        super.paint(g);
        //drawLines(g);
        Intersection p1 = new Intersection(1,123L, 55.75, 105.26);
        Intersection p2 = new Intersection(2,124L, 49.75, 105.56);
        Intersection p3 = new Intersection(3,125L, 41.85, 106.26);
        Intersection p4 = new Intersection(4,126L, 43.15, 103.26);
        Intersection p5 = new Intersection(5,127L, 46.75, 153.26);
        Intersection p6 = new Intersection(6,128L, 45.25, 158.26);
        Street l1 = new Street(p1, p2, "lala", 155.0);
        Street l2 = new Street(p3, p2, "lala", 155.0);
        Street l3 = new Street(p5, p6, "lala", 155.0);
        Street l4 = new Street(p4, p5, "lala", 155.0);
        Street l5 = new Street(p1, p6, "lala", 155.0);
        List<Street> list=new ArrayList<Street>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);
        list.add(l5);
        readLines (list, g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawMap().setVisible(true);
            }
        });
    }
}