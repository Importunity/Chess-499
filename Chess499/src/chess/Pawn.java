package chess;

public class Pawn extends ChessPiece {
	
	public Pawn(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color.toString() + " Pawn";
	}
}
