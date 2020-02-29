package chess;

/**
 * 
 * @author Luke Newman
 *
 */
public class Pawn extends ChessPiece {
	
	private static final long serialVersionUID = 1L;

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
		return super.toString() + "Pawn";
	}
}
