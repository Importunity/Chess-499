package chess;

import java.io.Serializable;

/**
 * Represents the ChessBoard.  It is a two dimensional 8 x 8 array of Square objects 
 * 	in which ChessPieces may be placed.  The King positions for both Color.BLACK and 
 * 	Color.WHITE are kept track of. In all honesty, a one dimensional array of size 
 * 	64 would probably work and be more efficient but this is what I started with.
 * 
 * @author Luke Newman 2020
 *
 */
public class ChessBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Square[][] chessBoard;
	private int whiteKingPosition;
	private int blackKingPosition;
	
	/**
	 * 
	 */
	public ChessBoard() {
		chessBoard = new Square[8][8];
		for (int i = 0; i < 64; i++) {
			chessBoard[i/8][i%8]= new Square(i);
		}
		
	}
	
	/**
	 * 
	 * @param squareNumber
	 * @return
	 */
	public ChessPiece getPieceOnSquare(int squareNumber) {
		if (squareNumber < 0 || squareNumber > 63) {
			return null;
		}
		return chessBoard[squareNumber/8][squareNumber%8].getPiece();
	}
	
	/**
	 * 
	 * @param piece
	 * @param squareNumber
	 */
	public void placePieceOnSquare(ChessPiece piece, int squareNumber) {
		
		// row = squareNumber/8 and column = squareNumber%8
		chessBoard[squareNumber/8][squareNumber%8].setPiece(piece);
		if (piece instanceof King) {
			if (piece.getColor() == Color.BLACK) {
				setBlackKingPosition(squareNumber);
			} else {
				setWhiteKingPosition(squareNumber);
			}
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWhiteKingPosition() {
		return whiteKingPosition;
	}
	
	/**
	 * 
	 * @param whiteKingPosition
	 */
	private void setWhiteKingPosition(int whiteKingPosition) {
		this.whiteKingPosition = whiteKingPosition;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBlackKingPosition() {
		return blackKingPosition;
	}

	/**
	 * 
	 * @param blackKingPosition
	 */
	private void setBlackKingPosition(int blackKingPosition) {
		this.blackKingPosition = blackKingPosition;
	}
	
	/**
	 * 
	 */
	void initialize() {
		for (int i = 0; i < 16; i++) {
			// place rooks
			if (i == 0 || i == 7) {
				// 0, 7, 56, 63 (i and 63 - i) 
				placePieceOnSquare(new Rook(Color.WHITE), i);
				placePieceOnSquare(new Rook(Color.BLACK), 63 - i);
			}
			// place knights
			else if (i == 1 || i == 6) {
				// 1, 6, 57, 62 (i and 63 - i)
				placePieceOnSquare(new Knight(Color.WHITE), i);
				placePieceOnSquare(new Knight(Color.BLACK), 63 - i);
			}
			//place bishops
			else if (i == 2 || i == 5) {
				// 2, 5, 58, 61 (i and 63 - i)
				placePieceOnSquare(new Bishop(Color.WHITE), i);
				placePieceOnSquare(new Bishop(Color.BLACK), 63 - i);
			}
			// place white queen and black king
			else if (i == 3) {
				placePieceOnSquare(new Queen(Color.WHITE), i);
				placePieceOnSquare(new King(Color.BLACK), 63 - i);
			}
			// place white king and black queen
			else if (i == 4) {
				placePieceOnSquare(new King(Color.WHITE), i);
				placePieceOnSquare(new Queen(Color.BLACK), 63 - i);
			}
			// place pawns
			else {
				// 8 - 15, 48 - 55 (i and 63 - i)
				placePieceOnSquare(new Pawn(Color.WHITE), i);
				placePieceOnSquare(new Pawn(Color.BLACK), 63 - i);
			}
			
		}
	}
	
	/**
	 *   I used this method to print out a string representation of the board 
	 *   	before having a GUI.
	 */
	public String toString() {
		int rowCounter = 7;
		String output = "";
		while(rowCounter > -1) {
			for(Square square: chessBoard[rowCounter]) {
				if(square.getPiece() != null) {
					output += String.format("%1$14s", square.getPiece().toString()); 
				}
				else {
					output += String.format("%1$14s", square.getSquareNumber());
				}
			}
			rowCounter--;
			output += "\n";
		}
		return output;
	}
}
