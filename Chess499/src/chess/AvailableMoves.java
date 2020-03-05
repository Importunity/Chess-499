package chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
/**
 * 
 * @author Luke Newman
 *
 */
public class AvailableMoves implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
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
	 * @param piece
	 * @return
	 */
	public boolean hasAvailable(ChessPiece piece) {
		if (availableMoves.get(piece) != null) {
			return !availableMoves.get(piece).isEmpty();
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece
	 * @return
	 */
	public ArrayList<Move> getMovesForPiece(ChessPiece piece){
		return availableMoves.get(piece);
	}
	
	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean isAvailable(Move move) {
		return availableMoves.get(move.getMovingPiece()).contains(move);
	}
	
	/**
	 * 
	 * @param chessMove
	 * @param chessPiece
	 * @return
	 */
	public ArrayList<Move> getCommonMovesEqualDestination(Move chessMove){
		ArrayList<Move> movesForDestinationSquare = new ArrayList<Move>();
		
		Set<ChessPiece> pieces = availableMoves.keySet();
		for (ChessPiece piece: pieces) {
			if (piece.matches(chessMove.getMovingPiece()) && piece != chessMove.getMovingPiece()) {
				for(Move move: availableMoves.get(piece)) {
					if (move.getDestination() == chessMove.getDestination() && move.getSource() != chessMove.getSource()) {
						movesForDestinationSquare.add(move);
					}
				}
			}
		}
		
		return movesForDestinationSquare;
	}
}
