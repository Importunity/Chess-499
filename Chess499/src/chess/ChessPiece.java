package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public abstract class ChessPiece {
	
	protected Color color;
	
	public ChessPiece(Color color){
		this.color = color;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}
}
