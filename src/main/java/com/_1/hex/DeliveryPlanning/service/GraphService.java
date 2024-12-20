package com._1.hex.DeliveryPlanning.service;
import java.util.*;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Street;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;


@Service
public class GraphService {

    private final DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
    private Map<Integer, Intersection> intersections;
    public GraphService() {
        // Initialize the graph with weighted edges
        this.graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }

    /**
     * Sets the map to the graph for the shortest path computation with the Dijkstra algorithm and the JGraphT library
     * @param maps the map to add
     */
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
        this.intersections = maps.getIntersections();

    }

    /**
     * Check if a path exists between two intersections
     * @param source the source intersection
     * @param target the target intersection
     * @return true if a path exists, false otherwise
     */
    public boolean doesPathExists(Intersection source, Intersection target){

        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        try {
            GraphPath<Integer, DefaultWeightedEdge> graphPath =  dijkstra.getPath(source.getInternalId(), target.getInternalId());
            return graphPath != null;
        }catch (Exception e){
            return false;
        }

    }

    // Add edge with weight
    /**
     * Add an edge to the graph with a weight
     * @param source the source intersection
     * @param target the target intersection
     * @return Pair of the list of vertices (by long id) composing the shortest route and the distance, null if no path exists
     */
    public Pair<List<Long>, Double> computeTheShortestPath(Intersection source, Intersection target) {
        // Compute the shortest path from vertex 2 to vertex 5
        boolean doesPathExists = doesPathExists(source, target);
        if (!doesPathExists) {
            System.out.println("No path exists for " + source + " and " + target);
            return null;
        }

        DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        GraphPath<Integer, DefaultWeightedEdge> graphPath =  dijkstra.getPath(source.getInternalId(), target.getInternalId());
        //System.out.println(graphPath);
        Double distance = dijkstra.getPathWeight(source.getInternalId(), target.getInternalId());
        List<Long> verticesLongList = new ArrayList<>() ;
        for (Integer i : graphPath.getVertexList()) {
            verticesLongList.add(intersections.get(i).getId());
        }
        //return Pair.of(graphPath.getVertexList(),distance);
        return Pair.of(verticesLongList,distance);
    }
}