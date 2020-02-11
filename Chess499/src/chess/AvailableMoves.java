package chess;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * 
 * @author Luke Newman
 *
 */
public class AvailableMoves {
	
	private Map<ChessPiece, ArrayList<Move>> availableMoves;
	
	/**
	 * 
	 */
	public AvailableMoves() {
		availableMoves = new HashMap<ChessPiece, ArrayList<Move>>();
	}
	
	/**
	 * 
	 * @param piece
	 * @param moves
	 */
	public void updateAvailableMoves(ChessPiece piece, ArrayList<Move> moves) {
		availableMoves.put(piece, moves);
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void clearAvailableMovesForPiece(ChessPiece piece) {
		if(availableMoves.containsKey(piece)) {
			availableMoves.get(piece).clear();
		}
	}
	
	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean isAvailable(Move move) {
		return availableMoves.get(move.getMovingPiece()).contains(move);
	}
}
