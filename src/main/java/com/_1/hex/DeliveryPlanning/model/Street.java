package com._1.hex.DeliveryPlanning.model;

public class Street {
    Integer origin;
    Integer destination;
    String name;
    Double length;

    public Street(Integer origin, Integer destination, String name, Double length) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.length = length;
    }

    public Integer getOrigin() {
        return origin;
    }

    public Integer getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public Double getLength() {
        return length;
    }
}
