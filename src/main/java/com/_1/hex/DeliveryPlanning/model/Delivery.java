package com._1.hex.DeliveryPlanning.model;

public class Delivery {
    Intersection startPoint;
    Intersection destinationPoint;
    Integer pickupDuration;

    public Delivery(Intersection startPoint, Intersection destinationPoint, Integer pickupDuration) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.pickupDuration = pickupDuration;
    }


    public Intersection getStartPoint() {return startPoint; }

    public Intersection getDestinationPoint() {return destinationPoint; }

    public Integer getPickupDuration() {return pickupDuration; }
}
