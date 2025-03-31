package org.carpark.main;

import org.carpark.readers.BarcodeReader;
import org.carpark.readers.IDReader;
import org.carpark.utils.InputValidator;

import java.util.HashMap;
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
    private IDReader<?> idReader;
    private HashSet<Object> parkedCars;
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
        this.parkedCars = new HashSet<>();
        this.activeTickets = new HashMap<>();
    }


    // Manage/process car park operations(car park cycle)
    public void manageOperations(){
        String carAtEntrance = InputValidator.getYesOrNoInput("Is a car at the entrance? (yes/no):"); // Validate that the user chooses either yes or no.

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
                Object vehicleIdentifier = idReader instanceof BarcodeReader ? ((BarcodeReader) idReader).generateBarcode() : idReader.readId(); // Explicit type casting is needed because generateBarcode() is a method exclusive to the org.carpark.readers.BarcodeReader class and is not available in the org.carpark.readers.IDReader class.

                if(parkedCars.add(vehicleIdentifier)){ // This does a check for duplicates and adds the car at the same time if no duplicates are found (HashSet returns true or false)
                    entryBarrier.raise("Entrance");
                    decrementSpaces();
                    System.out.println("Car with ID " + vehicleIdentifier + " successfully parked! Remaining spaces: " + spacesLeft());
                    entryBarrier.lower("Entrance");
                    entrySensor.reset("entrance");
                    // Generate a ticket with the vehicle ID
                    System.out.println();
                    issueTicket(vehicleIdentifier);
                    System.out.println();
                }else{
                    System.out.println("Duplicate entry detected! Car with ID " + vehicleIdentifier + " is already parked.");
                }
            }
        }

        String carAtExit = InputValidator.getYesOrNoInput("Is a car at the exit? (yes/no):");
        if(carAtExit.equalsIgnoreCase("yes")){
            exitSensor.detectCar("exit");
            idReader.displayEntranceOrExitMessage("exit"); // asks for the right id based on the car park's id reader type
            Object vehicleIdentifier = idReader.readId(); //readId() validates the id first before returning it

            if(parkedCars.remove(vehicleIdentifier)){ // This does a check to see if the vehicle id is found
                exitBarrier.raise("Exit");
                incrementSpaces();
                System.out.println("Car with id " + vehicleIdentifier + " successfully exited the car park! Remaining spaces: " + spacesLeft());
                exitBarrier.lower("Exit");
                exitSensor.reset("exit");
                // Remove the ticket for that vehicle id
                removeTicket(vehicleIdentifier);
            }else{
                System.out.printf("❌ Exit denied. A car with the ID - %s is not in the car park.\n", vehicleIdentifier);
            }
        }

        // Check if the car park is full after doing the entry and exit steps
        System.out.println();
        if(spacesLeft() <= 0){
            fullSign.switchOn();
        }else{
            fullSign.switchOff();
        }

        System.out.println(parkedCars);
        System.out.println(activeTickets);
    }

    public <T> void issueTicket(T vehicleId){
        Ticket<T> ticket = new Ticket<>(vehicleId, this.name); // The ticket created will be the same type as the vehicle id passed in (Integer, String)
        activeTickets.put(vehicleId, ticket);
        ticket.printTicket();
    }

    public <T> void removeTicket(T vehicleId){
        if(activeTickets.containsKey(vehicleId)){
            activeTickets.remove(vehicleId);
            System.out.println("Ticket removed for car with ID: " + vehicleId);
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
}
