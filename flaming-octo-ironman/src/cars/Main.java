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
		driveCarForward(f150);
		
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
