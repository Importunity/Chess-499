package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class King extends ChessPiece{
	
	private static final long serialVersionUID = 1L;
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
	public String getNotation() {
		return "K";
	}
	
	
	@Override
	public int getValue() {
		return 0;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return super.toString() + "King";
	}

}
