package lsystem.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import lsystem.Down;
import lsystem.F;
import lsystem.Grammar;
import lsystem.Instruction;
import lsystem.InstructionList;
import lsystem.LSystem;
import lsystem.Left;
import lsystem.Lf;
import lsystem.Pop;
import lsystem.ProbabilitySwitch;
import lsystem.Push;
import lsystem.Right;
import lsystem.Rule;
import lsystem.RuleHolder;
import lsystem.Up;

public class LSystems {

	private static final LSystems singleton = new LSystems();

	private LSystems() { }

	public static LSystem parse(String start, String rule, int n, double scaleFactor, double r, double defaultAngle) {
		Grammar gram = parseGrammar(new StringReader(rule.replaceAll("\\s", "")));
		return new LSystem(gram, parseRule(new StringReader(start.replaceAll("\\s", "")), gram), n, scaleFactor, r, defaultAngle);
	}

	private static Grammar parseGrammar(StringReader reader) {
		Grammar gram = new Grammar();

		int ch;
		try {
			while ((ch = reader.read()) != -1) {
				String id = "" + (char) ch;
				ch = reader.read();
				if ('-' == (char) ch) {
					ch = reader.read();
					if ('>' == (char) ch) {
						ch = reader.read();
						if ('{' == (char) ch) {
							Rule rule = parseRule(reader, gram);
							gram.add(id, rule);
							ch = reader.read();
							if ('}' != (char) ch) {
								System.err.println("Expected \'}\', but was \'" + (char) ch + "\'.");
								throw new IOException();
							}
						} else {
							System.err.println("Expected \'{\', but was \'" + (char) ch + "\'.");
							throw new IOException();
						}
					} else {
						System.err.println("Expected \'-\', but was \'" + (char) ch + "\'.");
						throw new IOException();
					}
				} else {
					System.err.println("Expected character \'>\' but was \'" + (char) ch + "\'.");
					throw new IOException();
				}
			}

			return gram;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Rule parseRule(StringReader reader, Grammar gram) {
		return new Rule(parseStart(reader, gram));
	}

	private static Instruction parseStart(StringReader reader, Grammar gram) {

		InstructionList ins = new InstructionList(new ArrayList<Instruction>());
		try {
			reader.mark(1);
			int ch = reader.read();
			if (ch != -1) {
				reader.reset();

				ins.add(parseA(reader, gram));
				reader.mark(1);
				ch = reader.read();
				if (ch != -1 && ch != ';' && ch != ':' && ch != '.' && ch != '}' && !Character.isDigit(ch)) {
					reader.reset();
					Instruction i = parseStart(reader, gram);

					if (i instanceof InstructionList && !((InstructionList) i).isEmpty()) ins.add(i);
				} else {
					//System.out.println((char) ch);
					reader.reset();
				}
			}

		} catch (Exception e) {

		}
		return ins;

	}

	class ProbabilitySwitchElement {
		private Instruction ins;
		private double prob;
		public ProbabilitySwitchElement(Instruction ins, double prob) {
			this.ins = ins;
			this.prob = prob;
		}
	}

	private static Instruction parseA(StringReader reader, Grammar gram) throws IOException {
		reader.mark(1);
		char c = (char) reader.read();

		if (c == '{') {
			List<Instruction> ins = new ArrayList<>();
			List<Double> probs = new ArrayList<>();
			do {
				ProbabilitySwitchElement pse = parseB(reader, gram);

				ins.add(pse.ins);
				if (pse.prob != -1) probs.add(pse.prob);

				reader.mark(1);
				c = (char) reader.read();
				//System.out.println(c);
			} while (c == ';');
			reader.reset();

			c = (char) reader.read();
			if (c == '}') {
				return new ProbabilitySwitch(ins, probs);
			} else {
				System.err.println("Expected \'}\' but was \'" + c + "\'.");
				throw new IOException();
			}

		} else {
			reader.reset();
			return parseC(reader, gram);
		}
	}

	private static ProbabilitySwitchElement parseB(StringReader reader, Grammar gram) throws IOException {
		reader.mark(1);
		Character c = (char) reader.read();

		if (c == '.' || Character.isDigit(c)) {
			String floaty = "";

			do {
				floaty += c;
				reader.mark(1);
				c = (char) reader.read();
			} while (c == '.' || Character.isDigit(c));
			//System.out.println(floaty + " " + c);
			reader.reset();

			double prob = Double.parseDouble(floaty);

			c = (char) reader.read();
			if (c == ':') {
				Instruction ins = parseStart(reader, gram);

				return singleton.new ProbabilitySwitchElement(ins, prob);
			} else {
				System.err.println("Expected \':\'.");
				throw new IOException();
			}
		} else {
			reader.reset();
			return singleton.new ProbabilitySwitchElement(parseStart(reader, gram), -1);
		}
	}

	private static Instruction parseC(StringReader reader, Grammar gram) throws IOException {
		reader.mark(1);
		char c = (char) reader.read();

		if (c == '[') {
			return new Push();
		} else if (c == ']') {
			return new Pop();
		} else if (c != 'F' && c != 'f' && c != '+' && c != '-' && c != '^' && c != 'v') {
			RuleHolder rule = new RuleHolder("" + c, gram);

			return rule;

		} else {
			char delim = c;
			reader.mark(1);
			c = (char) reader.read();

			double val = -1;
			String doubleStr = "";
			if (c == '(') {
				c = (char) reader.read();
				while (c == '.' || Character.isDigit(c)) {
					doubleStr += c;
					c = (char) reader.read();
				}

				if (c != ')') {
					System.err.println("Expected \')\'.");
					throw new IOException();
				}

				val = Double.parseDouble(doubleStr);

			} else {
				reader.reset();
			}

			switch(delim) {
			case 'F':
				return new F(val);
			case 'f':
				return new Lf(val);
			case '-':
				return new Right(val);
			case '+':
				return new Left(val);
			case '^':
				return new Up(val < 0 ? 1 : val);
			case 'v':
				return new Down(val < 0 ? 1 : val);
			default:
				System.err.println("Expected \')\'.");
				throw new IOException();
			}


		}

	}

}
