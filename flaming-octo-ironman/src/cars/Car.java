package cars;

public class Car {
	static int engineCount = 1;
	protected String color;
	int size;
	int wheelCount;
	private int trunkSize = 4;
	
	public Car(String c, int s, int w){
		color = c;
		size = s;
		wheelCount = w;
	}
	
	public void driveForward(){
		System.out.println("Driving");
	}
	
	public void driveBackward(){
		
	}
	
	public void popWheel(){
		wheelCount = wheelCount - 1;
		System.out.println(wheelCount);
	}
	
	public void printColor(){
		System.out.println(color);
	}
	
	public void printEngineCount(){
		System.out.println(engineCount);
	}
	public static void setEngineCount(int count){
		engineCount = count;
	}
	public int getTrunkSize(){
		return trunkSize;
	}
	public void turnLeft(){
		System.out.println("turningLeft");
	}
	public void turnRight(){
		System.out.println("turningRight");
		
	}
	public void driveForward(int distance){
		this.driveForward();
	}
	public void accelerate(int amount){
		System.out.println(amount);
	}
}
