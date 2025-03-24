import java.util.Random;

public abstract class IDReader {
    private String serialNumber;

    IDReader() {
        Random random = new Random();
        this.serialNumber = "A" + (100000 + random.nextInt(999999));
    }

    abstract String readId();
}
