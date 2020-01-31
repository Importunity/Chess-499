package chess;

public class Knight extends ChessPiece {
	
	public Knight(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color.toString() + " Knight";
	}
}
