package org.carpark.model;

import org.carpark.reader.IDReader;
import org.carpark.util.InputValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CarParkTest {
    private IDReader mockIdReader; // Mocking IDReader to simulate vehicle ID reading
    private CarPark carPark;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalSystemOut;



    @BeforeEach
    public void setUp() {
        mockIdReader = Mockito.mock(IDReader.class);
        carPark = new CarPark("Test Park", "Test Location", 10, mockIdReader);

        originalSystemOut = System.out; // Saves the original System.out so it can be restored after the test
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void parkVehicle_NewVehicle_VehicleShouldBeAdded(){

        Object vehicleId = "AB12UOK";
        Vehicle vehicle = new Car("Toyota", "Corolla", 2022);

        carPark.parkVehicle(vehicleId, vehicle);

        assertTrue(carPark.getParkedVehicles().containsKey(vehicleId));
        assertEquals(9, carPark.spacesLeft());

        // Check that the vehicle info was printed (from the park message)
        assertTrue(outputStream.toString().trim().contains("with ID " + vehicleId + " successfully parked"));
    }

    @Test
    public void unparkVehicle_VehicleExists_VehicleShouldBeRemoved(){
        Object vehicleId = "AB12UOK";
        Vehicle vehicle = new Car("Toyota", "Corolla", 2022);
        // Start by parking a vehicle
        carPark.parkVehicle(vehicleId, vehicle);

        // Unpark the vehicle
        carPark.unparkVehicle(vehicleId);

        //Confirm that the vehicle was removed from the car park
        assertEquals(0, carPark.getParkedVehicles().size(), "Should be no more vehicles left");
        assertFalse(carPark.getParkedVehicles().containsKey(vehicleId));
        assertEquals(10, carPark.spacesLeft()); // free spaces should be restored
    }

    @Test
    public void unparkVehicle_VehicleDoesNotExist_ShouldShowError(){
        Object vehicleId = "NONEXISTENT123";
        carPark.unparkVehicle(vehicleId);

        //Confirm that the vehicle was not removed and the spaces left is still down by 1
        assertFalse(carPark.getParkedVehicles().containsKey(vehicleId)); // Should still be empty
        assertTrue(outputStream.toString().trim().contains("Exit denied"));
    }

    @Test
    public void unparkVehicle_PayableVehicle_ShouldCalculateFee(){
        Object vehicleId = "OY45UOK";
        Vehicle payableVehicle = new Car("Hyundai", "i30", 2017); //Create an instance of a vehicle type that implements payable

        carPark.parkVehicle(vehicleId, payableVehicle);

        carPark.unparkVehicle(vehicleId);

        assertTrue(outputStream.toString().trim().contains("Parking fee: £"));
    }





    @Test
    public void createVehicle_WhenUserSelectsCar_ShouldReturnCar() {
        // try-with-resource
        try (MockedStatic<InputValidator> mockedInput = Mockito.mockStatic(InputValidator.class)) {
            // Simulate user input for vehicle type selection and details
            mockedInput.when(() -> InputValidator.getNumberInput(Mockito.anyString())).thenReturn(1, 2022);
            mockedInput.when(() -> InputValidator.getStringInput(Mockito.anyString())).thenReturn("Toyota", "Corolla");

            // Call the method
            Vehicle vehicle = carPark.createVehicle();

            // Assertions
            assertNotNull(vehicle);
            assertTrue(vehicle instanceof Car);
            assertEquals("Car: Toyota Corolla 2022", vehicle.displayInfo());
        }
    }

    @Test
    public void createVehicle_WhenUserSelectsMotorcycle_ShouldReturnMotorcycle() {
        try (MockedStatic<InputValidator> mockedInput = Mockito.mockStatic(InputValidator.class)) {
            mockedInput.when(() -> InputValidator.getNumberInput(Mockito.anyString())).thenReturn(2, 2021);
            mockedInput.when(() -> InputValidator.getStringInput(Mockito.anyString())).thenReturn("Honda", "CBR500R");

            Vehicle vehicle = carPark.createVehicle();

            assertNotNull(vehicle);
            assertTrue(vehicle instanceof Motorcycle);
            assertEquals("Motorcycle: Honda CBR500R 2021", vehicle.displayInfo());
        }
    }

    @Test
    public void createVehicle_WhenUserSelectsTruck_ShouldReturnTruck() {
        try (MockedStatic<InputValidator> mockedInput = Mockito.mockStatic(InputValidator.class)) {
            mockedInput.when(() -> InputValidator.getNumberInput(Mockito.anyString())).thenReturn(3, 2020);
            mockedInput.when(() -> InputValidator.getStringInput(Mockito.anyString())).thenReturn("Ford", "F-150");

            Vehicle vehicle = carPark.createVehicle();

            assertNotNull(vehicle);
            assertTrue(vehicle instanceof Truck);
            assertEquals("Truck: Ford F-150 2020", vehicle.displayInfo());
        }
    }

    @Test
    public void createVehicle_WhenUserSelectsInvalidOption_ShouldReturnCar() {
        try (MockedStatic<InputValidator> mockedInput = Mockito.mockStatic(InputValidator.class)) {
            mockedInput.when(() -> InputValidator.getNumberInput(Mockito.anyString())).thenReturn(99, 2018);
            mockedInput.when(() -> InputValidator.getStringInput(Mockito.anyString())).thenReturn("Default", "Car");

            Vehicle vehicle = carPark.createVehicle();

            assertNotNull(vehicle);
            assertTrue(vehicle instanceof Car);  // Default case should return a Car
            assertEquals("Car: Default Car 2018", vehicle.displayInfo());
        }
    }



    @Test
    public void issueTicket_WhenCalledWithValidVehicleId_ShouldReturnTicket() {
        String vehicleId = "OY45UOK";
        carPark.issueTicket(vehicleId);

        // Assert
        assertTrue(carPark.getActiveTickets().containsKey(vehicleId), "Ticket should be stored in activeTickets");
        assertEquals(vehicleId, carPark.getActiveTickets().get(vehicleId).getId(), "Ticket should have the correct vehicleId");
    }

    @Test
    public void issueTicket_WhenCalled_ShouldPrintCorrectTicketDetails() {
        String vehicleId = "XY34ABC";

        carPark.issueTicket(vehicleId);

        // Get the printed output
        String printedOutput = outputStream.toString().trim();

        // Assert that the printed ticket contains essential details
        assertTrue(printedOutput.contains("🎟 PARKING TICKET"), "Ticket header should be present");
        assertTrue(printedOutput.contains("🏢 Car Park: Test Park"), "Car park name should be printed");
        assertTrue(printedOutput.contains("🆔 ID: " + vehicleId), "Vehicle ID should be printed");
        assertTrue(printedOutput.contains("🔹 Keep this ticket safe! 🔹"), "Safety message should be printed");
    }



    @Test
    public void removeTicket_TicketExists_ShouldRemoveTicketAndPrintMessage() {
        String vehicleId = "XY34ABC";
        carPark.issueTicket(vehicleId); // Issue a ticket first to ensure it exists

        carPark.removeTicket(vehicleId);

        assertFalse(carPark.getActiveTickets().containsKey(vehicleId), "Ticket should be removed");
        assertTrue(outputStream.toString().trim().contains("Ticket removed for vehicle with ID: " + vehicleId), "Should print correct removal message");
    }

    @Test
    public void removeTicket_TicketDoesNotExist_ShouldPrintErrorMessage() {
        String nonExistentVehicleId = "ZZ34ABC";
        carPark.removeTicket(nonExistentVehicleId);

        assertTrue(outputStream.toString().trim().contains("No active ticket found for ID: " + nonExistentVehicleId), "Should print error message for missing ticket");
    }






    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalSystemOut); //Clear the output stream after each test
    }
}
