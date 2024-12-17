package com._1.hex.DeliveryPlanning.tsp;

import org.jgrapht.alg.util.Pair;

import java.util.*;

public abstract class TemplateTSP implements TSP {
	private Integer[] bestSol;
	protected Graph g;
	private double bestSolCost;
	private int timeLimit;
	private long startTime;
	private HashMap<Pair,Double> timeBetweenTakeDownAndReturn;
	
	public void searchSolution(int timeLimit, Graph g){
		if (timeLimit <= 0) return;
		startTime = System.currentTimeMillis();	
		this.timeLimit = timeLimit;
		this.g = g;
		bestSol = new Integer[g.getNbVertices()];
		Collection<Integer> unvisited = new ArrayList<Integer>(g.getNbVertices()-1);
		for (int i=1; i<g.getNbVertices(); i++) unvisited.add(i);
		Collection<Integer> visited = new ArrayList<Integer>(g.getNbVertices());
		visited.add(0); // The first visited vertex is 0
		bestSolCost = Integer.MAX_VALUE;
		Map<Integer,Double> costBetweenTakeUpAndReturn = new HashMap<>();
		this.timeBetweenTakeDownAndReturn = new HashMap<>();

		costBetweenTakeUpAndReturn.put(0,0.0);
		Integer max_Time = 300; // 300 seconds is 5 min
		if (branchAndBound(0, unvisited, visited, 0,costBetweenTakeUpAndReturn,max_Time)){
			System.out.println("Solution found");
			System.out.println("time between each 2 nodes: " + this.timeBetweenTakeDownAndReturn);
		}
		else {
			System.out.println("cost exceeded");
		}

	}


	public Integer getSolution(int i){
		if (g != null && i>=0 && i<g.getNbVertices())
			return bestSol[i];
		return -1;
	}
	
	public double getSolutionCost(){
		if (g != null)
			return bestSolCost;
		return -1;
	}
	
	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * @param currentVertex
	 * @param unvisited
	 * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting 
	 * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
	 */
	protected abstract int bound(Integer currentVertex, Collection<Integer> unvisited);
	
	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * @param currentVertex
	 * @param unvisited
	 * @param g
	 * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
	 */
	protected abstract Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g);
	
	/**
	 * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
	 * @param currentVertex the last visited vertex
	 * @param unvisited the set of vertex that have not yet been visited
	 * @param visited the sequence of vertices that have been already visited (including currentVertex)
	 * @param currentCost the cost of the path corresponding to <code>visited</code>
	 */	
	private boolean branchAndBound(int currentVertex, Collection<Integer> unvisited,
								   Collection<Integer> visited, double currentCost, Map<Integer,Double> costBetweenTakeUpAndReturn,Integer maximum_time){

		costBetweenTakeUpAndReturn.put(currentVertex,currentCost);


		if (System.currentTimeMillis() - startTime > timeLimit) return false;
		Integer predecessor = g.getPredecessor(currentVertex);
		if (predecessor!=-1 && !visited.contains(predecessor)) return false;


		if (costBetweenTakeUpAndReturn.containsKey(predecessor)){
			Double predecessorCost = costBetweenTakeUpAndReturn.get(predecessor);
			Double differanceCost = currentCost-predecessorCost;
			if(differanceCost/4.167 > maximum_time)return false;
		}


		if (unvisited.size() == 0){
	    	if (g.isArc(currentVertex,0)){
	    		if (currentCost+g.getCost(currentVertex,0) < bestSolCost){
	    			visited.toArray(bestSol);
	    			bestSolCost = currentCost+g.getCost(currentVertex,0);
					return true;
	    		}
	    	}
	    } else if (currentCost+bound(currentVertex,unvisited) < bestSolCost){
			if (costBetweenTakeUpAndReturn.containsKey(predecessor)) {
				Double predecessorCost = costBetweenTakeUpAndReturn.get(predecessor);
				Double differanceCost = currentCost - predecessorCost;
				Pair<Integer,Integer> pair = Pair.of(predecessor,currentVertex);
				this.timeBetweenTakeDownAndReturn.put(pair,differanceCost/4.167);
			}
	        Iterator<Integer> it = iterator(currentVertex, unvisited, g);
	        boolean test = false;
			while (it.hasNext()){
	        	Integer nextVertex = it.next();
					visited.add(nextVertex);
					unvisited.remove(nextVertex);
					boolean currentTest =  branchAndBound(nextVertex, unvisited, visited,
							currentCost + g.getCost(currentVertex, nextVertex),costBetweenTakeUpAndReturn,maximum_time);
					test = test || currentTest;
					visited.remove(nextVertex);
					unvisited.add(nextVertex);
	    	}
			return test;

		}
		return false;
		}

}

