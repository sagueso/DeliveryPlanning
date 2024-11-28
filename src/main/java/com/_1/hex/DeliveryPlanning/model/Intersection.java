package com._1.hex.DeliveryPlanning.model;

public class Intersection {
    protected Long id;
    protected Double latitude;
    protected Double longitude;

    public Intersection(Long id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
