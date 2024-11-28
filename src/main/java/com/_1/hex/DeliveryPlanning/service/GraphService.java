package com._1.hex.DeliveryPlanning.service;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private final DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;

    public GraphService() {
        // Initialize the graph with weighted edges
        this.graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }


    public void  addNodes(Integer nodeId) {
        // Add vertices
        graph.addVertex(nodeId);

    }
    public void  addEdge(Integer nodeDep, Integer nodeArr, Double edgeLenth) {
        // Add vertices
        DefaultWeightedEdge edge = graph.addEdge(Math.toIntExact(nodeDep), Math.toIntExact(nodeArr));
        graph.setEdgeWeight(edge, edgeLenth);
    }
    public boolean  checkIfNodeExists(Integer node) {
        // Add vertices
        return graph.containsVertex(node);
    }

    public boolean  checkIfEdgeExists(Integer nodeSource,Integer nodeDestination) {
        // Add vertices
        return graph.containsEdge(nodeSource, nodeDestination);
    }


    // Add edge with weight
        public void computeTheShortestPath(Integer source, Integer target) {
        // Compute the shortest path from vertex 2 to vertex 5
        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        System.out.println("Shortest path from source to target: " + dijkstra.getPath(Math.toIntExact(source), Math.toIntExact(target)));
        System.out.println("Shortest path weight: " + dijkstra.getPathWeight(Math.toIntExact(source), Math.toIntExact(target)));
    }
}