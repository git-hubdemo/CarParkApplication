package org.carpark.reader;
import org.carpark.util.InputValidator;

import java.util.Random;

public class BarcodeReader extends IDReader<Integer>{

    public BarcodeReader() {
        super();
    }

    // Generate a random barcode for entry to simulate a real world barcode reader machine that prints out a ticket with barcode
    public int generateBarcode(){
        Random random = new Random();
        return 1000 + (random.nextInt(9000));
    }


    @Override
    public Integer readId(){
        // Validate barcode format first here before sending it to get checked in the records
        String input = scanner.nextLine().trim();
        System.out.println("🔍 Checking Barcode : " + input + "...");
        int barcode = InputValidator.validateNumber(input);
        return barcode;
    }

    @Override
    public void displayEntranceOrExitMessage(String location){
        if(location.equalsIgnoreCase("entrance")) {
            System.out.println("\t\t✅ A barcode has been generated for your vehicle.\n\t\tIt will be printed on your ticket. Please keep your ticket safe.");
        }
        else{
            System.out.println("Please enter the barcode on your ticket to exit>>.");
        }
    }
}
