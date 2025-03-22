public class Sensor {
    private boolean carDetected;

    Sensor(){
        this.carDetected = false;
    }

    void detectCar(){
        this.carDetected = true;
    }

    public void reset(){
        this.carDetected = false;
    }

    public boolean isCarDetected(){
        return this.carDetected;
    }
}
