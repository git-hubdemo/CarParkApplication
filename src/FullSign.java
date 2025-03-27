public class FullSign {
    private boolean lightOn;

    FullSign() {
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
}
