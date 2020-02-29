package chess;

import java.io.Serializable;

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
	 */
	public String toString() {
		return super.toString() + "Bishop";
	}
}
