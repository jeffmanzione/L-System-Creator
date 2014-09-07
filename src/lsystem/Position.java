package lsystem;

import javafx.scene.paint.Color;

public class Position {
	public final double x, y, angle;
	
	public final double stroke;
	
	public final Color col;
	
	public Position(double x, double y, double angle, Color col, double stroke) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.col = col;
		this.stroke = stroke;
	}
}
