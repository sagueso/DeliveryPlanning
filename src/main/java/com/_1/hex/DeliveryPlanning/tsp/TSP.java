package com._1.hex.DeliveryPlanning.tsp;

public interface TSP {
	/**
	 * Search for a shortest cost hamiltonian circuit in <code>g</code> within <code>timeLimit</code> milliseconds
	 * (returns the best found tour whenever the time limit is reached)
	 * Warning: The computed tour always start from vertex 0
	 * @param timeLimit the time limit in milliseconds
	 * @param g the graph in which the tour must be found
	 */
	public void searchSolution(int timeLimit, Graph g);
	
	/**
	 * @param i
	 * @return the ith visited vertex in the solution computed by <code>searchSolution</code>
	 * (-1 if <code>searcheSolution</code> has not been called yet, or if i smaller than 0 or i greater or equal to g.getNbSommets())
	 */
	public Integer getSolution(int i);
	
	/** 
	 * @return the total cost of the solution computed by <code>searchSolution</code> 
	 * (-1 if <code>searcheSolution</code> has not been called yet).
	 */
	public double getSolutionCost();

}
