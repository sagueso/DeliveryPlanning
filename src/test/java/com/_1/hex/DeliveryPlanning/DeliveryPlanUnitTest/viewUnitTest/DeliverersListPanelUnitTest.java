package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.viewUnitTest;


import com._1.hex.DeliveryPlanning.view.DeliverersListPanel;


import javax.swing.*;
import java.awt.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliverersListPanelUnitTest {

    private DeliverersListPanel deliverersListPanel;

    @BeforeEach
    public void setup() {
        deliverersListPanel = new DeliverersListPanel();
    }

    @Test
    public void testAddPersonButtonNotNull() {
        // Arrange

        // Act
        JButton addPersonButton = deliverersListPanel.getAddPersonButton();

        // Assert
        assertNotNull(addPersonButton);
    }

    @Test
    public void testPanelLayout() {
        // Arrange

        // Act
        LayoutManager layout = deliverersListPanel.getLayout();

        // Assert
        assertTrue(layout instanceof BoxLayout);
    }

    @Test
    public void testPanelPreferredSize() {
        // Arrange

        // Act
        Dimension preferredSize = deliverersListPanel.getPreferredSize();

        // Assert
        assertEquals(new Dimension(600, 1000), preferredSize);
    }

}
