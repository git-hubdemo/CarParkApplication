import java.util.HashSet;

public class CarPark {
    private String name;
    private String location;
    private int capacity;
    private int freeSpaces;
    private int occupiedSpaces;
    private Barrier barrier;
    private Sensor sensor;
    private FullSign fullSign;
    private IDReader idReader;
    private HashSet<String> parkedCars;


    CarPark(String name, String location, int capacity, IDReader idReader) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.freeSpaces = this.capacity;
        this.occupiedSpaces = 0;
        this.barrier = new Barrier();
        this.sensor = new Sensor();
        this.fullSign = new FullSign();
        this.idReader = idReader;
        this.parkedCars = new HashSet<>();
    }


    public void parkCar(){};
    public void unparkCar(){};

    void incrementSpaces(){}

    void decrementSpaces(){}

    public boolean isFull(){
        return occupiedSpaces >= capacity;
    }

    int spacesLeft(){
        return capacity - occupiedSpaces;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(int occupiedSpaces) {
        this.occupiedSpaces = occupiedSpaces;
    }

    public Barrier getBarrier() {
        return barrier;
    }

    public void setBarrier(Barrier barrier) {
        this.barrier = barrier;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public FullSign getFullSign() {
        return fullSign;
    }

    public void setFullSign(FullSign fullSign) {
        this.fullSign = fullSign;
    }

    public IDReader getIdReader() {
        return idReader;
    }

    public void setIdReader(IDReader idReader) {
        this.idReader = idReader;
    }
}
