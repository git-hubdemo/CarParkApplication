import java.util.Random;
import java.util.Scanner;

public abstract class IDReader {
    protected String serialNumber;
    protected Scanner scanner;

    IDReader() {
        Random random = new Random();
        this.serialNumber = "A" + (100000 + random.nextInt(900000));
        this.scanner = new Scanner(System.in);
    }

    abstract String readId();
}
