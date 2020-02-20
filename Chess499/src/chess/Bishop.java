package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Bishop extends ChessPiece {
	
	/**
	 * 
	 * @param color
	 */
	public Bishop(Color color) {
		super(color);
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Bishop";
	}
}
