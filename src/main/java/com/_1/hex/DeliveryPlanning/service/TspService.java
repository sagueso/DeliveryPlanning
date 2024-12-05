package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Delivery;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import org.jgrapht.alg.util.Pair;

import java.util.List;

public class TspService {

    private Request request;
    private GraphService graphService;

    public TspService(Request request, GraphService graphService) {
        this.request = request;
        this.graphService = graphService;
    }

    public double getCost(int i, int j) {
        /*
         * *i et j ont un usage pour le tsp qui ordonne des noueds de 0 à n-1
         * *les intersections sont ordonnées de 0 à 2n-1 avec les intersections de départ et d'arrivée
         * *du k-ième delivery en 2k+1 et 2k+2, 0 est le warehouse
         */

        if(i < 0 || j < 0 || i >= 2*request.getTrip().size() || j >= 2*request.getTrip().size() || i == j) {
            return -1; // Error here bad call
        }

        Intersection int1;
        Intersection int2;
        if(i == 0) {
            int1 = request.getWarehouse();// Warehouse
        }
        else {
            int1 = i % 2 == 1 ? request.getTrip().get(i / 2).getStartPoint() : request.getTrip().get(i / 2).getDestinationPoint();
        }
        if(j == 0) {
            int2 = request.getWarehouse();// Warehouse
        }
        else {
            int2 = j % 2 == 1 ? request.getTrip().get(j / 2).getStartPoint() : request.getTrip().get(j / 2).getDestinationPoint();
        }
        Long id1 = int1.getId();
        Long id2 = int2.getId();

        Double distance = request.getDistance(id1, id2);
        if(distance != null) {
            return distance;
        }
        else {
            distance = request.getDistance(id2, id1);
            if (distance != null) {
                return distance;
            } else {
                Pair<List<Integer>, Double> shortestPath = graphService.computeTheShortestPath(int1, int2);
                //TODO List of Long ids
                request.addDistance(id1, id2, shortestPath.getFirst(), shortestPath.getSecond());
                return shortestPath.getSecond();
            }
        }
    }
}
