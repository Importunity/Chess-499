package chess;

/**
 * 
 * @author Luke Newman
 *
 */
public class Bishop extends ChessPiece{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param color
	 */
	public Bishop(Color color) {
		super(color);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNotation() {
		return "B";
	}
	
	@Override
	public int getValue() {
		return 300 * color.getBoardPerspective();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Bishop";
	}
}
