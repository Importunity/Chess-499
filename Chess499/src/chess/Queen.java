package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Queen extends ChessPiece {
	
	private static final long serialVersionUID = 1L;
	private boolean promoted;
	
	/**
	 * 
	 * @param color
	 */
	public Queen(Color color) {
		super(color);
		promoted = false;
	}
	
	/**
	 * 
	 * @param color
	 * @param promoted
	 */
	public Queen(Color color, boolean promoted) {
		super(color);
		this.promoted = promoted;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean wasPromoted() {
		return promoted;
	}
	
	/**
	 * 
	 */
	public String getNotation() {
		return "Q";
	}
	
	@Override
	public int getValue() {
		return 900 * color.getBoardPerspective();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "Queen";
	}
}
