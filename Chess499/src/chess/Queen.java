package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Queen extends ChessPiece {
	
	/**
	 * 
	 * @param color
	 */
	public Queen(Color color) {
		super(color);
	}
	
	/**
	 * 
	 */
	public String toString() {
		return color.toString() + " Queen";
	}
}
