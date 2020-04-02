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
	
	@Override
	public int getValue() {
		return 100 * color.getBoardPerspective();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Pawn";
	}
}
