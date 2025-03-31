package org.carpark.main;

public class FullSign {
    private boolean lightOn;

    public FullSign() {
        lightOn = false;
    }

    void switchOn(){
        lightOn = true;
    }

    void switchOff(){
        lightOn = false;
    }

    boolean isLightOn() {
        return lightOn;
    }

    void displayFullSign() {
        System.out.println("🚨 FULL SIGN ON: Car park is full!");
    }
}
