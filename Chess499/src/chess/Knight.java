package chess;

/**
 * 
 * @author Luke Newman
 *
 */
public class Knight extends ChessPiece {
	
	private static final long serialVersionUID = 1L;

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
	public String getNotation() {
		return "N";
	}
	
	@Override
	public int getValue() {
		return 300 * color.getBoardPerspective();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Knight";
	}
}
