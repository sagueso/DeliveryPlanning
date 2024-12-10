package com._1.hex.DeliveryPlanning.tsp;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.GraphService;
import org.jgrapht.alg.util.Pair;

import java.util.List;

public class StreetGraph implements Graph{

    private Request request;
    private GraphService graphService;

    public StreetGraph(Request request, GraphService graphService) {
        this.request = request;
        this.graphService = graphService;
    }

    @Override
    public int getNbVertices() {
        return request.getNbOfIntersections();
    }

    @Override
    //getCost return double
    public Double getCost(int i, int j) {
        if(i < 0 || j < 0 || i > 2*request.getTrip().size() || j > 2*request.getTrip().size()) {
            return null; // Error here bad call
        }

        Intersection int1;
        Intersection int2;
        if(i == 0) {
            int1 = request.getWarehouse();// Warehouse
        }
        else {
            int1 = i % 2 == 1 ? request.getTrip().get((i-1) / 2).getStartPoint() : request.getTrip().get((i-1) / 2).getDestinationPoint();
        }
        if(j == 0) {
            int2 = request.getWarehouse();// Warehouse
        }
        else {
            int2 = j % 2 == 1 ? request.getTrip().get((j-1) / 2).getStartPoint() : request.getTrip().get((j-1) / 2).getDestinationPoint();
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
                Pair<List<Long>, Double> shortestPath = graphService.computeTheShortestPath(int1, int2);
                //TODO List of Long ids
                request.addDistance(id1, id2, shortestPath.getFirst(), shortestPath.getSecond());
                return shortestPath.getSecond();
            }
        }
    }

    @Override
    public boolean isArc(int i, int j) {
        return i >= 0 && i < getNbVertices() && j >= 0 && j < getNbVertices();
    }

    @Override
    public Integer getPredecessor(int i) {
        return request.getPredecessor(i);
    }
}
