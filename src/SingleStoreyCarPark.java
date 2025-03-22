public class SingleStoreyCarPark extends CarPark {
    private boolean hasOutdoorParking;

    SingleStoreyCarPark(String name, String location, int capacity, IDReader idReader, boolean hasOutdoorParking) {
        super(name, location, capacity, idReader);
        this.hasOutdoorParking = hasOutdoorParking;
    }

    @Override
    public void parkCar(){
    }

    @Override
    public void unparkCar(){}
}
