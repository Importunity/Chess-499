package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Rook extends ChessPiece {
	
	private int startingPosition;
	private int motioned;
	
	/**
	 * 
	 * @param color
	 */
	public Rook(Color color, int startingPosition) {
		super(color);
		this.startingPosition = startingPosition;
		motioned = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getStartingPosition() {
		return startingPosition;
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
	public String toString() {
		return color + " Rook";
	}
}
