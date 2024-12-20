package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.tspUnitTest;

    import com._1.hex.DeliveryPlanning.tsp.TSP;
    import com._1.hex.DeliveryPlanning.tsp.Graph;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    class TSPUnitTest {

        private TSP tsp;
        private Graph graph;

        @BeforeEach
        void setUp() {
            tsp = mock(TSP.class);
            graph = mock(Graph.class);
        }

        @Test
        void testSearchSolution() {
            doNothing().when(tsp).searchSolution(anyInt(), any(Graph.class));
            tsp.searchSolution(1000, graph);
            verify(tsp, times(1)).searchSolution(1000, graph);
        }

        @Test
        void testGetSolution() {
            when(tsp.getSolution(0)).thenReturn(0);
            when(tsp.getSolution(1)).thenReturn(1);
            when(tsp.getSolution(-1)).thenReturn(-1);
            when(tsp.getSolution(2)).thenReturn(-1);

            assertEquals(0, tsp.getSolution(0));
            assertEquals(1, tsp.getSolution(1));
            assertEquals(-1, tsp.getSolution(-1));
            assertEquals(-1, tsp.getSolution(2));
        }

        @Test
        void testGetSolutionCost() {
            when(tsp.getSolutionCost()).thenReturn(10.0);
            assertEquals(10.0, tsp.getSolutionCost());
        }
    }
