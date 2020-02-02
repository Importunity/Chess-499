package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Square {
	
	private int squareNumber;
	private ChessPiece piece;
	
	public Square(int squareNumber) {
		this.squareNumber = squareNumber;
		// Should we create an EmptyPiece instead of dealing with null?????? Vacant.java
		this.piece = null;
	}
	
	public ChessPiece getPiece() {
		return piece;
	}
	
	public void setPiece(ChessPiece piece) {
		this.piece = piece;
	}
	
	public int getSquareNumber() {
		return squareNumber;
	}
}
