package chess;

/**
 * 
 * @author Luke Newman
 *
 */
public class Pawn extends ChessPiece {
	
	/**
	 * 
	 * @param color
	 */
	public Pawn(Color color) {
		super(color);
	}
	
	/**
	 * 
	 */
	public String toString() {
		return color.toString() + " Pawn";
	}
}
