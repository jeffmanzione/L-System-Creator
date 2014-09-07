package lsystem;

public class RuleHolder extends Instruction {

	private String id;
	private Grammar gram;
	
	public RuleHolder(String id, Grammar gram) {
		this.id = id;
		this.gram = gram;
	}
	
	@Override
	public void addPath(Move old, double size, int n, LSystem system, double r,
			double defaultAngle) {
		gram.get(id).addPath(old, size, n, system, r, defaultAngle);
	}

	
	public String toString() {
		return id;
	}
}
