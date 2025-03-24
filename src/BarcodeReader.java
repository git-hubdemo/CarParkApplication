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

        // use the alphabets above to randomly generate a 5 character code for the first part of the barcode
        for(int i = 0; i < alphabets.length(); i++){
            sb.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }
        String firstHalf = sb.toString();
        String secondHalf = String.valueOf(1000 + random.nextInt(9999));

        return firstHalf +"-"+ secondHalf;
    }
    @Override
    public String readId(){
        return null;
    }


    public static void main(String[] args) {
        BarcodeReader reader = new BarcodeReader();
        System.out.println(reader.generateBarcode());
    }
}
