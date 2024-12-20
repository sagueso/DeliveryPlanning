package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.serviceUnitTest;

import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import com._1.hex.DeliveryPlanning.service.XmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class XmlParserTestUnitTest {

    @Mock
    private GraphService graphService = default!; // Mock the dependency

    @InjectMocks
    private XmlParser xmlParser = default!; // Inject mocks into this class

    private String validXmlPath = default!;
    private String invalidXmlPath = default!;

    @BeforeEach
    void setup() {
        validXmlPath = "src/test/resources/valid-streetmap.xml"; // Replace with actual test XML path
        invalidXmlPath = "src/test/resources/invalid-streetmap.xml"; // For negative cases
    }

    @Test
    void testParseValidXml() throws FileNotFoundException, XMLStreamException {
        // Act
        StreetMap result = xmlParser.parse(validXmlPath);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getIntersections().size()); // Adjust based on your XML
        assertEquals(2, result.getStreets().size()); // Adjust based on your XML
    }

    @Test
    void testParseWithMissingFile() {
        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> xmlParser.parse("nonexistent-file.xml"));
    }

    @Test
    void testParseWithMalformedXml() {
        // Act & Assert
        assertThrows(XMLStreamException.class, () -> xmlParser.parse(invalidXmlPath));
    }

    @Test
    void testInteractionsWithGraphService() throws FileNotFoundException, XMLStreamException {
        // Act
        xmlParser.parse(validXmlPath);

        // Assert
        verifyNoInteractions(graphService); // Update this if graphService is used
    }

}
