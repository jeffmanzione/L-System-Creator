package lsystem;


public class Push extends Instruction {

	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		old.push();
	}
	public String toString() {
		return "[";
	}
}
