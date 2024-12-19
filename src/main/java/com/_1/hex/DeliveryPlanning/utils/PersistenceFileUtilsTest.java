package com._1.hex.DeliveryPlanning.utils;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.Route;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceFileUtilsTest {

    private final String TEST_FILE_PATH = "test-route.json";

    @AfterEach
    public void cleanUp() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveRouteToFile() throws IOException {
        // Arrange
        Route route = new Route();
        route.setId(1);
        Intersection intersection_1 = new Intersection(1,100L,100.1,100.2);
        Intersection intersection_2 = new Intersection(2,120L,200.1,200.2);
        List<Intersection> intersections = new ArrayList<Intersection>();
        intersections.add(intersection_1);
        intersections.add(intersection_2);
        route.setIntersections(intersections);
        // Act
        PersistenceFileUtils.saveRouteToFile(route, TEST_FILE_PATH);

        // Assert
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "File should exist after saving the route.");
        System.out.println(file.length());
        assertTrue(file.length() > 0, "File should not be empty.");


        Route routeFromFile = PersistenceFileUtils.readRouteFromFile(TEST_FILE_PATH,1);
        assertEquals(route.getId(), routeFromFile.getId());
        assertEquals(route.getIntersections().size(), routeFromFile.getIntersections().size());
        for (int i = 0; i < route.getIntersections().size(); i++) {
            assertEquals(route.getIntersections().get(i).getId(), routeFromFile.getIntersections().get(i).getId());
        }
    }

    @Test
    public void testReadRouteFromFile_FileNotFound() {
        // Act & Assert
        assertThrows(IOException.class, () -> {
            PersistenceFileUtils.readRouteFromFile("nonexistent-file.json",1);
        }, "Reading a non-existing file should throw IOException.");
    }
}
