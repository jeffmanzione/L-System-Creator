package lsystem;


public class Lf extends Instruction {

	private final double scalar;
	
	public Lf() {
		this.scalar = 1;
	}
	
	public Lf(double scalar) {
		this.scalar = (scalar < 0) ? 1 : scalar;
	}
	
	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		
		Position newPos = new Position(old.getPosition().x + size * scalar * Math.cos(Math.toRadians(old.getPosition().angle)), old.getPosition().y + size * scalar * Math.sin(Math.toRadians(old.getPosition().angle)), old.getPosition().angle, old.getPosition().col, old.getPosition().stroke);
		old.setPosition(newPos);
		//old.getPath().getChildren().add(new MoveTo(newPos.x, newPos.y));
	}
	
	public String toString() {
		return "f" + ((scalar != 1.0) ? ("(" + scalar + ")") : "");
	}

}
