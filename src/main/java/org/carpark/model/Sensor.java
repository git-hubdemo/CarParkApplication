package org.carpark.model;

public class Sensor {
    private boolean carDetected;

    public Sensor(){
        this.carDetected = false;
    }

    void detectCar(String location){
        this.carDetected = true;
        System.out.println("Sensor: Vehicle detected at the " + location + "!");
    }

    public void reset(String location){
        this.carDetected = false;
        System.out.println("Sensor: Turning off " + location + " sensor.");
    }

    public boolean isCarPresent(){
        return this.carDetected;
    }
}
