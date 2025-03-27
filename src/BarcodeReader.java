import java.util.Random;

public class BarcodeReader extends IDReader{

    BarcodeReader() {
        super();
    }

    // Generate a random barcode for entry to simulate a real world barcode reader machine that prints out a ticket with barcode
    public String generateBarcode(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String alphabets = "ABCD";

        // use the alphabets above to randomly generate a 4 character code for the first part of the barcode
        for(int i = 0; i < 4; i++){
            sb.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }
        String firstHalf = sb.toString();
        String secondHalf = String.valueOf(1000 + random.nextInt(9000));

        return firstHalf +"-"+ secondHalf;
    }


    @Override
    public String readId(){
        // Validate barcode format first here before sending it to get checked in the records
        while(true){
            String barcode = scanner.nextLine().trim();
            System.out.println("🔍 Scanning Barcode : " + barcode + "...");
            if(barcode.matches("^[ABCD]{4}-[1-9]\\d{3}$")){
                return barcode;
            }
            System.out.println("Invalid barcode format! Check your ticket and try again");
        }
    }
}
