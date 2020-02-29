package chess;

import java.io.Serializable;

/**
 * 
 * @author Luke Newman
 *
 */
public class Square implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int squareNumber;
	private ChessPiece piece;
	
	/**
	 * 
	 * @param squareNumber
	 */
	public Square(int squareNumber) {
		this.squareNumber = squareNumber;
		// Should we create an EmptyPiece instead of dealing with null?????? Vacant.java
		this.piece = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getPiece() {
		return piece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setPiece(ChessPiece piece) {
		this.piece = piece;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSquareNumber() {
		return squareNumber;
	}
}
