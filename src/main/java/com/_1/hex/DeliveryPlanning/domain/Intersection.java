package com._1.hex.DeliveryPlanning.domain;

public class Intersection {
    protected Integer id;
    protected Double latitude;
    protected Double longitude;

    public Intersection(Integer id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
