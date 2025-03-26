public class Main {
    public static void main(String[] args) {
        CarParkSystem carParkSystem = new CarParkSystem();

        System.out.println("Welcome to the Car Park Simulation!");
        System.out.println("Select a car park from the list below:");
        CarPark chosenCarPark = carParkSystem.chooseCarPark();

        // Check what type of id reader is used in the chosen car park so that the user can be informed
        if(chosenCarPark.getIdReader() instanceof BarcodeReader){
            System.out.println("The car park you chose uses a barcode system. A barcode will be generated for you.");
        }

        System.out.println("Your barcode: **CCBD-5159**\n.Please keep this barcode safe. You will need it to exit.");
    }
}