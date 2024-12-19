package com._1.hex.DeliveryPlanning.model;
import java.util.Random;

public class Courrier {
    private int id;
    private String name;
    private Warehouse warehouse;


    public Courrier(String name) {
        Random random = new Random();
        this.id = random.nextInt();
        this.name = name;
    }

    public Courrier() {}

    public Courrier(String name, Warehouse warehouse) {
        Random random = new Random();
        this.id = random.nextInt();
        this.name = name;
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public int getId() {return id;}

}
