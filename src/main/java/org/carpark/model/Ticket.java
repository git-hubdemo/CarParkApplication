package org.carpark.model;

import java.time.LocalDateTime;

public class Ticket <T>{
    private T id;
    private LocalDateTime entryTime;
    private String carParkName;

    public Ticket(T id, String carParkName){
        this.id = id;
        this.entryTime = LocalDateTime.now();
        this.carParkName = carParkName;
    }

    public T getId(){
        return id;
    }

    public LocalDateTime getEntryTime(){
        return entryTime;
    }

    public void printTicket(){
        System.out.print("🔴 Printing ticket");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(700);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        String border = "|=======================================|";
        String separator = "|=======================================|";
        String footer = "|=======================================|";

        System.out.println(border);
        System.out.println("|           🎟 PARKING TICKET           |");
        System.out.println(separator);
        System.out.printf("| 🏢 Car Park: %-20s |\n", carParkName);
        System.out.printf("| 🕒 Entry: %-20s |\n", entryTime);
        System.out.printf("| 🆔 ID: %-20s |\n", id);
        System.out.println(separator);
        System.out.println("|      🔹 Keep this ticket safe! 🔹    |");
        System.out.println(footer);
    }
}
