import java.util.HashSet;

public class CarPark {
    private String name;
    private String location;
    private int capacity;
    private int freeSpaces;
    private Barrier entryBarrier;
    private Barrier exitBarrier;
    private Sensor entrySensor;
    private Sensor exitSensor;
    private FullSign fullSign;
    private IDReader idReader;
    private HashSet<String> parkedCars;


    CarPark(String name, String location, int capacity, IDReader idReader) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.freeSpaces = this.capacity;
        this.entryBarrier = new Barrier();
        this.exitBarrier = new Barrier();
        this.entrySensor = new Sensor();
        this.exitSensor = new Sensor();
        this.fullSign = new FullSign();
        this.idReader = idReader;
        this.parkedCars = new HashSet<>();
    }


    // Manage/process car park operations(car park cycle)
    public void manageOperations(){
        String carAtEntrance = InputValidator.getYesOrNoInput("Is a car at the entrance? (yes/no):"); // Validate that the user chooses either yes or no.

        if(carAtEntrance.equalsIgnoreCase("yes")){
            String vehicleIdentifier; //used to store either number plate or barcode

            entrySensor.detectCar("entrance");

            // Check if there is space in the car park first before trying to let them in
            if(fullSign.isLightOn()){
                System.out.println("The car park is full! entry denied");
                entrySensor.reset("entrance"); //turn entry sensor off
            }
            else{
                // Check if the car park uses a barcode reader so that a barcode can be generated for the user
                if(idReader instanceof BarcodeReader){
                    vehicleIdentifier = ((BarcodeReader) idReader).generateBarcode();
                    System.out.printf("\t\tYour barcode: **%s**\nPlease keep this barcode safe. You will need it to exit.\n",  vehicleIdentifier);
                }else{
                    // car park uses number plate reader - ask user for number plate and validate it.
                    System.out.println("Enter your car number plate (e.g AB12 XYZ or CD34LMN)");
                    vehicleIdentifier = idReader.readId();
                }


                if(parkedCars.add(vehicleIdentifier)){ // This does a check for duplicates and adds the car at the same time if no duplicates are found (HashSet returns true or false)
                    entryBarrier.raise("Entrance");
                    decrementSpaces();
                    System.out.println("Car with id " + vehicleIdentifier + " successfully parked! Remaining spaces: " + spacesLeft());
                    entryBarrier.lower("Entrance");
                    entrySensor.reset("entrance");
                }else{
                    System.out.printf("There is already a car with the id - %s parked!\n", vehicleIdentifier);
                }
            }
        }

        String carAtExit = InputValidator.getYesOrNoInput("Is a car at the exit? (yes/no):");
        if(carAtExit.equalsIgnoreCase("yes")){
            String vehicleIdentifier;

            exitSensor.detectCar("exit");

            System.out.println((idReader instanceof BarcodeReader) ? "Enter your barcode to exit:" : "Enter your number plate to exit:"); // asks for the right id based on the car park's id reader type
            vehicleIdentifier = idReader.readId(); //readId() validates the id first before returning it

            if(parkedCars.remove(vehicleIdentifier)){ // This does a check to see if the vehicle id is found
                exitBarrier.raise("Exit");
                incrementSpaces();
                System.out.println("Car with id " + vehicleIdentifier + " successfully exited the car park! Remaining spaces: " + spacesLeft());
                exitBarrier.lower("Exit");
                exitSensor.reset("exit");
            }else{
                System.out.printf("Exit denied. The car with the id - %s is not in the car park.\n", vehicleIdentifier);
            }
        }

        // Check if the car park is full after doing the entry and exit steps
        if(spacesLeft() <= 0){
            fullSign.switchOn();
        }else{
            fullSign.switchOff();
        }
    }

    void incrementSpaces(){
        freeSpaces++;
    }

    void decrementSpaces(){
        freeSpaces--;
    }

    int spacesLeft(){
        return freeSpaces;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public IDReader getIdReader() {
        return idReader;
    }
}
