package com._1.hex.DeliveryPlanning.model;

import java.util.ArrayList;
import java.util.List;

public class Request {
    List<Delivery> trip;

    public Request() {
        trip = new ArrayList<Delivery>();
    }


    public List<Delivery> getTrip() {return trip; }
}
