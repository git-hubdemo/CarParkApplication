package org.carpark.reader;

public class NumberPlateReader extends IDReader<String> {

    public NumberPlateReader(){
        super();
    }

    @Override
    public String readId(){
        // Validate number plate
        while(true){
            String numberPlate = scanner.nextLine().trim();
            System.out.println("🔍 Checking Number Plate : " + numberPlate + "...");
            if(numberPlate.matches("^[A-Z]{2}\\d{2}\\s?[A-Z]{3}$")){
                return numberPlate;
            }
            System.out.println("Invalid number plate format! Try again");
        }
    }

    @Override
    public void displayEntranceOrExitMessage(String location){
        if (location.equalsIgnoreCase("entrance")) {
            System.out.println("Enter your car number plate (e.g AB12 XYZ or CD34LMN) to enter.");
        } else {
            System.out.println("Enter your car number plate to exit.");
        }
    }
}
