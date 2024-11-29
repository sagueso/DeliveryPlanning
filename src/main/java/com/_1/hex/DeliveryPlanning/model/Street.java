package com._1.hex.DeliveryPlanning.model;

public class Street {
    Intersection origin;
    Intersection destination;
    String name;
    Double length;

    public Street(Intersection origin, Intersection destination, String name, Double length) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.length = length;
    }

    public Intersection getOrigin() {
        return origin;
    }

    public Intersection getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public Double getLength() {
        return length;
    }
}
