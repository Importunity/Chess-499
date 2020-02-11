package chess;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AvailableMoves {
	
	private Map<ChessPiece, ArrayList<Move>> availableMoves;
	
	public AvailableMoves() {
		availableMoves = new HashMap<ChessPiece, ArrayList<Move>>();
	}
	
	public void updateAvailableMoves(ChessPiece piece, ArrayList<Move> moves) {
		availableMoves.put(piece, moves);
	}
	
	public void clearAvailableMovesForPiece(ChessPiece piece) {
		if(availableMoves.containsKey(piece)) {
			availableMoves.get(piece).clear();
		}
	}
	
	public boolean isAvailable(Move move) {
		return availableMoves.get(move.getMovingPiece()).contains(move);
	}
}
