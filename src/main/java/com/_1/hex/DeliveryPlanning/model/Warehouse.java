package com._1.hex.DeliveryPlanning.model;

public class Warehouse extends Intersection {
    private String name;

    public Warehouse(Integer internalId, Long id, Double latitude, Double longitude, String name) {
        super(internalId, id, latitude, longitude);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
