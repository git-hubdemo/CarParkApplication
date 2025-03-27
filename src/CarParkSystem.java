import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarParkSystem {
    private static Scanner scanner = new Scanner(System.in);
    private List<CarPark> carParks;

    CarParkSystem() {
        this.carParks = new ArrayList<>();
    }


    public void startSimulation() {
        System.out.println("Welcome to the Car Park Simulation!");
        System.out.println("Select a car park from the list below:");
        CarPark chosenCarPark = this.chooseCarPark();

        System.out.printf("You have selected %s located in %s\n", chosenCarPark.getName(), chosenCarPark.getLocation());

        // Check what type of id reader is used in the chosen car park so that the user can be informed
        if(chosenCarPark.getIdReader() instanceof BarcodeReader){
            System.out.println("The car park you chose uses a barcode system. A barcode will be generated for you.");
        }

        System.out.println("**********Simulation Starts**********");
        // Start the simulation loop.
        for(int i = 0; i < 10; i++){
            chosenCarPark.manageOperations();
            System.out.println(i + ": polling car park components and reacting to state");
            try {
                System.in.read();
            }
            catch (Exception e) {
            }
        }
    }


    public CarPark chooseCarPark() {
        carParks.add(new CarPark("NCP - The City Gates", "Swansea", 5, new BarcodeReader()));
        carParks.add(new CarPark("NCP - Orchard Street", "Swansea", 7, new NumberPlateReader()));

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
