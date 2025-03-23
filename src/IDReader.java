public abstract class IDReader {
    private int serialNumber;

    IDReader(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    abstract void read();
}
