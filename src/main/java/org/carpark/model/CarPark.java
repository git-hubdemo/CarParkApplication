package org.carpark.model;

import org.carpark.interfaces.Payable;
import org.carpark.reader.BarcodeReader;
import org.carpark.reader.IDReader;
import org.carpark.util.InputValidator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

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
    private IDReader<?> idReader;
    private HashMap<Object, Vehicle> parkedVehicles;
    private HashMap<Object, Ticket<?>> activeTickets;


    public CarPark(String name, String location, int capacity, IDReader idReader) {
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
        this.parkedVehicles = new HashMap<>();
        this.activeTickets = new HashMap<>();
    }


    // Manage/process car park operations(car park cycle)
    public void manageOperations(){
        String carAtEntrance = InputValidator.getYesOrNoInput("Is a vehicle at the entrance? (yes/no):"); // Validate that the user chooses either yes or no.

        // Handle vehicle entrance
        if(carAtEntrance.equalsIgnoreCase("yes")){
            entrySensor.detectCar("entrance");

            // Check if there is space in the car park first before trying to let them in
            if(fullSign.isLightOn()){
                fullSign.displayFullSign();
                entrySensor.reset("entrance"); //turn entry sensor off
            }
            else{
                idReader.displayEntranceOrExitMessage("entrance");
                // If the car park uses a barcode reader, a barcode can be generated for the vehicle. if not read and validate their id
                Object vehicleId = idReader instanceof BarcodeReader ? ((BarcodeReader) idReader).generateBarcode() : idReader.readId(); // Explicit type casting is needed because generateBarcode() is a method exclusive to the org.carpark.readers.BarcodeReader class and is not available in the org.carpark.readers.IDReader class.

                // after getting the vehicleId from the user or generating one, attempt to park the vehicle
                if(!parkedVehicles.containsKey(vehicleId)){ // Check that a vehicle with the id doesn't exist
                    // Create a new vehicle object
                    Vehicle vehicle = createVehicle();

                    parkVehicle(vehicleId, vehicle);
                }else{
                    System.out.println("❌ Duplicate entry detected! Vehicle with ID " + vehicleId + " is already parked.");
                }
            }
        }

        // Handle vehicle exit
        String carAtExit = InputValidator.getYesOrNoInput("Is a vehicle at the exit? (yes/no):");
        if(carAtExit.equalsIgnoreCase("yes")){
            exitSensor.detectCar("exit");
            idReader.displayEntranceOrExitMessage("exit");
            Object vehicleId = idReader.readId();

            if(parkedVehicles.containsKey(vehicleId)){
                unparkVehicle(vehicleId);
            }else{
                System.out.printf("❌ Exit denied. A car with the ID - %s is not in the car park.\n", vehicleId);
            }
        }

        // Check if the car park is full after doing the entry and exit steps
        System.out.println();
        if(spacesLeft() <= 0){
            fullSign.switchOn();
        }else{
            fullSign.switchOff();
        }
    }

    public void parkVehicle(Object vehicleId, Vehicle vehicle){
        entryBarrier.raise("Entrance");
        parkedVehicles.put(vehicleId, vehicle);
        decrementSpaces();
        System.out.println(vehicle.displayInfo() + " with ID " + vehicleId + " successfully parked! Remaining spaces: " + spacesLeft());
        entryBarrier.lower("Entrance");
        entrySensor.reset("entrance");
        // Generate a ticket with the vehicle ID
        System.out.println();
        issueTicket(vehicleId);
        System.out.println();
    }


    public void unparkVehicle(Object vehicleId){
        Vehicle removedVehicle = parkedVehicles.remove(vehicleId);
        //Extra fail-safe check to make sure that an invalid vehicle ID is not passed in
        if(removedVehicle == null){
            System.out.println("Exit denied. A vehicle with the ID - " + vehicleId + " is not in the car park.");
            return; //Exit early to prevent NullPointerException
        }
        exitBarrier.raise("Exit");
        incrementSpaces();
        System.out.println(removedVehicle.displayInfo() + " with ID " + vehicleId + " successfully exited the car park! Remaining spaces: " + spacesLeft());

        // Calculate parking fee if applicable
        if(removedVehicle instanceof Payable && activeTickets.containsKey(vehicleId)){
            LocalDateTime entryTime = activeTickets.get(vehicleId).getEntryTime(); // get the entry time from the ticket issued to the vehicle
            LocalDateTime exitTime = LocalDateTime.now().plusHours(2); //always add 2 hours to the exit time by default (for testing)
            long hoursParked = ChronoUnit.HOURS.between(entryTime, exitTime);
            double fee = ((Payable) removedVehicle).calculateFee(hoursParked);
            System.out.println("💰 Parking fee: £" + fee);
        }

        exitBarrier.lower("Exit");
        exitSensor.reset("exit");
        // Remove the ticket for that vehicle id
        removeTicket(vehicleId);
    }


    public Vehicle createVehicle(){
        System.out.println("Select Vehicle Type: ");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.println("3. Truck");

        int choice = InputValidator.getNumberInput("Enter your choice (1-3):");
        String make = InputValidator.getStringInput("Enter vehicle make:");
        String model = InputValidator.getStringInput("Enter vehicle model:");
        int year = InputValidator.getNumberInput("Enter vehicle year:");

        switch (choice) {
            case 1:
                return new Car(make, model, year);
            case 2:
                return new Motorcycle(make, model, year);
            case 3:
                return new Truck(make, model, year);
            default:
                System.out.println("Invalid choice. Defaulting to Car.");
                return new Car(make, model, year);
        }
    }

    public <T> void issueTicket(T vehicleId){
        Ticket<T> ticket = new Ticket<>(vehicleId, this.name); // The ticket created will be the same type as the vehicle id passed in (Integer, String)
        activeTickets.put(vehicleId, ticket);
        ticket.printTicket();
    }

    public <T> void removeTicket(T vehicleId){
        if(activeTickets.containsKey(vehicleId)){
            activeTickets.remove(vehicleId);
            System.out.println("Ticket removed for vehicle with ID: " + vehicleId);
        }
        else{
            System.out.println("No active ticket found for ID: " + vehicleId);
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

    public HashMap<Object, Vehicle> getParkedVehicles() {
        return parkedVehicles;
    }

    public HashMap<Object, Ticket<?>> getActiveTickets() {
        return activeTickets;
    }
}
