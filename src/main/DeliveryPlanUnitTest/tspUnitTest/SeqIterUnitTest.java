package com._1.hex.DeliveryPlanning.tsp;

import java.util.Collection;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SeqIterUnitTest {

    private SeqIter seqIter;
    private Graph mockGraph;

    @BeforeEach
    public void setup() {
        mockGraph = new Graph();
        // Initialize mockGraph with necessary data
    }

    @Test
    public void testHasNext_WithCandidates() {
        // Arrange
        Collection<Integer> unvisited = List.of(1, 2, 3);
        int currentVertex = 0;
        seqIter = new SeqIter(unvisited, currentVertex, mockGraph);

        // Act
        boolean result = seqIter.hasNext();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasNext_NoCandidates() {
        // Arrange
        Collection<Integer> unvisited = List.of();
        int currentVertex = 0;
        seqIter = new SeqIter(unvisited, currentVertex, mockGraph);

        // Act
        boolean result = seqIter.hasNext();

        // Assert
        assertFalse(result);
    }

    @Test
    public void testNext() {
        // Arrange
        Collection<Integer> unvisited = List.of(1, 2, 3);
        int currentVertex = 0;
        seqIter = new SeqIter(unvisited, currentVertex, mockGraph);

        // Act
        Integer nextVertex = seqIter.next();

        // Assert
        assertNotNull(nextVertex);
        assertTrue(unvisited.contains(nextVertex));
    }

    @Test
    public void testRemove() {
        // Arrange
        Collection<Integer> unvisited = List.of(1, 2, 3);
        int currentVertex = 0;
        seqIter = new SeqIter(unvisited, currentVertex, mockGraph);

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> seqIter.remove());
    }
}
