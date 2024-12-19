package com._1.hex.DeliveryPlanning.model;

public class Courrier {
    private static int counter = 0; // Static counter shared across all instances
    private int id; // Instance variable for unique ID
    private String name;
    private Warehouse warehouse;
    private Delivery delivery;

    public Courrier(String name) {
        this.id = ++counter;
        this.name = name;
    }

    public Courrier(String name, Warehouse warehouse) {
        this.id = ++counter;
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

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

}
