package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

import com._1.hex.DeliveryPlanning.tsp.SeqIter;
import com._1.hex.DeliveryPlanning.tsp.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SeqIterUnitTest {

    @Test
    public void testHasNextWithNoCandidates() {
        Graph g = new Graph(3);
        Set<Integer> unvisited = new HashSet<>(Arrays.asList(1, 2));
        SeqIter iter = new SeqIter(unvisited, 0, g);
        assertFalse(iter.hasNext());
    }

    @Test
    public void testHasNextWithCandidates() {
        Graph g = new Graph(3);
        g.addArc(0, 1);
        Set<Integer> unvisited = new HashSet<>(Arrays.asList(1, 2));
        SeqIter iter = new SeqIter(unvisited, 0, g);
        assertTrue(iter.hasNext());
    }

    @Test
    public void testNextWithSingleCandidate() {
        Graph g = new Graph(3);
        g.addArc(0, 1);
        Set<Integer> unvisited = new HashSet<>(Arrays.asList(1, 2));
        SeqIter iter = new SeqIter(unvisited, 0, g);
        assertEquals(1, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testNextWithMultipleCandidates() {
        Graph g = new Graph(3);
        g.addArc(0, 1);
        g.addArc(0, 2);
        Set<Integer> unvisited = new HashSet<>(Arrays.asList(1, 2));
        SeqIter iter = new SeqIter(unvisited, 0, g);
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(1, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testRemove() {
        Graph g = new Graph(3);
        Set<Integer> unvisited = new HashSet<>(Arrays.asList(1, 2));
        SeqIter iter = new SeqIter(unvisited, 0, g);
        iter.remove(); // Should do nothing
    }
}
