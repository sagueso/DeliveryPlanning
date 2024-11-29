package com._1.hex.DeliveryPlanning.service;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
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


    public void  addMap(StreetMap maps) {
        // Add vertices
        for(Integer i : maps.getIntersectionsIds()){
            graph.addVertex(i);
        }
        // Add edges
        for(Street street: maps.getStreets()){
            DefaultWeightedEdge edge = graph.addEdge(street.getOrigin().getInternalId(), street.getDestination().getInternalId());
            graph.setEdgeWeight(edge, street.getLength());
        }

    }

    // Add edge with weight
        public void computeTheShortestPath(Intersection source, Intersection target) {
        // Compute the shortest path from vertex 2 to vertex 5
        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        System.out.println("Shortest path from source to target: " + dijkstra.getPath(source.getInternalId(), target.getInternalId()));
        System.out.println("Shortest path weight: " + dijkstra.getPathWeight(source.getInternalId(), Math.toIntExact(target.getInternalId())));
    }
}