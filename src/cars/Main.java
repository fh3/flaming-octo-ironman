package cars;

public class Main {

	public static void main(String[] args) {
		Car.setEngineCount(1);
		Car f150 = new Car("red", 4, 4);
		f150.driveForward();
		f150.popWheel();
		f150.printColor();
		Car towTruck = new Car("Brown", 5, 6);
		towTruck.driveForward();
		towTruck.driveForward();
		towTruck.driveForward();
		towTruck.printColor();
		f150.driveForward();
		f150.printEngineCount();
		towTruck.printEngineCount();
		Car.setEngineCount(2);
		f150.printEngineCount();
		towTruck.printEngineCount();
		RedCar red = new RedCar();
		red.driveForward();
		red.redCarMethod();
		int size = red.getTrunkSize();
		red.turnLeft();
		red.turnRight();
		GreenCar green = new GreenCar();
		green.driveForward();	//Car is driving forward
		Main.driveCarForward(f150);
		Main.driveCarForward(green);
		f150.driveForward(10);
		Car Jeep = new Car("Yellow", 7, 9);
		Jeep.accelerate(34);
		Jeep.popWheel();
		Jeep.popWheel();
		Computer Dell = new Computer(2);
		Dell.turnOn();
		
		
		/*
		 * ashdjgahjf
		 * 
		 */
		
		/**
		 * asdfadsfsdsaadgdagd
		 */
		
		
		
				
	}
	
	public static void driveCarForward(Car toDriveForward){
		toDriveForward.driveForward();
	}

}
