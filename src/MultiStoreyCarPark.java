public class MultiStoreyCarPark extends CarPark {
    private int numberOfFloors;
    private boolean hasElevators;
    private boolean hasRampAccess;

    MultiStoreyCarPark(String name, String location, int capacity, Barrier barrier, Sensor sensor, FullSign fullSign, IDReader idReader, int numberOfFloors) {
        super(name, location, capacity, barrier, sensor, fullSign, idReader);
        this.numberOfFloors = numberOfFloors;
    }


    @Override
    public void parkCar(){
    }

    @Override
    public void unparkCar(){}
}
