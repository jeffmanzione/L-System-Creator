package lsystem;


import javafx.scene.Group;
import javafx.scene.paint.Color;

public class LSystem extends Group {
	private final Grammar gram;
	private final Rule start;
	
	public final double r, angle, size;
	public final int iters;
	
	public LSystem(Grammar gram, Rule start, int iters, double size, 
			double r, double defaultAngle) {
		
		super();
		this.gram = gram;
		this.start = start;
		this.r = r;
		this.iters = iters;
		this.angle = defaultAngle;
		this.size = size;
	
		Move move = new Move(this, Color.BLACK, 1);
		this.start.addPath(move, size, iters, this, r, defaultAngle);
		//this.setStroke(Color.BLACK);
		//this.setStrokeWidth(1);
		
	}
	
	public String getInitiator() {
		return start.toString();
	}
	
	String result;
	public String getRules() {
		result = "";
		gram.get().forEach(r -> { result += r + "&"; } );
		
		return result;
	}
	
	public String toString() {
		return "[ \"" + start.toString() + "\", \"" + gram.toString() + "\" ]";
	}
	
	/*protected Group getPath(Move start, int n, double size, double r, 
			double defaultAngle, boolean drawOld) {
		Move move = new Move(this, Color.BLACK, 1);
		this.start.addPath(move, size, iters, this, r, defaultAngle, drawOld);
		return start.getPath();
	}*/
	
	public double n() {
		return start.countF();
	}

}
