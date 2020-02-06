package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class King extends ChessPiece{
	
	private int motioned;
	
	/**
	 * 
	 * @param color
	 */
	public King(Color color) {
		super(color);
		motioned = 0;
	}
	
	/**
	 * 
	 */
	public void motion() {
		++motioned;
	}
	
	/**
	 * 
	 */
	public void unmotion() {
		--motioned;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMotioned() {
		return motioned;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return color.toString() + " King";
	}
}
