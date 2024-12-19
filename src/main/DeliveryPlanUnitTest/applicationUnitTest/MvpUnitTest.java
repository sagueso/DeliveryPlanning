package com._1.hex.DeliveryPlanning.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com._1.hex.DeliveryPlanning.model.*;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.Services;
import com._1.hex.DeliveryPlanning.service.XmlParser;

import javax.xml.stream.XMLStreamException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MvpUnitTest {

    private GraphService graphService = default!;
    private XmlParser xmlParser = default!;
    private Services services = default!;
    private Mvp mvp = default!;

    @BeforeEach
    public void setup() {
        graphService = mock(GraphService.class);
        xmlParser = mock(XmlParser.class);
        services = mock(Services.class);
        mvp = new Mvp(graphService, xmlParser, services);
    }

    @Test
    public void testLaunch() throws IOException, XMLStreamException {
        // Arrange
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(xmlPath)).thenReturn(map);

        // Act
        mvp.launch();

        // Assert
        verify(xmlParser).parse(xmlPath);
        verify(graphService).addMap(map);
    }

    @Test
    public void testCreateWarehouse() throws IOException {
        // Arrange
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("Warehouse1", "1");
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(anyString())).thenReturn(map);
        Warehouse warehouse = mock(Warehouse.class);
        when(services.createWarehouse(map, 1L, "Warehouse1")).thenReturn(warehouse);

        // Act
        mvp.launch();

        // Assert
        verify(services).createWarehouse(map, 1L, "Warehouse1");
    }

    @Test
    public void testCreateCourier() throws IOException {
        // Arrange
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("Courier1", "Warehouse1");
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(anyString())).thenReturn(map);
        Warehouse warehouse = mock(Warehouse.class);
        when(services.createCourier("Courier1", warehouse)).thenReturn(mock(Courrier.class));

        // Act
        mvp.launch();

        // Assert
        verify(services).createCourier("Courier1", warehouse);
    }

    @Test
    public void testCreateDelivery() throws IOException {
        // Arrange
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1", "2");
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(anyString())).thenReturn(map);
        Intersection start = mock(Intersection.class);
        Intersection end = mock(Intersection.class);
        when(map.getIntersectionById(1L)).thenReturn(start);
        when(map.getIntersectionById(2L)).thenReturn(end);

        // Act
        mvp.launch();

        // Assert
        verify(map).getIntersectionById(1L);
        verify(map).getIntersectionById(2L);
    }

    @Test
    public void testAssignDelivery() throws IOException {
        // Arrange
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("Courier1", "1");
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(anyString())).thenReturn(map);
        Courrier courrier = mock(Courrier.class);
        Delivery delivery = mock(Delivery.class);
        when(courrier.getName()).thenReturn("Courier1");
        when(delivery.getStartPoint()).thenReturn(mock(Intersection.class));
        when(delivery.getStartPoint().getId()).thenReturn(1L);

        // Act
        mvp.launch();

        // Assert
        verify(courrier).setDelivery(delivery);
    }
}
