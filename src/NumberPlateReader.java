public class NumberPlateReader extends IDReader{

    NumberPlateReader(){
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
}
