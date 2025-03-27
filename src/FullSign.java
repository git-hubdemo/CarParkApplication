public class FullSign {
    private boolean lightOn;

    FullSign() {
        lightOn = false;
    }

    void switchOn(){
        lightOn = true;
        System.out.println("🚨 FULL SIGN ON: Car park is full!");
    }

    void switchOff(){
        lightOn = false;
        System.out.println("✅ FULL SIGN OFF: Spaces available.");
    }

    boolean isLightOn() {
        return lightOn;
    }
}
