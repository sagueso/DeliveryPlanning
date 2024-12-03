package com._1.hex.DeliveryPlanning.tsp;

import com._1.hex.DeliveryPlanning.model.Request;

public class StreetGraph implements Graph{

    private Request request;

    public StreetGraph(Request request) {
        this.request = request;
    }

    @Override
    public int getNbVertices() {
        return request.getNbOfIntersections();
    }

    @Override
    public int getCost(int i, int j) {
        return (int) request.getCost(i, j);
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
