package lsystem;

import java.util.List;

public class InstructionList extends Instruction {
	
	private List<Instruction> ins;
	
	public InstructionList(List<Instruction> ins) {
		this.ins = ins;
	}
	
	public void add(List<Instruction> ins) {
		this.ins.addAll(ins);
	}
	
	public void add(Instruction ins) {
		this.ins.add(ins);
	}
	
	public void addPath(Move old, double size, int n, LSystem sys, double r, double defaultAngle) {
		for (Instruction i : ins) {
			i.addPath(old, size, n, sys, r, defaultAngle);
		}
	}
	
	public String toString() {
		String result = "";
		for (Instruction  i : ins) {
			result += i.toString();
		}
		
		return result;
	}

	
	public boolean isEmpty() {
		return ins.isEmpty();
	}

	public double countF() {
		return ins.stream().map(i -> i.countF()).reduce((x, y) -> x + y).get();
	}
	
}
