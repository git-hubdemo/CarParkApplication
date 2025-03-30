import java.util.Random;
import java.util.Scanner;

public abstract class IDReader<T> {
    protected Scanner scanner;

    IDReader() {
        this.scanner = new Scanner(System.in);
    }


    abstract T readId();

    abstract void displayEntranceOrExitMessage(String location);
}
