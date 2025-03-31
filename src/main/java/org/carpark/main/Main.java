package org.carpark.main;

import org.carpark.service.CarParkSystem;

public class Main {
    public static void main(String[] args) {
        CarParkSystem carParkSystem = new CarParkSystem();
        carParkSystem.startSimulation();
    }
}