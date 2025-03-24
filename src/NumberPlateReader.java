public class NumberPlateReader extends IDReader{

    NumberPlateReader(){
        super();
    }

    @Override
    public String readId(){
        System.out.println("Enter your car number plate (e.g AB12 XYZ or CD34LMN)");

        // Validate number plate
        while(true){
            String numberPlate = scanner.nextLine().trim();
            if(numberPlate.matches("^[A-Z]{2}\\d{2}\\s?[A-Z]{3}$")){
                return numberPlate;
            }
            System.out.println("Invalid number plate format! Try again");
        }
    }
}
