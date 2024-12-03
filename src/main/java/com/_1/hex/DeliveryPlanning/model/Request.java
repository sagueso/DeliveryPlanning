package com._1.hex.DeliveryPlanning.model;

import org.jgrapht.alg.util.Pair;

import java.util.*;

public class Request {

    private final List<Delivery> trip;
    //Key id paire d'id intersections, value paire de longueur et liste des id intersection chemin entre les deux intersections
    private final Map<Pair<Long, Long>, Pair<Double, List<Long>>> distances;


    public Request() {
        this.trip = new ArrayList<Delivery>();
        this.distances = new HashMap<>();
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
        * *du k-ième delivery en k et k+1
        */

        if(i < 0 || j < 0 || i >= 2*this.trip.size() || j >= 2*this.trip.size() || i == j) {
            return -1; // Error here bad call
        }

        Long id1 = i % 2 == 0? this.trip.get(i/2).startPoint.getId() : this.trip.get(i/2).destinationPoint.getId();
        Long id2 = j % 2 == 0? this.trip.get(j/2).startPoint.getId() : this.trip.get(j/2).destinationPoint.getId();


        if(distances.containsKey(new Pair<>(id1, id2))) {
            return distances.get(new Pair<>(id1, id2)).getFirst();
        }
        else if(distances.containsKey(new Pair<>(id2, id1))) {
            return distances.get(new Pair<>(id2, id1)).getFirst();
        }
        else{
            //TODO: look for distance with graph service put distance in map
            return -1;
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
