package com._1.hex.DeliveryPlanning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreetMap {
    private String name;
    private Map<Integer,Intersection> intersections;
    private Map<Long, Integer> intersectionsIds;
    private List<Street> streets;

    public StreetMap(String name) {
        this.name = name;
        intersections = new HashMap<Integer, Intersection>();
        streets = new ArrayList<Street>();
        intersectionsIds = new HashMap<Long, Integer>();
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Intersection> getIntersections() {
        return intersections;
    }

    public Map<Long, Integer> getIntersectionsIds() {
        return intersectionsIds;
    }

    public List<Street> getStreets() {
        return streets;
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

    public Intersection getIntersectionById(Long id) {
        return intersections.get(intersectionsIds.get(id));
    }

    public Intersection getIntersectionById(Integer internalId) {
        return intersections.get(internalId);
    }

}
