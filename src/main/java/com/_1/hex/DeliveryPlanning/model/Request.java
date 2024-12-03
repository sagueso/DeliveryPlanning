package com._1.hex.DeliveryPlanning.model;

import com._1.hex.DeliveryPlanning.service.GraphService;
import org.jgrapht.alg.util.Pair;

import java.util.*;

public class Request {
    private Warehouse warehouse;
    private final List<Delivery> trip;
    //Key id paire d'id intersections, value paire de longueur et liste des id intersection chemin entre les deux intersections
    //TODO LIST INTEGER
    private final Map<Pair<Long, Long>, Pair<List<Integer>, Double>> distances;
    private final GraphService graphService;

    //TODO: is this the right way to do it?
    public Request(GraphService graphService, Warehouse warehouse) {
        this.warehouse = warehouse;
        this.trip = new ArrayList<Delivery>();
        this.distances = new HashMap<>();
        this.graphService = graphService;
    }

    public void addDelivery(Delivery delivery) {
        this.trip.add(delivery);
    }

    public int getNbOfIntersections() {
        return 2*this.trip.size();
    }

    public double getCost(int i, int j) {
        /*
        * *i et j ont un usage pour le tsp qui ordonne des noueds de 0 à n-1
        * *les intersections sont ordonnées de 0 à 2n-1 avec les intersections de départ et d'arrivée
        * *du k-ième delivery en 2k+1 et 2k+2, 0 est le warehouse
        */

        if(i < 0 || j < 0 || i >= 2*this.trip.size() || j >= 2*this.trip.size() || i == j) {
            return -1; // Error here bad call
        }

        Intersection int1;
        Intersection int2;
        if(i == 0) {
            int1 = warehouse;// Warehouse
        }
        else {
            int1 = i % 2 == 1 ? this.trip.get(i / 2).startPoint : this.trip.get(i / 2).destinationPoint;
        }
        if(j == 0) {
            int2 = warehouse;// Warehouse
        }
        else {
            int2 = j % 2 == 1 ? this.trip.get(j / 2).startPoint : this.trip.get(j / 2).destinationPoint;
        }
        Long id1 = int1.getId();
        Long id2 = int2.getId();


        if(distances.containsKey(new Pair<>(id1, id2))) {
            return distances.get(new Pair<>(id1, id2)).getSecond();
        }
        else if(distances.containsKey(new Pair<>(id2, id1))) {
            return distances.get(new Pair<>(id2, id1)).getSecond();
        }
        else{
            Pair<List<Integer>, Double> shortestPath = graphService.computeTheShortestPath(int1, int2);
            //TODO List of Long ids
            distances.put(new Pair<>(id1, id2), new Pair<>(shortestPath.getFirst(), shortestPath.getSecond()));
            return shortestPath.getSecond();
        }
    }

       public int getPredecessor(int i) {
            if(i < 0 || i >= 2*this.trip.size()) {
                return -1; // Error here bad call
            }

            if( i % 2 == 0) {
                return -1; // No predecessor for start point
            }
            else {
            return i - 1; // Predecessor is the start point
        }
    }
}
