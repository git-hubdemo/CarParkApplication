public class Barrier {
    private boolean up;

    Barrier() {
        this.up = false; //Barrier starts in the down position
    }

    void raise(){
        this.up = true;
    }

    void lower(){
        this.up = false;
    }

    boolean isUp(){
        return this.up;
    }

}
