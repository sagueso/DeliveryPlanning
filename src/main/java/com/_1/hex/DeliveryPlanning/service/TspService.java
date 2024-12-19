package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Delivery;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.tsp.Graph;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import com._1.hex.DeliveryPlanning.tsp.TSP;
import com._1.hex.DeliveryPlanning.tsp.TSP1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TspService {
    TSP tsp ;
    List<Integer> nodes = new ArrayList<>();
    List<Double> distances = new ArrayList<>();

    @Autowired
    public TspService(TSP1 tsp) {
        this.tsp = tsp;
    }

    public List<Long> searchSolution(int time, Request request, GraphService GraphService){

        distances = new ArrayList<>();
        double t = 0.0;
        Long lastIntersection = request.getWarehouse().getId();
        Long currentIntersection = request.getWarehouse().getId();
        Graph graph = new StreetGraph(request, GraphService);
        tsp.searchSolution(time, graph);
        List<Integer> nodes_int = new ArrayList<>();
        List<Long> nodes = new ArrayList<>();
        for (int i=0; i<graph.getNbVertices(); i++) {
            Integer node = tsp.getSolution(i);
            if(node == null){
                this.nodes = new ArrayList<>();
                return null;
            } // No solution found
            nodes_int.add(node);
            System.out.println(node);
        }
        this.nodes = nodes_int;

        for (Integer integer : nodes_int) {
            if (integer == 0) {
                nodes.add(request.getWarehouse().getId());
            } else {
                if (integer % 2 == 1) {

                    Delivery currentDelivery = request.getTrip().get((integer-1) / 2);
                    currentIntersection = currentDelivery.getStartPoint().getId();
                    t += request.getDistance(lastIntersection, currentIntersection);
                    currentDelivery.setPickupDuration(t);
                    distances.add(t);

                    nodes.add(currentIntersection);
                    lastIntersection = currentIntersection;
                } else {
                    Delivery currentDelivery = request.getTrip().get((integer-1) / 2);
                    currentIntersection = currentDelivery.getDestinationPoint().getId();
                    Double pickUpArrival = currentDelivery.getPickupDuration();
                    t += request.getDistance(lastIntersection, currentIntersection);
                    currentDelivery.setPickupDuration(t-pickUpArrival);
                    distances.add(t);
                    nodes.add(currentIntersection);
                    lastIntersection = currentIntersection;
                }
            }
        }
        List<Long> listeNodesId = new ArrayList<>();
        int n = nodes.size();
        for (int i=0; i<n-1; i++) {
            listeNodesId.addAll(request.getDistancesRoute(nodes.get(i), nodes.get(i+1)));
        }

        return listeNodesId;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public List<Double> getDistances() {
        return distances;
    }
}
