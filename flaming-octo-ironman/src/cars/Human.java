package cars;

public class Human {
	static int head = 1;
	int arms;
	static int chest = 1;
	int legs;
	int feet;
	String name;
	
	public Human(String name, int arms, int legs, int feet){
		this.arms = arms;
		this.legs = legs;
		this.feet = feet;
		this.name = name;
	
			
	}
	
	public void startWalk(int distance, int speed){
		System.out.println(name + "walking " + distance + " yards " + "at a rate of " + speed);
	}
}