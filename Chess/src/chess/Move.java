package chess;

public class Move {
	
	private int source;
	private int destination;
	private ChessPiece movingPiece;
	private ChessPiece capturedPiece;
	
	
	public Move(int source, int destination, ChessPiece movingPiece, ChessPiece capturedPiece) {
		this.source = source;
		this.destination = destination;
		this.capturedPiece = capturedPiece;
	}
	
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
	
	public String toString() {
		return "Moving " + movingPiece + " from "+ source + " to " + destination + " capturing " + capturedPiece;
	}
}
