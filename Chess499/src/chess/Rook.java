package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Rook extends ChessPiece {
	
	private static final long serialVersionUID = 1L;
	private int motioned;
	
	/**
	 * 
	 * @param color
	 */
	public Rook(Color color) {
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
	public void unMotion() {
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
	public String getNotation() {
		return "R";
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Rook";
	}
}
