package com._1.hex.DeliveryPlanning.tsp;

import com._1.hex.DeliveryPlanning.model.Request;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.TspService;

public class StreetGraph implements Graph{

    private Request request;
    private TspService tspService;

    public StreetGraph(Request request, GraphService graphService) {
        this.request = request;
        this.tspService = new TspService(request, graphService);
    }

    @Override
    public int getNbVertices() {
        return request.getNbOfIntersections();
    }

    @Override
    //getCost return double
    public int getCost(int i, int j) {
        return (int) tspService.getCost(i, j);
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
