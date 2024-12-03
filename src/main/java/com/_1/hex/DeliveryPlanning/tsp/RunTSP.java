package com._1.hex.DeliveryPlanning.tsp;

import java.util.HashMap;
import java.util.Map;

public class RunTSP {
	public static void main(String[] args) {
		TSP tsp = new TSP1();
		TSP tsp1 = new TSP1();
		for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			Map<Integer, Integer> predecessors = new HashMap<>();
			predecessors.put(1, 2);
			predecessors.put(5, 4);
			System.out.println("Graphs with "+nbVertices+" vertices:");
			CompleteGraph graphNonPred = new CompleteGraph(nbVertices);
			CompleteGraph graphPred = new CompleteGraph(graphNonPred, predecessors);
			//Non Pred
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, graphNonPred);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println();
			startTime = System.currentTimeMillis();
			tsp1.searchSolution(20000, graphPred);
			System.out.print("Solution of cost "+tsp1.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for(int i=0; i<nbVertices; i++)
				System.out.print(tsp1.getSolution(i)+" ");
			System.out.println();
		}
	}

}
