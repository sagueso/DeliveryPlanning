package com._1.hex.DeliveryPlanning.service;

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

    @Autowired
    public TspService(TSP1 tsp) {
        this.tsp = tsp;
    }

    public List<Long> searchSolution(int time, Request request, GraphService GraphService){

        Graph graph = new StreetGraph(request, GraphService);
        tsp.searchSolution(time, graph);
        List<Integer> nodes_int = new ArrayList<>();
        List<Long> nodes = new ArrayList<>();
        for (int i=0; i<graph.getNbVertices(); i++) {
            nodes_int.add(tsp.getSolution(i));
        }
        for (Integer integer : nodes_int) {
            if (integer == 0) {
                nodes.add(request.getWarehouse().getId());
            } else {
                if (integer % 2 == 1) {
                    nodes.add(request.getTrip().get(integer / 2).getStartPoint().getId());
                } else {
                    nodes.add(request.getTrip().get(integer / 2).getDestinationPoint().getId());
                }
            }
        }
        return nodes;
    }
}
