package lsystem;

public class Pop extends Instruction {

	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		old.pop();
	}
	
	public String toString() {
		return "]";
	}
}
