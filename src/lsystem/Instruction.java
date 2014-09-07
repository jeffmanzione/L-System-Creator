package lsystem;


public abstract class Instruction {
	
	public abstract void addPath(Move old, double size, int n, LSystem system, double r, double defaultAngle);
	
	public double countF() {
		return 0;
	}
}
