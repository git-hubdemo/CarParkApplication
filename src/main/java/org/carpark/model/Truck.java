package org.carpark.model;

import org.carpark.interfaces.Payable;

public class Truck extends Vehicle implements Payable {

    public Truck(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public String displayInfo(){
        return "Truck: " + make + " " + model + " " + year;
    }

    @Override
    public double calculateFee(long hours) {
        return hours * 5.00; // Trucks pay £5 per hour
    }
}
