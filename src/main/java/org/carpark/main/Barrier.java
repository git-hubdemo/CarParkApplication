package org.carpark.main;

public class Barrier {
    private boolean up;

    public Barrier() {
        this.up = false; //Barrier starts in the down position
    }

    void raise(String location){
        if(!isUp()){
            System.out.print("🔴 " + location + " barrier raising");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(700);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("✅ Barrier fully raised!");
            this.up = true;
        }
    }

    void lower(String location){
        if(isUp()){
            System.out.printf("🔴 " + location + " barrier lowering");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(700);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("✅ Barrier fully lowered!");
            this.up = false;
        }
    }

    boolean isUp(){
        return this.up;
    }

}
