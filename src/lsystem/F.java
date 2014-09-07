package lsystem;

import javafx.scene.shape.Line;

public class F extends Instruction {

	private final double scalar;

	public F() {
		this.scalar = 1;
	}

	public F(double scalar) {
		this.scalar = (scalar < 0) ? 1 : scalar;
	}


	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {

		Position newPos = new Position(old.getPosition().x + size * scalar * Math.cos(Math.toRadians(old.getPosition().angle)), old.getPosition().y + size * scalar * Math.sin(Math.toRadians(old.getPosition().angle)), old.getPosition().angle, old.getPosition().col, old.getPosition().stroke);
		Position oldCopy = new Position(old.getPosition().x, old.getPosition().y, old.getPosition().angle, old.getPosition().col, old.getPosition().stroke);

		Line line = new Line(oldCopy.x, oldCopy.y, newPos.x, newPos.y);
		line.setFill(old.getPosition().col);
		line.setStrokeWidth(old.getPosition().stroke);
		old.getPath().getChildren().add(line);

		old.setPosition(newPos);

	}

	public String toString() {
		return "F" + ((scalar != 1.0) ? ("(" + scalar + ")") : "");
	}

	@Override
	public double countF() {
		return scalar;
	}

}
