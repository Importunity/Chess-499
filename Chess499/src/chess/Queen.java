package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Queen extends ChessPiece {
	
	public Queen(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color.toString() + " Queen";
	}
}
