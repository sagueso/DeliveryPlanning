package com._1.hex.DeliveryPlanning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreetMap {
    private String name;
    Map<Integer,Intersection> intersections;
    Map<Long, Integer> intersectionsIds;
    List<Street> streets;

    public StreetMap(String name) {
        this.name = name;
        intersections = new HashMap<Integer, Intersection>();
        streets = new ArrayList<Street>();
        intersectionsIds = new HashMap<Long, Integer>();
    }

    public void addIntersection(Intersection intersection) {
        intersections.put(intersection.getInternalId(), intersection);
        intersectionsIds.put(intersection.getId(), intersection.getInternalId());
    }

    public void addStreet(Long originId, Long destinationId, String name, Double length) {
        Intersection origin = intersections.get(intersectionsIds.get(originId));
        Intersection destination = intersections.get(intersectionsIds.get(destinationId));
        Street street = new Street(origin, destination, name, length);
        streets.add(street.hashCode(), street);
    }
}
