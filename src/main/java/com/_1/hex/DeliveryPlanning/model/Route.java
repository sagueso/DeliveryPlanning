package com._1.hex.DeliveryPlanning.model;

import java.util.List;

public class Route {

    private String id;  // For MongoDB, it's typically a String (ObjectId), but for a file-based approach, it could be any identifier.

    private List<Intersection> intersections;  // The list of intersections representing the route

    public Route() {}
    // Constructor
    public Route(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }
}
