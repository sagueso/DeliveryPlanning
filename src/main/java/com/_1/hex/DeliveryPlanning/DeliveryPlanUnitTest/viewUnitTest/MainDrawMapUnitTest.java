package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.viewUnitTest;

import com._1.hex.DeliveryPlanning.DeliveryPlanningApplication;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.DelevaryService;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import com._1.hex.DeliveryPlanning.view.MainDrawMap;
import com._1.hex.DeliveryPlanning.view.MainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainDrawMapUnitTest {

    @Mock
    private DelevaryService delevaryService;

    @Mock
    private GraphService graphService;

    @Mock
    private MainWindow mainWindow;

    private XmlParser xmlParser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        xmlParser = new XmlParser();
    }

    @Test
    public void testMainDrawMap() throws FileNotFoundException, XMLStreamException {
        // Arrange
        String xmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml";
        StreetMap map = mock(StreetMap.class);
        when(xmlParser.parse(xmlPath)).thenReturn(map);

        // Act
        ApplicationContext context = SpringApplication.run(DeliveryPlanningApplication.class);
        when(context.getBean(DelevaryService.class)).thenReturn(delevaryService);
        when(context.getBean(GraphService.class)).thenReturn(graphService);
        when(context.getBean(MainWindow.class)).thenReturn(mainWindow);

        MainDrawMap.main(new String[]{});

        // Assert
        verify(delevaryService).addStreetMap(map);
        verify(mainWindow).setStreetMap();
        verify(mainWindow).setVisible(true);
    }
}
