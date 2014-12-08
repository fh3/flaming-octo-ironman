package cars;

public class Chair{
	public boolean Wheels;
	public boolean Backrest;
	public int height;
	public Chair(boolean hasWheels, boolean hasBackrest, int height){
		Wheels = hasWheels;
		Backrest = hasBackrest;
		this.height = height;
		
	}
	public void chairMovement(int distance, int speed){
		System.out.println("moving chair " + distance + " speed of chair " + speed);
	}
	
	

	
		
	

}
