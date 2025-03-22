public class Barrier {
    private boolean up;

    Barrier() {
        this.up = false; //Barrier starts in the down position
    }

    void raise(){}

    void lower(){}

    boolean isUp(){
        return this.up;
    }

}
