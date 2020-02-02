package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Rook extends ChessPiece {
	
	public Rook(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color + " Rook";
	}
}
