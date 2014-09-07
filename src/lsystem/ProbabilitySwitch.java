package lsystem;

import java.util.List;
import java.util.Random;

public class ProbabilitySwitch extends Instruction {

	private List<Instruction> ins;
	private List<Double> probs;

	public ProbabilitySwitch(List<Instruction> ins, List<Double> probs) {
		this.ins = ins;
		this.probs = probs;
		if (ins.size() != probs.size() + 1) {
			System.err.println("How did I get here?");
		} else {
			for (int i = 1; i < probs.size(); i++) {
				probs.set(i, probs.get(i) + probs.get(i - 1));
			}
			probs.add(1.00);
		}
	}

	private static final Random rand = new Random();

	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		double randy = rand.nextDouble();

		for (int i = 0; i < probs.size(); i++) {
			if (randy < probs.get(i)) {
				ins.get(i).addPath(old, size, n, sys, r, defaultAngle);
				break;
			}
		}
	}

	public String toString() {
		String result = "{";
		int i;
		for (i = 0; i < probs.size() - 1; i++) {
			result += (i == 0 ? probs.get(0) : probs.get(i) - probs.get(i-1)) + ":" + ins.get(i) + ";";
		}

		result += ins.get(i) + "}";

		return result;
	}

	public double countF() {
		double sum = ins.get(0).countF() * probs.get(0);
		for (int i = 1; i < ins.size(); i++) {
			sum += ins.get(i).countF() * (probs.get(i) - probs.get(i - 1));
		}
		return sum;
	}

}
