package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class XmlParserUnitTest {

    private GraphService graphService = default!;
    private XmlParser xmlParser = default!;

    @BeforeEach
    public void setup() {
        graphService = mock(GraphService.class);
        xmlParser = new XmlParser();
        xmlParser.graphService = graphService;
    }

    @Test
    public void testParseNoeud() throws FileNotFoundException, XMLStreamException {
        // Arrange
        String xmlPath = "path/to/test.xml";
        when(graphService.addNodes(any())).thenReturn(true);

        // Act
        StreetMap result = xmlParser.parse(xmlPath);

        // Assert
        assertEquals(1, result.getIntersections().size());
        verify(graphService, times(1)).addNodes(any());
    }

    @Test
    public void testParseTroncon() throws FileNotFoundException, XMLStreamException {
        // Arrange
        String xmlPath = "path/to/test.xml";
        when(graphService.addEdge(anyInt(), anyInt(), anyDouble())).thenReturn(true);

        // Act
        StreetMap result = xmlParser.parse(xmlPath);

        // Assert
        assertEquals(1, result.getStreets().size());
        verify(graphService, times(1)).addEdge(anyInt(), anyInt(), anyDouble());
    }

}
