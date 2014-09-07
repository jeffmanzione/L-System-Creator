package lsystem;


public class Left extends Instruction {

	private double dAngle;
	
	public Left(double dAngle) {
		this.dAngle = dAngle;
	}

	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		
		Position newPos = new Position(old.getPosition().x, old.getPosition().y, old.getPosition().angle - ((dAngle < 0) ? defaultAngle : dAngle), old.getPosition().col, old.getPosition().stroke);
		old.setPosition(newPos);
	}
	
	public String toString() {
		return "+" + ((dAngle != -1.0) ? ("(" + dAngle + ")") : "");
	}


}
