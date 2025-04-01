package org.carpark.service;

import org.carpark.model.CarPark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarParkSystemTest {

    private CarParkSystem carParkSystem;

    @BeforeEach
    public void setUp() {
        carParkSystem = new CarParkSystem();
    }

    @Test
    void chooseCarPark_ValidInputType_ShouldReturnSelectedCarPark() {
        // Mock scanner to simulate user choosing "0"
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("1"); //simulated user input

        CarPark selectedCarPark = carParkSystem.chooseCarPark(mockScanner);

        // Verify the expected car park was chosen
        assertNotNull(selectedCarPark);
        assertEquals("NCP - Orchard Street", selectedCarPark.getName());
    }

    @Test
    void chooseCarPark_InvalidInputType_ShouldPromptAgainForTheCorrectType(){
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("NaN", "0");

        CarPark selectedCarPark = carParkSystem.chooseCarPark(mockScanner);

        // Verify Scanner was called at least twice (for invalid then valid input)
        verify(mockScanner, atLeast(2)).nextLine();

        //Verify the correct car park is selected after valid input
        assertEquals("NCP - The City Gates", selectedCarPark.getName());
    }

    @Test
    void chooseCarPark_InvalidInput_ShouldPromptAgain(){
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("3", "0");

        CarPark selectedCarPark = carParkSystem.chooseCarPark(mockScanner);

        // Verify Scanner was called at least twice (for invalid then valid input)
        verify(mockScanner, atLeast(2)).nextLine();
        assertEquals("NCP - The City Gates", selectedCarPark.getName());
    }
}
