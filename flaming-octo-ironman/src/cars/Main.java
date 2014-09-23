package cars;

public class Main {

	public static void main(String[] args) {
		Car.setEngineCount(0);
		Car f150 = new Car("red", 4, 4);
		f150.driveForward();
		f150.popWheel();
		f150.printColor();
		Car towTruck = new Car("Brown", 5, 6);
		towTruck.driveForward();
		towTruck.driveForward();
		towTruck.driveForward();
		towTruck.printColor();
		Car smallCar = new Car("yellow", 2, 4);
		smallCar.printEngineCount();
		Car.setEngineCount(2);
		f150.printEngineCount();
		towTruck.printEngineCount();
		smallCar.printEngineCount();
		RedCar red = new RedCar();
		red.printColor();
	}

}
