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
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Bishop";
	}
}
