package chess;

import java.io.Serializable;

/**
 * 
 * @author Luke Newman
 *
 */
public abstract class ChessPiece implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected Color color;
	
	/**
	 * 
	 * @param color
	 */
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
	
	/**
	 * 
	 * @return
	 */
	public String getNotation() {
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract int getValue();
	
	/**
	 * 
	 * @param piece
	 * @return
	 */
	public boolean matches(ChessPiece piece) {
		return this.toString().equals(piece.toString());
	}
	
	/**
	 * 
	 */
	public String toString() {
		return color == Color.WHITE ? "white" : "black";
	}
}
