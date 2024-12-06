package com._1.hex.DeliveryPlanning.tsp;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
@Service
public class TSP1 extends TemplateTSP {
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
