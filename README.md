## **User Guide – Running the Application**

To run the Car Park Simulation System:

1. Navigate to the `Main` class located at:  
   `src/main/java/org/carpark/main/Main.java`

2. Run the application using your preferred IDE (e.g., IntelliJ, Eclipse) or build tool.

This `Main` class serves as the entry point and will launch the simulation by invoking the `startSimulation()` method from the `CarParkSystem` class.

```java
public class Main {
    public static void main(String[] args) {
        CarParkSystem carParkSystem = new CarParkSystem();
        carParkSystem.startSimulation();
    }
}