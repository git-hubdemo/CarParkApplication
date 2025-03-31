package org.carpark.readers;

import java.util.Scanner;

public abstract class IDReader<T> {
    protected Scanner scanner;

    public IDReader() {
        this.scanner = new Scanner(System.in);
    }


    public abstract T readId();

    public abstract void displayEntranceOrExitMessage(String location);
}
