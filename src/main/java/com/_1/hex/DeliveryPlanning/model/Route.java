package com._1.hex.DeliveryPlanning.model;

import java.util.List;

public class Route {
    private int courierId;
    private List<Intersection> intersections;
    private List<Intersection> selectedIntersections;

    public Route() {}
    // Constructor
    public Route(int id,List<Intersection> intersections,List<Intersection> selectedIntersections) {
        this.courierId = id;
        this.intersections = intersections;
        this.selectedIntersections = selectedIntersections;
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

    public List<Intersection> getSelectedIntersections() {
        return selectedIntersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }
}
