package org.carpark.model;

public class Motorcycle extends Vehicle {
    public Motorcycle(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public String displayInfo(){
        return "Motorcycle: " + make + " " + model + " " + year;
    }
}
