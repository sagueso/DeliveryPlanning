package com._1.hex.DeliveryPlanning.model;

public class Courrier {
    private String name;
    private Warehouse warehouse;
    private Delivery delivery;

    public Courrier(String name, Warehouse warehouse) {
        this.name = name;
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

}
