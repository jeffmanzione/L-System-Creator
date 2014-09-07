package lsystem;

public class Up extends Instruction {

	private double dWidth;
	
	
	public Up(double dWidth) {
		this.dWidth = dWidth;
	}

	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		
		Position newPos = new Position(old.getPosition().x, old.getPosition().y, old.getPosition().angle, old.getPosition().col, old.getPosition().stroke * dWidth);
		old.setPosition(newPos);
	}
	
	public String toString() {
		return "^" + ((dWidth != 1.0) ? ("(" + dWidth + ")") : "");
	}

}
