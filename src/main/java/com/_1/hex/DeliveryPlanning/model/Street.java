package com._1.hex.DeliveryPlanning.model;

public class Street {
    Long origin;
    Long destination;
    String name;
    Double length;

    public Street(Long origin, Long destination, String name, Double length) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.length = length;
    }

    public Long getOrigin() {
        return origin;
    }

    public Long getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public Double getLength() {
        return length;
    }
}
