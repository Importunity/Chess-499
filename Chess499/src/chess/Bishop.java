package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Bishop extends ChessPiece {
	
	public Bishop(Color color) {
		super(color);
	}
	
	public String toString() {
		return color.toString() + " Bishop";
	}
}
