public abstract class CarPark {
    protected String name;
    protected String location;
    protected int capacity;
    protected int freeSpaces;
    protected int occupiedSpaces;
    private Barrier barrier;
    private Sensor sensor;
    private FullSign fullSign;
    private IDReader idReader;


    CarPark(String name, String location, int capacity, Barrier barrier, Sensor sensor, FullSign fullSign, IDReader idReader) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.freeSpaces = this.capacity;
        this.occupiedSpaces = 0;
        this.barrier = barrier;
        this.sensor = sensor;
        this.fullSign = fullSign;
        this.idReader = idReader;
    }


    public abstract void parkCar();
    public abstract void unparkCar();

    void incrementSpaces(){}

    void decrementSpaces(){}

    public boolean isFull(){
        return occupiedSpaces >= capacity;
    }

    int spacesLeft(){
        return capacity - occupiedSpaces;
    }
}
