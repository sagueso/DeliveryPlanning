package com._1.hex.DeliveryPlanning.model;

public class Delivery {
    Intersection startPoint;
    Intersection destinationPoint;
    Double pickupDuration;

    public Delivery(Intersection startPoint, Intersection destinationPoint) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
    }

    public void setPickupDuration(Double pickupDuration) {
        this.pickupDuration = pickupDuration;
    }

    public Intersection getStartPoint() {return startPoint; }

    public Intersection getDestinationPoint() {return destinationPoint; }

    public Double getPickupDuration() {return pickupDuration; }
}
