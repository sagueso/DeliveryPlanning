package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.Courrier;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.model.Warehouse;
import com._1.hex.DeliveryPlanning.service.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicesUnitTest {

    private Services services = default!;
    @Mock
    private StreetMap streetMap = default!;
    @Mock
    private Intersection intersection = default!;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        services = new Services();
    }

    @Test
    public void testCreateWarehouse() {
        // Arrange
        Long intersectionId = 1L;
        String name = "Warehouse1";
        when(streetMap.getIntersectionById(intersectionId)).thenReturn(intersection);

        // Act
        Warehouse warehouse = services.createWarehouse(streetMap, intersectionId, name);

        // Assert
        assertEquals(intersection, warehouse.getIntersection());
        assertEquals(name, warehouse.getName());
    }

    @Test
    public void testCreateCourier() {
        // Arrange
        String name = "Courier1";
        Warehouse warehouse = new Warehouse(intersection, "Warehouse1");

        // Act
        Courrier courier = services.createCourier(name, warehouse);

        // Assert
        assertEquals(name, courier.getName());
        assertEquals(warehouse, courier.getWarehouse());
    }
}
