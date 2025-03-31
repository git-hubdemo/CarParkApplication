package org.carpark.main;

import org.carpark.readers.BarcodeReader;
import org.carpark.readers.NumberPlateReader;
import org.carpark.utils.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarParkSystem {
    private static Scanner scanner = new Scanner(System.in);
    private List<CarPark> carParks;

    public CarParkSystem() {
        this.carParks = new ArrayList<>();
    }


    public void startSimulation() {
        System.out.println("Welcome to the Car Park Simulation!");
        System.out.println("Select a car park from the list below:");
        CarPark chosenCarPark = this.chooseCarPark();

        System.out.printf("You have selected %s located in %s\n", chosenCarPark.getName(), chosenCarPark.getLocation());

        // Check what type of id reader is used in the chosen car park so that the user can be informed
        if(chosenCarPark.getIdReader() instanceof BarcodeReader){
            System.out.println("The car park you chose uses a barcode system. Your vehicle ID will be generated for you.");
        }

        System.out.println();
        System.out.println("**********Simulation Starts**********");
        // Start the simulation loop.
        int simulationIterations = 10;
        for(int i = 0; i < simulationIterations; i++){
            chosenCarPark.manageOperations();
            System.out.println("Iteration " + (i + 1) + " of " + simulationIterations + ": polling car park components and reacting to state");

            // Ask the user if they want to continue only when they are not on the second to last iteration
            if(i != simulationIterations - 1){
                String continueChoice = InputValidator.getYesOrNoInput("Do you want to continue to the next car park operation? (yes/no):");
                if (continueChoice.equalsIgnoreCase("no")) {
                    System.out.println("Simulation ended.");
                    break; // Exit the loop
                }
            }
        }
    }


    public CarPark chooseCarPark() {
        carParks.add(new CarPark("NCP - The City Gates", "Swansea", 5, new BarcodeReader()));
        carParks.add(new CarPark("NCP - Orchard Street", "Swansea", 5, new NumberPlateReader()));

        for (int i =0; i<carParks.size(); i++) {
            System.out.println(i + ". " + carParks.get(i).getName());
        }

        while (true) {
            int input = InputValidator.validateNumber(scanner.nextLine()); // Validates the user input to make sure it's a number

            if (input >= 0 && input < carParks.size()) {
                return carParks.get(input);
            }

            System.out.println("Invalid option. Try again.");
        }
    }
}
