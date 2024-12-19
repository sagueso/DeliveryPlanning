package com._1.hex.DeliveryPlanning.model;

import java.util.List;

public class Route {
    private int courierId;
    private List<Intersection> intersections;

    public Route() {}
    // Constructor
    public Route(int id,List<Intersection> intersections) {
        this.courierId = id;
        this.intersections = intersections;
    }

    // Getters and Setters
    public int getId() {
        return courierId;
    }

    public void setId(int id) {
        this.courierId = id;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }
}
