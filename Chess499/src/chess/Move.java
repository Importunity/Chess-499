package chess;
/**
 * 
 * @author Luke Newman
 *
 */
public class Move {
	
	private int source;
	private int destination;
	private ChessPiece movingPiece;
	private ChessPiece capturedPiece;
	private boolean pawnPromotion;
	private boolean enPassant;
	private boolean castling;
	
	/**
	 * 
	 * @param source
	 * @param destination
	 * @param movingPiece
	 * @param capturedPiece
	 * @param pawnPromotion
	 * @param enPassant
	 */
	public Move(int source, int destination, ChessPiece movingPiece, ChessPiece capturedPiece, boolean pawnPromotion, boolean enPassant, boolean castling) {
		this.source = source;
		this.destination = destination;
		this.movingPiece = movingPiece;
		this.capturedPiece = capturedPiece;
		this.pawnPromotion = pawnPromotion;
		this.enPassant = enPassant;
		this.castling = castling;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getMovingPiece() {
		return movingPiece;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSource() {
		return source;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDestination() {
		return destination;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPawnPromotion(){
		return pawnPromotion;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEnPassant() {
		return enPassant;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isCastling() {
		return castling;
	}
	
	/**
	 * 
	 */
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		if (obj instanceof Move) {
			Move move = (Move) obj;
			if (move.source == this.source && move.destination == this.destination && 
					move.movingPiece == this.movingPiece && move.capturedPiece == this.capturedPiece) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		
	}
	
	/**
	 * 
	 */
	public String toString() {
		return movingPiece + " from " + source + " to " + destination + " " + (capturedPiece != null? " capturing " + capturedPiece: "") + " " + (enPassant? "ep": "") + " " + (pawnPromotion? "+Q": "") + " " + (castling? "castling": "");
	}
}
