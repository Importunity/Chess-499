package chess;

/**
 * 
 * @author Luke Newman
 *
 */
public class Knight extends ChessPiece {
	
	/**
	 * 
	 * @param color
	 */
	public Knight(Color color) {
		super(color);
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Knight";
	}
}
