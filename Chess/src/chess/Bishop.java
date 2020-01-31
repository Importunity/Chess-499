package chess;

public class Bishop extends ChessPiece {
	
	public Bishop(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color.toString() + " Bishop";
	}
}
