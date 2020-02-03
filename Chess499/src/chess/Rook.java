package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Rook extends ChessPiece {
	
	/**
	 * 
	 * @param color
	 */
	public Rook(Color color) {
		super(color);
	}
	
	/**
	 * 
	 */
	public String toString() {
		return color + " Rook";
	}
}
