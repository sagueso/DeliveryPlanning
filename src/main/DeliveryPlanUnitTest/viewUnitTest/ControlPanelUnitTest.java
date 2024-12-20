package com._1.hex.DeliveryPlanning.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ControlPanelUnitTest {

    private ControlPanel controlPanel;

    @BeforeEach
    public void setUp() {
        controlPanel = new ControlPanel();
    }

    @Test
    public void testInitialState() {
        assertEquals("Nom du livreur", controlPanel.getNameLabel().getText());
        assertEquals("Click on an intersection to set it as a warehouse", controlPanel.getControlText().getText());
    }

    @Test
    public void testReinitializeControlPanel() {
        controlPanel.reinitializeControlPanel();
        assertEquals("Click on an intersection to set it as a warehouse", controlPanel.getControlText().getText());
    }

    @Test
    public void testUpdateControlText() {
        int currentState = 0;
        currentState = controlPanel.updateControlText(currentState);
        assertEquals(1, currentState);
        assertEquals("Click on an intersection to set it as a start point", controlPanel.getControlText().getText());

        currentState = controlPanel.updateControlText(currentState);
        assertEquals(0, currentState);
        assertEquals("Click on an intersection to set it as a warehouse", controlPanel.getControlText().getText());
    }

    @Test
    public void testPopulateScrollContentPanel() {
        controlPanel.populateScrollContentPanel(Arrays.asList(0, 1, 2), Arrays.asList(0.0, 1.0), Arrays.asList(0.0, 1.0, 2.0));
        assertEquals(3, controlPanel.getScrollContentPanel().getComponentCount());
    }

    @Test
    public void testButtonLabels() {
        assertEquals("Generate Path", controlPanel.getGeneratePathButton().getText());
        assertEquals("SaveRoute", controlPanel.getSaveRoutePathButton().getText());
        assertEquals("LoadRoute", controlPanel.getLoadRoutePathButton().getText());
        assertEquals("Return to Main Menu", controlPanel.getReturnMainButton().getText());
    }
}
