package lsystem;

import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Move {
	private Position pos;
	private Group path;
	
	private Stack<Position> stack;
	
	public Move(Group path, Color col, int stroke) {
		this.path = path;;
		pos = new Position(0, 0, 0, col, stroke);
		stack = new Stack<>();
	}
	
	public Position getPosition() {
		return pos;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public Group getPath() {
		return path;
	}
	
	public void push() {
		stack.push(this.pos);
	}
	
	public void pop() {
		this.pos = stack.pop();
		//path.getElements().add(new MoveTo(pos.x, pos.y));
	}

}
