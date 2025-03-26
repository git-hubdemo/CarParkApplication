import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarParkSystem {
    private static Scanner scanner = new Scanner(System.in);
    private List<CarPark> carParks;

    CarParkSystem() {
        this.carParks = new ArrayList<>();
    }


    public CarPark chooseCarPark() {
        carParks.add(new CarPark("NCP - The City Gates", "Swansea", 50, new BarcodeReader()));
        carParks.add(new CarPark("NCP - Orchard Street", "Swansea", 20, new NumberPlateReader()));

        for (int i =0; i<carParks.size(); i++) {
            System.out.println(i + ". " + carParks.get(i).getName());
        }

        while (true) {
            int input = validateNumber(scanner.nextLine()); // Validates the user input to make sure it's a number

            if (input >= 0 && input < carParks.size()) {
                return carParks.get(input);
            }

            System.out.println("Invalid input. Try again.");
        }

    }

    public int validateNumber(String input){

        int number = -1;
        boolean isValidNumber = false;

        while(!isValidNumber){
            try{
                number = Integer.parseInt(input);
                isValidNumber = true;

            }catch (NumberFormatException e){
                System.out.println("Invalid number! try again");
                input = scanner.nextLine();
            }
        }
        return number;

    }
}
