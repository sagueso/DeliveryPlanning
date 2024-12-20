package com._1.hex.DeliveryPlanning.utils;

import com._1.hex.DeliveryPlanning.model.StreetMap;
import com._1.hex.DeliveryPlanning.service.GraphService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class XmlParserTest {

    @Mock
    private GraphService graphService; // Mock the dependency

    @InjectMocks
    private XmlParser xmlParser; // Inject mocks into this class

    private String validXmlPath;
    private String invalidXmlPath;

    @BeforeEach
    void setup() {
        validXmlPath = "src/main/java/com/_1/hex/DeliveryPlanning/utils/petitPlan.xml"; // Replace with actual test XML path
        invalidXmlPath = "src/test/resources/invalid-streetmap.xml"; // For negative cases
    }

    @Test
    void testParseValidXml() throws FileNotFoundException, XMLStreamException {
        // Act
        StreetMap result = xmlParser.parse(validXmlPath);

        // Assert
        assertNotNull(result);
        assertEquals(308, result.getIntersections().size()); // Adjust based on your XML
        assertEquals(616, result.getStreets().size()); // Adjust based on your XML
    }

    @Test
    void testParseWithMissingFile() {
        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> xmlParser.parse("nonexistent-file.xml"));
    }
}
