package lsystem;

public class Rule extends Instruction {

	private Instruction ins;
	public Rule(Instruction ins) {
		this.ins = ins;
	}
	public void addPath(Move move, double size, int iters, LSystem lSystem,
			double r, double defaultAngle) {
		if (iters == 0) {
			new F().addPath(move, size, iters, lSystem, r, defaultAngle);
		} else {
			Position newPos = new Position(move.getPosition().x + size * Math.cos(Math.toRadians(move.getPosition().angle)), move.getPosition().y + size * Math.sin(Math.toRadians(move.getPosition().angle)), move.getPosition().angle, move.getPosition().col, move.getPosition().stroke);
			
			
			
			ins.addPath(move, size*r, iters-1, lSystem, r, defaultAngle);
			
			move.setPosition(newPos);
		}
	}

	public String toString() {
		return ins.toString();
	}
}
