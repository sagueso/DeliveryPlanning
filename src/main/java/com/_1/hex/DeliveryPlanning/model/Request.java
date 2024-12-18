package com._1.hex.DeliveryPlanning.model;


import org.jgrapht.alg.util.Pair;

import java.util.*;

public class Request {
    private Warehouse warehouse;
    private final List<Delivery> trip;
    //Key id paire d'id intersections, value paire de longueur et liste des id intersection chemin entre les deux intersections
    //TODO LIST INTEGER
    private final Map<Pair<Long, Long>, Pair<List<Long>, Double>> distances;

    public Request(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.trip = new ArrayList<Delivery>();
        this.distances = new HashMap<>();
    }

    public int getNbOfIntersections() {
        return 2*this.trip.size() + 1;
    }

    public Double getDistance(Long originId, Long destinationId) {
        Pair<Long, Long> key = new Pair<>(originId, destinationId);

        return this.distances.containsKey(key) ? this.distances.get(key).getSecond() : null;
    }

    public List<Long> getDistancesRoute(long originId, long destinationId) {
        Pair<Long, Long> key = new Pair<>(originId, destinationId);
        return this.distances.containsKey(key) ? this.distances.get(key).getFirst() : null;
    }

    public List<Delivery> getTrip() {
        return trip;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Double addDistance(Long originId, Long destinationId, List<Long> path, Double length) {
        Pair<Long, Long> key = new Pair<>(originId, destinationId);
        Pair<List<Long>, Double> value = new Pair<>(path, length);
        this.distances.put(key, value);
        return length;
    }

    public void addDelivery(Delivery delivery) {
        this.trip.add(delivery);
    }

    /**
     * @param i: Warehouse is 0
     *         For each delivery, the start point is 2i+1 and the destination point is 2i+2
     * @return
     */
   public int getPredecessor(int i) {
        if(i <= 0 || i >= 2*this.trip.size()+1) {
            return -1; // Error here bad call
        }
        if(i % 2 == 1) {
            return -1; // No predecessor for start point
        }
        else {
        return i - 1; // Predecessor is the start point
        }
   }
}
