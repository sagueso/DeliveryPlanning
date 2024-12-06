package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.tsp.Graph;
import com._1.hex.DeliveryPlanning.tsp.StreetGraph;
import com._1.hex.DeliveryPlanning.tsp.TSP;
import com._1.hex.DeliveryPlanning.tsp.TSP1;

import java.util.ArrayList;
import java.util.List;

public class TspService {
    TSP tsp = new TSP1();
    Request request = null;
    Graph graph = null;

    public TspService() {
    }

    public List<Long> searchSolution(int time, Request request, GraphService GraphService){
        this.request = request;
        this.graph = new StreetGraph(request, GraphService);
        tsp.searchSolution(time, graph);
        return getSolutions();
    }

    private List<Long> getSolutions(){
        List<Integer> nodes_int = new ArrayList<>();
        List<Long> nodes = new ArrayList<>();
        for (int i=0; i<this.graph.getNbVertices(); i++) {
            nodes_int.add(tsp.getSolution(i));
        }
        for (Integer integer : nodes_int) {
            if (integer == 0) {
                nodes.add(this.request.getWarehouse().getId());
            } else {
                if (integer % 2 == 1) {
                    nodes.add(this.request.getTrip().get(integer / 2).getStartPoint().getId());
                } else {
                    nodes.add(this.request.getTrip().get(integer / 2).getDestinationPoint().getId());
                }
            }
        }
        return nodes;
    }
}
