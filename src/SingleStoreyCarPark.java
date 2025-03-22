public class SingleStoreyCarPark extends CarPark {
    private boolean hasOutdoorParking;

    SingleStoreyCarPark(String name, String location, int capacity, Barrier barrier, Sensor sensor, FullSign fullSign, IDReader idReader, boolean hasOutdoorParking) {
        super(name, location, capacity, barrier, sensor, fullSign, idReader);
        this.hasOutdoorParking = hasOutdoorParking;
    }

    @Override
    public void parkCar(){
    }

    @Override
    public void unparkCar(){}
}
