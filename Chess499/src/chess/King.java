package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class King extends ChessPiece{
	
	public King(Color color) {
		super.setColor(color);
	}
	
	public String toString() {
		return color.toString() + " King";
	}
}
