package org.carpark.model;

import org.carpark.interfaces.Payable;

public class Car extends Vehicle implements Payable {

    public Car(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public String displayInfo(){
        return "Car: " + make + " " + model + " " + year;
    }

    @Override
    public double calculateFee(long hours){ // Method from payable interface
        return hours * 2.50; // £2.50 per hour
    }

}
