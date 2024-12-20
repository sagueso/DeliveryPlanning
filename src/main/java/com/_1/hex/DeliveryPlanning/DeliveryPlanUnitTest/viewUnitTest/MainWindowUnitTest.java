package com._1.hex.DeliveryPlanning.DeliveryPlanUnitTest.viewUnitTest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import com._1.hex.DeliveryPlanning.model.Courrier;
import com._1.hex.DeliveryPlanning.model.Intersection;
import com._1.hex.DeliveryPlanning.service.DelevaryService;
import com._1.hex.DeliveryPlanning.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainWindowUnitTest {

    @Mock
    private DelevaryService delevaryService;

    @InjectMocks
    private MainWindow mainWindow;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mainWindow = new MainWindow(delevaryService);
    }

    @Test
    public void testChangePanel01() {
        // Arrange

        // Act
        mainWindow.changePanel01();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testChangePanel10() {
        // Arrange

        // Act
        mainWindow.changePanel10();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testAddReturnMainButtonCallback() {
        // Arrange

        // Act
        mainWindow.addReturnMainButtonCallback();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testAddCourrierButtonCallback() {
        // Arrange

        // Act
        mainWindow.addCourrierButtonCallback();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testSetStreetMap() {
        // Arrange

        // Act
        mainWindow.setStreetMap();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testDrawPointsWhenIntersectionsIsClicked() {
        // Arrange

        // Act
        mainWindow.drawPointsWhenIntersectionsIsClicked();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testAddGenerateRouteButtonCallback() {
        // Arrange

        // Act
        mainWindow.addGenerateRouteButtonCallback();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testAddSaveRoutePathButtonCallback() {
        // Arrange

        // Act
        mainWindow.addSaveRoutePathButtonCallback();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testAddLoadRoutePathButtonCallback() {
        // Arrange

        // Act
        mainWindow.addLoadRoutePathButtonCallback();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testLoadRouteFromFile() {
        // Arrange

        // Act
        mainWindow.loadRouteFromFile();

        // Assert
        // Add assertions to verify the behavior
    }

    @Test
    public void testGenerateRoute() {
        // Arrange

        // Act
        mainWindow.generateRoute();

        // Assert
        // Add assertions to verify the behavior
    }
}
